package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.bajondelgorila.backend.model.DetallePedido;
import cl.bajondelgorila.backend.model.Pedido;
import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.repository.DetallePedidoRepository;
import cl.bajondelgorila.backend.repository.PedidoRepository;
import cl.bajondelgorila.backend.repository.ProductoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    private final DetallePedidoRepository detallePedidoRepository;

    private final ProductoRepository productoRepository;

    public PedidoService(
        PedidoRepository pedidoRepository,
        DetallePedidoRepository detallePedidoRepository,
        ProductoRepository productoRepository
    ){
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<Pedido> obtenerPedidos(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedidoPorId(
        Long idPedido
    ){
        return pedidoRepository.findById(
            idPedido
        );
    }

    public List<Pedido> obtenerPedidosPorUsuario(
        Long idUsuario
    ){
        return pedidoRepository.findByUsuarioIdUsuario(
            idUsuario
        );
    }

    public List<Pedido> obtenerPedidosPorCorreo(
        String correo
    ){
        return pedidoRepository.findByCorreo(
            correo
        );
    }

    public List<DetallePedido> obtenerDetallesPorPedido(
        Long idPedido
    ){
        return detallePedidoRepository.findByPedidoIdPedido(
            idPedido
        );
    }

    @Transactional
    public Pedido guardarPedido(
        Pedido pedido,
        List<DetallePedido> detalles
    ){
        Pedido pedidoGuardado = pedidoRepository.save(
            pedido
        );

        for (DetallePedido detalle : detalles){

            Producto producto = productoRepository
                .findById(
                    detalle
                        .getProducto()
                        .getIdProducto()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "PRODUCTO NO ENCONTRADO"
                    )
                );

            if (
                producto.getStock()
                < detalle.getCantidad()
            ){
                throw new RuntimeException(
                    "STOCK INSUFICIENTE PARA "
                    + producto.getNombre()
                );
            }

            Integer nuevoStock =
                producto.getStock()
                - detalle.getCantidad();

            producto.setStock(
                nuevoStock
            );

            if (nuevoStock == 0){
                producto.setEstado(
                    "SIN STOCK"
                );
            }

            productoRepository.save(
                producto
            );

            detalle.setPedido(
                pedidoGuardado
            );

            detalle.setProducto(
                producto
            );

            detalle.setNombreProducto(
                producto.getNombre()
            );

            detalle.setPrecioUnitario(
                producto.getPrecio()
            );

            detalle.setSubtotal(
                producto.getPrecio()
                * detalle.getCantidad()
            );

            detallePedidoRepository.save(
                detalle
            );
        }

        return pedidoGuardado;
    }
}