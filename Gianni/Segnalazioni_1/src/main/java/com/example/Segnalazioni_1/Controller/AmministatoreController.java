package com.example.Segnalazioni_1.Controller;

import com.example.Segnalazioni_1.File.FileController;
import com.example.Segnalazioni_1.File.FileStorageService;
import com.example.Segnalazioni_1.Model.Account;
import com.example.Segnalazioni_1.Model.Category;
import com.example.Segnalazioni_1.Model.Segnalazioni;
import com.example.Segnalazioni_1.Repository.AccountRepository;
import com.example.Segnalazioni_1.Repository.AmministratoreRepository;
import com.example.Segnalazioni_1.Repository.SegnalazioniRepository;
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

    @Autowired
    SegnalazioniRepository s_repository;

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
