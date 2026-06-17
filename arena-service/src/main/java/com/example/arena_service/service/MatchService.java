package com.example.arena_service.service;

import com.example.arena_service.client.UserServiceClient;
import com.example.arena_service.dto.*;
import com.example.arena_service.exception.*;
import com.example.arena_service.model.*;
import com.example.arena_service.model.Character;
import com.example.arena_service.model.enums.StatusEnum;
import com.example.arena_service.repository.ICharacterRepository;
import com.example.arena_service.repository.IMatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class MatchService {
    private final IMatchRepository matchRepository;
    private final ICharacterRepository characterRepository;
    private final UserServiceClient userServiceClient;

    public MatchResponseDto findById(String id){
        Match match = matchRepository.findById(id)
                .orElseThrow(()-> new MatchNotFoundException(id));
        return toResponse(match);
    }

//    public List<MatchResponseDto> findByUserId(String userId){
//        return matchRepository.findByPlayer1IdOrPlayer2Id(userId, userId).stream()
//                .map(this::toResponse)
//                .toList();
//    }

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

    public TurnResponseDto playTurn(String matchId, TurnRequestDto dto){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(()-> new MatchNotFoundException(matchId));

        if (match.getStatus() == StatusEnum.FINISHED) {
            throw new MatchFinishedException(matchId);
        }
        if (!match.getCurrentTurn().equals(dto.getAttackerId())){
            throw new NotPlayersTurnException(dto.getAttackerId());
        }

        boolean isPlayer1 = dto.getAttackerId().equals(match.getPlayer1Id());
        String attackerCharId = isPlayer1 ? match.getChar1Id() : match.getChar2Id();
        String defenderPlayerid = isPlayer1 ? match.getPlayer2Id() : match.getPlayer1Id();

        Character attackerChar = characterRepository.findById(attackerCharId)
                .orElseThrow(()-> new CharacterNotFoundException(attackerCharId));

        Move move = attackerChar.getMoveset().stream()
                .filter(m -> m.getName().equals(dto.getMoveId()))
                .findFirst()
                .orElseThrow(()-> new MoveNotFoundException(dto.getMoveId()));

        int diceRoll = (int) (Math.random() * 20) + 1;
        boolean activated = diceRoll >= move.getActivationValue();
        int damageDealt = 0;

        if (activated) {
            int damage = rollDice(move.getDamageDice());
            if (isPlayer1) {
                int newHealth = match.getChar2State().getHealth() - damage;
                match.getChar2State().setHealth(Math.max(newHealth, 0));
            } else {
                int newHealth = match.getChar1State().getHealth() - damage;
                match.getChar1State().setHealth(Math.max(newHealth, 0));
            }
            damageDealt = damage;
        }
        match.setCurrentTurn(defenderPlayerid);

        Turn turn = new Turn();
        turn.setTurnNumber(match.getTurnsLog().size() +1);
        turn.setAttackerId(dto.getAttackerId());
        turn.setMoveUsed(move.getName());
        turn.setDiceRoll(diceRoll);
        turn.setActivated(activated);
        turn.setDamageDealt(damageDealt);
        turn.setChar1StateAfter(new CharacterState(
                match.getChar1State().getHealth(),
                match.getChar1State().getMana(),
                match.getChar1State().getSanity()
        ));
        turn.setChar2StateAfter(new CharacterState(
                match.getChar2State().getHealth(),
                match.getChar2State().getMana(),
                match.getChar2State().getSanity()
        ));

        String winnerId = null;
        if (match.getChar1State().getHealth() <= 0){
            winnerId = match.getPlayer2Id();
            match.setStatus(StatusEnum.FINISHED);
            match.setWinnerId(winnerId);
        } else if (match.getChar2State().getHealth() <= 0) {
            winnerId = match.getPlayer1Id();
            match.setStatus(StatusEnum.FINISHED);
            match.setWinnerId(winnerId);
        }

        if (match.getStatus() == StatusEnum.FINISHED){
            String loserId = winnerId.equals(match.getPlayer1Id()) ? match.getPlayer1Id() : match.getPlayer2Id();
            boolean updated = userServiceClient.updateScore(winnerId, loserId);
            match.setScoreUpdated(updated);
        }

        match.getTurnsLog().add(turn);
        matchRepository.save(match);
        return toTurnResponse(turn, match, move, diceRoll, damageDealt, activated, winnerId);
    }

    private int rollDice(String damageDice) {
        if (damageDice == null || damageDice.isBlank()) return 0;
        String[] parts = damageDice.split("d");
        int quantity = Integer.parseInt(parts[0]);
        int sides = Integer.parseInt(parts[1]);
        return IntStream.range(0, quantity)
                .map(i->(int)(Math.random() * sides) + 1)
                .sum();
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

    private TurnResponseDto toTurnResponse(Turn turn, Match match, Move move, int diceRoll, int damageDealt, boolean activated, String winnerId) {
        return new TurnResponseDto(
                turn.getTurnNumber(),
                diceRoll,
                move.getActivationValue(),
                activated,
                damageDealt,
                move.getName(),
                match.getChar1State(),
                match.getChar2State(),
                match.getStatus(),
                match.getCurrentTurn(),
                winnerId,
                false
        );
    }


}
