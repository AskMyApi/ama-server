package askmyapi.amaserver.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Security Scheme 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Security Requirement 정의
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("BearerAuth");

        return new OpenAPI()
                .info(new Info().title("AskMyApi API")
                        .description("AskMyApi API Documentation")
                        .version("v1.0"))
                .addSecurityItem(securityRequirement)
                .schemaRequirement("BearerAuth", securityScheme);
    }

    /**
     * 특정 경로는 보안 요구 제거 (Swagger 문서에서 자물쇠 제거용)
     */
    @Bean
    public OpenApiCustomizer excludeRefreshTokenSecurity() {
        return openApi -> {
            Optional.ofNullable(openApi.getPaths())
                    .map(paths -> paths.get("/api/v1/auth/refresh"))
                    .map(PathItem::readOperations)
                    .ifPresent(operations -> operations.forEach(op -> op.setSecurity(List.of())));
        };
    }
}
