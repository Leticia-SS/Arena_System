package com.example.arena_service.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String id) {
        super("Partida não encontrada com id: " + id);
    }
}
