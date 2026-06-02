package com.example.arena_service.dto;

import com.example.arena_service.model.Ability;
import com.example.arena_service.model.Move;
import com.example.arena_service.model.Trait;
import com.example.arena_service.model.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CharacterRequestDto {
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
