package com.example.arena_service.service;

import com.example.arena_service.dto.CharacterRequestDto;
import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.exception.CharacterAlreadyExistsException;
import com.example.arena_service.exception.CharacterNotFoundException;
import com.example.arena_service.model.Character;
import com.example.arena_service.repository.ICharacterRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CharacterService {
    private final ICharacterRepository characterRepository;
    private static final Logger logger = LoggerFactory.getLogger(CharacterService.class);

    @Cacheable(value = "characters", key = "'all'")
    public List<CharacterResponseDto> findAll() {
        logger.info("Cache miss | buscando todos os personagens no banco");
        return characterRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Cacheable(value = "characters", key = "#charId")
    public CharacterResponseDto findById(String charId) {
        logger.info("Cache miss | buscando personagem {} no banco", charId);
        Character character = characterRepository.findById(charId)
                .orElseThrow(() -> new CharacterNotFoundException(charId));
        return toResponse(character);
    }

    @CacheEvict(value = "characters", key = "'all'")
    public CharacterResponseDto create(CharacterRequestDto dto){
        characterRepository.findByName(dto.getName()).ifPresent(c->{
            throw new CharacterAlreadyExistsException(dto.getName());
        });
        Character character = new Character(
                null,
                dto.getName(),
                dto.getTypes(),
                dto.getHealth(),
                dto.getMana(),
                dto.getSanity(),
                dto.getSpeed(),
                dto.getPowerType(),
                dto.getCharacterClass(),
                dto.getMedia(),
                dto.getAbility(),
                dto.getMoveset(),
                dto.getTraits(),
                dto.getFinisher(),
                dto.getSpectacle()
        );
        return toResponse(characterRepository.save(character));
    }


    @Caching(evict = {
            @CacheEvict(value = "characters", key = "#charId"),
            @CacheEvict(value = "characters", key = "'all'")})
    public CharacterResponseDto update(String charId, CharacterRequestDto dto) {
        Character character = characterRepository.findById(charId)
                .orElseThrow(() -> new CharacterNotFoundException(charId));

        if (dto.getName() != null) character.setName(dto.getName());
        if (dto.getTypes() != null) character.setTypes(dto.getTypes());
        if (dto.getHealth() != null) character.setHealth(dto.getHealth());
        if (dto.getMana() != null) character.setMana(dto.getMana());
        if (dto.getSanity() != null) character.setSanity(dto.getSanity());
        if (dto.getSpeed() != null) character.setSpeed(dto.getSpeed());
        if (dto.getPowerType() != null) character.setPowerType(dto.getPowerType());
        if (dto.getCharacterClass() != null) character.setCharacterClass(dto.getCharacterClass());
        if (dto.getMedia() != null) character.setMedia(dto.getMedia());
        if (dto.getAbility() != null) character.setAbility(dto.getAbility());
        if (dto.getMoveset() != null) character.setMoveset(dto.getMoveset());
        if (dto.getTraits() != null) character.setTraits(dto.getTraits());
        if (dto.getFinisher() != null) character.setFinisher(dto.getFinisher());
        if (dto.getSpectacle() != null) character.setSpectacle(dto.getSpectacle());

        return toResponse(characterRepository.save(character));
    }

    private CharacterResponseDto toResponse(Character character) {
        return new CharacterResponseDto(
                character.getId(),
                character.getName(),
                character.getTypes(),
                character.getHealth(),
                character.getMana(),
                character.getSanity(),
                character.getSpeed(),
                character.getPowerType(),
                character.getCharacterClass(),
                character.getMedia(),
                character.getAbility(),
                character.getMoveset(),
                character.getTraits(),
                character.getFinisher(),
                character.getSpectacle()
        );
    }



}
