package com.tech.solutio.produtos.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI springNewsApi() {
	      return new OpenAPI()
	              .info(new Info().title(" API - Spring Boot")
	              .description("Teste Técnico para Tech Solutio")
	              .version("v0.0.1")
	              .license(new License().name("Apache 2.0").url("https://github.com/MatheusMMonteiro/Back-End-Spring-Boot-API/blob/main/LICENSE")))
	              .externalDocs(new ExternalDocumentation()
	              .description("REST API Spring de Produtos")
	              .url("https://github.com/MatheusMMonteiro/Back-End-Spring-Boot-API"));
	              
	  }
}