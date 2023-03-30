package com.Segnalazioni.Demo;

import com.Segnalazioni.Demo.Segnalazione;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SegnalazioneRepository extends CrudRepository<Segnalazione, Long>{
    List<Segnalazione> findByUtente(String utente);
}
