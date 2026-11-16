package cl.bajondelgorila.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bajondelgorila.backend.model.Producto;

public interface ProductoRepository
    extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaIdCategoria(
        Long idCategoria
    );

    List<Producto> findByEstado(
        String estado
    );

    List<Producto> findByPromocionTrue();
}