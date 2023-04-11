package microservizi.gestione_magazzino.controller;

import microservizi.gestione_magazzino.model.Magazzino;
import microservizi.gestione_magazzino.repo.MagazzinoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5432")
@RestController
@RequestMapping("/api/magazzini")
public class MagazzinoController {
    final
    MagazzinoRepository repository;

    public MagazzinoController(MagazzinoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Magazzino getMagazzino(@PathVariable("id") long id) {
        Optional<Magazzino> m = repository.findById(id);
        if(m.isPresent()) {
            Magazzino _m = m.get();
            return _m;
        } else return null;
    }

    @GetMapping("/allMagazzini")
    public List<Magazzino> getAllMagazzini() {
        System.out.println("Lista di tutti i magazzini...");
        List<Magazzino> magazzini = new ArrayList<>();
        repository.findAll().forEach(magazzini::add);
        return magazzini;
    }

    @PostMapping("/create")
    public Magazzino postMagazzino(@RequestBody Magazzino magazzino) {
        System.out.println("Creazione magazzino " + magazzino.getLuogo() + "...");
        return repository.save(new Magazzino(magazzino.getLuogo(), magazzino.getMateriali()));
    }

    @PostMapping("/create/{id}/{materiale}")
    public Magazzino postMaterialeInMagazzino(@PathVariable("id") long id, @PathVariable("materiale") String materiale) {
        System.out.println("Aggiungo materiale " + materiale + " in magazzino di id: " + id);
        Magazzino m = getMagazzino(id);

        // Controllo se esiste il magazzino
        if(m != null){
            System.out.println("Materiale " + materiale + " aggiunto con successo.");
            m.setMateriali(materiale);
            return repository.save(m);
        } else {
            System.out.println("Magazzino inesistente! Impossibile aggiungere il materiale.");
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMagazzino(@PathVariable("id") long id) {
        System.out.println("Rimozione Magazzino con ID = " + id + "...");
        repository.deleteById(id);
        return new ResponseEntity<>("Magazzino è stato rimosso!", HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAllMagazzini() {
        System.out.println("Rimuovi tutti i magazzini...");
        repository.deleteAll();
        return new ResponseEntity<>("Tutti i magazzini sono stati rimossi!", HttpStatus.OK);
    }

    @GetMapping(value = "/luogo/{luogo}")
    public List<Magazzino> findByLuogo(@PathVariable String luogo) {
        return repository.findByLuogo(luogo);
    }
}
