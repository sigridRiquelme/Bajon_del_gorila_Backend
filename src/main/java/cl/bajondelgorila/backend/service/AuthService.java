package cl.bajondelgorila.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.dto.AuthResponse;
import cl.bajondelgorila.backend.dto.LoginRequest;
import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.repository.UsuarioRepository;
import cl.bajondelgorila.backend.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(
        Usuario usuario
    ){

        if (
            usuarioRepository.existsByCorreo(
                usuario.getCorreo()
            )
        ){
            throw new RuntimeException(
                "EL CORREO YA ESTA REGISTRADO"
            );
        }

        usuario.setPassword(
            passwordEncoder.encode(
                usuario.getPassword()
            )
        );

        usuario.setRol(
            "CLIENTE"
        );

        Usuario usuarioGuardado =
            usuarioRepository.save(
                usuario
            );

        String token =
            jwtService.generarToken(
                usuarioGuardado
            );

        return new AuthResponse(
            token,
            usuarioGuardado.getIdUsuario(),
            usuarioGuardado.getNombre(),
            usuarioGuardado.getCorreo(),
            usuarioGuardado.getRol()
        );
    }

    public AuthResponse login(
        LoginRequest loginRequest
    ){

        Usuario usuario =
            usuarioRepository
                .findByCorreo(
                    loginRequest.getCorreo()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "CORREO O CONTRASENA INCORRECTOS"
                    )
                );

        boolean passwordCorrecta =
            passwordEncoder.matches(
                loginRequest.getPassword(),
                usuario.getPassword()
            );

        if (!passwordCorrecta){
            throw new RuntimeException(
                "CORREO O CONTRASENA INCORRECTOS"
            );
        }

        String token =
            jwtService.generarToken(
                usuario
            );

        return new AuthResponse(
            token,
            usuario.getIdUsuario(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol()
        );
    }
}