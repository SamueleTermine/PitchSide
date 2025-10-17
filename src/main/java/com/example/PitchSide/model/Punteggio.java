package com.example.PitchSide.model;

import jakarta.persistence.*;

@Entity
public class Punteggio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_punteggio;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    private Integer punteggio_totale;

    public Punteggio(Long id_punteggio, Utente utente, Integer punteggio_totale) {
        this.utente = utente;
        this.punteggio_totale = punteggio_totale;
    }
    public Punteggio() {}

    public Long getId_punteggio() {
        return id_punteggio;
    }

    public Utente getUtente() {
        return utente;
    }

    public Integer getPunteggio_totale() {
        return punteggio_totale;
    }

    public void setId_punteggio(Long id_punteggio) {
        this.id_punteggio = id_punteggio;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setPunteggio_totale(Integer punteggio_totale) {
        this.punteggio_totale = punteggio_totale;
    }

    @Override
    public String toString() {
        return "Punteggio{" +
                "id_punteggio=" + id_punteggio +
                ", utente=" + utente +
                ", punteggio_totale=" + punteggio_totale +
                '}';
    }
}
