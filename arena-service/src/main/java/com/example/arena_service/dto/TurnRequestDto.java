package com.example.arena_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnRequestDto {
    private String attackerId;
    private String moveId;


}
