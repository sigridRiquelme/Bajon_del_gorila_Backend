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
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    @ManyToOne
    @JoinColumn(
        name = "id_usuario",
        nullable = true
    )
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String cliente;

    @Column(nullable = false, length = 150)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 20)
    private String fecha;

    @Column(nullable = false, length = 10)
    private String hora;

    @Column(nullable = false, length = 30)
    private String tipoEntrega;

    @Column(length = 255)
    private String direccion;

    @Column(length = 100)
    private String comuna;

    @Column(length = 255)
    private String referencia;

    @Column(nullable = false, length = 30)
    private String medioPago;

    @Column(nullable = false)
    private Integer subtotal;

    @Column(nullable = false)
    private Integer costoDelivery;

    @Column(nullable = false)
    private Integer neto;

    @Column(nullable = false)
    private Integer iva;

    @Column(nullable = false)
    private Integer total;

    public Pedido(){

    }

    public Pedido(
        Long idPedido,
        Usuario usuario,
        String cliente,
        String correo,
        String telefono,
        String fecha,
        String hora,
        String tipoEntrega,
        String direccion,
        String comuna,
        String referencia,
        String medioPago,
        Integer subtotal,
        Integer costoDelivery,
        Integer neto,
        Integer iva,
        Integer total
    ){
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.cliente = cliente;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.tipoEntrega = tipoEntrega;
        this.direccion = direccion;
        this.comuna = comuna;
        this.referencia = referencia;
        this.medioPago = medioPago;
        this.subtotal = subtotal;
        this.costoDelivery = costoDelivery;
        this.neto = neto;
        this.iva = iva;
        this.total = total;
    }

    public Long getIdPedido(){
        return idPedido;
    }

    public void setIdPedido(
        Long idPedido
    ){
        this.idPedido = idPedido;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(
        Usuario usuario
    ){
        this.usuario = usuario;
    }

    public String getCliente(){
        return cliente;
    }

    public void setCliente(
        String cliente
    ){
        this.cliente = cliente;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(
        String correo
    ){
        this.correo = correo;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(
        String telefono
    ){
        this.telefono = telefono;
    }

    public String getFecha(){
        return fecha;
    }

    public void setFecha(
        String fecha
    ){
        this.fecha = fecha;
    }

    public String getHora(){
        return hora;
    }

    public void setHora(
        String hora
    ){
        this.hora = hora;
    }

    public String getTipoEntrega(){
        return tipoEntrega;
    }

    public void setTipoEntrega(
        String tipoEntrega
    ){
        this.tipoEntrega = tipoEntrega;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(
        String direccion
    ){
        this.direccion = direccion;
    }

    public String getComuna(){
        return comuna;
    }

    public void setComuna(
        String comuna
    ){
        this.comuna = comuna;
    }

    public String getReferencia(){
        return referencia;
    }

    public void setReferencia(
        String referencia
    ){
        this.referencia = referencia;
    }

    public String getMedioPago(){
        return medioPago;
    }

    public void setMedioPago(
        String medioPago
    ){
        this.medioPago = medioPago;
    }

    public Integer getSubtotal(){
        return subtotal;
    }

    public void setSubtotal(
        Integer subtotal
    ){
        this.subtotal = subtotal;
    }

    public Integer getCostoDelivery(){
        return costoDelivery;
    }

    public void setCostoDelivery(
        Integer costoDelivery
    ){
        this.costoDelivery = costoDelivery;
    }

    public Integer getNeto(){
        return neto;
    }

    public void setNeto(
        Integer neto
    ){
        this.neto = neto;
    }

    public Integer getIva(){
        return iva;
    }

    public void setIva(
        Integer iva
    ){
        this.iva = iva;
    }

    public Integer getTotal(){
        return total;
    }

    public void setTotal(
        Integer total
    ){
        this.total = total;
    }
}