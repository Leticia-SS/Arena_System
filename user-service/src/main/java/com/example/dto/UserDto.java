package com.example.dto;

import java.time.LocalDateTime;

public record UserDto(
        String name,
        String email,
        LocalDateTime createdAt,
        ScoreDto score
) {}
