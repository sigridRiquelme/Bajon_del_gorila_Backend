package cl.bajondelgorila.backend.dto;

public class ActualizarPerfilRequest {

    private String nombre;

    private String correo;

    private String telefono;

    private String direccion;

    private String password;

    public ActualizarPerfilRequest(){

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

    public String getPassword(){
        return password;
    }

    public void setPassword(
        String password
    ){
        this.password = password;
    }
}