package com.example.arena_service.service;

import com.example.arena_service.repository.IMatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatchService {
    private final IMatchRepository matchRepository;


}
