package com.GestoreCredenziali.GestoreCredenziali.Controller;


import com.GestoreCredenziali.GestoreCredenziali.File.FileController;
import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageService;
import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import com.GestoreCredenziali.GestoreCredenziali.Model.Category;
import com.GestoreCredenziali.GestoreCredenziali.Repository.AmministratoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/amministratore")
public class AmministatoreController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    AmministratoreRepository repository;


    // metodo per amministratore, ritorna tutti gli account in stato di apertura (da approvare)
    // va aggiunta la parte di invio dei documenti e curriculum all'amministratore per la verifica
    @GetMapping("/da_approvare/list")
    public List<Account> getAllAccountToApprove() {

        List<Account> accounts = new ArrayList<>();
        repository.findAllByStato("da_approvare").forEach(accounts::add);
        return accounts;
    }

    // inizialmente frontend per amministratore chiede tutti tutti gli account da approvare
    // per ciascun account chiamerà una getCv e getId per ottenere i suoi documenti e curriculum
    @GetMapping("/cv/{fileName:.+}")
    public ResponseEntity<Resource> getCV(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType("/CV" + resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/ID/{fileName:.+}")
    public ResponseEntity<Resource> getDocumento(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType("/Documenti_identita" + resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // metodo per amministratore approva o disapprova la creazione di un account in base ai parametri dell'account
    // da valutare se aggiungere fattori di sicurezza tipo controllare se chi richiama il microservizio è un amministratore valido
    @PutMapping("/da_approvare/valuta/{id}")
    public ResponseEntity<Account> valutaAccount(@PathVariable("id") long id, @RequestBody String decisione) {

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

    @PutMapping("/blocca/{id}")
    public ResponseEntity<Account> bloccaAccount(@PathVariable("id") long id) {

        Optional<Account> c = repository.findById(id);

        if (c.isPresent()) {
            Account _account = c.get();
            _account.setStato("bloccato");
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
