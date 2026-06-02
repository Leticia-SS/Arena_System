package com.example.controller;

import com.example.dto.ScoreDto;
import com.example.dto.UserDto;
import com.example.model.Score;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
