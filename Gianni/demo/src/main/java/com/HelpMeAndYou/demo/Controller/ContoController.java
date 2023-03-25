package com.HelpMeAndYou.demo.Controller;


import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ContoController {

   // @Autowired
    //ContoRepository repository;

    /**   @PostMapping(value = "/conto/create")
    public Conto postConto(@RequestBody Conto conto) {

        Conto c = repository.save(new Conto(conto.getSaldo()));
        return c;
    }
     @PutMapping("/customers/add/{id}")
    public ResponseEntity<Conto> updateConto(@PathVariable("id") long id, @RequestBody int saldo_aggiuntivo) {
        System.out.println("Update com.example.Spring2.Conto with ID = " + id + "...");

        Optional<Conto> c = repository.findById(id);

        if (c.isPresent()) {
            Conto _conto = c.get();
            _conto.addSaldo(saldo_aggiuntivo);

            return new ResponseEntity<>(repository.save(_conto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
