package com.example.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateScoreRequest (
        @NotNull
        boolean victory
){}
