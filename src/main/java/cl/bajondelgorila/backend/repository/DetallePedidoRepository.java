package cl.bajondelgorila.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bajondelgorila.backend.model.DetallePedido;

public interface DetallePedidoRepository
    extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedidoIdPedido(
        Long idPedido
    );

    boolean existsByProductoIdProducto(
        Long idProducto
    );
}