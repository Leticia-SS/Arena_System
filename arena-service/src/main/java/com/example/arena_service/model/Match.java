package com.example.arena_service.model;

import com.example.arena_service.model.enums.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "matches")
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    @Id
    private String id;
    @NotBlank(message = "Player 1 é obrigatório")
    private String player1Id;
    @NotBlank(message = "Player 2 é obrigatório")
    private String player2Id;
    @NotBlank(message= "Personagem 1 é obrigatório")
    private String char1Id;
    @NotBlank(message= "Personagem 2 é obrigatório")
    private String char2Id;
    private StatusEnum status;
    private List<Turn> turnsLog;
    private String currentTurn;
    private CharacterState char1State;
    private CharacterState char2State;
    private String winnerId;
    private boolean scoreUpdated;


}
