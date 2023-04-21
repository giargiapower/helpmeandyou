package magazzino.controller;

import magazzino.model.Materiale;
import magazzino.model.RichiestaAiutoMat;
import magazzino.repo.MaterialeRepository;
import magazzino.model.Magazzino;
import magazzino.repo.MagazzinoRepository;
import magazzino.repo.RichiestaAiutoMatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api/magazzini")
public class MagazzinoController {
	@Autowired
	MagazzinoRepository magRepository;

	@Autowired
	MaterialeRepository matRepository;

	@Autowired
	RichiestaAiutoMatRepository reqRepository;

	public MagazzinoController(MagazzinoRepository repository) {
		this.magRepository = repository;
	}

	@GetMapping("/{id}")
	public Magazzino getMagazzino(@PathVariable("id") long id) {
		Optional<Magazzino> m = magRepository.findById(id);
		return m.orElse(null);
	}

	@GetMapping("/allMagazzini")
	public List<Magazzino> getAllMagazzini() {
		System.out.println("Lista di tutti i magazzini...");
		List<Magazzino> magazzini = new ArrayList<>();
		magRepository.findAll().forEach(magazzini::add);
		return magazzini;
	}

	// Crea un magazzino
	@PostMapping("/create")
	public Magazzino postMagazzino(@RequestBody Magazzino magazzino) {
		System.out.println("Creazione magazzino :" + magazzino);
		return magRepository.save(new Magazzino(magazzino.getRegione(), magazzino.getProvincia(), magazzino.getCitta(), magazzino.getIndirizzo()));
	}

	// Aggiunge un materiale in un magazzino (id): questo materiale verrà aggiunto come nuovo nella tabella materiali
	@PostMapping("/create/{magazzino_id}/{materiale_nome}")
	public Magazzino postMaterialeInMagazzino(@PathVariable("magazzino_id") long mag_id, @PathVariable("materiale_nome") String materiale_nome) {
		System.out.println("Aggiungo materiale " + materiale_nome + " in magazzino di id: " + mag_id);
		Magazzino m = getMagazzino(mag_id);

		// Controllo se esiste il magazzino
		if(m != null){
			// Creare il materiale
			Materiale materiale = new Materiale(materiale_nome);
			matRepository.save(materiale);
			System.out.println("Materiale " + materiale_nome + " aggiunto con successo.");
			materiale.setMagazzino(m);
			return magRepository.save(m);
		} else {
			System.out.println("Magazzino inesistente! Impossibile aggiungere il materiale.");
			return null;
		}
	}

	// put in cui data la richiesta di aiuto prende il materiale e a questo aggiunge la richiesta nella lista
	@PutMapping("/aggiungi/richiesta")
	public Materiale aggiungiRichiesta(@RequestBody RichiestaAiutoMat richiestaAiutoMat) {
		Materiale material = richiestaAiutoMat.getMateriale();
		material.aggiungiRichiesta(richiestaAiutoMat);
		// salva il materiale con la nuova richiesta
		return matRepository.save(material);
	}


	// Fornita una data e una provincia mi restituisce tutti i materiali disponibili in quella provincia e in quel giorno
	@GetMapping("/materiali/{provincia}/{data}")
	public List<Materiale> getMaterialiDisponibili(@PathVariable("provincia") String provincia, @PathVariable("data") LocalDate data) {
		List<Materiale> materialiDisponibili = new ArrayList<>();
		List<Magazzino> magazzini = magRepository.findByProvincia(provincia);
		boolean disponibile;
		for (Magazzino magazzino : magazzini) {
			for (Materiale materiale : magazzino.getMateriali()) {
				disponibile = true;
				for (RichiestaAiutoMat richiestaAiutoMat : materiale.getRichieste()) {
					if (richiestaAiutoMat.getGiorno().isEqual(data)) {
						disponibile = false;
						break;
					}
					// se la richiesta di aiuto è stata fatta in un giorno precedente a quello in cui si esegue la get, allora questa richiesta deve essere eliminata
					// lo faccio qui per diminuire il costo di dover fare altrettanti cicli for altrove
					if (richiestaAiutoMat.getGiorno().isBefore(LocalDate.now())) {
						materiale.getRichieste().remove(richiestaAiutoMat);
						reqRepository.delete(richiestaAiutoMat);
					}
				}
				if (disponibile)
					materialiDisponibili.add(materiale);
			}
		}
		return materialiDisponibili;
	}


	// Elimina un materiale in un magazzino (id)
	@DeleteMapping("/delete/{magazzino_id}/{materiale_id}")
	public Map<String, Boolean> deleteMaterialeInMagazzino(@PathVariable("magazzino_id") long mag_id, @PathVariable("materiale_id") long mat_id) {
		Optional<Magazzino> m = magRepository.findById(mag_id);
		Map<String, Boolean> risposta = new HashMap<>();
		// Controllo se esiste il magazzino
		if(m.isPresent()){
			// Elimino il materiale solo se non è in uso in nessuna richiesta
			Optional<Materiale> materiale = matRepository.findById(mat_id);
			if (materiale.isPresent() && ! materiale.get().inUso()){
				matRepository.deleteById(materiale.get().getId());
				magRepository.save(m.get());
				risposta.put("deleted", Boolean.TRUE);
			} else
				risposta.put("not deleted", Boolean.FALSE);
		} else
			risposta.put("not deleted", Boolean.FALSE);
		return risposta;

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMagazzino(@PathVariable("id") long id) {
		System.out.println("Rimozione Magazzino con ID = " + id + "...");
		magRepository.deleteById(id);
		return new ResponseEntity<>("Magazzino è stato rimosso!", HttpStatus.OK);
	}

	@DeleteMapping("delete")
	public ResponseEntity<String> deleteAllMagazzini() {
		System.out.println("Rimuovi tutti i magazzini...");
		magRepository.deleteAll();
		return new ResponseEntity<>("Tutti i magazzini sono stati rimossi!", HttpStatus.OK);
	}

	@GetMapping(value = "/nome/{nome}")
	public List<Materiale> findByNome(@PathVariable String nome) { return matRepository.findByNome(nome); }
}
