package com.HelpMeAndYou.demo.Repository;
import com.HelpMeAndYou.demo.Model.Conto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContRepository extends CrudRepository<Conto, Long>{
    List<Conto> findBySaldo(float saldo);
}
