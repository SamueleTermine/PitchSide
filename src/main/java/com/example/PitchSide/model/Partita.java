package com.example.PitchSide.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_partita;

    private Integer giornata;
    private String squadra_casa;
    private String squadra_ospite;
    private Integer goal_casa;
    private Integer goal_ospite;
    private String stato;
    private LocalDateTime data_partita;

    @ManyToOne
    @JoinColumn(name = "id_campionato")
    private Campionato campionato;

    @OneToMany(mappedBy = "partita")
    private List<Pronostico> pronostici;

    public Partita(Long id_partita, Integer giornata, String squadra_casa, String squadra_ospite, Integer goal_casa, Integer goal_ospite, String stato, LocalDateTime data_partita, Campionato campionato, List<Pronostico> pronostici) {
        this.id_partita = id_partita;
        this.giornata = giornata;
        this.squadra_casa = squadra_casa;
        this.squadra_ospite = squadra_ospite;
        this.goal_casa = goal_casa;
        this.goal_ospite = goal_ospite;
        this.stato = stato;
        this.data_partita = data_partita;
        this.campionato = campionato;
        this.pronostici = pronostici;
    }

    public Partita() {}

    public Long getId_partita() {
        return id_partita;
    }

    public Integer getGiornata() {
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
}
