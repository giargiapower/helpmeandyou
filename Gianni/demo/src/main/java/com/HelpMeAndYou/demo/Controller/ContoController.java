package com.HelpMeAndYou.demo.Controller;


import com.HelpMeAndYou.demo.Model.Conto;
import com.HelpMeAndYou.demo.Repository.ContoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class ContoController {

    @Autowired
    ContoRepository repository;

    @PostMapping(value = "/conto/create")
    public Conto postCustomer(@RequestBody Conto conto) {

        Conto _customer = repository.save(new Conto(conto.getConto(), conto.getSaldo()));
        return _customer;
    }

    @PutMapping("/customers/{add}")
    public ResponseEntity<Conto> updateConto(@PathVariable("id") long id, @RequestBody int saldo_aggiuntivo) {
        System.out.println("Update com.example.Spring2.Conto with ID = " + id + "...");

        Optional<Conto> contoData = repository.findById(id);

        if (contoData.isPresent()) {
            Conto _customer = contoData.get();
            _customer.addSaldo(saldo_aggiuntivo);

            return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
