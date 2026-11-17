package cl.bajondelgorila.backend.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
    description = "Gestion y consulta de pedidos"
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

        return pedidoService
            .obtenerPedidos();
    }

    @GetMapping("/{idPedido}")
    @Operation(
        summary = "Buscar pedido",
        description = "Obtiene un pedido por su identificador"
    )
    public Pedido obtenerPedidoPorId(
        @PathVariable Long idPedido
    ){

        return pedidoService
            .obtenerPedidoPorId(
                idPedido
            )
            .orElseThrow(
                () -> new RuntimeException(
                    "PEDIDO NO ENCONTRADO"
                )
            );
    }

    @GetMapping("/{idPedido}/detalles")
    @Operation(
        summary = "Obtener detalle del pedido",
        description = "Obtiene los productos asociados a un pedido"
    )
    public List<DetallePedido> obtenerDetallesPedido(
        @PathVariable Long idPedido
    ){

        return pedidoService
            .obtenerDetallesPedido(
                idPedido
            );
    }

    @PostMapping
    @PreAuthorize(
        "isAnonymous() or hasRole('CLIENTE')"
    )
    @Operation(
        summary = "Crear pedido",
        description = "Permite crear pedidos a invitados y clientes autenticados"
    )
    public Pedido crearPedido(
        @RequestBody PedidoRequest request
    ){

        return pedidoService
            .guardarPedido(
                request.getPedido(),
                request.getDetalles()
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