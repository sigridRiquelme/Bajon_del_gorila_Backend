package cl.bajondelgorila.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.model.DetallePedido;
import cl.bajondelgorila.backend.model.Pedido;
import cl.bajondelgorila.backend.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(
    name = "Pedidos",
    description = "Gestion de pedidos y sus detalles"
)
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(
        PedidoService pedidoService
    ){
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(
        summary = "Listar pedidos",
        description = "Obtiene todos los pedidos registrados"
    )
    public List<Pedido> obtenerPedidos(){
        return pedidoService.obtenerPedidos();
    }

    @GetMapping("/{idPedido}")
    @Operation(
        summary = "Buscar pedido",
        description = "Obtiene un pedido mediante su identificador"
    )
    public ResponseEntity<Pedido> obtenerPedidoPorId(
        @PathVariable Long idPedido
    ){
        return pedidoService
            .obtenerPedidoPorId(idPedido)
            .map(ResponseEntity::ok)
            .orElse(
                ResponseEntity.notFound().build()
            );
    }

    @GetMapping("/{idPedido}/detalles")
    @Operation(
        summary = "Obtener detalles de un pedido",
        description = "Obtiene los productos y cantidades asociados a un pedido"
    )
    public List<DetallePedido> obtenerDetallesPorPedido(
        @PathVariable Long idPedido
    ){
        return pedidoService.obtenerDetallesPorPedido(
            idPedido
        );
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(
        summary = "Listar pedidos por usuario",
        description = "Obtiene los pedidos asociados a un usuario registrado"
    )
    public List<Pedido> obtenerPedidosPorUsuario(
        @PathVariable Long idUsuario
    ){
        return pedidoService.obtenerPedidosPorUsuario(
            idUsuario
        );
    }

    @PostMapping
    @Operation(
        summary = "Crear pedido",
        description = "Registra un pedido, sus detalles y actualiza el stock de los productos"
    )
    public Pedido guardarPedido(
        @RequestBody PedidoRequest pedidoRequest
    ){
        return pedidoService.guardarPedido(
            pedidoRequest.getPedido(),
            pedidoRequest.getDetalles()
        );
    }

    public static class PedidoRequest {

        private Pedido pedido;

        private List<DetallePedido> detalles;

        public PedidoRequest(){

        }

        public Pedido getPedido(){
            return pedido;
        }

        public void setPedido(
            Pedido pedido
        ){
            this.pedido = pedido;
        }

        public List<DetallePedido> getDetalles(){
            return detalles;
        }

        public void setDetalles(
            List<DetallePedido> detalles
        ){
            this.detalles = detalles;
        }
    }
}