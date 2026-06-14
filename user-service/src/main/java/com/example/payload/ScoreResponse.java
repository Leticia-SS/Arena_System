package com.example.payload;

public record ScoreResponse(
        Integer wins,
        Integer losses,
        Integer totalMatches,
        Integer points
) {}