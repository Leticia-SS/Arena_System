package com.example.arena_service.exception;

public class CharacterNotFoundException extends RuntimeException{
    public CharacterNotFoundException(String id){
        super("Personagem não encontrado com id: " + id);
    }
}
