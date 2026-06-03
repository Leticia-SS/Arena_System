package com.example.arena_service.service;

import com.example.arena_service.dto.CharacterResponseDto;
import com.example.arena_service.dto.MatchResponseDto;
import com.example.arena_service.exception.CharacterAlreadyExistsException;
import com.example.arena_service.exception.MatchNotFoundException;
import com.example.arena_service.model.Character;
import com.example.arena_service.model.Match;
import com.example.arena_service.repository.IMatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MatchService {
    private final IMatchRepository matchRepository;

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
