package com.example.arena_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MatchRequestDto {
    private String player1Id;
    private String player2Id;
    private String char1Id;
    private String char2Id;

}
