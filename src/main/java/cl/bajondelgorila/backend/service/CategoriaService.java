package cl.bajondelgorila.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Categoria;
import cl.bajondelgorila.backend.repository.CategoriaRepository;
import cl.bajondelgorila.backend.repository.ProductoRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final ProductoRepository productoRepository;

    public CategoriaService(
        CategoriaRepository categoriaRepository,
        ProductoRepository productoRepository
    ){
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Categoria> obtenerCategorias(){
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoriaPorId(
        Long idCategoria
    ){
        return categoriaRepository.findById(
            idCategoria
        );
    }

    public Categoria guardarCategoria(
        Categoria categoria
    ){

        validarCategoria(
            categoria
        );

        return categoriaRepository.save(
            categoria
        );
    }

    public Categoria actualizarCategoria(
        Long idCategoria,
        Categoria categoria
    ){

        Categoria categoriaExistente =
            categoriaRepository
                .findById(
                    idCategoria
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "CATEGORIA NO ENCONTRADA"
                    )
                );

        validarCategoria(
            categoria
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

        if (
            !categoriaRepository.existsById(
                idCategoria
            )
        ){
            throw new RuntimeException(
                "CATEGORIA NO ENCONTRADA"
            );
        }

        if (
            productoRepository
                .existsByCategoriaIdCategoria(
                    idCategoria
                )
        ){
            throw new RuntimeException(
                "NO SE PUEDE ELIMINAR UNA CATEGORIA QUE TIENE PRODUCTOS"
            );
        }

        categoriaRepository.deleteById(
            idCategoria
        );
    }

    private void validarCategoria(
        Categoria categoria
    ){

        if (
            categoria.getNombre() == null
            || categoria.getNombre().isBlank()
        ){
            throw new RuntimeException(
                "EL NOMBRE DE LA CATEGORIA ES OBLIGATORIO"
            );
        }
    }
}