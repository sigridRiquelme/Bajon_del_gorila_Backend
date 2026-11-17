package cl.bajondelgorila.backend.dto;

public class UsuarioResponse {

    private Long idUsuario;

    private String nombre;

    private String correo;

    private String telefono;

    private String direccion;

    private String rol;

    private Boolean administradorPrincipal;

    public UsuarioResponse(){

    }

    public UsuarioResponse(
        Long idUsuario,
        String nombre,
        String correo,
        String telefono,
        String direccion,
        String rol,
        Boolean administradorPrincipal
    ){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
        this.administradorPrincipal = administradorPrincipal;
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

    public Boolean getAdministradorPrincipal(){
        return administradorPrincipal;
    }

    public void setAdministradorPrincipal(
        Boolean administradorPrincipal
    ){
        this.administradorPrincipal = administradorPrincipal;
    }
}