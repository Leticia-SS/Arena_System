package com.example.arena_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICharacterRepository extends MongoRepository<Character, String> {

}
