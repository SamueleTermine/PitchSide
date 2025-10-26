package com.example.PitchSide.DTO;

import java.time.LocalDateTime;

public class PartitaDTO {
    private Long id;
    private String squadraCasa;
    private String squadraOspite;
    private Integer goalCasa;
    private Integer goalOspite;
    private String stato;
    private LocalDateTime dataPartita;
    private String giornata;

    public PartitaDTO(Long id, String squadraCasa, String squadraOspite, Integer goalCasa, Integer goalOspite,
                      String stato, LocalDateTime dataPartita, String giornata) {
        this.id = id;
        this.squadraCasa = squadraCasa;
        this.squadraOspite = squadraOspite;
        this.goalCasa = goalCasa;
        this.goalOspite = goalOspite;
        this.stato = stato;
        this.dataPartita = dataPartita;
        this.giornata = giornata;
    }

    public Long getId() { return id; }
    public String getSquadraCasa() { return squadraCasa; }
    public String getSquadraOspite() { return squadraOspite; }
    public Integer getGoalCasa() { return goalCasa; }
    public Integer getGoalOspite() { return goalOspite; }
    public String getStato() { return stato; }
    public LocalDateTime getDataPartita() { return dataPartita; }
    public String getGiornata() { return giornata; }
}
