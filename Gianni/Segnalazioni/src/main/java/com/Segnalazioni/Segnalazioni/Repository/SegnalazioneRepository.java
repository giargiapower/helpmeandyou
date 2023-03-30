package com.Segnalazioni.Segnalazioni.Repository;

import com.Segnalazioni.Segnalazioni.Model.Segnalazione;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SegnalazioneRepository extends CrudRepository<Segnalazione, Long>{
    List<Segnalazione> findByUtente(String utente);
}
