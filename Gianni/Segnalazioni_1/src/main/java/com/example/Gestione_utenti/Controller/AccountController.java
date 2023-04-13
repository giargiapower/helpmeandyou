package com.example.Gestione_utenti.Controller;

import com.example.Gestione_utenti.Model.Segnalazioni;
import com.example.Gestione_utenti.Model.Account;
import com.example.Gestione_utenti.Repository.AccountRepository;
import com.example.Gestione_utenti.Repository.SegnalazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class AccountController {
    @Autowired
    SegnalazioniRepository repository;

    @Autowired
    AccountRepository a_repository;


    @PostMapping(value = "/segnalazione/create")
    public Segnalazioni postSegnalazione(@RequestBody Segnalazioni segnalazione) {

        Segnalazioni s = repository.save(new Segnalazioni(segnalazione.getTitolo() , segnalazione.getDescrizione() , segnalazione.getTipologia(), segnalazione.getCreatore() , segnalazione.getSegnalato()));
        return s;
    }

    // crea l'account
    @PostMapping(value = "/create")
    public ResponseEntity<String> postAccount(@RequestBody Account account) {
        // Verifica se l'account è già presente nel database
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null) {
            // L'account è già presente, restituisce una segnalazione corrispondente
            return ResponseEntity.badRequest().body("Email già presente nel database");
        } else {
            // L'account non è presente, salva l'account nel database
            a_repository.save(account);
            // Restituisce una segnalazione di successo
            return ResponseEntity.ok("ok");
        }
    }




}




