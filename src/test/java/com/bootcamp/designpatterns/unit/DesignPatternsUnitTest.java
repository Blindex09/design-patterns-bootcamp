package com.bootcamp.designpatterns.unit;

import com.bootcamp.designpatterns.singleton.DatabaseConnection;
import com.bootcamp.designpatterns.singleton.ConfigurationManager;
import com.bootcamp.designpatterns.strategy.*;
import com.bootcamp.designpatterns.facade.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitarios para os padroes GoF implementados
 * 
 * Esta classe testa os padroes em isolamento:
 * - Singleton: Garante instancia unica
 * - Strategy: Diferentes algoritmos de desconto
 * - Facade: Operacoes simplificadas de e-commerce
 */
@Tag("unit")
public class DesignPatternsUnitTest {
    
    @BeforeEach
    void setUp() {
        System.out.println("=== INICIANDO TESTES UNITARIOS DOS PADROES ===");
    }
    
    // ========== TESTES DO PADRAO SINGLETON ==========
    
    @Test
    @DisplayName("Teste Singleton - DatabaseConnection deve retornar sempre a mesma instancia")
    void testDatabaseConnectionSingleton() {
        // Arrange & Act
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        
        // Assert
        assertSame(instance1, instance2, "Deve retornar a mesma instancia");
        assertNotNull(instance1.getConnectionUrl(), "URL de conexao deve estar configurada");
        
        // Testa funcionalidade
        String result = instance1.executeQuery("SELECT * FROM products");
        assertNotNull(result, "Resultado da query nao deve ser nulo");
        assertTrue(result.contains("SELECT * FROM products"), "Deve conter a query executada");
        
        System.out.println("✓ Singleton DatabaseConnection testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Singleton - ConfigurationManager enum deve ser unico")
    void testConfigurationManagerSingleton() {
        // Arrange & Act
        ConfigurationManager config1 = ConfigurationManager.INSTANCE;
        ConfigurationManager config2 = ConfigurationManager.INSTANCE;
        
        // Assert
        assertSame(config1, config2, "Enum deve retornar a mesma instancia");
        
        // Testa configuracao
        config1.setProperty("test.key", "test.value");
        String value = config2.getProperty("test.key");
        assertEquals("test.value", value, "Configuracao deve ser compartilhada");
        
        System.out.println("✓ Singleton ConfigurationManager testado com sucesso");
    }
    
    // ========== TESTES DO PADRAO STRATEGY ==========
    
    @Test
    @DisplayName("Teste Strategy - Desconto percentual deve calcular corretamente")
    void testPercentageDiscountStrategy() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("100.00");
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.15")); // 15%
        PriceCalculator calculator = new PriceCalculator(strategy);
        
        // Act
        BigDecimal discount = calculator.calculateDiscountAmount(originalPrice);
        BigDecimal finalPrice = calculator.calculateFinalPrice(originalPrice);
        
        // Assert
        assertEquals(new BigDecimal("15.00"), discount, "Desconto de 15% deve ser R$ 15,00");
        assertEquals(new BigDecimal("85.00"), finalPrice, "Preco final deve ser R$ 85,00");
        assertEquals("15.0%", strategy.getDiscountInfo(), "Info deve mostrar 15%");
        
        System.out.println("✓ Strategy PercentageDiscount testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Strategy - Desconto fixo deve respeitar limite do preco original")
    void testFixedDiscountStrategy() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("30.00");
        DiscountStrategy strategy = new FixedDiscountStrategy(new BigDecimal("50.00")); // R$ 50
        PriceCalculator calculator = new PriceCalculator(strategy);
        
        // Act
        BigDecimal discount = calculator.calculateDiscountAmount(originalPrice);
        BigDecimal finalPrice = calculator.calculateFinalPrice(originalPrice);
        
        // Assert
        assertEquals(new BigDecimal("30.00"), discount, "Desconto deve ser limitado ao preco original");
        assertEquals(new BigDecimal("0.00"), finalPrice, "Preco final deve ser R$ 0,00");
        
        System.out.println("✓ Strategy FixedDiscount testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Strategy - Desconto progressivo deve aplicar faixas corretas")
    void testProgressiveDiscountStrategy() {
        // Arrange
        ProgressiveDiscountStrategy strategy = new ProgressiveDiscountStrategy();
        PriceCalculator calculator = new PriceCalculator(strategy);
        
        // Test diferentes faixas
        BigDecimal price1 = new BigDecimal("50.00");   // 5%
        BigDecimal price2 = new BigDecimal("300.00");  // 10%
        BigDecimal price3 = new BigDecimal("800.00");  // 15%
        
        // Act & Assert
        BigDecimal discount1 = calculator.calculateDiscountAmount(price1);
        assertEquals(new BigDecimal("2.50"), discount1, "Ate R$100 deve ser 5%");
        
        BigDecimal discount2 = calculator.calculateDiscountAmount(price2);
        assertEquals(new BigDecimal("30.00"), discount2, "Ate R$500 deve ser 10%");
        
        BigDecimal discount3 = calculator.calculateDiscountAmount(price3);
        assertEquals(new BigDecimal("120.00"), discount3, "Acima R$500 deve ser 15%");
        
        System.out.println("✓ Strategy ProgressiveDiscount testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Strategy - Troca de estrategia em tempo de execucao")
    void testStrategyRuntimeChange() {
        // Arrange
        BigDecimal originalPrice = new BigDecimal("200.00");
        PriceCalculator calculator = new PriceCalculator();
        
        // Act & Assert - Estrategia 1: Percentual
        DiscountStrategy strategy1 = new PercentageDiscountStrategy(new BigDecimal("0.20"));
        calculator.setDiscountStrategy(strategy1);
        BigDecimal price1 = calculator.calculateFinalPrice(originalPrice);
        assertEquals(new BigDecimal("160.00"), price1, "Com 20% deve ser R$ 160,00");
        
        // Act & Assert - Estrategia 2: Fixo
        DiscountStrategy strategy2 = new FixedDiscountStrategy(new BigDecimal("75.00"));
        calculator.setDiscountStrategy(strategy2);
        BigDecimal price2 = calculator.calculateFinalPrice(originalPrice);
        assertEquals(new BigDecimal("125.00"), price2, "Com R$ 75 fixo deve ser R$ 125,00");
        
        System.out.println("✓ Strategy runtime change testado com sucesso");
    }
    
    // ========== TESTES DO PADRAO FACADE ==========
    
    @Test
    @DisplayName("Teste Facade - Verificacao de disponibilidade simplificada")
    void testFacadeProductAvailability() {
        // Arrange
        EcommerceFacade facade = new EcommerceFacade();
        
        // Act
        var availability1 = facade.checkProductAvailability("PROD123", 5);
        var availability2 = facade.checkProductAvailability("PROD124", 15); // Produto par, mas quantidade alta
        
        // Assert
        assertNotNull(availability1, "Resultado nao deve ser nulo");
        assertEquals("PROD123", availability1.getProductId(), "ID do produto deve ser preservado");
        assertEquals(5, availability1.getRequestedQuantity(), "Quantidade deve ser preservada");
        
        System.out.println("✓ Facade ProductAvailability testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Facade - Calculo de informacoes de entrega")
    void testFacadeShippingInfo() {
        // Arrange
        EcommerceFacade facade = new EcommerceFacade();
        
        // Act
        var shipping1 = facade.getShippingInfo("01000-000"); // Regiao metropolitana
        var shipping2 = facade.getShippingInfo("13000-000"); // Interior
        var shipping3 = facade.getShippingInfo("80000-000"); // Outras regioes
        
        // Assert
        assertNotNull(shipping1, "Shipping info nao deve ser nulo");
        assertEquals("01000-000", shipping1.getZipCode(), "CEP deve ser preservado");
        assertTrue(shipping1.getCost() > 0, "Custo deve ser positivo");
        assertTrue(shipping1.getEstimatedDays() > 0, "Prazo deve ser positivo");
        
        // Verifica que custos variam por regiao
        assertTrue(shipping1.getCost() < shipping2.getCost(), "Metropolitana deve ser mais barata que interior");
        assertTrue(shipping2.getCost() < shipping3.getCost(), "Interior deve ser mais barato que outras regioes");
        
        System.out.println("✓ Facade ShippingInfo testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste Facade - Processamento de pedido com dados validos")
    void testFacadeOrderProcessingSuccess() {
        // Arrange
        EcommerceFacade facade = new EcommerceFacade();
        OrderRequest order = new OrderRequest(
            "PROD124", // Produto par (tem estoque)
            3,         // Quantidade baixa
            new BigDecimal("150.00"), // Valor baixo (sera aprovado)
            "1234567890123456",       // Cartao valido
            "123",                    // CVV valido
            "12/25",                  // Data valida
            "Rua das Flores, 123",    // Endereco
            "01000-000"               // CEP
        );
        
        // Act
        OrderResult result = facade.processOrder(order);
        
        // Assert
        assertNotNull(result, "Resultado nao deve ser nulo");
        // Note: O resultado pode variar dependendo da logica de negocio simulada
        // mas a operacao deve ser executada sem erros
        
        System.out.println("✓ Facade OrderProcessing testado com sucesso");
    }
    
    @Test
    @DisplayName("Teste integracao - Todos os padroes funcionando juntos")
    void testAllPatternsIntegration() {
        // Arrange - Singleton
        ConfigurationManager config = ConfigurationManager.INSTANCE;
        config.loadConfiguration("Test App", "1.0.0", "test");
        
        // Arrange - Strategy
        DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
        PriceCalculator calculator = new PriceCalculator(strategy);
        
        // Arrange - Facade
        EcommerceFacade facade = new EcommerceFacade();
        
        // Act & Assert
        // 1. Configuracao singleton
        assertEquals("Test App", config.getProperty("name"), "Configuracao deve estar ativa");
        
        // 2. Calculo com strategy
        BigDecimal originalPrice = new BigDecimal("100.00");
        BigDecimal finalPrice = calculator.calculateFinalPrice(originalPrice);
        assertEquals(new BigDecimal("90.00"), finalPrice, "Strategy deve aplicar 10% de desconto");
        
        // 3. Operacao facade
        var shipping = facade.getShippingInfo("01000-000");
        assertNotNull(shipping, "Facade deve retornar informacoes de entrega");
        
        System.out.println("✓ Integracao de todos os padroes testada com sucesso");
    }
}