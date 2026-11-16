package cl.bajondelgorila.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 150)
    private String correo;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 20)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    @Column(nullable = false, length = 20)
    private String rol;

    public Usuario(){

    }

    public Usuario(
        Long idUsuario,
        String nombre,
        String correo,
        String password,
        String telefono,
        String direccion,
        String rol
    ){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
    }

    public Long getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(
        Long idUsuario
    ){
        this.idUsuario = idUsuario;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(
        String nombre
    ){
        this.nombre = nombre;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(
        String correo
    ){
        this.correo = correo;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(
        String password
    ){
        this.password = password;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(
        String telefono
    ){
        this.telefono = telefono;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setDireccion(
        String direccion
    ){
        this.direccion = direccion;
    }

    public String getRol(){
        return rol;
    }

    public void setRol(
        String rol
    ){
        this.rol = rol;
    }
}