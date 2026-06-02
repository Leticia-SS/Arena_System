package com.example.arena_service.service;

import com.example.arena_service.dto.CharacterRequestDto;
import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.exception.CharacterNotFoundException;
import com.example.arena_service.model.Character;
import com.example.arena_service.repository.ICharacterRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CharacterService {
    private final ICharacterRepository characterRepository;

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public Character findById(String charId) {
        return characterRepository.findById(charId)
                .orElseThrow(() -> new CharacterNotFoundException(charId));
    }

//    public Character create(CharacterRequestDto dto){
//
//    }



}
