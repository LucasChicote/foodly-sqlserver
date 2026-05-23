package com.foodly.api.dto;

public record LoginResponseDTO(
        String token,
        String nome,
        String email,
        String role
) {}
