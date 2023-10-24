package dev.nichoko.diogenes.config;

import java.util.ArrayList;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    /**
     * 
     * This method is needed to allow sending multipart requests. For example, when
     * an item is
     * created together with an image. If this is not set the request will return an
     * exception with:
     * 
     * Resolved [org.springframework.web.HttpMediaTypeNotSupportedException:
     * Content-Type
     * 'application/octet-stream' is not supported]
     * 
     * @param converter
     */
    public OpenApiConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }

    @Bean
    public OpenAPI customOpenAPI(BuildProperties buildProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title(buildProperties.getArtifact())
                        .description("API to manage the inventory.")
                        .version(buildProperties.getVersion()))
                .components(new Components()
                        .addSecuritySchemes("jwtAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("jwtAuth"));
    }

}