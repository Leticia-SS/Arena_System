package com.example.arena_service.repository;

import com.example.arena_service.model.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICharacterRepository extends MongoRepository<Character, String> {

}
