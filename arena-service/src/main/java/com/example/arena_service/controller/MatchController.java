package com.example.arena_service.controller;

import com.example.arena_service.dto.MatchRequestDto;
import com.example.arena_service.dto.MatchResponseDto;
import com.example.arena_service.dto.TurnRequestDto;
import com.example.arena_service.dto.TurnResponseDto;
import com.example.arena_service.service.MatchService;
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
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;
    private static final Logger logger = LoggerFactory.getLogger("REQUEST_LOGGER");


    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatchById(@PathVariable String id){
        logger.info("GET /matches/{} | correlationId: {}", id, org.slf4j.MDC.get("correlationId"));
        return ResponseEntity.ok(matchService.findById(id));
    }

//    @GetMapping("/users/{userId}")
//    public ResponseEntity<List<MatchResponseDto>> getMatchByUserId(@PathVariable String userId){
//        return ResponseEntity.ok(matchService.findByUserId(userId));
//    }

    @PostMapping
    public ResponseEntity<MatchResponseDto> createMatch(@RequestBody @Valid MatchRequestDto match){
        logger.info("POST /matches | player1Id: {} | player2Id: {}", match.getPlayer1Id(), match.getPlayer2Id());
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(match));
    }

    @PostMapping("/{id}/turn")
    public ResponseEntity<TurnResponseDto> executeTurn(@PathVariable String id, @RequestBody @Valid TurnRequestDto turn){
        logger.info("POST /matches/{}/turn | attackerId: {}", id, turn.getAttackerId());
        return ResponseEntity.ok(matchService.playTurn(id, turn));
    }


}
