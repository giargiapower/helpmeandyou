package magazzino.controller;

import magazzino.model.Materiale;
import magazzino.model.RichiestaAiutoMat;
import magazzino.repo.MaterialeRepository;
import magazzino.model.Magazzino;
import magazzino.repo.MagazzinoRepository;
import magazzino.repo.RichiestaAiutoMatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/magazzini")
public class MagazzinoController {
	@Autowired
	MagazzinoRepository magRepository;

	@Autowired
	MaterialeRepository matRepository;

	@Autowired
	RichiestaAiutoMatRepository ricRepository;

	public MagazzinoController(MagazzinoRepository repository) {
		this.magRepository = repository;
	}

	// Restituisce tutti i magazzini
	@GetMapping("/magazzini")
	public List<Magazzino> getAllMagazzini() {
		List<Magazzino> magazzini = new ArrayList<>();
		magRepository.findAll().forEach(magazzini::add);
		return magazzini;
	}

	// TODO: da sistemare, restituisce materiali ripetuti con lo stesso id
	// Restituisce tutte le informazioni di un magazzino dato il suo id
	@GetMapping("/{id}")
	public Magazzino getMagazzino(@PathVariable("id") long id) {
		Optional<Magazzino> m = magRepository.findById(id);
		return m.orElse(null);
	}

	// TODO: da sistemare, restituisce materiali ripetuti con lo stesso id
	// Restituisce tutti i materiali di un magazzino dato il suo id
	@GetMapping("/{id_magazzino}/materiali")
	public ResponseEntity<List<Materiale>> getAllMateriali(@PathVariable("id_magazzino") long magId) {
		Optional<Magazzino> magazzino = magRepository.findById(magId);
		if (magazzino.isPresent()) {
			List<Materiale> materiali = magazzino.get().getMateriali();
			return new ResponseEntity<>(materiali, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Fornita una data e una provincia mi restituisce tutti i materiali disponibili in quella provincia e in quel giorno
	@GetMapping("/materiali/{provincia}/{data}")
	public ResponseEntity<List<Materiale>> getMaterialiDisponibili(@PathVariable("provincia") String provincia, @PathVariable("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		// Prende tutti i magazzini della provincia
		List<Magazzino> magazzini = magRepository.findByProvincia(provincia);
		List<Materiale> materialiDisponibili = new ArrayList<>();
		boolean materialeDisponibile;
		for (Magazzino magazzino : magazzini) {
			// Per ogni magazzino prende tutti i materiali
			for (Materiale materiale : magazzino.getMateriali()) {
				// Per ogni materiale controlla se è disponibile in quel giorno
				materialeDisponibile = true;
				for (RichiestaAiutoMat richiestaAiutoMat : materiale.getRichieste()) {
					// Se la richiesta di aiuto è stata fatta in un giorno precedente a quello attuale (in cui si esegue la get), allora questa richiesta deve essere eliminata
					// Lo faccio qui per diminuire il costo di dover fare altrettanti cicli for altrove
					// Anche se idealmente sarebbe più furbo farlo es. in modo batch la notte
					if (richiestaAiutoMat.getGiorno().isBefore(LocalDate.now())) {
						ricRepository.delete(richiestaAiutoMat);
						materialeDisponibile = false;
					} else if (richiestaAiutoMat.getGiorno().isEqual(data)) {
						materialeDisponibile = false;
						break;
					}
				}
				if (materialeDisponibile)
					materialiDisponibili.add(materiale);
			}
		}
		return new ResponseEntity<>(materialiDisponibili, HttpStatus.OK);
	}

	// Crea un magazzino
	@PostMapping("/create")
	public Magazzino postMagazzino(@RequestBody Magazzino magazzino) {
		return magRepository.save(new Magazzino(magazzino.getRegione(), magazzino.getProvincia(), magazzino.getCitta(), magazzino.getIndirizzo()));
	}

	// Aggiunge un materiale in un magazzino (id): questo materiale verrà aggiunto come nuovo nella tabella materiali
	@PutMapping("/create/{magazzino_id}/{materiale_nome}")
	public ResponseEntity<Magazzino> postMaterialeInMagazzino(@PathVariable("magazzino_id") long magazzinoId, @PathVariable("materiale_nome") String nomeMateriale) {
		// Controllo se il magazzino esiste
		Optional<Magazzino> magazzino = magRepository.findById(magazzinoId);
		if(magazzino.isPresent()) {
			Materiale nuovoMateriale = new Materiale(nomeMateriale, magazzino.get());
			// Aggiungo il materiale al magazzino
			magazzino.get().aggiungiMateriale(nuovoMateriale);
			// Salvo il nuovo materiale sul database
			matRepository.save(nuovoMateriale);
			// Salvo le modifiche al magazzino sul database
			magRepository.save(magazzino.get());
			return new ResponseEntity<>(magazzino.get(), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// put in cui data la richiesta di aiuto prende il materiale e a questo aggiunge la richiesta nella lista
	@PutMapping("/aggiungi/{materiale_id}/richiesta")
	public ResponseEntity<Materiale> aggiungiRichiesta(@PathVariable("materiale_id") long matId, @RequestBody RichiestaAiutoMat richiestaAiutoMat) {
		Optional<Materiale> materiale = matRepository.findById(matId);
		if (materiale.isPresent()) {
			RichiestaAiutoMat newRichiesta = new RichiestaAiutoMat(richiestaAiutoMat.getGiorno(), richiestaAiutoMat.getProvincia(), materiale.get());
			// Aggiungo la richiesta alla lista di richieste del materiale
			materiale.get().aggiungiRichiesta(newRichiesta);
			// Salvo la richiesta sul database
			ricRepository.save(newRichiesta);
			// Salvo le modifiche sul database
			matRepository.save(materiale.get());
			return new ResponseEntity<>(materiale.get(), HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Elimina tutti i magazzini
	@DeleteMapping("/elimina")
	public ResponseEntity<String> eliminaMagazzini() {
		List<Magazzino> magazzini = (List<Magazzino>) magRepository.findAll();
		for (Magazzino magazzino: magazzini)
			eliminaMagazzino(magazzino.getId());
		return new ResponseEntity<>("Tutti i magazzini sono stati rimossi!", HttpStatus.OK);
	}

	// Elimina un magazzino dato il suo id
	@DeleteMapping("/elimina/{magazzino_id}")
	public ResponseEntity<String> eliminaMagazzino(@PathVariable("magazzino_id") long magId) {
		Optional<Magazzino> magazzino = magRepository.findById(magId);
		if (magazzino.isPresent()) {
			// elimina ogni materiale del magazzino
			for (Materiale materiale : magazzino.get().getMateriali())
				eliminaMaterialeInMagazzino(magId, materiale.getId());
			magRepository.deleteById(magId);
			return new ResponseEntity<>("Magazzino è stato rimosso!", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Magazzino non trovato!", HttpStatus.NOT_FOUND);
	}

	// TODO: idealmente quando si elimina un materiale occorre inviare un messaggio a tutte le richieste che utilizzavano quel materiale
	// Elimina un materiale in un magazzino dati entrambi gli id
	@DeleteMapping("/elimina/{magazzino_id}/{materiale_id}")
	public ResponseEntity<Magazzino> eliminaMaterialeInMagazzino(@PathVariable("magazzino_id") long magId, @PathVariable("materiale_id") long matId) {
		Optional<Magazzino> magazzino = magRepository.findById(magId);
		// Controllo se esiste il magazzino
		if(magazzino.isPresent()){
			Magazzino mag = magazzino.get();
			// Elimino il materiale dal magazzino
			Optional<Materiale> materiale = matRepository.findById(matId);
			if (materiale.isPresent() && mag.getMateriali().contains(materiale.get())) {
				// Se il materiale è in uso elimino le richieste di aiuto a cui è associato
				if (materiale.get().inUso()){
					// TODO: invia messaggio a tutte le richieste che utilizzano quel materiale
					ricRepository.deleteAll(materiale.get().getRichieste());
				}
				mag.rimuoviMateriale(materiale.get());
				magRepository.save(mag);
				matRepository.deleteById(matId);
				return new ResponseEntity<>(mag, HttpStatus.OK);
			} else
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Elimina una richiesta di aiuto dato il suo id
	@DeleteMapping("/elimina/richiesta/{richiesta_id}")
	public ResponseEntity<String> eliminaRichiestaAiutoMat(@PathVariable("richiesta_id") long ricId) {
		Optional<RichiestaAiutoMat> richiesta = ricRepository.findById(ricId);
		if (richiesta.isPresent()) {
			ricRepository.deleteById(ricId);
			return new ResponseEntity<>("Richiesta di aiuto rimossa!", HttpStatus.OK);
		}
		else
			return new ResponseEntity<>("Richiesta di aiuto non trovata!", HttpStatus.NOT_FOUND);
	}
}
