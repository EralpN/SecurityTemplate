package com.eralp.configuration.swagger;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

/**
 * This class configures swagger to work with custom headers both for security and locale.
 *
 * @author Eralp Nitelik
 */
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme().name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public OperationCustomizer addCustomGlobalHeader() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter headerParameter = new Parameter().in(ParameterIn.HEADER.toString()).description("Language code can be added below.").
                    schema(new StringSchema().name("Accept-Language")).name("Accept-Language");
            operation.addParametersItem(headerParameter);
            return operation;
        };
    }
}
