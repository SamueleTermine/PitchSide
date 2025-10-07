package com.example.PitchSide.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Partita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_partita;

    private String stato;

    @OneToMany
    private Set<Preferito> preferitaDa = new HashSet<Preferito>();
}
