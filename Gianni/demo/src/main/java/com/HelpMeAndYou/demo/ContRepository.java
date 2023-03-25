package com.HelpMeAndYou.demo;
import com.HelpMeAndYou.demo.Conto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContRepository extends CrudRepository<Conto, Long>{
    List<Conto> findBySaldo(float saldo);
}
