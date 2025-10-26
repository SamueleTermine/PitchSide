package com.example.PitchSide.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Partita> partite;

    @Column(unique = true)
    private int apiId;

    public Campionato(Long id_campionato, String nome_campionato, String nazione, String logo, List<Partita> partite, int apiId) {
        this.id_campionato = id_campionato;
        this.nome_campionato = nome_campionato;
        this.nazione = nazione;
        this.logo = logo;
        this.partite = partite;
        this.apiId = apiId;
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

    public void setId_campionato(Long id_campionato) {
        this.id_campionato = id_campionato;
    }

    public void setNome_campionato(String nome_campionato) {
        this.nome_campionato = nome_campionato;
    }

    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPartite(List<Partita> partite) {
        this.partite = partite;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
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
