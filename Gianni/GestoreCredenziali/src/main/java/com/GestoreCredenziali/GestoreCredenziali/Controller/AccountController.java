package com.GestoreCredenziali.GestoreCredenziali.Controller;

import com.GestoreCredenziali.GestoreCredenziali.File.FileController;
import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageService;
import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import com.GestoreCredenziali.GestoreCredenziali.Repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class AccountController {


    @Autowired
    AccountRepository a_repository;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    @Autowired
    private FileStorageService fileStorageService;


    // crea l'account
    //@RequestBody Account account,
    // trasforma Request body in tante Requestparam di stringe e costruisci l'account con i parametri
    @PostMapping(value = "/registrazione/create/account")
    public ResponseEntity<String> postAccount(@RequestBody Account account){
        // Verifica se l'account è già presente nel database
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null) {
            // L'account è già presente, restituisce una segnalazione corrispondente
            return ResponseEntity.badRequest().body("Email già presente nel database");
        } else {
            // L'account non è presente, salva l'account nel database
            account.setPath_curriculum(account.getEmail() + ".pdf");
            account.setPath_documento(account.getEmail() + ".pdf");
            account.setStato("da_approvare");
            a_repository.save(account);
            // Restituisce una segnalazione di successo
            return ResponseEntity.ok("ok");
          }
    }


    // PRIMA DI CHIAMARLO E' NECESSARIO CHE PRIMA SIA STATO SALVATO L'ACCOUNT NEL SISTEMA !!!!
    @PostMapping(value = "/registrazione/create/Documents")
    public ResponseEntity<String> postDocuments(@RequestPart("email") String email , @RequestParam("doc") MultipartFile document, @RequestParam("cv") MultipartFile curriculum) {
        String doc = fileStorageService.storeFile(document, "Documenti_identita" , email);
        String cv = fileStorageService.storeFile(curriculum,"CV",  email);
        Account account = a_repository.findByEmail(email);
        account.setPath_curriculum(cv);
        account.setPath_documento(doc);
        a_repository.save(account);
        // Restituisce una segnalazione di successo
        return ResponseEntity.ok("Documenti inviati con successo");
    }


    @GetMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null && accountEsistente.getPassword().equals(account.getPassword()) && accountEsistente.getStato().equals("approvato")) {
            return ResponseEntity.ok(accountEsistente);
        } else {
            // L'account non è presente o password errata
            return ResponseEntity.badRequest().body(null);
        }
    }








}




