package com.dk.swaggerintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author Dinesh
 * @version 1.0
 * @since 01/16/2022
 */
@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    // URL: /v2/api-docs
    // URL: http://localhost:8080/swagger-ui.html

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/employee/*"))
                .apis(RequestHandlerSelectors.basePackage("com.dk"))
                .build()
                .apiInfo(getApiInfo());
    }

    /**
     * @return the ApiInfo object
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo("Employee Info API",
                "Sample API for Employees", "1.0", "Free to use",
                new springfox.documentation.service.Contact("Dinesh", "https://github.com/dnskmr/dk-java-handsons", "dinesh.com")
                , "API License", "https://github.com", Collections.emptyList());
    }

}
