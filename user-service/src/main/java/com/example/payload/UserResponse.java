package com.example.payload;

import java.time.LocalDateTime;

public record UserResponse(
        String name,
        String email,
        LocalDateTime createdAt,
        ScoreResponse score
) {}