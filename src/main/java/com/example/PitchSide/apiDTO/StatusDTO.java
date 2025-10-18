package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusDTO {
    @JsonProperty("long")
    private String longStatus;

    @JsonProperty("short")
    private String shortStatus;

    @JsonProperty("elapsed")
    private int elapsed;

    public String getLongStatus() {
        return longStatus;
    }

    public void setLongStatus(String longStatus) {
        this.longStatus = longStatus;
    }

    public String getShortStatus() {
        return shortStatus;
    }

    public void setShortStatus(String shortStatus) {
        this.shortStatus = shortStatus;
    }

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    @Override
    public String toString() {
        return "StatusDTO{" +
                "longStatus='" + longStatus + '\'' +
                ", shortStatus='" + shortStatus + '\'' +
                ", elapsed=" + elapsed +
                '}';
    }
}
