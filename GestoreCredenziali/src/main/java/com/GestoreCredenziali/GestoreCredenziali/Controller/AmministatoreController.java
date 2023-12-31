package com.GestoreCredenziali.GestoreCredenziali.Controller;

import com.GestoreCredenziali.GestoreCredenziali.File.FileController;
import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageService;
import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import com.GestoreCredenziali.GestoreCredenziali.Model.Amministratore;
import com.GestoreCredenziali.GestoreCredenziali.Model.Categoria;
import com.GestoreCredenziali.GestoreCredenziali.RabbitSender;
import com.GestoreCredenziali.GestoreCredenziali.Repository.AmministratoreRepository;
import com.GestoreCredenziali.GestoreCredenziali.Repository.CategoriaRepository;
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


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/amministratore")
public class AmministatoreController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	AmministratoreRepository repository;

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	RabbitSender rabbitSender = new RabbitSender();

	@GetMapping("/utenti")
	public List<Account> getAllUtenti() {
		List<Account> accounts = new ArrayList<>(repository.findAllByStato("approvato"));
		accounts.addAll(repository.findAllByStato("da_approvare"));
		accounts.addAll(repository.findAllByStato("in_aggiornamento"));
		accounts.addAll(repository.findAllByStato("bloccato"));
		return accounts;
	}

	// Prende le informazioni di un account dato il suo id
	@GetMapping("/utente/{id}")
	public ResponseEntity<Account> getInfoUtente(@PathVariable("id") long idUtente) {
		Account account = repository.findById(idUtente);
		if (account != null) {
			return new ResponseEntity<>(account, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// metodo per amministratore, ritorna tutti gli account in stato di apertura (da approvare)
	// va aggiunta la parte di invio dei documenti e curriculum all'amministratore per la verifica
	@GetMapping("/da_approvare/list")
	public List<Account> getAllAccountToApprove() {
		List<Account> accounts = new ArrayList<>();
		accounts.addAll(repository.findAllByStato("da_approvare"));
		accounts.addAll(repository.findAllByStato("in_aggiornamento"));
		return accounts;
	}

	// inizialmente frontend per amministratore chiede tutti gli account da approvare
	// per ciascun account chiamerà una getCv e getId per ottenere i suoi documenti e curriculum
	@GetMapping("/cv")
	public ResponseEntity<Resource> getCV(@RequestParam("fileName")  String fileName, HttpServletRequest request) {
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
	public ResponseEntity<Resource> getDocumento(@RequestParam("fileName") String fileName, HttpServletRequest request) {
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

	@GetMapping("/categorie")
	public List<Categoria> getAllCategorie() {
		List<Categoria> categories = new ArrayList<>();
		categoriaRepository.findAll().forEach(categories::add);
		return categories;
	}

	@PostMapping("/login")
	public ResponseEntity<Amministratore> login(@RequestBody Amministratore amministratore) {
		Account accountEsistente = repository.findByEmail(amministratore.getEmail());
		if (accountEsistente != null && accountEsistente.getPassword().equals(amministratore.getPassword()) && accountEsistente.getStato().equals("approvato")) {
			return ResponseEntity.ok().body(amministratore);
		} else {
			// L'account non è presente o password errata
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/categoria/crea")
	public Categoria creaCategoria(@RequestBody Categoria categoria){
		Categoria fin = new Categoria(categoria.getTipo());
		return categoriaRepository.save(fin);
	}

	// metodo per amministratore approva o disapprova la creazione di un account in base ai parametri dell'account
	// da valutare se aggiungere fattori di sicurezza tipo controllare se chi richiama il microservizio è un amministratore valido
	@PutMapping("/da_approvare/valuta/{id}")
	public ResponseEntity<Account> valutaAccount(@PathVariable("id") long id, @RequestBody String decisione) {
		Account account = repository.findById(id);
		if (account!=null){
			if (decisione.contains("approva")) {
				account.setStato("approvato");
				// manda l'account approvato al miscorsevizio di richieste di aiuto
				rabbitSender.send(account);
			} else {
				account.setStato("bloccato");
				//elimino i file caricati
				fileStorageService.deleteFile(account.getPath_curriculum());
				fileStorageService.deleteFile(account.getPath_documento());
			}
			return new ResponseEntity<>(repository.save(account), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/aggiorna_categoria/{id}")
	public ResponseEntity<Account> aggiornaCategoria(@PathVariable("id") long id, @RequestBody Categoria categoria) {
		Account account = repository.findById(id);
		Categoria cat = categoriaRepository.findByTipo(categoria.getTipo());

		if (account!=null && cat!=null){
			account.setCategoria(cat);
			return new ResponseEntity<>(repository.save(account), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/blocca/{id}")
	public ResponseEntity<Account> bloccaAccount(@PathVariable("id") long id) {
		Account account = repository.findById(id);

		if (account!=null){
			account.setStato("bloccato");
			return new ResponseEntity<>(repository.save(account), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/modifica_dati/{id}")
	public ResponseEntity<Account> modificaDati(@PathVariable("id") long id, @RequestBody Account account) {
		Account accountEsistente = repository.findById(id);
		if (accountEsistente!=null){
			accountEsistente.setEmail(account.getEmail());
			accountEsistente.setTelefono(account.getTelefono());
			return new ResponseEntity<>(repository.save(accountEsistente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/categoria/elimina")
	public ResponseEntity<Categoria> eliminaCategoria (@RequestBody Categoria categoria){
		Categoria cat = categoriaRepository.findByTipo(categoria.getTipo());
		categoriaRepository.delete(cat);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/account/elimina/{id}")
	public ResponseEntity<Account> eliminaAccount (@PathVariable("id") long id){
		Account account = repository.findById(id);
		if (account!=null){
			repository.delete(account);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
