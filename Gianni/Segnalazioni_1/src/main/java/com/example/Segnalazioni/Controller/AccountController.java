package com.example.Segnalazioni.Controller;

import com.example.Segnalazioni.Model.Segnalazioni;
import com.example.Segnalazioni.Model.Account;
import com.example.Segnalazioni.Repository.AccountRepository;
import com.example.Segnalazioni.Repository.SegnalazioniRepository;
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




