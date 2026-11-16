package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(
        UsuarioRepository usuarioRepository
    ){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(
        Long idUsuario
    ){
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> obtenerUsuarioPorCorreo(
        String correo
    ){
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario guardarUsuario(
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

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(
        Long idUsuario,
        Usuario usuario
    ){
        Usuario usuarioExistente = usuarioRepository
            .findById(idUsuario)
            .orElseThrow(
                () -> new RuntimeException(
                    "USUARIO NO ENCONTRADO"
                )
            );

        Optional<Usuario> usuarioConCorreo =
            usuarioRepository.findByCorreo(
                usuario.getCorreo()
            );

        if (
            usuarioConCorreo.isPresent()
            && !usuarioConCorreo
                .get()
                .getIdUsuario()
                .equals(idUsuario)
        ){
            throw new RuntimeException(
                "EL CORREO YA ESTA REGISTRADO"
            );
        }

        usuarioExistente.setNombre(
            usuario.getNombre()
        );

        usuarioExistente.setCorreo(
            usuario.getCorreo()
        );

        usuarioExistente.setPassword(
            usuario.getPassword()
        );

        usuarioExistente.setTelefono(
            usuario.getTelefono()
        );

        usuarioExistente.setDireccion(
            usuario.getDireccion()
        );

        usuarioExistente.setRol(
            usuario.getRol()
        );

        return usuarioRepository.save(
            usuarioExistente
        );
    }

    public void eliminarUsuario(
        Long idUsuario
    ){
        if (!usuarioRepository.existsById(idUsuario)){
            throw new RuntimeException(
                "USUARIO NO ENCONTRADO"
            );
        }

        usuarioRepository.deleteById(
            idUsuario
        );
    }
}