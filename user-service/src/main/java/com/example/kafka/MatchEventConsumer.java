package com.example.kafka;

import com.example.event.MatchFinishedEvent;
import com.example.service.UserService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MatchEventConsumer {

    private final UserService userService;

    public MatchEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @KafkaListener(topics = "match-finished", groupId = "user-service")
    public void consume(MatchFinishedEvent event) {

        System.out.println("Evento recebido: " + event.matchId());

        userService.updateScore(
                event.winnerId(),
                Boolean.TRUE
        );

        userService.updateScore(
                event.loserId(),
                Boolean.FALSE
        );
    }
}
