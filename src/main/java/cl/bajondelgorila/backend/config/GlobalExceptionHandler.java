package cl.bajondelgorila.backend.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> manejarRuntimeException(
        RuntimeException exception
    ){

        Map<String, Object> respuesta =
            new HashMap<>();

        respuesta.put(
            "fecha",
            LocalDateTime.now()
        );

        respuesta.put(
            "estado",
            HttpStatus.BAD_REQUEST.value()
        );

        respuesta.put(
            "error",
            "BAD REQUEST"
        );

        respuesta.put(
            "mensaje",
            exception.getMessage()
        );

        return ResponseEntity
            .status(
                HttpStatus.BAD_REQUEST
            )
            .body(
                respuesta
            );
    }
}