package com.example.arena_service.dto;

import com.example.arena_service.model.CharacterState;
import com.example.arena_service.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRepsonseDto {
    private String matchId;
    private StatusEnum status;
    private String currentTurn;
    private CharacterState char1State;
    private CharacterState char2State;
    private String winnerId;
    private boolean scoreUpdated;


}
