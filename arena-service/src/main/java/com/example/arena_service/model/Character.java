package com.example.arena_service.model;

import com.example.arena_service.model.enums.TypeEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "characters")
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    @Id
    private String id;
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    @NotNull(message = "Tipos são obrigatórios")
    @Size(min = 1, max = 2, message = "Personagem deve ter entre 1 e 2 tipos")
    private List<TypeEnum> types;
    @NotNull(message = "Vida é obrigatória")
    @Min(value = 1, message = "Vida deve ser maior que 0")
    private Integer health;
    @NotNull(message = "Mana é obrigatória")
    private Integer mana;
    @NotNull(message = "Sanidade é obrigatória")
    private Integer sanity;
    @NotNull(message = "Velocidade é obrigatória")
    private Integer speed;
    private String powerType;
    private String characterClass;
    private String media;
    private Ability ability;
    @NotNull(message = "Moveset é obrigatório")
    @Size(min = 13, max = 13, message = "Personagem deve ter exatamente 13 moves")
    private List<Move> moveset;
    private List<Trait> traits;
    private String finisher;
    private String spectacle;


}
