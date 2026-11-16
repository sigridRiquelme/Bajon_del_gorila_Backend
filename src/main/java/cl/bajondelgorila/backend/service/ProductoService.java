package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Categoria;
import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.repository.CategoriaRepository;
import cl.bajondelgorila.backend.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    public ProductoService(
        ProductoRepository productoRepository,
        CategoriaRepository categoriaRepository
    ){
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProductoPorId(
        Long idProducto
    ){
        return productoRepository.findById(idProducto);
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
        Categoria categoria = categoriaRepository
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

        producto.setCategoria(categoria);

        if (producto.getStock() == 0){
            producto.setEstado("SIN STOCK");
        }

        return productoRepository.save(
            producto
        );
    }

    public Producto actualizarProducto(
        Long idProducto,
        Producto producto
    ){
        Producto productoExistente = productoRepository
            .findById(idProducto)
            .orElseThrow(
                () -> new RuntimeException(
                    "PRODUCTO NO ENCONTRADO"
                )
            );

        Categoria categoria = categoriaRepository
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

        if (producto.getStock() == 0){
            productoExistente.setEstado(
                "SIN STOCK"
            );
        }

        return productoRepository.save(
            productoExistente
        );
    }

    public void eliminarProducto(
        Long idProducto
    ){
        if (!productoRepository.existsById(idProducto)){
            throw new RuntimeException(
                "PRODUCTO NO ENCONTRADO"
            );
        }

        productoRepository.deleteById(
            idProducto
        );
    }
}