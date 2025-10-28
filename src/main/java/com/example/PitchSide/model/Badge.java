package com.example.PitchSide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_badge;

    private String nome_badge;
    private String descrizione;
    private Integer soglia_punti;
    private String icona;

    @OneToMany (mappedBy = "badge")
    @JsonIgnore
    private List<Utente_Badge> badge_utente;
    
    public Badge(String nome_badge, String descrizione, Integer soglia_punti, String icona) {
        this.nome_badge = nome_badge;
        this.descrizione = descrizione;
        this.soglia_punti = soglia_punti;
        this.icona = icona;
    }

    public Badge() {}

    public Long getId_badge() {
        return id_badge;
    }

    public String getNome_badge() {
        return nome_badge;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Integer getSoglia_punti() {
        return soglia_punti;
    }

    public String getIcona() {
        return icona;
    }

    @Override
    public String toString() {
        return "Badge{" +
                "id_badge=" + id_badge +
                ", nome_badge='" + nome_badge + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", soglia_punti=" + soglia_punti +
                ", icona='" + icona + '\'' +
                '}';
    }
}
