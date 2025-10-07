package com.example.PitchSide.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.grammars.hql.HqlParser;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_utente;

    @Size(min = 1, max = 15, message="nickname deve essere di 15 caratteri")
    @Column(unique=true)
    @NotNull
    private String nickname;

    @Size(min=2, max=40, message="nome deve essere tra 2 e 40 caratteri")
    @NotNull
    String nome_utente;

    @Size(min=2, max=40, message="cognome deve essere tra 2 e 40 caratteri")
    @NotNull
    String cognome_utente;

    @Email
    @NotNull
    String email;

    @Size(min=8, max=64, message="password deve essere tra 8 e 64 caratteri")
    @NotNull
    String password;

    String immagine_profilo_percorso;

    @OneToMany
    private Set<Preferito> preferiti = new HashSet<Preferito>();

    public Utente() {}

    public Utente(String nickname, String nome_utente, String cognome_utente, String email, String password, String immagine_profilo_percorso) {
        this.nickname = nickname;
        this.nome_utente = nome_utente;
        this.cognome_utente = cognome_utente;
        this.email = email;
        this.password = password;
        this.immagine_profilo_percorso = immagine_profilo_percorso;
    }

    public Utente(String nickname, String nome_utente, String cognome_utente, String email, String password) {
        this.nickname = nickname;
        this.nome_utente = nome_utente;
        this.cognome_utente = cognome_utente;
        this.email = email;
        this.password = password;
    }

    public long getId_utente() {
        return id_utente;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNome_utente() {
        return nome_utente;
    }

    public String getCognome_utente() {
        return cognome_utente;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImmagine_profilo_percorso() {
        return immagine_profilo_percorso;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id_utente=" + id_utente +
                ", nickname='" + nickname + '\'' +
                ", nome_utente='" + nome_utente + '\'' +
                ", cognome_utente='" + cognome_utente + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", immagine_profilo_percorso='" + immagine_profilo_percorso + '\'' +
                '}';
    }
}

