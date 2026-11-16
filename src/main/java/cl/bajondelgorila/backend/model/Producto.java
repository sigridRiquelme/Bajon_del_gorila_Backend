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
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @ManyToOne
    @JoinColumn(
        name = "id_categoria",
        nullable = false
    )
    private Categoria categoria;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 500)
    private String imagen;

    @Column(nullable = false)
    private Boolean promocion;

    @Column(nullable = false)
    private Integer descuento;

    public Producto(){

    }

    public Producto(
        Long idProducto,
        Categoria categoria,
        String nombre,
        String descripcion,
        Integer precio,
        Integer stock,
        String estado,
        String imagen,
        Boolean promocion,
        Integer descuento
    ){
        this.idProducto = idProducto;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.imagen = imagen;
        this.promocion = promocion;
        this.descuento = descuento;
    }

    public Long getIdProducto(){
        return idProducto;
    }

    public void setIdProducto(
        Long idProducto
    ){
        this.idProducto = idProducto;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(
        Categoria categoria
    ){
        this.categoria = categoria;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(
        String nombre
    ){
        this.nombre = nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(
        String descripcion
    ){
        this.descripcion = descripcion;
    }

    public Integer getPrecio(){
        return precio;
    }

    public void setPrecio(
        Integer precio
    ){
        this.precio = precio;
    }

    public Integer getStock(){
        return stock;
    }

    public void setStock(
        Integer stock
    ){
        this.stock = stock;
    }

    public String getEstado(){
        return estado;
    }

    public void setEstado(
        String estado
    ){
        this.estado = estado;
    }

    public String getImagen(){
        return imagen;
    }

    public void setImagen(
        String imagen
    ){
        this.imagen = imagen;
    }

    public Boolean getPromocion(){
        return promocion;
    }

    public void setPromocion(
        Boolean promocion
    ){
        this.promocion = promocion;
    }

    public Integer getDescuento(){
        return descuento;
    }

    public void setDescuento(
        Integer descuento
    ){
        this.descuento = descuento;
    }
}