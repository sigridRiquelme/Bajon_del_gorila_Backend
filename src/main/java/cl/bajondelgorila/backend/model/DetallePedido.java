package cl.bajondelgorila.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(
        name = "id_pedido",
        nullable = false
    )
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(
        name = "id_producto",
        nullable = false
    )
    private Producto producto;

    @Column(nullable = false, length = 150)
    private String nombreProducto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precioUnitario;

    @Column(nullable = false)
    private Integer subtotal;

    public DetallePedido(){

    }

    public DetallePedido(
        Long idDetalle,
        Pedido pedido,
        Producto producto,
        String nombreProducto,
        Integer cantidad,
        Integer precioUnitario,
        Integer subtotal
    ){
        this.idDetalle = idDetalle;
        this.pedido = pedido;
        this.producto = producto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Long getIdDetalle(){
        return idDetalle;
    }

    public void setIdDetalle(
        Long idDetalle
    ){
        this.idDetalle = idDetalle;
    }

    public Pedido getPedido(){
        return pedido;
    }

    public void setPedido(
        Pedido pedido
    ){
        this.pedido = pedido;
    }

    public Producto getProducto(){
        return producto;
    }

    public void setProducto(
        Producto producto
    ){
        this.producto = producto;
    }

    public String getNombreProducto(){
        return nombreProducto;
    }

    public void setNombreProducto(
        String nombreProducto
    ){
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad(){
        return cantidad;
    }

    public void setCantidad(
        Integer cantidad
    ){
        this.cantidad = cantidad;
    }

    public Integer getPrecioUnitario(){
        return precioUnitario;
    }

    public void setPrecioUnitario(
        Integer precioUnitario
    ){
        this.precioUnitario = precioUnitario;
    }

    public Integer getSubtotal(){
        return subtotal;
    }

    public void setSubtotal(
        Integer subtotal
    ){
        this.subtotal = subtotal;
    }
}