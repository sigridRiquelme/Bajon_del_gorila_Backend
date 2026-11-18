package cl.bajondelgorila.backend.dto;

import java.util.List;

import cl.bajondelgorila.backend.model.DetallePedido;
import cl.bajondelgorila.backend.model.Pedido;

public class PedidoClienteResponse {

    private Pedido pedido;

    private List<DetallePedido> detalles;

    public PedidoClienteResponse(){

    }

    public PedidoClienteResponse(
        Pedido pedido,
        List<DetallePedido> detalles
    ){
        this.pedido = pedido;
        this.detalles = detalles;
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