package com.Segnalazioni.Segnalazioni.Controller;


import com.Segnalazioni.Segnalazioni.Model.Segnalazione;
import com.Segnalazioni.Segnalazioni.Repository.SegnalazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/segnalazioni")
public class SegnalazioneController {
    @Autowired
    SegnalazioneRepository repository;

    @GetMapping("/{id}")
    public Segnalazione getSegnalazione(@PathVariable("id") long id) {
        Optional<Segnalazione> s = repository.findById(id);

        if (s.isPresent()) {
            Segnalazione _segnalazione = s.get();
            return _segnalazione;
        } else {
            return null;
        }
    }

    @GetMapping("/list")
    public List<Segnalazione> getAllSegnalazioni() {
        System.out.println("Get all Segnalations...");

        List<Segnalazione> segnalazione = new ArrayList<>();
        repository.findAll().forEach(segnalazione::add);

        return segnalazione;
    }

    @PostMapping(value = "/create")
    public Segnalazione postSegnalazione(@RequestBody Segnalazione segnalazione) {

        Segnalazione s = repository.save(new Segnalazione(segnalazione.getTitolo() , segnalazione.getDescrizione() , segnalazione.getTipologia(), segnalazione.getCreatore() , segnalazione.getSegnalato()));
        return s;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Segnalazione> deleteSegnalazione(@PathVariable("id") long id) {
        System.out.println("Delete Segnalazione with ID = " + id + "...");

        Optional<Segnalazione> c = repository.findById(id);
        if (c.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
