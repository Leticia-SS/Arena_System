package com.example.arena_service.controller;

import com.example.arena_service.dto.CharacterRequestDto;
import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.service.CharacterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;
    private static final Logger logger = LoggerFactory.getLogger("REQUEST_LOGGER");

    @GetMapping
    public ResponseEntity<List<CharacterResponseDto>> getAllCharacters(){
        logger.info("GET /characters | buscando todos os personagens");
        return ResponseEntity.ok(characterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDto> getCharacterById(@PathVariable String id){
        logger.info("GET /characters/{} | buscando personagem por id", id);
        return ResponseEntity.ok(characterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDto> createCharacter(@RequestBody @Valid CharacterRequestDto character){
        logger.info("POST /characters | criando personagem: {}", character.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(characterService.create(character));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CharacterResponseDto> updateCharacter(@PathVariable String id, @RequestBody @Valid CharacterRequestDto character){
        logger.info("PATCH /characters/{} | atualizando personagem", id);
        return ResponseEntity.ok(characterService.update(id, character));
    }

}
