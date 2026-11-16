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

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(
        CategoriaService categoriaService
    ){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> obtenerCategorias(){
        return categoriaService.obtenerCategorias();
    }

    @GetMapping("/{idCategoria}")
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
    public Categoria guardarCategoria(
        @RequestBody Categoria categoria
    ){
        return categoriaService.guardarCategoria(
            categoria
        );
    }

    @PutMapping("/{idCategoria}")
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
    public ResponseEntity<Void> eliminarCategoria(
        @PathVariable Long idCategoria
    ){
        categoriaService.eliminarCategoria(
            idCategoria
        );

        return ResponseEntity.noContent().build();
    }
}