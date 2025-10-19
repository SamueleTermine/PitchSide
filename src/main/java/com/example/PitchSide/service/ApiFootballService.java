package com.example.PitchSide.service;

import com.example.PitchSide.apiDTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiFootballService {

    @Autowired
    private WebClient apiFootballWebClient;

    // Campionati
    private final List<Integer> supportedLeagueIds = List.of(135, 78, 61, 140, 39, 2);

    public Mono<List<LeagueItemDTO>> getLeaguesFiltered() {
        return apiFootballWebClient.get()
                .uri("/leagues")
                .retrieve()
                .bodyToMono(LeaguesResponseDTO.class)
                .map(response -> response.getResponse().stream()
                        .filter(leagueItem -> supportedLeagueIds.contains(leagueItem.getLeague().getId()))
                        .collect(Collectors.toList())
                );
    }

    public Mono<String> getFixturesByLeague(int leagueId, int season) {
        return apiFootballWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getTeams(int leagueId, int season) {
        return apiFootballWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/teams")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    // Fixtures
    public Mono<List<FixtureItemDTO>> getFixturesByLeagueAndSeason(int leagueId, int season) {
        return apiFootballWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fixtures")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .bodyToMono(FixturesResponseDTO.class)
                .map(response -> response.getResponse());
    }

    //Standings
    public Mono<List<StandingGroupDTO>> getStandings(int leagueId, int season) {
        return apiFootballWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/standings")
                        .queryParam("league", leagueId)
                        .queryParam("season", season)
                        .build())
                .retrieve()
                .bodyToMono(StandingsResponseDTO.class)
                .map(StandingsResponseDTO::getResponse);
    }


}
