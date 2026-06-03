package com.example.arena_service.dto;

import com.example.arena_service.model.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterSummaryResponseDto {
    private String id;
    private String name;
    private List<TypeEnum> types;
    private String characterClass;
    private Integer health;
    private Integer mana;
    private Integer sanity;
    private Integer speed;
    private String media;

}
