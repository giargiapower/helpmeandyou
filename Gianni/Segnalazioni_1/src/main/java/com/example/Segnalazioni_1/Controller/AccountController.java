package com.example.Segnalazioni_1.Controller;


import com.example.Segnalazioni_1.Model.Account;
import com.example.Segnalazioni_1.Model.Segnalazioni;
import com.example.Segnalazioni_1.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class AccountController {
    @Autowired
    AccountRepository repository;


    // metodo per amministratore, ritorna tutti gli account in stato di apertura (da approvare)
    @GetMapping("/da_approvare/list")
    public List<Account> getAllAccountToApprove() {

        List<Account> accounts = new ArrayList<>();
        repository.findAllByStato("da_approvare").forEach(accounts::add);
        return accounts;
    }

    // metodi per amministratore approva o disapprova la creazione di un account in base ai parametri dell'account
    // da valutare se aggiungere fattori di sicurezza tipo controllare se chi richiama il microservizio è un amministratore valido
    @PutMapping("/da_approvare/approva/{id}")
    public ResponseEntity<Account> approvaAccount(@PathVariable("id") long id) {

        Optional<Account> c = repository.findById(id);

        if (c.isPresent()) {
            Account _account = c.get();
            _account.setStato("approvato");

            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/da_approvare/disapprova/{id}")
    public ResponseEntity<Account> disapprovaAccount(@PathVariable("id") long id) {

        Optional<Account> c = repository.findById(id);

        if (c.isPresent()) {
            Account _account = c.get();
            _account.setStato("bloccato");

            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    // crea l'account
    @PostMapping(value = "/create")
    public ResponseEntity<String> postAccount(@RequestBody Account account) {
        // Verifica se l'account è già presente nel database
        Account accountEsistente = repository.findByEmail(account.getEmail());
        if (accountEsistente != null) {
            // L'account è già presente, restituisce una segnalazione corrispondente
            return ResponseEntity.badRequest().body("Email già presente nel database");
        } else {
            // L'account non è presente, salva l'account nel database
            repository.save(account);
            // Restituisce una segnalazione di successo
            return ResponseEntity.ok("ok");
        }
    }








}
