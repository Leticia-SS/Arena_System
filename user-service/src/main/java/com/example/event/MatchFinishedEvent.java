package com.example.event;

public record MatchFinishedEvent(
        String matchId,
        String winnerId,
        String loserId
){}
