package com.example.arena_service.controller;

import com.example.arena_service.dto.CharacterRequestDto;
import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.model.Character;
import com.example.arena_service.service.CharacterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/characters")
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<List<CharacterResponseDto>> getAllCharacters(){
        return ResponseEntity.ok(characterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterResponseDto> getCharacterById(@PathVariable String id){
        return ResponseEntity.ok(characterService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDto> createCharacter(@RequestBody CharacterRequestDto character){
        return ResponseEntity.status(HttpStatus.CREATED).body(characterService.create(character));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CharacterResponseDto> updateCharacter(@PathVariable String id, @RequestBody CharacterRequestDto character){
        return ResponseEntity.ok(characterService.update(id, character));
    }

}
