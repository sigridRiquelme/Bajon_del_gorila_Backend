package cl.bajondelgorila.backend.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.bajondelgorila.backend.dto.ActualizarPerfilRequest;
import cl.bajondelgorila.backend.dto.CambiarRolRequest;
import cl.bajondelgorila.backend.dto.CrearUsuarioAdminRequest;
import cl.bajondelgorila.backend.dto.UsuarioResponse;
import cl.bajondelgorila.backend.model.Pedido;
import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.repository.PedidoRepository;
import cl.bajondelgorila.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PedidoRepository pedidoRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
        UsuarioRepository usuarioRepository,
        PedidoRepository pedidoRepository,
        PasswordEncoder passwordEncoder
    ){
        this.usuarioRepository = usuarioRepository;
        this.pedidoRepository = pedidoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> obtenerUsuarios(){

        return usuarioRepository
            .findAll()
            .stream()
            .map(
                this::convertirAResponse
            )
            .toList();
    }

    public UsuarioResponse obtenerUsuarioPorId(
        Long idUsuario
    ){

        Usuario usuario =
            buscarUsuario(
                idUsuario
            );

        return convertirAResponse(
            usuario
        );
    }

    public UsuarioResponse obtenerMiPerfil(){

        Usuario usuario =
            obtenerUsuarioAutenticado();

        return convertirAResponse(
            usuario
        );
    }

    public UsuarioResponse actualizarMiPerfil(
        ActualizarPerfilRequest request
    ){

        Usuario usuario =
            obtenerUsuarioAutenticado();

        validarDatosPerfil(
            request
        );

        Usuario usuarioConCorreo =
            usuarioRepository
                .findByCorreo(
                    request.getCorreo()
                )
                .orElse(
                    null
                );

        if (
            usuarioConCorreo != null
            && !usuarioConCorreo
                .getIdUsuario()
                .equals(
                    usuario.getIdUsuario()
                )
        ){
            throw new RuntimeException(
                "EL CORREO YA ESTA REGISTRADO"
            );
        }

        usuario.setNombre(
            request.getNombre()
        );

        usuario.setCorreo(
            request.getCorreo()
        );

        usuario.setTelefono(
            request.getTelefono()
        );

        usuario.setDireccion(
            request.getDireccion()
        );

        if (
            request.getPassword() != null
            && !request.getPassword().isBlank()
        ){
            usuario.setPassword(
                passwordEncoder.encode(
                    request.getPassword()
                )
            );
        }

        Usuario usuarioGuardado =
            usuarioRepository.save(
                usuario
            );

        return convertirAResponse(
            usuarioGuardado
        );
    }

    public UsuarioResponse crearUsuarioAdministrativo(
        CrearUsuarioAdminRequest request
    ){

        validarUsuarioAdministrativo(
            request
        );

        if (
            usuarioRepository.existsByCorreo(
                request.getCorreo()
            )
        ){
            throw new RuntimeException(
                "EL CORREO YA ESTA REGISTRADO"
            );
        }

        Usuario usuario =
            new Usuario();

        usuario.setNombre(
            request.getNombre()
        );

        usuario.setCorreo(
            request.getCorreo()
        );

        usuario.setPassword(
            passwordEncoder.encode(
                request.getPassword()
            )
        );

        usuario.setTelefono(
            request.getTelefono()
        );

        usuario.setDireccion(
            request.getDireccion()
        );

        usuario.setRol(
            request.getRol()
        );

        usuario.setAdministradorPrincipal(
            false
        );

        Usuario usuarioGuardado =
            usuarioRepository.save(
                usuario
            );

        return convertirAResponse(
            usuarioGuardado
        );
    }

    public UsuarioResponse cambiarRol(
        Long idUsuario,
        CambiarRolRequest request
    ){

        Usuario usuario =
            buscarUsuario(
                idUsuario
            );

        if (
            Boolean.TRUE.equals(
                usuario.getAdministradorPrincipal()
            )
        ){
            throw new RuntimeException(
                "NO SE PUEDE MODIFICAR EL ROL DEL ADMINISTRADOR PRINCIPAL"
            );
        }

        Usuario usuarioActual =
            obtenerUsuarioAutenticado();

        if (
            usuarioActual
                .getIdUsuario()
                .equals(
                    usuario.getIdUsuario()
                )
        ){
            throw new RuntimeException(
                "NO PUEDES MODIFICAR TU PROPIO ROL"
            );
        }

        validarRol(
            request.getRol()
        );

        usuario.setRol(
            request.getRol()
        );

        Usuario usuarioGuardado =
            usuarioRepository.save(
                usuario
            );

        return convertirAResponse(
            usuarioGuardado
        );
    }

    @Transactional
    public void eliminarUsuario(
        Long idUsuario
    ){

        Usuario usuario =
            buscarUsuario(
                idUsuario
            );

        if (
            Boolean.TRUE.equals(
                usuario.getAdministradorPrincipal()
            )
        ){
            throw new RuntimeException(
                "NO SE PUEDE ELIMINAR EL ADMINISTRADOR PRINCIPAL"
            );
        }

        Usuario usuarioActual =
            obtenerUsuarioAutenticado();

        if (
            usuarioActual
                .getIdUsuario()
                .equals(
                    usuario.getIdUsuario()
                )
        ){
            throw new RuntimeException(
                "NO PUEDES ELIMINAR TU PROPIA CUENTA"
            );
        }

        List<Pedido> pedidos =
            pedidoRepository
                .findByUsuarioIdUsuario(
                    idUsuario
                );

        for (Pedido pedido : pedidos){

            pedido.setUsuario(
                null
            );
        }

        pedidoRepository.saveAll(
            pedidos
        );

        usuarioRepository.delete(
            usuario
        );
    }

    private Usuario buscarUsuario(
        Long idUsuario
    ){

        return usuarioRepository
            .findById(
                idUsuario
            )
            .orElseThrow(
                () -> new RuntimeException(
                    "USUARIO NO ENCONTRADO"
                )
            );
    }

    private Usuario obtenerUsuarioAutenticado(){

        Authentication authentication =
            SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (
            authentication == null
            || !authentication.isAuthenticated()
        ){
            throw new RuntimeException(
                "USUARIO NO AUTENTICADO"
            );
        }

        return usuarioRepository
            .findByCorreo(
                authentication.getName()
            )
            .orElseThrow(
                () -> new RuntimeException(
                    "USUARIO NO ENCONTRADO"
                )
            );
    }

    private void validarDatosPerfil(
        ActualizarPerfilRequest request
    ){

        if (
            request.getNombre() == null
            || request.getNombre().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE ES OBLIGATORIO"
            );
        }

        if (
            request.getCorreo() == null
            || request.getCorreo().isBlank()
        ){
            throw new RuntimeException(
                "EL CORREO ES OBLIGATORIO"
            );
        }
    }

    private void validarUsuarioAdministrativo(
        CrearUsuarioAdminRequest request
    ){

        if (
            request.getNombre() == null
            || request.getNombre().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE ES OBLIGATORIO"
            );
        }

        if (
            request.getCorreo() == null
            || request.getCorreo().isBlank()
        ){
            throw new RuntimeException(
                "EL CORREO ES OBLIGATORIO"
            );
        }

        if (
            request.getPassword() == null
            || request.getPassword().isBlank()
        ){
            throw new RuntimeException(
                "LA CONTRASENA ES OBLIGATORIA"
            );
        }

        if (
            !request.getRol().equals(
                "ADMINISTRADOR"
            )
            && !request.getRol().equals(
                "VENDEDOR"
            )
        ){
            throw new RuntimeException(
                "SOLO SE PUEDEN CREAR CUENTAS DE ADMINISTRADOR O VENDEDOR"
            );
        }
    }

    private void validarRol(
        String rol
    ){

        if (
            rol == null
            || (
                !rol.equals(
                    "ADMINISTRADOR"
                )
                && !rol.equals(
                    "VENDEDOR"
                )
                && !rol.equals(
                    "CLIENTE"
                )
            )
        ){
            throw new RuntimeException(
                "ROL NO VALIDO"
            );
        }
    }

    private UsuarioResponse convertirAResponse(
        Usuario usuario
    ){

        return new UsuarioResponse(
            usuario.getIdUsuario(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getTelefono(),
            usuario.getDireccion(),
            usuario.getRol(),
            usuario.getAdministradorPrincipal()
        );
    }
}