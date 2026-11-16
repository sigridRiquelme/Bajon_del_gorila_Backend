package cl.bajondelgorila.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.bajondelgorila.backend.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
        JwtAuthenticationFilter jwtAuthenticationFilter
    ){
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {

        http
            .csrf(
                csrf -> csrf.disable()
            )
            .cors(
                Customizer.withDefaults()
            )
            .sessionManagement(
                session -> session
                    .sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                    )
            )
            .authorizeHttpRequests(
                auth -> auth

                    // SWAGGER
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**"
                    )
                    .permitAll()

                    // AUTENTICACION
                    .requestMatchers(
                        "/api/v1/auth/**"
                    )
                    .permitAll()

                    // PRODUCTOS PUBLICOS
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/productos/**"
                    )
                    .permitAll()

                    // CATEGORIAS PUBLICAS
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/categorias/**"
                    )
                    .permitAll()

                    // PRODUCTOS SOLO ADMINISTRADOR
                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/productos/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    .requestMatchers(
                        HttpMethod.PUT,
                        "/api/v1/productos/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/v1/productos/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    // CATEGORIAS SOLO ADMINISTRADOR
                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/categorias/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    .requestMatchers(
                        HttpMethod.PUT,
                        "/api/v1/categorias/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    .requestMatchers(
                        HttpMethod.DELETE,
                        "/api/v1/categorias/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    // USUARIOS SOLO ADMINISTRADOR
                    .requestMatchers(
                        "/api/v1/usuarios/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )

                    // PEDIDOS ADMINISTRADOR O VENDEDOR
                    .requestMatchers(
                        "/api/v1/pedidos/**"
                    )
                    .hasAnyRole(
                        "ADMINISTRADOR",
                        "VENDEDOR"
                    )

                    // RESTO DE RUTAS
                    .anyRequest()
                    .authenticated()
            )
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}