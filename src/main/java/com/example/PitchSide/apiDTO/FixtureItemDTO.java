package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FixtureItemDTO {
    @JsonProperty("fixture")
    private FixtureInfoDTO fixture;

    @JsonProperty("league")
    private LeagueLightDTO league;

    @JsonProperty("teams")
    private TeamsDTO teams;

    @JsonProperty("goals")
    private GoalsDTO goals;



    public FixtureInfoDTO getFixture() {
        return fixture;
    }

    public void setFixture(FixtureInfoDTO fixture) {
        this.fixture = fixture;
    }

    public LeagueLightDTO getLeague() {
        return league;
    }

    public void setLeague(LeagueLightDTO league) {
        this.league = league;
    }

    public TeamsDTO getTeams() {
        return teams;
    }

    public void setTeams(TeamsDTO teams) {
        this.teams = teams;
    }

    public GoalsDTO getGoals() {
        return goals;
    }

    public void setGoals(GoalsDTO goals) {
        this.goals = goals;
    }

    @Override
    public String toString() {
        return "FixtureItemDTO{" +
                "fixture=" + fixture +
                ", league=" + league +
                ", teams=" + teams +
                ", goals=" + goals +
                '}';
    }
}

