package com.example.arena_service.model;

import com.example.arena_service.model.enums.TypeEnum;
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
    private String name;
    private List<TypeEnum> types;
    private Integer health;
    private Integer mana;
    private Integer sanity;
    private Integer speed;
    private String powerType;
    private String characterClass;
    private String media;
    private Ability ability;
    private List<Move> moveset;
    private List<Trait> traits;
    private String finisher;
    private String spectacle;


}
