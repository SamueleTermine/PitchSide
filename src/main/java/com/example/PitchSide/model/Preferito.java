package com.example.PitchSide.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Preferito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_preferito;

    @NotNull
    private LocalDate data_aggiunta;

    @ManyToOne
    private Utente utente;

    @ManyToOne
    private Partita partita;
}

