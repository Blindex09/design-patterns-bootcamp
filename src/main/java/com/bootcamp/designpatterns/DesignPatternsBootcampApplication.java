package com.bootcamp.designpatterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;

/**
 * Classe principal da aplicacao Spring Boot
 * 
 * Esta aplicacao demonstra a implementacao dos padroes GoF (Gang of Four)
 * integrados com o Spring Framework:
 * 
 * - Singleton: ConfigurationManager e DatabaseConnection
 * - Strategy: Sistema de descontos com diferentes algoritmos
 * - Facade: EcommerceFacade simplificando operacoes complexas
 * 
 * A aplicacao expoe uma API REST documentada com Swagger/OpenAPI
 * e utiliza H2 como banco de dados em memoria para demonstracao.
 */
@SpringBootApplication
public class DesignPatternsBootcampApplication {
    
    /**
     * Metodo principal que inicia a aplicacao Spring Boot
     * 
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        System.out.println("=== INICIANDO DESIGN PATTERNS BOOTCAMP ===");
        System.out.println("Demonstrando padroes GoF com Spring Framework");
        System.out.println("- Singleton: Instancias unicas para configuracao");
        System.out.println("- Strategy: Algoritmos intercambiaveis de desconto");
        System.out.println("- Facade: Interface simplificada para e-commerce");
        System.out.println("============================================");
        
        SpringApplication.run(DesignPatternsBootcampApplication.class, args);
        
        System.out.println("============================================");
        System.out.println("Aplicacao iniciada com sucesso!");
        System.out.println("Acesse a documentacao da API em: http://localhost:8080/api/swagger-ui.html");
        System.out.println("Console H2 disponivel em: http://localhost:8080/api/h2-console");
        System.out.println("============================================");
    }
    
    /**
     * Configuracao do OpenAPI/Swagger para documentacao da API
     * 
     * @return configuracao personalizada do OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Design Patterns Bootcamp API")
                        .version("1.0.0")
                        .description("API demonstrando padroes GoF (Gang of Four) integrados com Spring Framework")
                        .contact(new Contact()
                                .name("Bootcamp Developer")
                                .email("developer@bootcamp.com")
                                .url("https://github.com/bootcamp/design-patterns"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}