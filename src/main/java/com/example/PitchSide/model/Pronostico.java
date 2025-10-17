package com.example.PitchSide.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pronostico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_pronostico;

    private String scelta;
    private String esito;
    private Integer punteggio_ottenuto;
    private LocalDateTime data_pronostico;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "id_partita")
    private Partita partita;

    public Pronostico(String scelta, String esito, Integer punteggio_ottenuto, LocalDateTime data_pronostico) {
        this.scelta = scelta;
        this.esito = esito;
        this.punteggio_ottenuto = punteggio_ottenuto;
        this.data_pronostico = data_pronostico;
    }

    public Pronostico() {}

    public Long getId_pronostico() {
        return id_pronostico;
    }

    public String getScelta() {
        return scelta;
    }

    public String getEsito() {
        return esito;
    }

    public Integer getPunteggio_ottenuto() {
        return punteggio_ottenuto;
    }

    public LocalDateTime getData_pronostico() {
        return data_pronostico;
    }

    public Utente getUtente() {
        return utente;
    }

    public Partita getPartita() {
        return partita;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setId_pronostico(Long id_pronostico) {
        this.id_pronostico = id_pronostico;
    }

    public void setScelta(String scelta) {
        this.scelta = scelta;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public void setPunteggio_ottenuto(Integer punteggio_ottenuto) {
        this.punteggio_ottenuto = punteggio_ottenuto;
    }

    public void setData_pronostico(LocalDateTime data_pronostico) {
        this.data_pronostico = data_pronostico;
    }

    public void setPartita(Partita partita) {
        this.partita = partita;
    }

    @Override
    public String toString() {
        return "Pronostico{" +
                "id_pronostico=" + id_pronostico +
                ", scelta='" + scelta + '\'' +
                ", esito='" + esito + '\'' +
                ", punteggio_ottenuto=" + punteggio_ottenuto +
                ", data_pronostico=" + data_pronostico +
                ", utente=" + utente +
                ", partita=" + partita +
                '}';
    }
}
