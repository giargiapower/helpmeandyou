package requests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import requests.model.Account;
import requests.model.Categoria;
import requests.model.Indirizzo;
import requests.model.RichiestaAiuto;
import requests.repository.AccountRepository;
import requests.repository.CategoriaRepository;
import requests.repository.IndirizzoRepository;
import requests.repository.RichiestaAiutoRepository;
import java.util.*;


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/richiesteaiuto")
public class RichiestaAiutoController {
	@Autowired
	RichiestaAiutoRepository richiestaAiutoRepository;

	@Autowired
	IndirizzoRepository indirizzoRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CategoriaRepository categoriaRepository;


	@GetMapping("/richiesta/{id}")
	public RichiestaAiuto getRichiestaById(@PathVariable(value = "id") long richiestaId) {
		Optional <RichiestaAiuto> richiesta = richiestaAiutoRepository.findById(richiestaId);
		return richiesta.orElse(null);
	}

	@GetMapping("/richieste")
	public List<RichiestaAiuto> getAllRichieste() {
		List <RichiestaAiuto> richieste = new ArrayList<>();
		richiestaAiutoRepository.findAll().forEach(richieste::add);
		return richieste;
	}

	@GetMapping("/richiestepubblicate/not/{id}")
	public List<RichiestaAiuto> getRichiestePubblicate(@PathVariable(value = "id") long accountId) {
		List<RichiestaAiuto> richieste = new ArrayList<>(richiestaAiutoRepository.findAllByStato("pubblicata"));
		richieste.removeIf(richiesta -> richiesta.getPubAccount().getId() == accountId || richiesta.getGiorno().isBefore(java.time.LocalDate.now()));
		return richieste;
	}


	@GetMapping("/categorie")
	public List<Categoria> getAllCategorie() {
		List<Categoria> categories = new ArrayList<>();
		categoriaRepository.findAll().forEach(categories::add);
		return categories;
	}


	@PostMapping("/richiesta/crea")
	public RichiestaAiuto creaRichiesta(@RequestBody RichiestaAiuto richiestaAiuto){
		RichiestaAiuto fin = new RichiestaAiuto(richiestaAiuto.getDescrizione(), richiestaAiuto.getGiorno(), richiestaAiuto.getIndirizzo(), richiestaAiuto.getIdMateriale(), richiestaAiuto.getPubAccount(), richiestaAiuto.getCategoria());

		// Crea sempre un nuovo oggetto indirizzo: il costo di tempo per andarlo a cercare sarebbe maggiore del costo in termini di spazio:
		// ipotizziamo infatti che uno stesso utente non faccia troppe richieste di aiuto, ma che si tratti di aiuti occasionali.
		Indirizzo ind = richiestaAiuto.getIndirizzo();
		indirizzoRepository.save(ind);

		return richiestaAiutoRepository.save(fin);
	}

	@PutMapping("/richiesta/accetta/{ric_id}/{account_id}")
	public ResponseEntity<RichiestaAiuto> accettaRichiesta(@PathVariable(value = "ric_id") long richiestaId, @PathVariable(value = "account_id") long accountId) {
		Optional <RichiestaAiuto> richiesta = richiestaAiutoRepository.findById(richiestaId);

		if (richiesta.isPresent()) {
			RichiestaAiuto existingRequest = richiesta.get();

			Optional <Account> accAccount = accountRepository.findById(accountId);
			if(accAccount.isPresent()){
				existingRequest.setAccAccount(accAccount.get());
				return new ResponseEntity<>(richiestaAiutoRepository.save(existingRequest), HttpStatus.OK);

			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/richiesta/termina/{id}")
	public ResponseEntity<RichiestaAiuto> terminaRichiesta(@PathVariable(value = "id") long richiestaId) {
		Optional <RichiestaAiuto> request = richiestaAiutoRepository.findById(richiestaId);

		if (request.isPresent()) {
			RichiestaAiuto existingRequest = request.get();
			existingRequest.setStato("terminata");
			return new ResponseEntity<>(richiestaAiutoRepository.save(existingRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/categoria/crea")
	public Categoria creaCategoria(@RequestBody Categoria categoria){
		Categoria fin = new Categoria(categoria.getTipo());
		return categoriaRepository.save(fin);
	}

	// Serve nel caso in cui venga bloccato un utente a fronte di segnalazione
	@DeleteMapping("/richiesta/elimina/{id}")
	public Map<String, Boolean> eliminaRichiesta(@PathVariable(value = "id") long richiestaId){
		RichiestaAiuto richiesta = richiestaAiutoRepository.findById(richiestaId).orElseThrow(() -> new ResourceNotFoundException("Richiesta non trovata con id: " + richiestaId));
		// quando elimino una richiesta elimino anche l'indirizzo a lui associato
		Indirizzo indirizzo = indirizzoRepository.findById(richiesta.getIndirizzo().getId()).orElseThrow(() -> new ResourceNotFoundException("Richiesta non trovata con id: " + richiesta.getIndirizzo().getId()));
		richiestaAiutoRepository.delete(richiesta);
		indirizzoRepository.delete(indirizzo);
		Map<String, Boolean> risposta = new HashMap<>();
		risposta.put("deleted", Boolean.TRUE);
		return risposta;
	}

	@DeleteMapping("/categoria/elimina")
	public ResponseEntity<Categoria> eliminaCategoria (@RequestBody Categoria categoria){
		Categoria cat = categoriaRepository.findByTipo(categoria.getTipo());
		categoriaRepository.delete(cat);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
