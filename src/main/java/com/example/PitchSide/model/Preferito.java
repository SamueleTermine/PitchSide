package com.example.PitchSide.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Preferito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_preferito;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_partita")
    private Partita partita;

    private LocalDateTime data_aggiunta;

    public Preferito(Utente utente, Partita partita, LocalDateTime data_aggiunta) {
        this.utente = utente;
        this.partita = partita;
        this.data_aggiunta = data_aggiunta;
    }

    public Preferito() {}

    public Long getId_preferito() {
        return id_preferito;
    }

    public Utente getUtente() {
        return utente;
    }

    public Partita getPartita() {
        return partita;
    }

    public LocalDateTime getData_aggiunta() {
        return data_aggiunta;
    }

    @Override
    public String toString() {
        return "Preferito{" +
                "id_preferito=" + id_preferito +
                ", utente=" + utente +
                ", partita=" + partita +
                ", data_aggiunta=" + data_aggiunta +
                '}';
    }
}

