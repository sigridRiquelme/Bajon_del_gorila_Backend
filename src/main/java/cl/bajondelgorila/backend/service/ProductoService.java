package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Categoria;
import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.repository.CategoriaRepository;
import cl.bajondelgorila.backend.repository.DetallePedidoRepository;
import cl.bajondelgorila.backend.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    private final DetallePedidoRepository detallePedidoRepository;

    public ProductoService(
        ProductoRepository productoRepository,
        CategoriaRepository categoriaRepository,
        DetallePedidoRepository detallePedidoRepository
    ){
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(
        Long idProducto
    ){
        return productoRepository.findById(
            idProducto
        );
    }

    public List<Producto> obtenerProductosPorCategoria(
        Long idCategoria
    ){
        return productoRepository.findByCategoriaIdCategoria(
            idCategoria
        );
    }

    public List<Producto> obtenerProductosPorEstado(
        String estado
    ){
        return productoRepository.findByEstado(
            estado
        );
    }

    public List<Producto> obtenerProductosEnPromocion(){
        return productoRepository.findByPromocionTrue();
    }

    public Producto guardarProducto(
        Producto producto
    ){

        validarProducto(
            producto
        );

        Categoria categoria =
            obtenerCategoria(
                producto
            );

        producto.setCategoria(
            categoria
        );

        establecerEstadoStock(
            producto
        );

        return productoRepository.save(
            producto
        );
    }

    public Producto actualizarProducto(
        Long idProducto,
        Producto producto
    ){

        Producto productoExistente =
            productoRepository
                .findById(
                    idProducto
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "PRODUCTO NO ENCONTRADO"
                    )
                );

        validarProducto(
            producto
        );

        Categoria categoria =
            obtenerCategoria(
                producto
            );

        productoExistente.setCategoria(
            categoria
        );

        productoExistente.setNombre(
            producto.getNombre()
        );

        productoExistente.setDescripcion(
            producto.getDescripcion()
        );

        productoExistente.setPrecio(
            producto.getPrecio()
        );

        productoExistente.setStock(
            producto.getStock()
        );

        productoExistente.setEstado(
            producto.getEstado()
        );

        productoExistente.setImagen(
            producto.getImagen()
        );

        productoExistente.setPromocion(
            producto.getPromocion()
        );

        productoExistente.setDescuento(
            producto.getDescuento()
        );

        establecerEstadoStock(
            productoExistente
        );

        return productoRepository.save(
            productoExistente
        );
    }

    public void eliminarProducto(
        Long idProducto
    ){

        Producto producto =
            productoRepository
                .findById(
                    idProducto
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "PRODUCTO NO ENCONTRADO"
                    )
                );

        if (
            detallePedidoRepository
                .existsByProductoIdProducto(
                    idProducto
                )
        ){
            throw new RuntimeException(
                "NO SE PUEDE ELIMINAR UN PRODUCTO QUE TIENE VENTAS REGISTRADAS. CAMBIA SU ESTADO A INACTIVO"
            );
        }

        productoRepository.delete(
            producto
        );
    }

    private Categoria obtenerCategoria(
        Producto producto
    ){

        if (
            producto.getCategoria() == null
            || producto
                .getCategoria()
                .getIdCategoria() == null
        ){
            throw new RuntimeException(
                "CATEGORIA NO VALIDA"
            );
        }

        return categoriaRepository
            .findById(
                producto
                    .getCategoria()
                    .getIdCategoria()
            )
            .orElseThrow(
                () -> new RuntimeException(
                    "CATEGORIA NO ENCONTRADA"
                )
            );
    }

    private void validarProducto(
        Producto producto
    ){

        if (
            producto.getNombre() == null
            || producto.getNombre().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE DEL PRODUCTO ES OBLIGATORIO"
            );
        }

        if (
            producto.getPrecio() == null
            || producto.getPrecio() <= 0
        ){
            throw new RuntimeException(
                "EL PRECIO DEBE SER MAYOR A CERO"
            );
        }

        if (
            producto.getStock() == null
            || producto.getStock() < 0
        ){
            throw new RuntimeException(
                "EL STOCK NO PUEDE SER NEGATIVO"
            );
        }

        if (
            producto.getPromocion() == null
        ){
            producto.setPromocion(
                false
            );
        }

        if (
            producto.getDescuento() == null
        ){
            producto.setDescuento(
                0
            );
        }

        if (
            producto.getDescuento() < 0
            || producto.getDescuento() > 100
        ){
            throw new RuntimeException(
                "EL DESCUENTO DEBE ESTAR ENTRE 0 Y 100"
            );
        }

        if (
            !producto.getPromocion()
        ){
            producto.setDescuento(
                0
            );
        }

        if (
            producto.getEstado() == null
            || producto.getEstado().isBlank()
        ){
            producto.setEstado(
                "ACTIVO"
            );
        }

        if (
            !producto.getEstado().equals(
                "ACTIVO"
            )
            && !producto.getEstado().equals(
                "INACTIVO"
            )
            && !producto.getEstado().equals(
                "SIN STOCK"
            )
        ){
            throw new RuntimeException(
                "ESTADO DE PRODUCTO NO VALIDO"
            );
        }
    }

    private void establecerEstadoStock(
        Producto producto
    ){

        if (
            producto.getStock() == 0
        ){
            producto.setEstado(
                "SIN STOCK"
            );
        }
    }
}