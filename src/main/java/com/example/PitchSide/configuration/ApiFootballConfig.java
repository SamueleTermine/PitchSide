package com.example.PitchSide.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiFootballConfig {

    @Value("${api.football.base-url}")
    private String baseUrl;

    @Value("${api.football.api-key}")
    private String apiKey;

    @Bean
    public WebClient apiFootballWebClient() {
        int size = 16 * 1024 * 1024;
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("x-apisports-key", apiKey)
                .exchangeStrategies(strategies)
                .build();
    }
}
