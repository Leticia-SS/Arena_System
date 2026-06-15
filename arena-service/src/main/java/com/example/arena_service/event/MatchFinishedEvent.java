package com.example.arena_service.event;

public record MatchFinishedEvent(
        String matchId,
        String winnerId,
        String loserId
){}
