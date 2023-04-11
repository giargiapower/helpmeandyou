package microservizi.gestione_magazzino.repo;

import microservizi.gestione_magazzino.model.Materiale;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MaterialeRepository extends CrudRepository<Materiale, Long> {
    List<Materiale> findByNome(String nome);
}