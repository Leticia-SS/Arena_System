package com.example.arena_service.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterState {
    private Integer health;
    private Integer mana;
    private Integer sanity;
}
