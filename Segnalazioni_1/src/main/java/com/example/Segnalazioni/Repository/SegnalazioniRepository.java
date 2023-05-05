package com.example.Segnalazioni.Repository;
import com.example.Segnalazioni.Model.Segnalazioni;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SegnalazioniRepository extends CrudRepository<Segnalazioni, Long> {

}
