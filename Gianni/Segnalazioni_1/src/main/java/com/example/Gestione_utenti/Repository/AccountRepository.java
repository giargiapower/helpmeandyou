package com.example.Gestione_utenti.Repository;

import com.example.Gestione_utenti.Model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);

    List<Account> findAllByStato(String stato);
}
