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
    @PostMapping(value = "/create")
    public ResponseEntity<String> postAccount(@RequestBody Account account,
                                              @RequestParam("doc") MultipartFile document,
                                              @RequestParam("cv") MultipartFile curriculum) {
        // Verifica se l'account è già presente nel database
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null) {
            // L'account è già presente, restituisce una segnalazione corrispondente
            return ResponseEntity.badRequest().body("Email già presente nel database");
        } else {
            // L'account non è presente, salva l'account nel database
            account.setStato("da_approvare");
            a_repository.save(account);
            fileStorageService.storeFile(document);
            fileStorageService.storeFile(curriculum);
            // Restituisce una segnalazione di successo
            return ResponseEntity.ok("ok");
        }
        
    }






}




