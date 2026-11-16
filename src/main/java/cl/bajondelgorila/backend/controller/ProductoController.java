package cl.bajondelgorila.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.model.Producto;
import cl.bajondelgorila.backend.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(
        ProductoService productoService
    ){
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    @GetMapping("/{idProducto}")
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
    public List<Producto> obtenerProductosPorCategoria(
        @PathVariable Long idCategoria
    ){
        return productoService.obtenerProductosPorCategoria(
            idCategoria
        );
    }

    @GetMapping("/estado")
    public List<Producto> obtenerProductosPorEstado(
        @RequestParam String estado
    ){
        return productoService.obtenerProductosPorEstado(
            estado
        );
    }

    @GetMapping("/promociones")
    public List<Producto> obtenerProductosEnPromocion(){
        return productoService.obtenerProductosEnPromocion();
    }

    @PostMapping
    public Producto guardarProducto(
        @RequestBody Producto producto
    ){
        return productoService.guardarProducto(
            producto
        );
    }

    @PutMapping("/{idProducto}")
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
    public ResponseEntity<Void> eliminarProducto(
        @PathVariable Long idProducto
    ){
        productoService.eliminarProducto(
            idProducto
        );

        return ResponseEntity.noContent().build();
    }
}