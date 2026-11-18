package cl.bajondelgorila.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.dto.ActualizarPerfilRequest;
import cl.bajondelgorila.backend.dto.CambiarRolRequest;
import cl.bajondelgorila.backend.dto.CrearUsuarioAdminRequest;
import cl.bajondelgorila.backend.dto.UsuarioResponse;
import cl.bajondelgorila.backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "Gestion de usuarios y perfil"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
        UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    @Operation(
        summary = "Obtener perfil",
        description = "Obtiene los datos del usuario autenticado"
    )
    public UsuarioResponse obtenerMiPerfil(){

        return usuarioService
            .obtenerMiPerfil();
    }

    @PutMapping("/me")
    @Operation(
        summary = "Actualizar perfil",
        description = "Permite al usuario autenticado modificar sus propios datos"
    )
    public UsuarioResponse actualizarMiPerfil(
        @RequestBody ActualizarPerfilRequest request
    ){

        return usuarioService
            .actualizarMiPerfil(
                request
            );
    }

    @GetMapping
    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene todos los usuarios sin exponer sus contrasenas"
    )
    public List<UsuarioResponse> obtenerUsuarios(){

        return usuarioService
            .obtenerUsuarios();
    }

    @GetMapping("/{idUsuario}")
    @Operation(
        summary = "Buscar usuario",
        description = "Obtiene un usuario mediante su identificador"
    )
    public UsuarioResponse obtenerUsuarioPorId(
        @PathVariable Long idUsuario
    ){

        return usuarioService
            .obtenerUsuarioPorId(
                idUsuario
            );
    }

    @PostMapping("/administrativos")
    @Operation(
        summary = "Crear usuario administrativo",
        description = "Permite crear cuentas de vendedor o administrador"
    )
    public UsuarioResponse crearUsuarioAdministrativo(
        @RequestBody CrearUsuarioAdminRequest request
    ){

        return usuarioService
            .crearUsuarioAdministrativo(
                request
            );
    }

    @PatchMapping("/{idUsuario}/rol")
    @Operation(
        summary = "Cambiar rol",
        description = "Permite al administrador otorgar o quitar roles"
    )
    public UsuarioResponse cambiarRol(
        @PathVariable Long idUsuario,
        @RequestBody CambiarRolRequest request
    ){

        return usuarioService
            .cambiarRol(
                idUsuario,
                request
            );
    }

    @DeleteMapping("/{idUsuario}")
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario excepto el administrador principal"
    )
    public ResponseEntity<Void> eliminarUsuario(
        @PathVariable Long idUsuario
    ){

        usuarioService.eliminarUsuario(
            idUsuario
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}