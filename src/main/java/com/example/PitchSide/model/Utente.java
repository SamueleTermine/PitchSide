package com.example.PitchSide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.grammars.hql.HqlParser;

import java.util.HashSet;
import java.util.List;
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

    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Pronostico> pronostici;

    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Preferito> preferiti;

    @OneToOne(mappedBy = "utente")
    @JsonIgnore
    private Punteggio punteggio;

    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Utente_Badge> badge_utente;


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

    public List<Pronostico> getPronostici() {
        return pronostici;
    }

    public List<Preferito> getPreferiti() {
        return preferiti;
    }

    public Punteggio getPunteggio() {
        return punteggio;
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

