package cl.bajondelgorila.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.bajondelgorila.backend.model.Pedido;

public interface PedidoRepository
    extends JpaRepository<Pedido, Long> {

    List<Pedido> findByUsuarioIdUsuario(
        Long idUsuario
    );

    List<Pedido> findByCorreo(
        String correo
    );
}