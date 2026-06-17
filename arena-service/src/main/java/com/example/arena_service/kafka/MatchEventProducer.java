package com.example.arena_service.kafka;

import com.example.arena_service.event.MatchFinishedEvent;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MatchEventProducer {
    private static final String TOPIC = "match-finished";
    private final KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(MatchEventProducer.class);

    public void sendMatchFinished(MatchFinishedEvent event){
        logger.info(
                "Publicando evento no Kafka = topic: {} | matchId: {} | winnerId: {} | loserId: {}",
                TOPIC, event.matchId(), event.winnerId(), event.loserId());
        kafkaTemplate.send(TOPIC, event.matchId(), event);
        logger.info("Evento publicado com sucesso no tópico: {} | matchId: {}", TOPIC, event.matchId());
    }
}

