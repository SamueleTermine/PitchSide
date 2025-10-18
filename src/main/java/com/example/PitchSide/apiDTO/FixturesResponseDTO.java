package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FixturesResponseDTO {
    @JsonProperty("response")
    private List<FixtureItemDTO> response;

    public List<FixtureItemDTO> getResponse() {
        return response;
    }

    public void setResponse(List<FixtureItemDTO> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "FixturesResponseDTO{" +
                "response=" + response +
                '}';
    }
}
