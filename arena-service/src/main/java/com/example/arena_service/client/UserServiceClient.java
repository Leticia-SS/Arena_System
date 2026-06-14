package com.example.arena_service.client;

import com.example.arena_service.dto.ScoreUpdateRequestDto;
import com.example.arena_service.model.enums.MatchResultEnum;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@AllArgsConstructor
public class UserServiceClient {
    private final RestClient userServiceRestClient;

    @CircuitBreaker(name = "user-service", fallbackMethod = "updateScoreFallback")
    @Retry(name = "user-service")
    public boolean updateScore(String winnerId, String loserId){
        userServiceRestClient.post().uri("/users/{id}/score",winnerId)
                .body(new ScoreUpdateRequestDto(MatchResultEnum.WIN))
                .retrieve()
                .toBodilessEntity();
        userServiceRestClient.post().uri("/users/{id}/score", loserId)
                .body(new ScoreUpdateRequestDto(MatchResultEnum.WIN))
                .retrieve()
                .toBodilessEntity();
        return true;
    }

    public boolean updateScoreFallback(String winnerId, String loserId, Throwable t){
        return false;
    }
}
