package cl.bajondelgorila.backend.controller;


import cl.bajondelgorila.backend.service.ImagenProductoService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;


@RestController
@RequestMapping(
        "/api/v1/productos/imagenes"
)
public class ImagenProductoController {


    private final ImagenProductoService
            imagenProductoService;


    public ImagenProductoController(
            ImagenProductoService imagenProductoService
    ) {

        this.imagenProductoService =
                imagenProductoService;
    }


    @PostMapping
    public ResponseEntity<Map<String, String>>
    subirImagen(
            @RequestParam("archivo")
            MultipartFile archivo
    ) {

        String nombreArchivo =
                imagenProductoService
                        .guardarImagen(
                                archivo
                        );


        String url =
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path(
                                "/api/v1/productos/imagenes/"
                        )
                        .path(
                                nombreArchivo
                        )
                        .toUriString();


        return ResponseEntity.ok(
                Map.of(
                        "url",
                        url
                )
        );
    }


    @GetMapping(
            "/{nombreArchivo}"
    )
    public ResponseEntity<Resource>
    obtenerImagen(
            @PathVariable
            String nombreArchivo
    ) {

        Resource recurso =
                imagenProductoService
                        .cargarImagen(
                                nombreArchivo
                        );


        String tipoContenido =
                imagenProductoService
                        .obtenerTipoContenido(
                                nombreArchivo
                        );


        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(
                                tipoContenido
                        )
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\""
                                +
                                nombreArchivo
                                +
                                "\""
                )
                .body(
                        recurso
                );
    }
}