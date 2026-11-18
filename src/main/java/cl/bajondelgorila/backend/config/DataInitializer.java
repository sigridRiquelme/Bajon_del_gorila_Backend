package cl.bajondelgorila.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.repository.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder
    ){
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args){

        boolean existeAdminPrincipal =
            usuarioRepository
                .existsByAdministradorPrincipalTrue();

        if (!existeAdminPrincipal){

            Usuario admin =
                new Usuario();

            admin.setNombre(
                "Administrador Principal"
            );

            admin.setCorreo(
                "admin@bajondelgorila.cl"
            );

            admin.setPassword(
                passwordEncoder.encode(
                    "admin123"
                )
            );

            admin.setTelefono(
                ""
            );

            admin.setDireccion(
                ""
            );

            admin.setRol(
                "ADMINISTRADOR"
            );

            admin.setAdministradorPrincipal(
                true
            );

            usuarioRepository.save(
                admin
            );

            System.out.println(
                "Administrador principal creado correctamente"
            );
        }
    }
}