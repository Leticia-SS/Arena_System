package com.example.arena_service.exception;

public class NotPlayersTurnException extends RuntimeException {
    public NotPlayersTurnException(String id) {
        super("Não é o turno do jogador " + id);
    }
}
