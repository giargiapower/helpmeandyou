package microservizi.gestione_magazzino.controller;

import microservizi.gestione_magazzino.model.Magazzino;
import microservizi.gestione_magazzino.model.Materiale;
import microservizi.gestione_magazzino.model.RequestHelp;
import microservizi.gestione_magazzino.repo.MagazzinoRepository;
import microservizi.gestione_magazzino.repo.MaterialeRepository;
import microservizi.gestione_magazzino.repo.RichiestaAiutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    MagazzinoRepository mag_repository;

    @Autowired
    MaterialeRepository mat_repository;

    @Autowired
    RichiestaAiutoRepository req_repository;

    public MagazzinoController(MagazzinoRepository repository) {
        this.mag_repository = repository;
    }

    @GetMapping("/{id}")
    public Magazzino getMagazzino(@PathVariable("id") long id) {
        Optional<Magazzino> m = mag_repository.findById(id);
        return m.orElse(null);
    }

    @GetMapping("/allMagazzini")
    public List<Magazzino> getAllMagazzini() {
        System.out.println("Lista di tutti i magazzini...");
        List<Magazzino> magazzini = new ArrayList<>();
        mag_repository.findAll().forEach(magazzini::add);
        return magazzini;
    }

    // Crea un magazzino
    @PostMapping("/create")
    public Magazzino postMagazzino(@RequestBody Magazzino magazzino) {
        System.out.println("Creazione magazzino " + magazzino.getLuogo() + "...");
        return mag_repository.save(new Magazzino(magazzino.getLuogo()));
    }

    // Aggiunge un materiale in un magazzino (id): questo materiale verrà aggiunto come nuovo nella tabella materiali
    @PostMapping("/create/{magazzino_id}/{materiale_nome}")
    public Magazzino postMaterialeInMagazzino(@PathVariable("magazzino_id") long mag_id, @PathVariable("materiale_nome") String materiale_nome) {
        System.out.println("Aggiungo materiale " + materiale_nome + " in magazzino di id: " + mag_id);
        Magazzino m = getMagazzino(mag_id);
        // Creare il materiale
        Materiale materiale = new Materiale(materiale_nome);
        mat_repository.save(materiale);

        // Controllo se esiste il magazzino
        if(m != null){
            System.out.println("Materiale " + materiale_nome + " aggiunto con successo.");
            materiale.setMagazzino(m);
            return mag_repository.save(m);
        } else {
            System.out.println("Magazzino inesistente! Impossibile aggiungere il materiale.");
            return null;
        }
    }

    // Elimina un materiale in un magazzino (id)
    @DeleteMapping("/delete/{magazzino_id}/{materiale_nome}")
    public Magazzino deleteMaterialeInMagazzino(@PathVariable("magazzino_id") long mag_id, @PathVariable("materiale_nome") String materiale_nome) {
        System.out.println("Rimuovo materiale " + materiale_nome + " in magazzino di id: " + mag_id);
        Magazzino m = getMagazzino(mag_id);

        // Elimino il materiale solo se non è in uso in nessuna richiesta
        // Cerco tutti i materiali con quel nome
        List<Materiale> materiali = mat_repository.findByNome(materiale_nome);

        // Da questa lista andiamo ad eliminare quei materiali che sono in qualche richiesta di aiuto futura
        for(Materiale mat:materiali) {
           Optional<RequestHelp> req = req_repository.findById(mat.getId());
           if(req.isPresent() && !req.get().getState().equals("completata"))
               materiali.remove(mat);
        }

        // Controllo se esiste il magazzino
        if(m != null){
//            System.out.println("Materiale " + materiale_nome + " rimosso con successo.");
            mat_repository.deleteById(materiali.get(0).getId());
            return mag_repository.save(m);
        } else {
//            System.out.println("Magazzino inesistente! Impossibile aggiungere il materiale.");
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMagazzino(@PathVariable("id") long id) {
        System.out.println("Rimozione Magazzino con ID = " + id + "...");
        mag_repository.deleteById(id);
        return new ResponseEntity<>("Magazzino è stato rimosso!", HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteAllMagazzini() {
        System.out.println("Rimuovi tutti i magazzini...");
        mag_repository.deleteAll();
        return new ResponseEntity<>("Tutti i magazzini sono stati rimossi!", HttpStatus.OK);
    }

    @GetMapping(value = "/luogo/{luogo}")
    public List<Magazzino> findByLuogo(@PathVariable String luogo) {
        return mag_repository.findByLuogo(luogo);
    }

    @GetMapping(value = "/nome/{nome}")
    public List<Materiale> findByNome(@PathVariable String nome) { return mat_repository.findByNome(nome); }
}
