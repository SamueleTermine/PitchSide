package com.example.PitchSide.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Campionato {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_campionato;

    private String nome_campionato;
    private String nazione;
    private String logo;

    @OneToMany(mappedBy = "campionato")
    private List<Partita> partite;

    public Campionato(Long id_campionato, String nome_campionato, String nazione, String logo, List<Partita> partite) {
        this.id_campionato = id_campionato;
        this.nome_campionato = nome_campionato;
        this.nazione = nazione;
        this.logo = logo;
        this.partite = partite;
    }
    public Campionato() {}

    public Long getId_campionato() {
        return id_campionato;
    }

    public String getNome_campionato() {
        return nome_campionato;
    }

    public String getNazione() {
        return nazione;
    }

    public String getLogo() {
        return logo;
    }

    public List<Partita> getPartite() {
        return partite;
    }

    @Override
    public String toString() {
        return "Campionato{" +
                "id_campionato=" + id_campionato +
                ", nome_campionato='" + nome_campionato + '\'' +
                ", nazione='" + nazione + '\'' +
                ", logo='" + logo + '\'' +
                ", partite=" + partite +
                '}';
    }
}
