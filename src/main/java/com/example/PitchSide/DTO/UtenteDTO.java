package com.example.PitchSide.DTO;

public class UtenteDTO {
    private long id;
    private String nickname;
    private String nome;
    private String cognome;
    private String email;

    public UtenteDTO(long id, String nickname, String nome, String cognome, String email) {
        this.id = id;
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public long getId() { return id; }
    public String getNickname() { return nickname; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
}
