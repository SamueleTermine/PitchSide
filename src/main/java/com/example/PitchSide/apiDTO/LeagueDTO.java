package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LeagueDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country")
    private String country;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("flag")
    private String flag;

    @JsonProperty("season")
    private int season;

    @JsonProperty("standings")
    private List<List<StandingDTO>> standings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public List<List<StandingDTO>> getStandings() {
        return standings;
    }

    public void setStandings(List<List<StandingDTO>> standings) {
        this.standings = standings;
    }

    @Override
    public String toString() {
        return "LeagueDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", logo='" + logo + '\'' +
                ", flag='" + flag + '\'' +
                ", season=" + season +
                ", standings=" + (standings != null ? standings.size() + " gruppi" : "null") +
                '}';
    }
}
