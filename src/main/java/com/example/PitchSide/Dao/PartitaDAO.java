package com.example.PitchSide.Dao;

import com.example.PitchSide.model.Partita;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PartitaDAO extends CrudRepository<Partita, Long> {

    @Query("SELECT p FROM Partita p WHERE p.idPartita = :idPartita")
    Optional<Partita> findByIdPartita(@Param("idPartita") Long idPartita);

    @Query("SELECT p FROM Partita p WHERE p.data_partita BETWEEN :inizio AND :fine")
    List<Partita> findByDataPartitaBetween(@Param("inizio") LocalDateTime inizio, @Param("fine") LocalDateTime fine);

    Optional<Partita> findByApiId(int apiId);

    List<Partita> findByStatoIn(List<String> stati);

    List<Partita> findByStato(String stato);

    @Query("SELECT p FROM Partita p WHERE p.campionato.id_campionato = :leagueId AND p.giornata = :giornata")
    List<Partita> findPartitaByCampionatoAndGiornata(@Param("leagueId") int leagueId, @Param("giornata") String giornata);

}
