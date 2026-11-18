package cl.bajondelgorila.backend.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cl.bajondelgorila.backend.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_TIME =
        1000 * 60 * 60 * 24;

    private SecretKey obtenerClave(){

        return Keys.hmacShaKeyFor(
            secretKey.getBytes(
                StandardCharsets.UTF_8
            )
        );
    }

    public String generarToken(
        Usuario usuario
    ){

        return Jwts.builder()
            .subject(
                usuario.getCorreo()
            )
            .claim(
                "idUsuario",
                usuario.getIdUsuario()
            )
            .claim(
                "rol",
                usuario.getRol()
            )
            .issuedAt(
                new Date()
            )
            .expiration(
                new Date(
                    System.currentTimeMillis()
                    + EXPIRATION_TIME
                )
            )
            .signWith(
                obtenerClave()
            )
            .compact();
    }

    public String obtenerCorreo(
        String token
    ){

        return Jwts.parser()
            .verifyWith(
                obtenerClave()
            )
            .build()
            .parseSignedClaims(
                token
            )
            .getPayload()
            .getSubject();
    }

    public String obtenerRol(
        String token
    ){

        return Jwts.parser()
            .verifyWith(
                obtenerClave()
            )
            .build()
            .parseSignedClaims(
                token
            )
            .getPayload()
            .get(
                "rol",
                String.class
            );
    }

    public boolean tokenValido(
        String token
    ){

        try{

            Jwts.parser()
                .verifyWith(
                    obtenerClave()
                )
                .build()
                .parseSignedClaims(
                    token
                );

            return true;

        } catch (Exception e){

            return false;
        }
    }
}