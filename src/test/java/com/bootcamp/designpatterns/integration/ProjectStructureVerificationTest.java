package com.bootcamp.designpatterns.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de verificacao da estrutura do projeto
 * 
 * Este teste garante que todos os arquivos necessarios estao presentes
 * e que a estrutura do projeto esta correta para execucao dos testes.
 */
@SpringBootTest
@ActiveProfiles("test")
@Tag("integration")
public class ProjectStructureVerificationTest {
    
    @Test
    @DisplayName("Verificacao - Estrutura de diretorios do projeto")
    void testProjectDirectoryStructure() {
        // Arrange - Diretorios esperados
        String[] expectedDirectories = {
            "src/main/java/com/bootcamp/designpatterns/singleton",
            "src/main/java/com/bootcamp/designpatterns/strategy", 
            "src/main/java/com/bootcamp/designpatterns/facade",
            "src/main/java/com/bootcamp/designpatterns/controller",
            "src/main/java/com/bootcamp/designpatterns/service",
            "src/main/java/com/bootcamp/designpatterns/model",
            "src/test/java/com/bootcamp/designpatterns/unit",
            "src/test/java/com/bootcamp/designpatterns/integration",
            "src/main/resources",
            "src/test/resources"
        };
        
        // Act & Assert
        for (String directory : expectedDirectories) {
            Path dirPath = Paths.get(directory);
            assertTrue(Files.exists(dirPath) && Files.isDirectory(dirPath), 
                      "Diretorio deve existir: " + directory);
        }
        
        System.out.println("✅ Estrutura de diretorios verificada com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Arquivos essenciais do projeto")
    void testEssentialProjectFiles() {
        // Arrange - Arquivos essenciais
        String[] essentialFiles = {
            "pom.xml",
            "README.md", 
            "TESTING_GUIDE.md",
            "EXEMPLOS_REQUESTS.md",
            "BUILD_GUIDE.md",
            "CHANGELOG.md",
            ".gitignore",
            "run-tests.sh",
            "run-tests.ps1",
            "src/main/resources/application.yml",
            "src/test/resources/application-test.yml",
            "src/main/resources/data.sql"
        };
        
        // Act & Assert
        for (String file : essentialFiles) {
            Path filePath = Paths.get(file);
            assertTrue(Files.exists(filePath) && Files.isRegularFile(filePath), 
                      "Arquivo deve existir: " + file);
        }
        
        System.out.println("✅ Arquivos essenciais verificados com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Classes dos padroes GoF")
    void testDesignPatternsClasses() {
        // Arrange - Classes dos padroes
        String[] patternClasses = {
            // Singleton
            "src/main/java/com/bootcamp/designpatterns/singleton/DatabaseConnection.java",
            "src/main/java/com/bootcamp/designpatterns/singleton/ConfigurationManager.java",
            // Strategy
            "src/main/java/com/bootcamp/designpatterns/strategy/DiscountStrategy.java",
            "src/main/java/com/bootcamp/designpatterns/strategy/PercentageDiscountStrategy.java",
            "src/main/java/com/bootcamp/designpatterns/strategy/FixedDiscountStrategy.java",
            "src/main/java/com/bootcamp/designpatterns/strategy/ProgressiveDiscountStrategy.java",
            "src/main/java/com/bootcamp/designpatterns/strategy/PriceCalculator.java",
            // Facade
            "src/main/java/com/bootcamp/designpatterns/facade/EcommerceFacade.java",
            "src/main/java/com/bootcamp/designpatterns/facade/InventoryService.java",
            "src/main/java/com/bootcamp/designpatterns/facade/PaymentService.java",
            "src/main/java/com/bootcamp/designpatterns/facade/DeliveryService.java",
            "src/main/java/com/bootcamp/designpatterns/facade/OrderRequest.java",
            "src/main/java/com/bootcamp/designpatterns/facade/OrderResult.java"
        };
        
        // Act & Assert
        for (String classFile : patternClasses) {
            Path classPath = Paths.get(classFile);
            assertTrue(Files.exists(classPath) && Files.isRegularFile(classPath), 
                      "Classe deve existir: " + classFile);
        }
        
        System.out.println("✅ Classes dos padroes GoF verificadas com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Classes de teste")
    void testTestClasses() {
        // Arrange - Classes de teste
        String[] testClasses = {
            "src/test/java/com/bootcamp/designpatterns/unit/DesignPatternsUnitTest.java",
            "src/test/java/com/bootcamp/designpatterns/unit/ProductServiceTest.java",
            "src/test/java/com/bootcamp/designpatterns/integration/DesignPatternsIntegrationTest.java",
            "src/test/java/com/bootcamp/designpatterns/integration/DesignPatternsControllerTest.java"
        };
        
        // Act & Assert
        for (String testClass : testClasses) {
            Path testPath = Paths.get(testClass);
            assertTrue(Files.exists(testPath) && Files.isRegularFile(testPath), 
                      "Classe de teste deve existir: " + testClass);
        }
        
        System.out.println("✅ Classes de teste verificadas com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Aplicacao principal e controller")
    void testApplicationAndControllerClasses() {
        // Arrange - Classes principais
        String[] mainClasses = {
            "src/main/java/com/bootcamp/designpatterns/DesignPatternsBootcampApplication.java",
            "src/main/java/com/bootcamp/designpatterns/DesignPatternsDemo.java",
            "src/main/java/com/bootcamp/designpatterns/controller/DesignPatternsController.java",
            "src/main/java/com/bootcamp/designpatterns/service/ProductService.java",
            "src/main/java/com/bootcamp/designpatterns/model/Product.java"
        };
        
        // Act & Assert
        for (String mainClass : mainClasses) {
            Path classPath = Paths.get(mainClass);
            assertTrue(Files.exists(classPath) && Files.isRegularFile(classPath), 
                      "Classe principal deve existir: " + mainClass);
        }
        
        System.out.println("✅ Classes principais verificadas com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Arquivos de CI/CD")
    void testCICDFiles() {
        // Arrange - Arquivos de CI/CD
        String[] cicdFiles = {
            ".github/workflows/ci-cd.yml"
        };
        
        // Act & Assert
        for (String cicdFile : cicdFiles) {
            Path cicdPath = Paths.get(cicdFile);
            assertTrue(Files.exists(cicdPath) && Files.isRegularFile(cicdPath), 
                      "Arquivo de CI/CD deve existir: " + cicdFile);
        }
        
        System.out.println("✅ Arquivos de CI/CD verificados com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Conteudo dos arquivos de configuracao")
    void testConfigurationFileContents() throws Exception {
        // Verificar pom.xml
        Path pomPath = Paths.get("pom.xml");
        String pomContent = Files.readString(pomPath);
        assertTrue(pomContent.contains("design-patterns-bootcamp"), "pom.xml deve conter o nome do projeto");
        assertTrue(pomContent.contains("spring-boot-starter-web"), "pom.xml deve conter dependencia web");
        assertTrue(pomContent.contains("spring-boot-starter-test"), "pom.xml deve conter dependencia de teste");
        assertTrue(pomContent.contains("jacoco-maven-plugin"), "pom.xml deve conter plugin JaCoCo");
        
        // Verificar application.yml
        Path appConfigPath = Paths.get("src/main/resources/application.yml");
        String appConfigContent = Files.readString(appConfigPath);
        assertTrue(appConfigContent.contains("h2"), "application.yml deve conter configuracao H2");
        assertTrue(appConfigContent.contains("jpa"), "application.yml deve conter configuracao JPA");
        
        // Verificar application-test.yml
        Path testConfigPath = Paths.get("src/test/resources/application-test.yml");
        String testConfigContent = Files.readString(testConfigPath);
        assertTrue(testConfigContent.contains("test"), "application-test.yml deve conter profile test");
        assertTrue(testConfigContent.contains("create-drop"), "application-test.yml deve usar create-drop");
        
        System.out.println("✅ Conteudo dos arquivos de configuracao verificado com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Scripts de execucao")
    void testExecutionScripts() {
        // Verificar script bash
        Path bashScript = Paths.get("run-tests.sh");
        assertTrue(Files.exists(bashScript), "Script bash deve existir");
        assertTrue(Files.isExecutable(bashScript) || Files.isRegularFile(bashScript), 
                  "Script bash deve ser executavel ou arquivo regular");
        
        // Verificar script PowerShell
        Path psScript = Paths.get("run-tests.ps1");
        assertTrue(Files.exists(psScript), "Script PowerShell deve existir");
        assertTrue(Files.isRegularFile(psScript), "Script PowerShell deve ser arquivo regular");
        
        System.out.println("✅ Scripts de execucao verificados com sucesso!");
    }
    
    @Test
    @DisplayName("Verificacao - Estrutura completa do projeto")
    void testCompleteProjectStructure() {
        // Verificar se diretorio target existe (pode nao existir em build limpo)
        // mas nao e obrigatorio para o teste passar
        
        // Verificar se existem pelo menos os diretorios principais
        String[] mainDirectories = {"src", "src/main", "src/test"};
        
        for (String dir : mainDirectories) {
            Path dirPath = Paths.get(dir);
            assertTrue(Files.exists(dirPath) && Files.isDirectory(dirPath), 
                      "Diretorio principal deve existir: " + dir);
        }
        
        // Verificar se o projeto tem estrutura Maven
        assertTrue(Files.exists(Paths.get("pom.xml")), "Projeto deve ter pom.xml");
        assertTrue(Files.exists(Paths.get("src/main/java")), "Projeto deve ter src/main/java");
        assertTrue(Files.exists(Paths.get("src/test/java")), "Projeto deve ter src/test/java");
        
        System.out.println("✅ Estrutura completa do projeto verificada com sucesso!");
        System.out.println("🎉 PROJETO DESIGN PATTERNS BOOTCAMP ESTA COMPLETO E PRONTO!");
    }
}