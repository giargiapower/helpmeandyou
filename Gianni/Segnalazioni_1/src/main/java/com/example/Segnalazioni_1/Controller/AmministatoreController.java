package com.example.Segnalazioni_1.Controller;

import com.example.Segnalazioni_1.Model.Account;
import com.example.Segnalazioni_1.Model.Category;
import com.example.Segnalazioni_1.Model.Segnalazioni;
import com.example.Segnalazioni_1.Repository.AccountRepository;
import com.example.Segnalazioni_1.Repository.AmministratoreRepository;
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
@RequestMapping("/api/amministratore")
public class AmministatoreController {

    @Autowired
    AmministratoreRepository repository;

    @Autowired
    SegnalazioniRepository s_repository;

    // metodo per amministratore, ritorna tutti gli account in stato di apertura (da approvare)
    // va aggiunta la parte di invio dei documenti e curriculum all'amministratore per la verifica
    @GetMapping("/da_approvare/list")
    public List<Account> getAllAccountToApprove() {

        List<Account> accounts = new ArrayList<>();
        repository.findAllByStato("da_approvare").forEach(accounts::add);
        for (Account a : accounts) {
            //manda all'amministratore i documenti e curriculum dei ciascun account

        }
        return accounts;
    }

    // metodo per amministratore approva o disapprova la creazione di un account in base ai parametri dell'account
    // da valutare se aggiungere fattori di sicurezza tipo controllare se chi richiama il microservizio è un amministratore valido
    @PutMapping("/da_approvare/approva/{id}")
    public ResponseEntity<Account> approvaAccount(@PathVariable("id") long id, @RequestBody String decisione) {

        Optional<Account> c = repository.findById(id);

        if (c.isPresent()) {
            Account _account = c.get();
            if (decisione.equals("approva")) {
                _account.setStato("approvato");
            } else {
                _account.setStato("bloccato");
            }
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/segnalazione/list")
    public List<Segnalazioni> getAllSegnalazioni() {
        System.out.println("Get all Segnalations...");

        List<Segnalazioni> segnalazione = new ArrayList<>();
        s_repository.findAll().forEach(segnalazione::add);

        return segnalazione;
    }

    @DeleteMapping("/segnalazione/delete/{id}")
    public ResponseEntity<Segnalazioni> deleteSegnalazione(@PathVariable("id") long id) {
        Optional<Segnalazioni> c = s_repository.findById(id);
        if (c.isPresent()) {
            s_repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/aggiorna_categoria/{id}")
    public ResponseEntity<Account> aggiorna_categoria(@PathVariable("id") long id, @RequestBody Category category) {

        Optional<Account> c = repository.findById(id);

        if (c.isPresent()) {
            Account _account = c.get();
            _account.setCategory(category);

            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    //parte per caricamento documenti e curriculum andrà inserito nella parte di login
/**    @PostMapping("/upload")
    public ResponseEntity<Account> handleFileUpload( @RequestParam("file") MultipartFile file ) {

        String fileName = file.getOriginalFilename();
        try {
            file.transferTo( new File("C:\\upload\\" + fileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("File uploaded successfully.");
    }
*/



}
