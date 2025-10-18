package com.example.PitchSide.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FixtureInfoDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("referee")
    private String referee;

    @JsonProperty("date")
    private String date; // ISO8601 date-time string

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("status")
    private StatusDTO status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FixtureInfoDTO{" +
                "id=" + id +
                ", referee='" + referee + '\'' +
                ", date='" + date + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}
