package cl.bajondelgorila.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(
    name = "Usuarios",
    description = "Gestion de usuarios del sistema"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
        UsuarioService usuarioService
    ){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene todos los usuarios registrados"
    )
    public List<Usuario> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{idUsuario}")
    @Operation(
        summary = "Buscar usuario",
        description = "Obtiene un usuario mediante su identificador"
    )
    public ResponseEntity<Usuario> obtenerUsuarioPorId(
        @PathVariable Long idUsuario
    ){
        return usuarioService
            .obtenerUsuarioPorId(idUsuario)
            .map(ResponseEntity::ok)
            .orElse(
                ResponseEntity.notFound().build()
            );
    }

    @PostMapping
    @Operation(
        summary = "Crear usuario",
        description = "Registra un nuevo usuario"
    )
    public Usuario guardarUsuario(
        @RequestBody Usuario usuario
    ){
        return usuarioService.guardarUsuario(
            usuario
        );
    }

    @PutMapping("/{idUsuario}")
    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente"
    )
    public Usuario actualizarUsuario(
        @PathVariable Long idUsuario,
        @RequestBody Usuario usuario
    ){
        return usuarioService.actualizarUsuario(
            idUsuario,
            usuario
        );
    }

    @DeleteMapping("/{idUsuario}")
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario mediante su identificador"
    )
    public ResponseEntity<Void> eliminarUsuario(
        @PathVariable Long idUsuario
    ){
        usuarioService.eliminarUsuario(
            idUsuario
        );

        return ResponseEntity.noContent().build();
    }
}