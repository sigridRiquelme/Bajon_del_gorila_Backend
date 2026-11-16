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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(
    name = "Productos",
    description = "Gestion de productos del sistema"
)
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(
        ProductoService productoService
    ){
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(
        summary = "Listar productos",
        description = "Obtiene todos los productos registrados"
    )
    public List<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    @GetMapping("/{idProducto}")
    @Operation(
        summary = "Buscar producto",
        description = "Obtiene un producto mediante su identificador"
    )
    public ResponseEntity<Producto> obtenerProductoPorId(
        @PathVariable Long idProducto
    ){
        return productoService
            .obtenerProductoPorId(idProducto)
            .map(ResponseEntity::ok)
            .orElse(
                ResponseEntity.notFound().build()
            );
    }

    @GetMapping("/categoria/{idCategoria}")
    @Operation(
        summary = "Listar productos por categoria",
        description = "Obtiene los productos asociados a una categoria"
    )
    public List<Producto> obtenerProductosPorCategoria(
        @PathVariable Long idCategoria
    ){
        return productoService.obtenerProductosPorCategoria(
            idCategoria
        );
    }

    @GetMapping("/estado")
    @Operation(
        summary = "Listar productos por estado",
        description = "Obtiene productos filtrados por su estado"
    )
    public List<Producto> obtenerProductosPorEstado(
        @RequestParam String estado
    ){
        return productoService.obtenerProductosPorEstado(
            estado
        );
    }

    @GetMapping("/promociones")
    @Operation(
        summary = "Listar productos en promocion",
        description = "Obtiene los productos que tienen promocion activa"
    )
    public List<Producto> obtenerProductosEnPromocion(){
        return productoService.obtenerProductosEnPromocion();
    }

    @PostMapping
    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto"
    )
    public Producto guardarProducto(
        @RequestBody Producto producto
    ){
        return productoService.guardarProducto(
            producto
        );
    }

    @PutMapping("/{idProducto}")
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza los datos de un producto existente"
    )
    public Producto actualizarProducto(
        @PathVariable Long idProducto,
        @RequestBody Producto producto
    ){
        return productoService.actualizarProducto(
            idProducto,
            producto
        );
    }

    @DeleteMapping("/{idProducto}")
    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto mediante su identificador"
    )
    public ResponseEntity<Void> eliminarProducto(
        @PathVariable Long idProducto
    ){
        productoService.eliminarProducto(
            idProducto
        );

        return ResponseEntity.noContent().build();
    }
}