package cl.bajondelgorila.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.bajondelgorila.backend.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
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


                    // PERFIL PROPIO

                    .requestMatchers(
                        "/api/v1/usuarios/me"
                    )
                    .authenticated()


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


                    // CREAR PEDIDO

                    .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/pedidos"
                    )
                    .permitAll()


                    // PEDIDOS DEL CLIENTE

                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/pedidos/mis-pedidos"
                    )
                    .hasRole(
                        "CLIENTE"
                    )


                    // CONSULTAR TODOS LOS PEDIDOS

                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/pedidos/**"
                    )
                    .hasAnyRole(
                        "ADMINISTRADOR",
                        "VENDEDOR"
                    )


                    // GESTIONAR PRODUCTOS

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


                    // GESTIONAR CATEGORIAS

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


                    // GESTIONAR USUARIOS

                    .requestMatchers(
                        "/api/v1/usuarios/**"
                    )
                    .hasRole(
                        "ADMINISTRADOR"
                    )


                    // CUALQUIER OTRA RUTA

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