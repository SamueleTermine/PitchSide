package com.example.PitchSide.DTO;

import com.example.PitchSide.model.Partita;
import com.example.PitchSide.model.Pronostico;
import com.example.PitchSide.model.Utente;

import java.time.LocalDateTime;

public class PronosticoDTO {
    private Long id;
    private String scelta;
    private String esito;
    private Integer punteggioOttenuto;
    private LocalDateTime dataPronostico;
    private UtenteDTO utente;
    private PartitaDTO partita;

    public PronosticoDTO(Long id, String scelta, String esito, Integer punteggioOttenuto, LocalDateTime dataPronostico,
                         UtenteDTO utente, PartitaDTO partita) {
        this.id = id;
        this.scelta = scelta;
        this.esito = esito;
        this.punteggioOttenuto = punteggioOttenuto;
        this.dataPronostico = dataPronostico;
        this.utente = utente;
        this.partita = partita;
    }

    public static PronosticoDTO toDTO(Pronostico pronostico) {
        if (pronostico == null) return null;

        Utente u = pronostico.getUtente();
        Partita p = pronostico.getPartita();

        UtenteDTO utenteDTO = new UtenteDTO(
                u.getId_utente(),
                u.getNickname(),
                u.getNome_utente(),
                u.getCognome_utente(),
                u.getEmail()
        );

        PartitaDTO partitaDTO = new PartitaDTO(
                p.getIdPartita(),
                p.getSquadra_casa(),
                p.getSquadra_ospite(),
                p.getGoal_casa(),
                p.getGoal_ospite(),
                p.getStato(),
                p.getData_partita(),
                p.getGiornata()
        );

        return new PronosticoDTO(
                pronostico.getId_pronostico(),
                pronostico.getScelta(),
                pronostico.getEsito(),
                pronostico.getPunteggio_ottenuto(),
                pronostico.getData_pronostico(),
                utenteDTO,
                partitaDTO
        );
    }

    public Long getId() { return id; }
    public String getScelta() { return scelta; }
    public String getEsito() { return esito; }
    public Integer getPunteggioOttenuto() { return punteggioOttenuto; }
    public LocalDateTime getDataPronostico() { return dataPronostico; }
    public UtenteDTO getUtente() { return utente; }
    public PartitaDTO getPartita() { return partita; }

    public void setId(Long id) {
        this.id = id;
    }
    public void setScelta(String scelta) {
        this.scelta = scelta;
    }
    public void setEsito(String esito) {
        this.esito = esito;
    }
    public void setPunteggioOttenuto(Integer punteggioOttenuto) {
        this.punteggioOttenuto = punteggioOttenuto;
    }
    public void setDataPronostico(LocalDateTime dataPronostico) {
        this.dataPronostico = dataPronostico;
    }
    public void setUtente(UtenteDTO utente) {
        this.utente = utente;
    }
    public void setPartita(PartitaDTO partita) {
        this.partita = partita;
    }
}
