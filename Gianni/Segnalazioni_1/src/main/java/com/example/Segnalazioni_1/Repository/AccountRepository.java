package com.example.Segnalazioni_1.Repository;

import com.example.Segnalazioni_1.Model.Account;
import com.example.Segnalazioni_1.Model.Segnalazioni;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);

    List<Account> findAllByStato(String stato);
}
