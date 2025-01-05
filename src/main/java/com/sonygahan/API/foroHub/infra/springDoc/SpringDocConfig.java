package com.sonygahan.API.foroHub.infra.springDoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                        .info(new Info()
                                .title("Foro - Hub API")
                                .description("API RestFul Foro Hub, con las funcionalidades CRUD para topicos, ademas crea y lista los temas como cursos, usuarios y respuestas.")
                                .contact(new Contact()
                                        .name("sonygahan")
                                        .email("sonygahan@forohub.com"))
                                .license(new License()
                                        .name("Apache 2.0")
                                        .url("http://foro-hub/api/licencia")));
    }
}
