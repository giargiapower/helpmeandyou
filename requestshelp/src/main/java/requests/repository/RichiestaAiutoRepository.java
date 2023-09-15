package requests.repository;

import org.springframework.data.repository.CrudRepository;
import requests.model.RichiestaAiuto;
import java.util.List;

public interface RichiestaAiutoRepository extends CrudRepository<RichiestaAiuto, Long> {

	List<RichiestaAiuto> findAllByStato(String stato);
}
