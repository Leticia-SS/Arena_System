package com.example.arena_service.dto;

import com.example.arena_service.model.enums.MatchResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreUpdateRequestDto {
    private MatchResultEnum result;
}
