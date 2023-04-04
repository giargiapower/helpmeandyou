package com.example.Segnalazioni_1.Controller;


import com.example.Segnalazioni_1.Model.Segnalazioni;
import com.example.Segnalazioni_1.Repository.SegnalazioniRepository;
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
public class SegnalazioniController {
    @Autowired
    SegnalazioniRepository repository;

    @GetMapping("/{id}")
    public Segnalazioni getSegnalazione(@PathVariable("id") long id) {
        Optional<Segnalazioni> s = repository.findById(id);

        if (s.isPresent()) {
            Segnalazioni _segnalazione = s.get();
            return _segnalazione;
        } else {
            return null;
        }
    }

    @GetMapping("/list")
    public List<Segnalazioni> getAllSegnalazioni() {
        System.out.println("Get all Segnalations...");

        List<Segnalazioni> segnalazione = new ArrayList<>();
        repository.findAll().forEach(segnalazione::add);

        return segnalazione;
    }

    @PostMapping(value = "/create")
    public Segnalazioni postSegnalazione(@RequestBody Segnalazioni segnalazione) {

        Segnalazioni s = repository.save(new Segnalazioni(segnalazione.getTitolo() , segnalazione.getDescrizione() , segnalazione.getTipologia(), segnalazione.getCreatore() , segnalazione.getSegnalato()));
        return s;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Segnalazioni> deleteSegnalazione(@PathVariable("id") long id) {
        System.out.println("Delete Segnalazione with ID = " + id + "...");

        Optional<Segnalazioni> c = repository.findById(id);
        if (c.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
