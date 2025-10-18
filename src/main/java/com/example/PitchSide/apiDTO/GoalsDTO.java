package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoalsDTO {
    @JsonProperty("home")
    private Integer home;

    @JsonProperty("away")
    private Integer away;

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getAway() {
        return away;
    }

    public void setAway(Integer away) {
        this.away = away;
    }

    @Override
    public String toString() {
        return "GoalsDTO{" +
                "home=" + home +
                ", away=" + away +
                '}';
    }
}
