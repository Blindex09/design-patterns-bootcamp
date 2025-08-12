package com.bootcamp.designpatterns.unit;

import com.bootcamp.designpatterns.service.ProductService;
import com.bootcamp.designpatterns.strategy.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para a camada de Service
 * 
 * Esta classe testa a logica de negocio implementada nos servicos:
 * - ProductService: Integracao dos padroes com Spring
 * - Operacoes complexas que combinam multiplos padroes
 * - Validacoes e tratamento de erros
 */
@SpringBootTest
@ActiveProfiles("test")
@Tag("service")
public class ProductServiceTest {
    
    private ProductService productService;
    
    @BeforeEach
    void setUp() {
        productService = new ProductService();
        System.out.println("=== INICIANDO TESTES DE SERVICE ===");
    }
    
    // ========== TESTES DE CALCULO DE DESCONTO ==========
    
    @Test
    @DisplayName("Service - Calculo de preco com desconto percentual")
    void testCalculateDiscountedPricePercentage() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("200.00");
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.25")); // 25%
        
        // Act
        BigDecimal result = productService.calculateDiscountedPrice(originalPrice, strategy);
        
        // Assert
        assertEquals(new BigDecimal("150.00"), result, "25% de desconto em R$200 deve resultar em R$150");
        
        System.out.println("✓ Service Percentage Discount testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Calculo de preco com desconto fixo")
    void testCalculateDiscountedPriceFixed() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("300.00");
        DiscountStrategy strategy = new FixedDiscountStrategy(new BigDecimal("100.00"));
        
        // Act
        BigDecimal result = productService.calculateDiscountedPrice(originalPrice, strategy);
        
        // Assert
        assertEquals(new BigDecimal("200.00"), result, "Desconto fixo de R$100 em R$300 deve resultar em R$200");
        
        System.out.println("✓ Service Fixed Discount testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Calculo de preco com desconto progressivo")
    void testCalculateDiscountedPriceProgressive() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("750.00"); // Acima de R$500 = 15%
        DiscountStrategy strategy = new ProgressiveDiscountStrategy();
        
        // Act
        BigDecimal result = productService.calculateDiscountedPrice(originalPrice, strategy);
        
        // Assert
        assertEquals(new BigDecimal("637.50"), result, "15% de desconto em R$750 deve resultar em R$637.50");
        
        System.out.println("✓ Service Progressive Discount testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Detalhes do calculo de preco")
    void testGetPriceCalculationDetails() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("100.00");
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
        
        // Act
        String details = productService.getPriceCalculationDetails(originalPrice, strategy);
        
        // Assert
        assertNotNull(details, "Detalhes nao devem ser nulos");
        assertTrue(details.contains("Desconto Percentual"), "Deve conter o tipo de estrategia");
        assertTrue(details.contains("100.00"), "Deve conter o preco original");
        assertTrue(details.contains("90.00"), "Deve conter o preco final");
        assertTrue(details.contains("10.00"), "Deve conter o valor do desconto");
        
        System.out.println("✓ Service Price Details testado com sucesso");
    }
    
    // ========== TESTES DE DISPONIBILIDADE DE PRODUTO ==========
    
    @Test
    @DisplayName("Service - Verificacao de disponibilidade de produto disponivel")
    void testCheckProductAvailabilityAvailable() {
        // Arrange
        String productId = "PROD124"; // ID par = disponivel
        int quantity = 5;
        
        // Act
        String result = productService.checkProductAvailability(productId, quantity);
        
        // Assert
        assertNotNull(result, "Resultado nao deve ser nulo");
        assertTrue(result.contains(productId), "Deve conter o ID do produto");
        assertTrue(result.contains("5"), "Deve conter a quantidade solicitada");
        
        System.out.println("✓ Service Product Availability testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Verificacao de disponibilidade de produto indisponivel")
    void testCheckProductAvailabilityUnavailable() {
        // Arrange
        String productId = "PROD123"; // ID impar = indisponivel
        int quantity = 20; // Quantidade alta
        
        // Act
        String result = productService.checkProductAvailability(productId, quantity);
        
        // Assert
        assertNotNull(result, "Resultado nao deve ser nulo");
        assertTrue(result.contains(productId), "Deve conter o ID do produto");
        
        System.out.println("✓ Service Product Unavailable testado com sucesso");
    }
    
    // ========== TESTES DE INFORMACOES DE ENTREGA ==========
    
    @Test
    @DisplayName("Service - Calculo de informacoes de entrega")
    void testCalculateShippingInfo() {
        // Arrange
        String zipCode = "01000-000"; // Regiao metropolitana
        
        // Act
        String result = productService.calculateShippingInfo(zipCode);
        
        // Assert
        assertNotNull(result, "Resultado nao deve ser nulo");
        assertTrue(result.contains(zipCode), "Deve conter o CEP");
        assertTrue(result.contains("15.50"), "Deve conter o custo do frete metropolitano");
        assertTrue(result.contains("2"), "Deve conter o prazo de entrega");
        
        System.out.println("✓ Service Shipping Info testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Calculo de entrega para diferentes regioes")
    void testCalculateShippingInfoDifferentRegions() {
        // Arrange
        String[] zipCodes = {"01000-000", "13000-000", "80000-000"};
        String[] expectedCosts = {"15.50", "25.00", "35.00"};
        String[] expectedDays = {"2", "5", "10"};
        
        // Act & Assert
        for (int i = 0; i < zipCodes.length; i++) {
            String result = productService.calculateShippingInfo(zipCodes[i]);
            
            assertNotNull(result, "Resultado nao deve ser nulo para CEP " + zipCodes[i]);
            assertTrue(result.contains(zipCodes[i]), "Deve conter o CEP");
            assertTrue(result.contains(expectedCosts[i]), "Deve conter o custo esperado");
            assertTrue(result.contains(expectedDays[i]), "Deve conter o prazo esperado");
        }
        
        System.out.println("✓ Service Different Regions testado com sucesso");
    }
    
    // ========== TESTES DE INFORMACOES DA APLICACAO ==========
    
    @Test
    @DisplayName("Service - Obter informacoes da aplicacao via Singleton")
    void testGetApplicationInfo() {
        // Act
        String result = productService.getApplicationInfo();
        
        // Assert
        assertNotNull(result, "Informacoes da aplicacao nao devem ser nulas");
        assertTrue(result.contains("Design Patterns Bootcamp"), "Deve conter o nome da aplicacao");
        assertTrue(result.contains("1.0.0"), "Deve conter a versao");
        assertTrue(result.contains("production"), "Deve conter o ambiente");
        
        System.out.println("✓ Service Application Info testado com sucesso");
    }
    
    // ========== TESTES DE DEMONSTRACAO DE ESTRATEGIAS ==========
    
    @Test
    @DisplayName("Service - Demonstracao de todas as estrategias")
    void testDemonstrateAllStrategies() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("400.00");
        
        // Act
        List<String> strategies = productService.demonstrateAllStrategies(originalPrice);
        
        // Assert
        assertNotNull(strategies, "Lista de estrategias nao deve ser nula");
        assertEquals(3, strategies.size(), "Deve ter 3 estrategias");
        
        // Verifica se todas as estrategias estao presentes
        boolean hasPercentage = strategies.stream().anyMatch(s -> s.contains("Desconto Percentual"));
        boolean hasFixed = strategies.stream().anyMatch(s -> s.contains("Desconto Fixo"));
        boolean hasProgressive = strategies.stream().anyMatch(s -> s.contains("Desconto Progressivo"));
        
        assertTrue(hasPercentage, "Deve conter estrategia percentual");
        assertTrue(hasFixed, "Deve conter estrategia fixa");
        assertTrue(hasProgressive, "Deve conter estrategia progressiva");
        
        // Verifica se todas contem o preco original
        strategies.forEach(strategy -> 
            assertTrue(strategy.contains("400"), "Cada estrategia deve conter o preco original")
        );
        
        System.out.println("✓ Service All Strategies testado com sucesso");
    }
    
    // ========== TESTES DE OPERACAO COMPLETA ==========
    
    @Test
    @DisplayName("Service - Operacao completa integrando todos os padroes")
    void testPerformCompleteOperation() {
        // Arrange
        String productId = "PROD124";
        int quantity = 3;
        BigDecimal originalPrice = new BigDecimal("500.00");
        String zipCode = "01000-000";
        
        // Act
        String report = productService.performCompleteOperation(productId, quantity, originalPrice, zipCode);
        
        // Assert
        assertNotNull(report, "Relatorio nao deve ser nulo");
        assertTrue(report.contains("RELATORIO COMPLETO"), "Deve conter cabecalho do relatorio");
        assertTrue(report.contains("Design Patterns Bootcamp"), "Deve conter info da aplicacao");
        assertTrue(report.contains(productId), "Deve conter o ID do produto");
        assertTrue(report.contains(zipCode), "Deve conter o CEP");
        assertTrue(report.contains("Estrategias de Desconto"), "Deve conter secao de estrategias");
        
        // Verifica se todas as estrategias estao no relatorio
        assertTrue(report.contains("Percentual"), "Deve conter estrategia percentual");
        assertTrue(report.contains("Fixo"), "Deve conter estrategia fixa");
        assertTrue(report.contains("Progressivo"), "Deve conter estrategia progressiva");
        
        System.out.println("✓ Service Complete Operation testado com sucesso");
    }
    
    // ========== TESTES DE VALIDACAO E TRATAMENTO DE ERROS ==========
    
    @Test
    @DisplayName("Service - Tratamento de preco nulo")
    void testHandleNullPrice() {
        // Arrange
        BigDecimal nullPrice = null;
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
        
        // Act
        BigDecimal result = productService.calculateDiscountedPrice(nullPrice, strategy);
        
        // Assert
        assertEquals(BigDecimal.ZERO, result, "Preco nulo deve retornar zero");
        
        System.out.println("✓ Service Null Price testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Tratamento de estrategia nula")
    void testHandleNullStrategy() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("100.00");
        DiscountStrategy nullStrategy = null;
        
        // Act & Assert
        assertDoesNotThrow(() -> {
            BigDecimal result = productService.calculateDiscountedPrice(originalPrice, nullStrategy);
            // Com estrategia nula, deve usar estrategia padrao (sem desconto)
            assertEquals(originalPrice, result, "Estrategia nula deve usar padrao sem desconto");
        }, "Nao deve lancar excecao com estrategia nula");
        
        System.out.println("✓ Service Null Strategy testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Validacao de quantidade negativa")
    void testNegativeQuantityValidation() {
        // Arrange
        String productId = "PROD123";
        int negativeQuantity = -1;
        
        // Act
        String result = productService.checkProductAvailability(productId, negativeQuantity);
        
        // Assert
        assertNotNull(result, "Resultado nao deve ser nulo mesmo com quantidade negativa");
        // O service deve lidar graciosamente com quantidades negativas
        
        System.out.println("✓ Service Negative Quantity testado com sucesso");
    }
    
    // ========== TESTES DE CONSISTENCIA ==========
    
    @Test
    @DisplayName("Service - Consistencia entre multiplas chamadas")
    void testConsistencyAcrossMultipleCalls() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("250.00");
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.20"));
        
        // Act - Multiplas chamadas
        BigDecimal result1 = productService.calculateDiscountedPrice(originalPrice, strategy);
        BigDecimal result2 = productService.calculateDiscountedPrice(originalPrice, strategy);
        BigDecimal result3 = productService.calculateDiscountedPrice(originalPrice, strategy);
        
        // Assert - Todos os resultados devem ser identicos
        assertEquals(result1, result2, "Resultados devem ser consistentes");
        assertEquals(result2, result3, "Resultados devem ser consistentes");
        assertEquals(new BigDecimal("200.00"), result1, "Resultado deve ser correto");
        
        System.out.println("✓ Service Consistency testado com sucesso");
    }
    
    @Test
    @DisplayName("Service - Singleton mantendo estado entre chamadas")
    void testSingletonStateConsistency() {
        // Act - Multiplas chamadas para informacoes da aplicacao
        String info1 = productService.getApplicationInfo();
        String info2 = productService.getApplicationInfo();
        String info3 = productService.getApplicationInfo();
        
        // Assert - Informacoes devem ser identicas (Singleton)
        assertEquals(info1, info2, "Informacoes devem ser consistentes via Singleton");
        assertEquals(info2, info3, "Informacoes devem ser consistentes via Singleton");
        
        System.out.println("✓ Service Singleton State testado com sucesso");
    }
}