package com.irctc.dto;

public record LoginRequest(
        String username,
        String password
) {
}
