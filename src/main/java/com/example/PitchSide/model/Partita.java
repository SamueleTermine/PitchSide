package com.example.PitchSide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPartita;

    private String giornata;
    private String squadra_casa;
    private String squadra_ospite;
    private Integer goal_casa;
    private Integer goal_ospite;
    private String stato;
    private LocalDateTime data_partita;

    @Column(unique = true)
    private int apiId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;

    @OneToMany(mappedBy = "partita")
    private List<Pronostico> pronostici;

    public Partita(Long idPartita, String giornata, String squadra_casa, String squadra_ospite, Integer goal_casa, Integer goal_ospite, String stato, LocalDateTime data_partita, int apiId, Campionato campionato, List<Pronostico> pronostici) {
        this.idPartita = idPartita;
        this.giornata = giornata;
        this.squadra_casa = squadra_casa;
        this.squadra_ospite = squadra_ospite;
        this.goal_casa = goal_casa;
        this.goal_ospite = goal_ospite;
        this.stato = stato;
        this.data_partita = data_partita;
        this.apiId = apiId;
        this.campionato = campionato;
        this.pronostici = pronostici;
    }

    public Partita() {}

    public String getGiornata() {
        return giornata;
    }

    public String getSquadra_casa() {
        return squadra_casa;
    }

    public String getSquadra_ospite() {
        return squadra_ospite;
    }

    public Integer getGoal_casa() {
        return goal_casa;
    }

    public Integer getGoal_ospite() {
        return goal_ospite;
    }

    public String getStato() {
        return stato;
    }

    public LocalDateTime getData_partita() {
        return data_partita;
    }

    public Campionato getCampionato() {
        return campionato;
    }

    public List<Pronostico> getPronostici() {
        return pronostici;
    }

    public void setGiornata(String giornata) {
        this.giornata = giornata;
    }

    public void setSquadra_casa(String squadra_casa) {
        this.squadra_casa = squadra_casa;
    }

    public void setSquadra_ospite(String squadra_ospite) {
        this.squadra_ospite = squadra_ospite;
    }

    public void setGoal_casa(Integer goal_casa) {
        this.goal_casa = goal_casa;
    }

    public void setGoal_ospite(Integer goal_ospite) {
        this.goal_ospite = goal_ospite;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setData_partita(LocalDateTime data_partita) {
        this.data_partita = data_partita;
    }

    public void setCampionato(Campionato campionato) {
        this.campionato = campionato;
    }

    public void setPronostici(List<Pronostico> pronostici) {
        this.pronostici = pronostici;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public Long getIdPartita() {
        return idPartita;
    }

    public void setIdPartita(Long idPartita) {
        this.idPartita = idPartita;
    }
}
