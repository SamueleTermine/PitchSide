package com.example.PitchSide.DTO;

public class NuovoPronosticoDTO {
    private Long idPartita;
    private String scelta;

    public NuovoPronosticoDTO() {}

    public NuovoPronosticoDTO(Long idPartita, String scelta) {
        this.idPartita = idPartita;
        this.scelta = scelta;
    }

    public Long getIdPartita() { return idPartita; }
    public String getScelta() { return scelta; }

    public void setIdPartita(Long idPartita) { this.idPartita = idPartita; }
    public void setScelta(String scelta) { this.scelta = scelta; }
}
