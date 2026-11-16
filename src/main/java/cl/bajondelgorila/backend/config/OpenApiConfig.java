package cl.bajondelgorila.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI configurarOpenAPI(){

        return new OpenAPI()
            .info(
                new Info()
                    .title(
                        "El Bajon del Gorila API"
                    )
                    .version(
                        "1.0"
                    )
                    .description(
                        "API REST para la gestion de categorias, productos, usuarios y pedidos"
                    )
            );
    }
}