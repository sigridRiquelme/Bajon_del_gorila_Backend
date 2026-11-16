package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Categoria;
import cl.bajondelgorila.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(
        CategoriaRepository categoriaRepository
    ){
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(
        Long idCategoria
    ){
        return categoriaRepository.findById(idCategoria);
    }

    public Categoria guardarCategoria(
        Categoria categoria
    ){
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(
        Long idCategoria,
        Categoria categoria
    ){
        Categoria categoriaExistente = categoriaRepository
            .findById(idCategoria)
            .orElseThrow(
                () -> new RuntimeException(
                    "CATEGORIA NO ENCONTRADA"
                )
            );

        categoriaExistente.setNombre(
            categoria.getNombre()
        );

        return categoriaRepository.save(
            categoriaExistente
        );
    }

    public void eliminarCategoria(
        Long idCategoria
    ){
        if (!categoriaRepository.existsById(idCategoria)){
            throw new RuntimeException(
                "CATEGORIA NO ENCONTRADA"
            );
        }

        categoriaRepository.deleteById(
            idCategoria
        );
    }
}