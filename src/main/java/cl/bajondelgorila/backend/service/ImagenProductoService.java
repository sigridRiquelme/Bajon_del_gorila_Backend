package cl.bajondelgorila.backend.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;


@Service
public class ImagenProductoService {


    private static final long TAMANO_MAXIMO =
            5 * 1024 * 1024;


    private static final Set<String> TIPOS_PERMITIDOS =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );


    private final Path directorioImagenes;


    public ImagenProductoService(
            @Value("${app.upload.productos-dir:uploads/productos}")
            String directorio
    ) {

        this.directorioImagenes =
                Paths.get(
                        directorio
                )
                .toAbsolutePath()
                .normalize();


        try {

            Files.createDirectories(
                    this.directorioImagenes
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    "NO SE PUDO CREAR EL DIRECTORIO DE IMAGENES"
            );
        }
    }


    public String guardarImagen(
            MultipartFile archivo
    ) {

        validarArchivo(
                archivo
        );


        String extension =
                obtenerExtension(
                        archivo.getContentType()
                );


        String nombreArchivo =
                UUID.randomUUID()
                        .toString()
                        +
                        extension;


        Path destino =
                directorioImagenes
                        .resolve(
                                nombreArchivo
                        )
                        .normalize();


        if (
                !destino.startsWith(
                        directorioImagenes
                )
        ) {

            throw new RuntimeException(
                    "RUTA DE ARCHIVO INVALIDA"
            );
        }


        try {

            Files.copy(
                    archivo.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (
                IOException e
        ) {

            throw new RuntimeException(
                    "NO SE PUDO GUARDAR LA IMAGEN"
            );
        }


        return nombreArchivo;
    }


    public Resource cargarImagen(
            String nombreArchivo
    ) {

        if (
                nombreArchivo == null
                ||
                nombreArchivo.isBlank()
                ||
                !Paths.get(
                        nombreArchivo
                )
                .getFileName()
                .toString()
                .equals(
                        nombreArchivo
                )
        ) {

            throw new RuntimeException(
                    "NOMBRE DE ARCHIVO INVALIDO"
            );
        }


        try {

            Path archivo =
                    directorioImagenes
                            .resolve(
                                    nombreArchivo
                            )
                            .normalize();


            Resource recurso =
                    new UrlResource(
                            archivo.toUri()
                    );


            if (
                    !recurso.exists()
                    ||
                    !recurso.isReadable()
            ) {

                throw new RuntimeException(
                        "IMAGEN NO ENCONTRADA"
                );
            }


            return recurso;

        } catch (
                Exception e
        ) {

            throw new RuntimeException(
                    "IMAGEN NO ENCONTRADA"
            );
        }
    }


    public String obtenerTipoContenido(
            String nombreArchivo
    ) {

        try {

            Path archivo =
                    directorioImagenes
                            .resolve(
                                    nombreArchivo
                            )
                            .normalize();


            String tipo =
                    Files.probeContentType(
                            archivo
                    );


            return tipo != null
                    ? tipo
                    : "application/octet-stream";

        } catch (
                IOException e
        ) {

            return "application/octet-stream";
        }
    }


    private void validarArchivo(
            MultipartFile archivo
    ) {

        if (
                archivo == null
                ||
                archivo.isEmpty()
        ) {

            throw new RuntimeException(
                    "DEBES SELECCIONAR UNA IMAGEN"
            );
        }


        if (
                archivo.getSize() >
                TAMANO_MAXIMO
        ) {

            throw new RuntimeException(
                    "LA IMAGEN NO PUEDE SUPERAR LOS 5 MB"
            );
        }


        String tipoContenido =
                archivo.getContentType();


        if (
                tipoContenido == null
                ||
                !TIPOS_PERMITIDOS.contains(
                        tipoContenido
                )
        ) {

            throw new RuntimeException(
                    "SOLO SE PERMITEN IMAGENES JPG, PNG O WEBP"
            );
        }
    }


    private String obtenerExtension(
            String tipoContenido
    ) {

        if (
                "image/png".equals(
                        tipoContenido
                )
        ) {

            return ".png";
        }


        if (
                "image/webp".equals(
                        tipoContenido
                )
        ) {

            return ".webp";
        }


        return ".jpg";
    }
}