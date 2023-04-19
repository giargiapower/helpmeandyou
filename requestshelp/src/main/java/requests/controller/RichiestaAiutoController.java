package requests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import requests.model.RichiestaAiuto;
import requests.repository.RichiestaAiutoRepository;
import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/richiesteaiuto")
public class RichiestaAiutoController {

	@Autowired
	RichiestaAiutoRepository repository;

	@GetMapping("/richiesta/{id}")
	public RichiestaAiuto getRichiestaById(@PathVariable(value = "id") long richiestaId) {
		Optional <RichiestaAiuto> richiesta = repository.findById(richiestaId);
		return richiesta.orElse(null);
	}

	@GetMapping("/richieste")
	public List<RichiestaAiuto> getAllRichieste() {
		List <RichiestaAiuto> richieste = new ArrayList<>();
		repository.findAll().forEach(richieste::add);
		return richieste;
	}

	@PostMapping("/richiesta/crea")
	public RichiestaAiuto creaRichiesta(@RequestBody RichiestaAiuto richiestaAiuto){
		RichiestaAiuto fin = new RichiestaAiuto(richiestaAiuto.getDescrizione(), richiestaAiuto.getGiorno(), richiestaAiuto.getPlace());
		fin.setPubAccount(richiestaAiuto.getPubAccount());
		fin.setCategoria(richiestaAiuto.getCategoria());
		return repository.save(fin);
	}

	@PutMapping("/richiesta/accetta/{id}")
	public ResponseEntity<RichiestaAiuto> accettaRichiesta(@PathVariable(value = "id") long richiestaId, @RequestBody RichiestaAiuto richiestaAiuto) {
		Optional <RichiestaAiuto> richiesta = repository.findById(richiestaId);

		if (richiesta.isPresent()) {
			RichiestaAiuto existingRequest = richiesta.get();
			existingRequest.setAccAccount(richiestaAiuto.getAccAccount());
			existingRequest.setMaterials(richiestaAiuto.getMaterials());
			return new ResponseEntity<>(repository.save(existingRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/richiesta/termina/{id}")
	public ResponseEntity<RichiestaAiuto> terminaRichiesta(@PathVariable(value = "id") long richiestaId) {
		Optional <RichiestaAiuto> request = repository.findById(richiestaId);

		if (request.isPresent()) {
			RichiestaAiuto existingRequest = request.get();
			existingRequest.setStato("terminata");
			return new ResponseEntity<>(repository.save(existingRequest), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Serve nel caso in cui venga bloccato un utente a fronte di segnalazione
	@DeleteMapping("/richiesta/elimina/{id}")
	public Map<String, Boolean> eliminaRichiesta(@PathVariable(value = "id") long richiestaId) throws Exception {
		RichiestaAiuto request = repository.findById(richiestaId).orElseThrow(() -> new ResourceNotFoundException("Richiesta non trovata con id: " + richiestaId));

		repository.delete(request);
		Map<String, Boolean> risposta = new HashMap<>();
		risposta.put("deleted", Boolean.TRUE);
		return risposta;
	}
}
