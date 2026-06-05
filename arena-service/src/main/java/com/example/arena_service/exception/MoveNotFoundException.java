package com.example.arena_service.exception;

public class MoveNotFoundException extends RuntimeException {
    public MoveNotFoundException(String id) {
        super("Move não encontrado com id: " + id);
    }
}
