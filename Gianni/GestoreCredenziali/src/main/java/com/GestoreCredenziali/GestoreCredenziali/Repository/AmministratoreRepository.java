package com.GestoreCredenziali.GestoreCredenziali.Repository;


import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AmministratoreRepository extends CrudRepository<Account, Long> {
    Account findByEmail(String email);

    List<Account> findAllByStato(String stato);
}

