package com.example.arena_service.repository;

import com.example.arena_service.model.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ICharacterRepository extends MongoRepository<Character, String> {
    Optional<Character> findByName(String name);
}
