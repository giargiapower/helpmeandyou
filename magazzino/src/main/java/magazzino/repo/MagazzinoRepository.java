package magazzino.repo;

import magazzino.model.Magazzino;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MagazzinoRepository extends CrudRepository<Magazzino, Long> {

	List<Magazzino> findByProvincia(String provincia);
}
