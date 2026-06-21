package com.example.controller;

import com.example.dto.*;
import com.example.model.Score;
import com.example.model.User;
import com.example.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger("REQUEST_LOGGER");

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        logger.info("GET /users | action=get_all_users");
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        logger.info("GET /users/{} | buscando usuário", id);
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
    public ResponseEntity<ScoreDto> getScore(@PathVariable String id) {
        logger.info("GET /users/{} | action=get_score", id);
        ScoreDto scoreDto = userService.getScoreByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(scoreDto);
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserRankingDto>> getRanking() {
        logger.info("GET /users/ranking | action=get_ranking");
        return ResponseEntity.status(HttpStatus.OK).body(userService.getRanking());
    }

    @GetMapping("/ranking/compare")
    public ResponseEntity<List<UserRankingDto>> compare(
            @RequestParam String user1,
            @RequestParam String user2
    ) {
        logger.info("GET /users/ranking/compare | user1={} user2={}", user1, user2);
        return ResponseEntity.ok(
                userService.compareUsers(user1, user2)
        );
    }

    @PostMapping("{id}/score")
    public ResponseEntity<Void> updateScore(
            @PathVariable String id,
            @Valid @RequestBody UpdateScoreRequest request
            ) {
        logger.info("POST /users/{}/score | victory={} | status=START", id, request.victory());
        userService.updateScore(id, request.victory());
        logger.info("POST /users/{}/score | victory={} | status=SUCCESS", id, request.victory());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        logger.info("POST /users/register | email={}", request.email());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

}
