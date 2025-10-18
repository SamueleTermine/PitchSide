package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamsDTO {
    @JsonProperty("home")
    private TeamInfoDTO home;

    @JsonProperty("away")
    private TeamInfoDTO away;

    public TeamInfoDTO getHome() {
        return home;
    }

    public void setHome(TeamInfoDTO home) {
        this.home = home;
    }

    public TeamInfoDTO getAway() {
        return away;
    }

    public void setAway(TeamInfoDTO away) {
        this.away = away;
    }

    @Override
    public String toString() {
        return "TeamsDTO{" +
                "home=" + home +
                ", away=" + away +
                '}';
    }
}
