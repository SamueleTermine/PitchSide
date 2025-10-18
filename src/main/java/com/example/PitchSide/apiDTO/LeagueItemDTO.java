package com.example.PitchSide.apiDTO;

public class LeagueItemDTO {
    private LeagueDTO league;

    public LeagueDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueDTO league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "LeagueItemDTO{" +
                "league=" + league +
                '}';
    }
}

