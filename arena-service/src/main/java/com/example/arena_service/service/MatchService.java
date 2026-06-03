package com.example.arena_service.service;

import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.dto.MatchRequestDto;
import com.example.arena_service.dto.MatchResponseDto;
import com.example.arena_service.exception.CharacterAlreadyExistsException;
import com.example.arena_service.exception.CharacterNotFoundException;
import com.example.arena_service.exception.MatchNotFoundException;
import com.example.arena_service.model.Character;
import com.example.arena_service.model.CharacterState;
import com.example.arena_service.model.Match;
import com.example.arena_service.model.enums.StatusEnum;
import com.example.arena_service.repository.ICharacterRepository;
import com.example.arena_service.repository.IMatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final IMatchRepository matchRepository;
    private final ICharacterRepository characterRepository;

    public MatchResponseDto findById(String id){
        Match match = matchRepository.findById(id)
                .orElseThrow(()-> new MatchNotFoundException(id));
        return toResponse(match);
    }

    public List<MatchResponseDto> findByUserId(String userId){
        return matchRepository.findByPlayer1IdOrPlayer2Id(userId, userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public MatchResponseDto create(MatchRequestDto dto){
        Character char1 = characterRepository.findById(dto.getChar1Id())
                .orElseThrow(()-> new CharacterNotFoundException(dto.getChar1Id()));
        Character char2 = characterRepository.findById(dto.getChar2Id())
                .orElseThrow(()-> new CharacterNotFoundException(dto.getChar2Id()));

        CharacterState char1State = new CharacterState(char1.getHealth(),char1.getMana(),char1.getSanity());
        CharacterState char2State = new CharacterState(char2.getHealth(),char2.getMana(),char2.getSanity());
        String firstTurn = char1.getSpeed() >= char2.getSpeed() ? dto.getPlayer1Id() : dto.getPlayer2Id();

        Match match = new Match();
        match.setPlayer1Id(dto.getPlayer1Id());
        match.setPlayer2Id(dto.getPlayer2Id());
        match.setChar1Id(char1.getId());
        match.setChar2Id(char2.getId());
        match.setStatus(StatusEnum.ONGOING);
        match.setCurrentTurn(firstTurn);
        match.setTurnsLog(new ArrayList<>());
        match.setChar1State(char1State);
        match.setChar2State(char2State);
        match.setWinnerId(null);
        match.setScoreUpdated(false);

        return toResponse(matchRepository.save(match));
    }

    private MatchResponseDto toResponse(Match match) {
        return new MatchResponseDto(
                match.getId(),
                match.getStatus(),
                match.getCurrentTurn(),
                match.getChar1State(),
                match.getChar2State(),
                match.getWinnerId(),
                match.isScoreUpdated()
        );
    }


}
