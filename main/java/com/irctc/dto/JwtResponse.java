package com.irctc.dto;

public record JwtResponse(
        String token,
        String username
) {
}
