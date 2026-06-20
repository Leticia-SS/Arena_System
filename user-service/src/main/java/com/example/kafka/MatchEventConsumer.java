package com.example.kafka;

import com.example.event.MatchFinishedEvent;
import com.example.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MatchEventConsumer {

    private final UserService userService;

    private static final Logger logger =
            LoggerFactory.getLogger(MatchEventConsumer.class);

    public MatchEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "match-finished", groupId = "user-service")
    public void consume(MatchFinishedEvent event) {

        logger.info(
                "Evento Kafka recebido | matchId={} | winnerId={} | loserId={}",
                event.matchId(),
                event.winnerId(),
                event.loserId()
        );

        try {
            userService.updateScore(
                    event.winnerId(),
                    Boolean.TRUE
            );
            userService.updateScore(
                    event.loserId(),
                    Boolean.FALSE
            );

            logger.info("Evento processado com sucesso | matchId={}", event.matchId());

        } catch (Exception e) {
            logger.error(
                    "Erro ao processar evento Kafka | matchId={}",
                    event.matchId(),
                    e
            );
            throw e;
        }
    }
}
