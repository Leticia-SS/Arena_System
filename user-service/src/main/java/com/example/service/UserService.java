package com.example.service;

import com.example.dto.ScoreDto;
import com.example.dto.UserRankingDto;
import com.example.model.Score;
import com.example.model.User;
import com.example.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Usuário não encontrado com id " + id
                        )
                );
    }

    public ScoreDto getScoreByUserId(Long id){
        User user = getById(id);
        Score score = user.getScore();

        return new ScoreDto(
                score.getWins(),
                score.getLosses(),
                score.getTotalMatches(),
                score.getPoints()
        );
    }

    public void updateScore(Long userId, Boolean victory) {
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

    public List<UserRankingDto> compareUsers(Long u1, Long u2) {
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
