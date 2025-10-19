package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class StandingGroupDTO {

    @JsonProperty("league")
    private LeagueDTO league;

    public LeagueDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueDTO league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "StandingGroupDTO{" +
                "league=" + league +
                '}';
    }
}
