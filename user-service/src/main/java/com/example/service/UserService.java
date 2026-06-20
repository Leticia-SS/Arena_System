package com.example.service;

import com.example.dto.RegisterUserRequest;
import com.example.dto.ScoreDto;
import com.example.dto.UserRankingDto;
import com.example.exception.EmailAlreadyExistsException;
import com.example.exception.UserNotFoundException;
import com.example.model.Score;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );
    }

    public User register(RegisterUserRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        Score score = Score.builder()
                .user(user)
                .wins(0)
                .losses(0)
                .totalMatches(0)
                .points(0)
                .build();

        user.setScore(score);

        return userRepository.save(user);
    }


    public ScoreDto getScoreByUserId(String id){
        User user = getById(id);
        Score score = user.getScore();

        return new ScoreDto(
                score.getWins(),
                score.getLosses(),
                score.getTotalMatches(),
                score.getPoints()
        );
    }

    public void updateScore(String userId, Boolean victory) {
        User user = getById(userId);
        Score score = user.getScore();

        score.setTotalMatches(score.getTotalMatches() + 1);

        if (victory) {
            score.setWins(score.getWins() + 1);
            score.setPoints(score.getPoints() + 3);
        } else {
            score.setLosses(score.getLosses() + 1);
        }

        userRepository.save(user);
    }

    public List<UserRankingDto> getRanking(){
        return userRepository.findRanking();
    }

    public List<UserRankingDto> compareUsers(String u1, String u2) {
        User user1 = getById(u1);
        User user2 = getById(u2);

        return List.of(
                new UserRankingDto(
                        user1.getName(),
                        user1.getScore().getPoints()
                ),
                new UserRankingDto(
                        user2.getName(),
                        user2.getScore().getPoints()
                )
        );
    }
}
