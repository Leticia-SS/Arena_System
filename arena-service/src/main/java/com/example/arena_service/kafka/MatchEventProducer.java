package com.example.arena_service.kafka;

import com.example.arena_service.event.MatchFinishedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatchEventProducer {
    private static final String TOPIC = "match-finished";
    private final KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate;

    public void sendMatchFinished(MatchFinishedEvent event){
        kafkaTemplate.send(TOPIC, event.matchId(), event);
    }
}

