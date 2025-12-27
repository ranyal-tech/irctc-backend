package com.irctc.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {
    private Long id;
    @NotBlank(message="Station name is required")
    private String name;
    @NotBlank(message="Station code is required")
    @Size(min=3,max=10,message="Size must be from 3 to 10 characters")
    private String code;
    @NotBlank(message="Station city is required")
    private String city;
    @NotBlank(message="Station state is required")
    private String state;
}
