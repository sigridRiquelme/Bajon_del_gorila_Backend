package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.bajondelgorila.backend.model.DetallePedido;
import cl.bajondelgorila.backend.model.Pedido;
import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.model.Usuario;
import cl.bajondelgorila.backend.repository.DetallePedidoRepository;
import cl.bajondelgorila.backend.repository.PedidoRepository;
import cl.bajondelgorila.backend.repository.ProductoRepository;
import cl.bajondelgorila.backend.repository.UsuarioRepository;

@Service
public class PedidoService {

    private static final Integer COSTO_DELIVERY = 2000;

    private final PedidoRepository pedidoRepository;

    private final DetallePedidoRepository detallePedidoRepository;

    private final ProductoRepository productoRepository;

    private final UsuarioRepository usuarioRepository;

    public PedidoService(
        PedidoRepository pedidoRepository,
        DetallePedidoRepository detallePedidoRepository,
        ProductoRepository productoRepository,
        UsuarioRepository usuarioRepository
    ){
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Pedido> obtenerPedidos(){
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPedidoPorId(
        Long idPedido
    ){
        return pedidoRepository.findById(
            idPedido
        );
    }

    public List<Pedido> obtenerPedidosPorUsuario(
        Long idUsuario
    ){
        return pedidoRepository.findByUsuarioIdUsuario(
            idUsuario
        );
    }

    public List<Pedido> obtenerPedidosPorCorreo(
        String correo
    ){
        return pedidoRepository.findByCorreo(
            correo
        );
    }

    public List<DetallePedido> obtenerDetallesPedido(
        Long idPedido
    ){

        if (
            !pedidoRepository.existsById(
                idPedido
            )
        ){
            throw new RuntimeException(
                "PEDIDO NO ENCONTRADO"
            );
        }

        return detallePedidoRepository
            .findByPedidoIdPedido(
                idPedido
            );
    }

    @Transactional
    public Pedido guardarPedido(
        Pedido pedido,
        List<DetallePedido> detalles
    ){

        validarPedido(
            pedido,
            detalles
        );

        asignarUsuarioAlPedido(
            pedido
        );

        Integer subtotalPedido = 0;

        for (
            DetallePedido detalle : detalles
        ){

            Producto producto =
                obtenerProducto(
                    detalle
                );

            validarProductoDisponible(
                producto,
                detalle.getCantidad()
            );

            Integer precioUnitario =
                calcularPrecioProducto(
                    producto
                );

            Integer subtotalDetalle =
                precioUnitario
                * detalle.getCantidad();

            detalle.setProducto(
                producto
            );

            detalle.setNombreProducto(
                producto.getNombre()
            );

            detalle.setPrecioUnitario(
                precioUnitario
            );

            detalle.setSubtotal(
                subtotalDetalle
            );

            subtotalPedido +=
                subtotalDetalle;

            descontarStock(
                producto,
                detalle.getCantidad()
            );
        }

        Integer costoDelivery =
            calcularCostoDelivery(
                pedido.getTipoEntrega()
            );

        Integer total =
            subtotalPedido
            + costoDelivery;

        Integer neto =
            (int) Math.round(
                total / 1.19
            );

        Integer iva =
            total - neto;

        pedido.setSubtotal(
            subtotalPedido
        );

        pedido.setCostoDelivery(
            costoDelivery
        );

        pedido.setNeto(
            neto
        );

        pedido.setIva(
            iva
        );

        pedido.setTotal(
            total
        );

        Pedido pedidoGuardado =
            pedidoRepository.save(
                pedido
            );

        for (
            DetallePedido detalle : detalles
        ){

            detalle.setPedido(
                pedidoGuardado
            );

            detallePedidoRepository.save(
                detalle
            );
        }

        return pedidoGuardado;
    }

    private void validarPedido(
        Pedido pedido,
        List<DetallePedido> detalles
    ){

        if (
            pedido == null
        ){
            throw new RuntimeException(
                "EL PEDIDO ES OBLIGATORIO"
            );
        }

        if (
            detalles == null
            || detalles.isEmpty()
        ){
            throw new RuntimeException(
                "EL PEDIDO DEBE CONTENER AL MENOS UN PRODUCTO"
            );
        }

        if (
            pedido.getCliente() == null
            || pedido.getCliente().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE DEL CLIENTE ES OBLIGATORIO"
            );
        }

        if (
            pedido.getCorreo() == null
            || pedido.getCorreo().isBlank()
        ){
            throw new RuntimeException(
                "EL CORREO ES OBLIGATORIO"
            );
        }

        if (
            pedido.getFecha() == null
            || pedido.getFecha().isBlank()
        ){
            throw new RuntimeException(
                "LA FECHA DEL PEDIDO ES OBLIGATORIA"
            );
        }

        if (
            pedido.getHora() == null
            || pedido.getHora().isBlank()
        ){
            throw new RuntimeException(
                "LA HORA DEL PEDIDO ES OBLIGATORIA"
            );
        }

        if (
            pedido.getTipoEntrega() == null
            || (
                !pedido
                    .getTipoEntrega()
                    .equalsIgnoreCase(
                        "DELIVERY"
                    )
                && !pedido
                    .getTipoEntrega()
                    .equalsIgnoreCase(
                        "RETIRO"
                    )
            )
        ){
            throw new RuntimeException(
                "TIPO DE ENTREGA NO VALIDO"
            );
        }

        if (
            pedido.getTipoEntrega()
                .equalsIgnoreCase(
                    "DELIVERY"
                )
            && (
                pedido.getDireccion() == null
                || pedido.getDireccion().isBlank()
            )
        ){
            throw new RuntimeException(
                "LA DIRECCION ES OBLIGATORIA PARA DELIVERY"
            );
        }

        if (
            pedido.getMedioPago() == null
            || pedido.getMedioPago().isBlank()
        ){
            throw new RuntimeException(
                "EL MEDIO DE PAGO ES OBLIGATORIO"
            );
        }
    }

    private void asignarUsuarioAlPedido(
        Pedido pedido
    ){

        Authentication authentication =
            SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (
            authentication == null
            || !authentication.isAuthenticated()
            || authentication.getName().equals(
                "anonymousUser"
            )
        ){

            pedido.setUsuario(
                null
            );

            return;
        }

        Usuario usuario =
            usuarioRepository
                .findByCorreo(
                    authentication.getName()
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "USUARIO AUTENTICADO NO ENCONTRADO"
                    )
                );

        if (
            !usuario.getRol().equals(
                "CLIENTE"
            )
        ){
            throw new RuntimeException(
                "SOLO LOS CLIENTES PUEDEN REALIZAR PEDIDOS"
            );
        }

        pedido.setUsuario(
            usuario
        );

        pedido.setCliente(
            usuario.getNombre()
        );

        pedido.setCorreo(
            usuario.getCorreo()
        );

        pedido.setTelefono(
            usuario.getTelefono()
        );
    }

    private Producto obtenerProducto(
        DetallePedido detalle
    ){

        if (
            detalle.getProducto() == null
            || detalle
                .getProducto()
                .getIdProducto() == null
        ){
            throw new RuntimeException(
                "PRODUCTO NO VALIDO"
            );
        }

        return productoRepository
            .findById(
                detalle
                    .getProducto()
                    .getIdProducto()
            )
            .orElseThrow(
                () -> new RuntimeException(
                    "PRODUCTO NO ENCONTRADO"
                )
            );
    }

    private void validarProductoDisponible(
        Producto producto,
        Integer cantidad
    ){

        if (
            cantidad == null
            || cantidad <= 0
        ){
            throw new RuntimeException(
                "LA CANTIDAD DEBE SER MAYOR A CERO"
            );
        }

        if (
            producto.getEstado() == null
            || producto.getEstado().equals(
                "INACTIVO"
            )
        ){
            throw new RuntimeException(
                "EL PRODUCTO NO ESTA DISPONIBLE"
            );
        }

        if (
            producto.getStock() == null
            || producto.getStock() <= 0
        ){
            throw new RuntimeException(
                "PRODUCTO SIN STOCK"
            );
        }

        if (
            producto.getStock()
            < cantidad
        ){
            throw new RuntimeException(
                "STOCK INSUFICIENTE PARA EL PRODUCTO "
                + producto.getNombre()
            );
        }
    }

    private Integer calcularPrecioProducto(
        Producto producto
    ){

        Integer precio =
            producto.getPrecio();

        if (
            Boolean.TRUE.equals(
                producto.getPromocion()
            )
            && producto.getDescuento() != null
            && producto.getDescuento() > 0
        ){

            double descuento =
                producto.getDescuento()
                / 100.0;

            precio =
                (int) Math.round(
                    producto.getPrecio()
                    * (
                        1 - descuento
                    )
                );
        }

        return precio;
    }

    private void descontarStock(
        Producto producto,
        Integer cantidad
    ){

        Integer nuevoStock =
            producto.getStock()
            - cantidad;

        producto.setStock(
            nuevoStock
        );

        if (
            nuevoStock == 0
        ){
            producto.setEstado(
                "SIN STOCK"
            );
        }

        productoRepository.save(
            producto
        );
    }

    private Integer calcularCostoDelivery(
        String tipoEntrega
    ){

        if (
            tipoEntrega.equalsIgnoreCase(
                "DELIVERY"
            )
        ){
            return COSTO_DELIVERY;
        }

        return 0;
    }
}