package cl.bajondelgorila.backend.dto;

public class CambiarRolRequest {

    private String rol;

    public CambiarRolRequest(){

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