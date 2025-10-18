package com.example.PitchSide.apiDTO;

import java.util.List;

public class LeaguesResponseDTO {
    private List<LeagueItemDTO> response;

    public List<LeagueItemDTO> getResponse() {
        return response;
    }

    public void setResponse(List<LeagueItemDTO> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "LeaguesResponseDTO{" +
                "response=" + response +
                '}';
    }
}
