package com.example.arena_service.exception;

public class MatchFinishedException extends RuntimeException {
    public MatchFinishedException(String matchId) {
        super("Partida já finalizada: " + matchId);
    }
}
