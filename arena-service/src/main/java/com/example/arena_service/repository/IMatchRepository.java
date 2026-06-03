package com.example.arena_service.repository;

import com.example.arena_service.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IMatchRepository extends MongoRepository<Match, String> {
    List<Match> findByPlayer1IdOrPlayer2Id(String player1Id, String player2Id);

}
