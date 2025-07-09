package ge.edu.cu.l_lomidze2.sabakalavro.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI apiInfo() {
        var securityScheme = "JWT Authentication";

        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList(securityScheme))
            .components(
                new Components().addSecuritySchemes(
                    securityScheme,
                    new SecurityScheme()
                        .name(securityScheme)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                    .bearerFormat("JWT")
                )
            )
        .info(
            new Info()
                .title("Security API")
                .version("1.0")
            .contact(new Contact().name("Luka Lomidze").email("l_lomidze2@cu.edu.ge"))
        );
    }

    @Bean
    GroupedOpenApi vulnerableApis() {
        return GroupedOpenApi.builder()
            .group("Vulnerable Endpoints")
            .pathsToMatch("/vulnerable/**")
        .build();
    }

    @Bean
    GroupedOpenApi secureApis() {
        return GroupedOpenApi.builder()
            .group("Secure Endpoints")
            .pathsToMatch("/secure/**")
        .build();
    }
}
