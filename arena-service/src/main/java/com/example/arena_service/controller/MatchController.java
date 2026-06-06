package com.example.arena_service.controller;

import com.example.arena_service.dto.MatchRequestDto;
import com.example.arena_service.dto.MatchResponseDto;
import com.example.arena_service.dto.TurnRequestDto;
import com.example.arena_service.dto.TurnResponseDto;
import com.example.arena_service.service.MatchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatchById(@PathVariable String id){
        return ResponseEntity.ok(matchService.findById(id));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<MatchResponseDto>> getMatchByUserId(@PathVariable String userId){
        return ResponseEntity.ok(matchService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<MatchResponseDto> createMatch(@RequestBody @Valid MatchRequestDto match){
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.create(match));
    }

    @PostMapping("/{id}/turn")
    public ResponseEntity<TurnResponseDto> executeTurn(@PathVariable String id, @RequestBody @Valid TurnRequestDto turn){
        return ResponseEntity.ok(matchService.playTurn(id, turn));
    }


}
