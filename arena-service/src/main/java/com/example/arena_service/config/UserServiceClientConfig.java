package com.example.arena_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class UserServiceClientConfig {
    @Bean
    public RestClient userServiceRestClient(@Value("${integrations.user-service.base-url}") String baseUrl){
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(3000))
                .build();
        JdkClientHttpRequestFactory reqFactory = new JdkClientHttpRequestFactory(httpClient);
        return RestClient.builder().baseUrl(baseUrl).requestFactory(reqFactory).build();
    }


}
