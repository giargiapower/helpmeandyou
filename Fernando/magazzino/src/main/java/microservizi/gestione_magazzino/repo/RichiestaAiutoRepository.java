package microservizi.gestione_magazzino.repo;

import microservizi.gestione_magazzino.model.RequestHelp;
import org.springframework.data.repository.CrudRepository;

public interface RichiestaAiutoRepository extends CrudRepository<RequestHelp, Long>{

}
