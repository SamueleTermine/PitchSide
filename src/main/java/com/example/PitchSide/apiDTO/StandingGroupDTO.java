package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StandingGroupDTO {
    @JsonProperty("league")
    private LeagueDTO league;

    @JsonProperty("standings")
    private List<List<StandingDTO>> standings;

    public LeagueDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueDTO league) {
        this.league = league;
    }

    public List<List<StandingDTO>> getStandings() {
        return standings;
    }

    public void setStandings(List<List<StandingDTO>> standings) {
        this.standings = standings;
    }

    @Override
    public String toString() {
        return "StandingGroupDTO{" +
                "league=" + league +
                ", standings=" + standings +
                '}';
    }
}
