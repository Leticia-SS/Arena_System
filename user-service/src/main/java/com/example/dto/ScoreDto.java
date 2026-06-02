package com.example.dto;

public record ScoreDto(
        Integer wins,
        Integer losses,
        Integer totalMatches,
        Integer points
) {}
