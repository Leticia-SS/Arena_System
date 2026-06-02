package com.example.arena_service.repository;

import com.example.arena_service.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMatchRepository extends MongoRepository<Match, String> {


}
