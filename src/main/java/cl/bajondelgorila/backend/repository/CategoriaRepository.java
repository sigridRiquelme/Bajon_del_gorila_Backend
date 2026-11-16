package cl.bajondelgorila.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.bajondelgorila.backend.model.Categoria;

public interface CategoriaRepository
    extends JpaRepository<Categoria, Long> {

}