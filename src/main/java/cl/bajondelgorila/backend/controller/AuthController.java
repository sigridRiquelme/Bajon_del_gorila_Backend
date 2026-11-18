package cl.bajondelgorila.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.dto.AuthResponse;
import cl.bajondelgorila.backend.dto.LoginRequest;
import cl.bajondelgorila.backend.dto.RegistroRequest;
import cl.bajondelgorila.backend.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
    name = "Autenticacion",
    description = "Registro e inicio de sesion de usuarios"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(
        AuthService authService
    ){
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(
        summary = "Registrar cliente",
        description = "Registra un nuevo usuario con rol CLIENTE"
    )
    public AuthResponse registrar(
        @RequestBody RegistroRequest registroRequest
    ){
        return authService.registrar(
            registroRequest
        );
    }

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesion",
        description = "Valida las credenciales y genera un token JWT"
    )
    public AuthResponse login(
        @RequestBody LoginRequest loginRequest
    ){
        return authService.login(
            loginRequest
        );
    }
}