package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StandingDTO {
    @JsonProperty("rank")
    private int rank;

    @JsonProperty("team")
    private TeamInfoDTO team;

    @JsonProperty("points")
    private int points;

    @JsonProperty("form")
    private String form;

    @JsonProperty("group")
    private String group;

    @JsonProperty("status")
    private String status;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public TeamInfoDTO getTeam() {
        return team;
    }

    public void setTeam(TeamInfoDTO team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StandingDTO{" +
                "rank=" + rank +
                ", team=" + team +
                ", points=" + points +
                ", form='" + form + '\'' +
                ", group='" + group + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
