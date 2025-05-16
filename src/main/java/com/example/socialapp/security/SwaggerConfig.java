package com.example.socialapp.security;


import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SocialApp API")
                        .version("1.0")
                        .description("Backend API documentation for the LinkedIn-style social application")

                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("socialapp-public")
                .pathsToMatch("/api/**")
                .build();
    }
}
