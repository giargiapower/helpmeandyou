package requests.repository;

import org.springframework.data.repository.CrudRepository;
import requests.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
     Account findByEmail(String email);
}
