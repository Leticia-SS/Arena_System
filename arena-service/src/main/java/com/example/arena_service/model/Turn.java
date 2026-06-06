package com.example.arena_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Turn {
    private Integer turnNumber;
    @NotBlank(message = "Atacante é obrigatório")
    private String attackerId;
    @NotBlank(message = "Move é obrigatório")
    private String moveUsed;
    private Integer diceRoll;
    private boolean activated;
    private Integer damageDealt;
    private CharacterState char1StateAfter;
    private CharacterState char2StateAfter;

}
