package cl.bajondelgorila.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.dto.AuthResponse;
import cl.bajondelgorila.backend.dto.LoginRequest;
import cl.bajondelgorila.backend.dto.RegistroRequest;
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
        RegistroRequest registroRequest
    ){

        if (
            registroRequest.getNombre() == null
            || registroRequest.getNombre().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE ES OBLIGATORIO"
            );
        }

        if (
            registroRequest.getCorreo() == null
            || registroRequest.getCorreo().isBlank()
        ){
            throw new RuntimeException(
                "EL CORREO ES OBLIGATORIO"
            );
        }

        if (
            registroRequest.getPassword() == null
            || registroRequest.getPassword().isBlank()
        ){
            throw new RuntimeException(
                "LA CONTRASENA ES OBLIGATORIA"
            );
        }

        if (
            usuarioRepository.existsByCorreo(
                registroRequest.getCorreo()
            )
        ){
            throw new RuntimeException(
                "EL CORREO YA ESTA REGISTRADO"
            );
        }

        Usuario usuario =
            new Usuario();

        usuario.setNombre(
            registroRequest.getNombre()
        );

        usuario.setCorreo(
            registroRequest.getCorreo()
        );

        usuario.setPassword(
            passwordEncoder.encode(
                registroRequest.getPassword()
            )
        );

        usuario.setTelefono(
            registroRequest.getTelefono()
        );

        usuario.setDireccion(
            registroRequest.getDireccion()
        );

        usuario.setRol(
            "CLIENTE"
        );

        usuario.setAdministradorPrincipal(
            false
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