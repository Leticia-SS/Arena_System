package com.example.arena_service.exception;

public class CharacterAlreadyExistsException extends RuntimeException {
    public CharacterAlreadyExistsException(String name) {
        super("Personagem já existe com o nome: " + name);
    }
}
