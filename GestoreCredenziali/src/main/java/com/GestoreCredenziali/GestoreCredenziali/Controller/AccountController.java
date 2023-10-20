package com.GestoreCredenziali.GestoreCredenziali.Controller;

import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageService;
import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import com.GestoreCredenziali.GestoreCredenziali.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/utenti")
public class AccountController {
    @Autowired
    AccountRepository a_repository;

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
            // L'account è già presente, restituisce la segnalazione corrispondente
            return ResponseEntity.badRequest().body("Email già presente nel database");
        } else {
            // L'account non è presente, salva l'account nel database
            //questi 2 set non sono strettamente necessari qua però li tengo in caso di possibili refactoring del codice
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

    // PRIMA DI CHIAMARLO E' NECESSARIO CHE PRIMA SIA STATO SALVATO L'ACCOUNT NEL SISTEMA !!!!
    @PostMapping(value = "/registrazione/update/Documents")
    public ResponseEntity<String> updateDocuments(@RequestPart("email") String email , @RequestParam("doc") MultipartFile document, @RequestParam("cv") MultipartFile curriculum) {
        String doc = fileStorageService.storeFile(document, "Documenti_identita" , email);
        String cv = fileStorageService.storeFile(curriculum,"CV",  email);
        Account account = a_repository.findByEmail(email);
        account.setStato("in_aggiornamento");
        account.setPath_curriculum(cv);
        account.setPath_documento(doc);
        a_repository.save(account);
        // Restituisce una segnalazione di successo
        return ResponseEntity.ok("Documenti inviati con successo");
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null && accountEsistente.getPassword().equals(account.getPassword()) && (accountEsistente.getStato().equals("approvato") || accountEsistente.getStato().equals("in_aggiornamento"))) {
            return ResponseEntity.ok(accountEsistente);
        } else if (accountEsistente == null) {
            return ResponseEntity.badRequest().body(null);
        } else
            return ResponseEntity.badRequest().body(accountEsistente);
    }

    @PostMapping("/google-login")
    public ResponseEntity<Account> GoogleLogin(@RequestBody Account account){
        Account accountEsistente = a_repository.findByEmail(account.getEmail());
        if (accountEsistente != null && (accountEsistente.getStato().equals("approvato") || accountEsistente.getStato().equals("in_aggiornamento"))) {
            return ResponseEntity.ok(accountEsistente);
        } else if (accountEsistente == null) {
            return ResponseEntity.badRequest().body(null);
        } else
            return ResponseEntity.badRequest().body(accountEsistente);
    }

    @GetMapping("/utente/{id}")
    public ResponseEntity<Account> getUtenteInfoById(@PathVariable(value = "id") long idUtente) {
        Account accountEsistente = a_repository.findById(idUtente);

        if (accountEsistente != null) {
            return ResponseEntity.ok(accountEsistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
