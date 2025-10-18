package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StandingsResponseDTO {
    @JsonProperty("response")
    private List<StandingGroupDTO> response;

    public List<StandingGroupDTO> getResponse() {
        return response;
    }

    public void setResponse(List<StandingGroupDTO> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "StandingsResponseDTO{" +
                "response=" + response +
                '}';
    }
}
