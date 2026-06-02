package com.example.arena_service.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Turn {
    private Integer turnNumber;
    private String attackerId;
    private String moveUsed;
    private Integer diceRoll;
    private boolean activated;
    private Integer damageDealt;
    private CharacterState char1StateAfter;
    private CharacterState char2StateAfter;

}
