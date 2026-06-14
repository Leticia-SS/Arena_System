package com.example.controller;

import com.example.dto.ScoreDto;
import com.example.dto.UpdateScoreRequest;
import com.example.dto.UserDto;
import com.example.dto.UserRankingDto;
import com.example.model.Score;
import com.example.model.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        User u = userService.getById(id);
        Score s = u.getScore();
        ScoreDto scoreDto = new ScoreDto(
                s.getWins(),
                s.getLosses(),
                s.getTotalMatches(),
                s.getPoints()
        );
        UserDto userDto = new UserDto(
                u.getName(),
                u.getEmail(),
                u.getCreatedAt(),
                scoreDto
        );

        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("{id}/score")
    public ResponseEntity<ScoreDto> getScore(@PathVariable Long id) {
        ScoreDto scoreDto = userService.getScoreByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(scoreDto);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserRankingDto>> getRanking() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getRanking());
    }

    @GetMapping("/ranking/compare")
    public ResponseEntity<List<UserRankingDto>> compare(
            @RequestParam Long user1,
            @RequestParam Long user2
    ) {
        return ResponseEntity.ok(
                userService.compareUsers(user1, user2)
        );
    }

    @PostMapping("{id}/score")
    public ResponseEntity<Void> updateScore(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScoreRequest request
            ) {
        userService.updateScore(id, request.victory());
        return ResponseEntity.noContent().build();
    }



}
