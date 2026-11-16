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

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(
        PedidoService pedidoService
    ){
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> obtenerPedidos(){
        return pedidoService.obtenerPedidos();
    }

    @GetMapping("/{idPedido}")
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
    public List<DetallePedido> obtenerDetallesPorPedido(
        @PathVariable Long idPedido
    ){
        return pedidoService.obtenerDetallesPorPedido(
            idPedido
        );
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Pedido> obtenerPedidosPorUsuario(
        @PathVariable Long idUsuario
    ){
        return pedidoService.obtenerPedidosPorUsuario(
            idUsuario
        );
    }

    @PostMapping
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