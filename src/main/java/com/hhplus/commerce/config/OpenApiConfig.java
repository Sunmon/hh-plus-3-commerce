package com.hhplus.commerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("프로젝트 API 문서")
                        .version("1.0.0")
                        .description("프로젝트의 API 명세서입니다."))
                .addSecurityItem(new SecurityRequirement().addList("userId"))
                .components(new Components()
                        .addSecuritySchemes("userId",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER) // 헤더로 전달
                                        .name("userId") // 헤더 이름
                        )
                );
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
//                .pathsToMatch("/api/v1/**")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}