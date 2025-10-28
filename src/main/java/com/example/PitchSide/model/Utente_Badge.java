package com.example.PitchSide.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Utente_Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_utente_badge;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    @JsonIgnore
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_badge")
    private Badge badge;

    private LocalDateTime data_sblocco;

    public Utente_Badge() {
    }

    public Utente_Badge(Utente utente, Badge badge, LocalDateTime data_sblocco) {
        this.utente = utente;
        this.badge = badge;
        this.data_sblocco = data_sblocco;
    }

    public Long getId_utente_badge() {

        return id_utente_badge;
    }

    public Utente getUtente() {
        return utente;
    }

    public Badge getBadge() {
        return badge;
    }

    public LocalDateTime getData_sblocco() {
        return data_sblocco;
    }

    @Override
    public String toString() {
        return "Utente_Badge{" +
                "id_utente_badge=" + id_utente_badge +
                ", utente=" + utente.getNickname() +
                ", badge=" + badge.getNome_badge() +
                ", data_sblocco=" + data_sblocco +
                '}';
    }
}
