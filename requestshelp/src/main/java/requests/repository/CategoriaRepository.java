package requests.repository;

import org.springframework.data.repository.CrudRepository;
import requests.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
}
