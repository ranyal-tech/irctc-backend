package com.irctc.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {


    private Long id;
    @Size(min=3,max=20, message="Invalid length")
    private String number;

    @NotEmpty(message="Train name is required")
    @Pattern(regexp = "^[A-Z0-9]+(?:[ -][A-Z0-9]+)*$", message="Only Uppercase letters,spaces and hyphens are allowed")
    private String name;

    private Integer totalDistance;

    private StationDto sourceStation;
    private StationDto destinationStation;

}
