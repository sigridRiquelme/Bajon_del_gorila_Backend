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
import org.springframework.web.bind.annotation.RestController;

import cl.bajondelgorila.backend.model.Categoria;
import cl.bajondelgorila.backend.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/categorias")
@Tag(
    name = "Categorias",
    description = "Gestion de categorias de productos"
)
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(
        CategoriaService categoriaService
    ){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(
        summary = "Listar categorias",
        description = "Obtiene todas las categorias registradas"
    )
    public List<Categoria> obtenerCategorias(){
        return categoriaService.obtenerCategorias();
    }

    @GetMapping("/{idCategoria}")
    @Operation(
        summary = "Buscar categoria",
        description = "Obtiene una categoria mediante su identificador"
    )
    public ResponseEntity<Categoria> obtenerCategoriaPorId(
        @PathVariable Long idCategoria
    ){
        return categoriaService
            .obtenerCategoriaPorId(idCategoria)
            .map(ResponseEntity::ok)
            .orElse(
                ResponseEntity.notFound().build()
            );
    }

    @PostMapping
    @Operation(
        summary = "Crear categoria",
        description = "Registra una nueva categoria"
    )
    public Categoria guardarCategoria(
        @RequestBody Categoria categoria
    ){
        return categoriaService.guardarCategoria(
            categoria
        );
    }

    @PutMapping("/{idCategoria}")
    @Operation(
        summary = "Actualizar categoria",
        description = "Actualiza los datos de una categoria existente"
    )
    public Categoria actualizarCategoria(
        @PathVariable Long idCategoria,
        @RequestBody Categoria categoria
    ){
        return categoriaService.actualizarCategoria(
            idCategoria,
            categoria
        );
    }

    @DeleteMapping("/{idCategoria}")
    @Operation(
        summary = "Eliminar categoria",
        description = "Elimina una categoria mediante su identificador"
    )
    public ResponseEntity<Void> eliminarCategoria(
        @PathVariable Long idCategoria
    ){
        categoriaService.eliminarCategoria(
            idCategoria
        );

        return ResponseEntity.noContent().build();
    }
}