package com.irctc.exceptions;

import com.irctc.dto.PagedResponse;
import com.irctc.dto.StationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import com.irctc.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String,String> errorResponse=new HashMap<>();
        
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error->
                {
                    String message=error.getDefaultMessage();
                    String field=((FieldError)error).getField();
                    errorResponse.put(field,message);
                }
                );
        
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        String message=sqlIntegrityConstraintViolationException.getMessage().contains("Duplicate entry")?"You are entering the data that are already in database.":sqlIntegrityConstraintViolationException.getMessage();

        ErrorResponse errorResponse=new ErrorResponse(message,"400",false);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

}
