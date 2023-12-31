package com.HelpMeAndYou.demo.Controller;

import com.HelpMeAndYou.demo.Repository.ContRepository;
import com.HelpMeAndYou.demo.Model.Conto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
@RestController
@RequestMapping("/api/v1")
public class ContoController {
    @Autowired
    ContRepository repository;

    @GetMapping("/conto/{id}")
    public Conto getConto(@PathVariable("id") long id) {
        Optional<Conto> c = repository.findById(id);

        if (c.isPresent()) {
            Conto conto = c.get();
            return conto;
        } else {
            return null;
        }
    }

    @GetMapping("/conti")
    public List<Conto> getAllConti() {
//        System.out.println("Get all Conti...");

        List<Conto> conti = new ArrayList<>();
        repository.findAll().forEach(conti::add);

        return conti;
    }
    @PostMapping(value = "/conto/create")
    public Conto postConto(@RequestBody Conto conto) {

        Conto c = repository.save(new Conto(conto.getSaldo()));
        return c;
    }
    @PutMapping("/conto/add/{id}")
    public ResponseEntity<Conto> updateConto(@PathVariable("id") long id, @RequestBody Conto conto) {
//        System.out.println("Update com.example.Spring2.Conto with ID = " + id + "...");

        Optional<Conto> c = repository.findById(id);

        if (c.isPresent()) {
            Conto contoGet = c.get();
            contoGet.addSaldo(conto.getSaldo());

            return new ResponseEntity<>(repository.save(contoGet), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
