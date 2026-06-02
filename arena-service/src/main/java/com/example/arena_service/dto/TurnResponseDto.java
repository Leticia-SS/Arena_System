package com.example.arena_service.dto;

import com.example.arena_service.model.CharacterState;
import com.example.arena_service.model.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnResponseDto {
    private Integer turnNumber;
    private Integer diceRoll;
    private Integer activationValue;
    private boolean activated;
    private Integer damageDealt;
    private String moveUsed;
    private CharacterState char1State;
    private CharacterState char2State;
    private StatusEnum status;
    private String nextTurn;
    private String winnerId;
    private boolean scoreUpdated;


}
