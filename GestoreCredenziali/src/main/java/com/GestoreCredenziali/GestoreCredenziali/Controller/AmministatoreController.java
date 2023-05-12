package com.GestoreCredenziali.GestoreCredenziali.Controller;

import com.GestoreCredenziali.GestoreCredenziali.File.FileController;
import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageService;
import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import com.GestoreCredenziali.GestoreCredenziali.Model.Amministratore;
import com.GestoreCredenziali.GestoreCredenziali.Model.Category;
import com.GestoreCredenziali.GestoreCredenziali.RabbitSender;
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


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/amministratore")
public class AmministatoreController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    AmministratoreRepository repository;

    @Autowired
    RabbitSender rabbitSender = new RabbitSender();


    // metodo per amministratore, ritorna tutti gli account in stato di apertura (da approvare)
    // va aggiunta la parte di invio dei documenti e curriculum all'amministratore per la verifica
    @GetMapping("/da_approvare/list")
    public List<Account> getAllAccountToApprove() {
        List<Account> accounts = new ArrayList<>();
        repository.findAllByStato("da_approvare").forEach(accounts::add);
        return accounts;
    }

    // inizialmente frontend per amministratore chiede tutti gli account da approvare
    // per ciascun account chiamerà una getCv e getId per ottenere i suoi documenti e curriculum
    @GetMapping("/cv")
    public ResponseEntity<Resource> getCV(@RequestPart("fileName")  String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource( "Documents/CV/" + fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
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

    @GetMapping("/id")
    public ResponseEntity<Resource> getDocumento(@RequestPart("fileName") String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource("Documents/Documenti_identita/" + fileName);

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
        Account _account = repository.findById(id);
        if (_account!=null){
            if (decisione.contains("approva")) {
                _account.setStato("approvato");
                // manda l'account approvato al miscorsevizio di richieste di aiuto
                rabbitSender.send(_account);
            } else {
                _account.setStato("bloccato");
                //elimino i file caricati
                fileStorageService.deleteFile(_account.getPath_curriculum());
                fileStorageService.deleteFile(_account.getPath_documento());
            }
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/aggiorna_categoria/{id}")
    public ResponseEntity<Account> aggiorna_categoria(@PathVariable("id") long id, @RequestBody Category category) {
        Account _account = repository.findById(id);

        if (_account!=null){
            _account.setCategory(category);
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/blocca/{id}")
    public ResponseEntity<Account> bloccaAccount(@PathVariable("id") long id) {
        Account _account = repository.findById(id);

        if (_account!=null){
            _account.setStato("bloccato");
            return new ResponseEntity<>(repository.save(_account), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<Amministratore> login(@RequestBody Amministratore amministratore) {
        Account accountEsistente = repository.findByEmail(amministratore.getEmail());
        if (accountEsistente != null && accountEsistente.getPassword().equals(amministratore.getPassword()) && accountEsistente.getStato().equals("approvato")) {
            return ResponseEntity.ok().body(amministratore);
        } else {
            // L'account non è presente o password errata
            return ResponseEntity.badRequest().body(null);
        }
    }
}
