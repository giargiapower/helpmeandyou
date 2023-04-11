package microservizi.gestione_magazzino.repo;

import microservizi.gestione_magazzino.model.Magazzino;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MagazzinoRepository extends CrudRepository<Magazzino, Long> {
    List<Magazzino> findByLuogo(String Luogo);
}
