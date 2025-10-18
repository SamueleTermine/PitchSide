package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LeagueDTO {
    private int id;
    private String name;
    private String country;
    private String logo;

    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "LeagueDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
