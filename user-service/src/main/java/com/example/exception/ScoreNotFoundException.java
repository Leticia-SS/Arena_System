package com.example.exception;

public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(Long userId) {
        super("Score não encontrado para usuário id: " + userId);
    }
}