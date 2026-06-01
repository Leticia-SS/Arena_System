package com.example.arena_service.model;

import com.example.arena_service.model.enums.MoveClassEnum;
import com.example.arena_service.model.enums.PhysOrSpecEnum;
import com.example.arena_service.model.enums.RangeEnum;
import com.example.arena_service.model.enums.TypeEnum;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {
    private String name;
    private Integer activationValue;
    private RangeEnum range;
    private PhysOrSpecEnum physOrSpec;
    private MoveClassEnum moveClass;
    private TypeEnum type;
    private String description;
    private Integer damage;
}
