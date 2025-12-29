package com.irctc.controllers;

import com.irctc.config.security.JwtHelper;
import com.irctc.dto.ErrorResponse;
import com.irctc.dto.JwtResponse;
import com.irctc.dto.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtHelper jwtHelper;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    public ResponseEntity<JwtResponse> login(
            @RequestBody LoginRequest loginRequest
    ){

        try {
            UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password());
            this.authenticationManager.authenticate(authentication);
            UserDetails userDetails= userDetailsService.loadUserByUsername(loginRequest.username());
            String token=this.jwtHelper.generateToken(userDetails);

            JwtResponse jwtResponse=new JwtResponse(
                    token,
                    userDetails.getUsername()
            );

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);


        }catch(BadCredentialsException e){
            System.out.println("Invalid Credentials");
            ErrorResponse errorResponse=new ErrorResponse(
                    "Invalid credentials",
                    "403",
                    false
            );


        }
    }

}
