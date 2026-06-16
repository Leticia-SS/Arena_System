package com.example.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String id) {
        super("Usuário não encontrado com id: " + id);
    }
}
