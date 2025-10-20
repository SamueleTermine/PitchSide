package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;


public class LeagueLightDTO {

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

    @JsonProperty("round")
    private String round;

    @JsonProperty("standings")
    private boolean standings;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }

    public String getFlag() { return flag; }
    public void setFlag(String flag) { this.flag = flag; }

    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }

    public String getRound() { return round; }
    public void setRound(String round) { this.round = round; }

    public boolean isStandings() { return standings; }
    public void setStandings(boolean standings) { this.standings = standings; }
}

