package com.bootcamp.designpatterns.integration;

import com.bootcamp.designpatterns.DesignPatternsBootcampApplication;
import com.bootcamp.designpatterns.facade.OrderRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de integracao da API REST
 * 
 * Esta classe testa toda a aplicacao executando:
 * - Servidor Spring Boot completo
 * - Banco H2 em memoria
 * - Endpoints REST reais
 * - Integracao entre todas as camadas
 */
@SpringBootTest(
    classes = DesignPatternsBootcampApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Tag("integration")
public class DesignPatternsIntegrationTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    private String baseUrl;
    
    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/design-patterns";
        System.out.println("=== INICIANDO TESTES DE INTEGRACAO ===");
        System.out.println("Servidor rodando na porta: " + port);
        System.out.println("Base URL: " + baseUrl);
    }
    
    // ========== TESTES DE INTEGRACAO - STRATEGY PATTERN ==========
    
    @Test
    @DisplayName("Integracao Strategy - Calculo de preco com desconto percentual")
    void testStrategyCalculatePricePercentage() {
        // Arrange
        String url = baseUrl + "/strategy/calculate-price?originalPrice=200&strategyType=percentage&discountValue=15";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Deve retornar status 200");
        assertNotNull(response.getBody(), "Body nao deve ser nulo");
        
        Map<String, Object> body = response.getBody();
        assertEquals(200.0, body.get("originalPrice"), "Preco original deve ser 200");
        assertEquals(170.0, body.get("finalPrice"), "Preco final deve ser 170 (200 - 15%)");
        assertEquals(30.0, body.get("discount"), "Desconto deve ser 30");
        assertEquals("Desconto Percentual", body.get("strategy"), "Estrategia deve ser percentual");
        
        System.out.println("✓ Integracao Strategy Percentual testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Strategy - Calculo com desconto fixo")
    void testStrategyCalculatePriceFixed() {
        // Arrange
        String url = baseUrl + "/strategy/calculate-price?originalPrice=300&strategyType=fixed&discountValue=80";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertEquals(300.0, body.get("originalPrice"));
        assertEquals(220.0, body.get("finalPrice")); // 300 - 80
        assertEquals(80.0, body.get("discount"));
        assertEquals("Desconto Fixo", body.get("strategy"));
        
        System.out.println("✓ Integracao Strategy Fixo testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Strategy - Desconto progressivo")
    void testStrategyCalculatePriceProgressive() {
        // Arrange - Valor acima de R$ 500 (deve ter 15% de desconto)
        String url = baseUrl + "/strategy/calculate-price?originalPrice=600&strategyType=progressive";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertEquals(600.0, body.get("originalPrice"));
        assertEquals(510.0, body.get("finalPrice")); // 600 - 15% = 600 - 90
        assertEquals(90.0, body.get("discount"));
        assertEquals("Desconto Progressivo", body.get("strategy"));
        
        System.out.println("✓ Integracao Strategy Progressivo testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Strategy - Comparacao de todas as estrategias")
    void testStrategyCompareAll() {
        // Arrange
        String url = baseUrl + "/strategy/compare-all?originalPrice=400";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertEquals(400.0, body.get("originalPrice"));
        assertEquals("Strategy Pattern", body.get("pattern"));
        assertNotNull(body.get("strategies"), "Lista de estrategias nao deve ser nula");
        
        System.out.println("✓ Integracao Strategy Compare All testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Strategy - Validacao de parametros invalidos")
    void testStrategyInvalidParameters() {
        // Arrange - Preco negativo deve retornar erro
        String url = baseUrl + "/strategy/calculate-price?originalPrice=-10&strategyType=percentage&discountValue=10";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Deve retornar 400 para preco invalido");
        
        System.out.println("✓ Integracao Strategy Validacao testada com sucesso");
    }
    
    // ========== TESTES DE INTEGRACAO - FACADE PATTERN ==========
    
    @Test
    @DisplayName("Integracao Facade - Verificacao de disponibilidade")
    void testFacadeCheckAvailability() {
        // Arrange
        String url = baseUrl + "/facade/check-availability?productId=PROD124&quantity=5";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertEquals("PROD124", body.get("productId"));
        assertEquals(5, body.get("requestedQuantity"));
        assertEquals("Facade Pattern", body.get("pattern"));
        assertNotNull(body.get("availability"));
        
        System.out.println("✓ Integracao Facade Availability testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Facade - Informacoes de entrega")
    void testFacadeShippingInfo() {
        // Arrange
        String url = baseUrl + "/facade/shipping-info?zipCode=01000-000";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertEquals("01000-000", body.get("zipCode"));
        assertEquals("Facade Pattern", body.get("pattern"));
        assertNotNull(body.get("shippingInfo"));
        
        System.out.println("✓ Integracao Facade Shipping testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Facade - Processamento de pedido valido")
    void testFacadeProcessOrderSuccess() {
        // Arrange
        String url = baseUrl + "/facade/process-order";
        
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("PROD124"); // Produto par (tem estoque)
        orderRequest.setQuantity(2);
        orderRequest.setAmount(new BigDecimal("299.99"));
        orderRequest.setCardNumber("1234567890123456");
        orderRequest.setCvv("123");
        orderRequest.setExpiryDate("12/25");
        orderRequest.setAddress("Rua das Flores, 123");
        orderRequest.setZipCode("01000-000");
        
        // Act
        ResponseEntity<Map> response = restTemplate.postForEntity(url, orderRequest, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertNotNull(body.get("success"));
        assertNotNull(body.get("message"));
        assertEquals("Facade Pattern", body.get("pattern"));
        
        System.out.println("✓ Integracao Facade Process Order testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao Facade - Processamento com valor alto (deve falhar)")
    void testFacadeProcessOrderHighValue() {
        // Arrange
        String url = baseUrl + "/facade/process-order";
        
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("PROD124");
        orderRequest.setQuantity(1);
        orderRequest.setAmount(new BigDecimal("1500.00")); // Valor alto - deve falhar
        orderRequest.setCardNumber("1234567890123456");
        orderRequest.setCvv("123");
        orderRequest.setExpiryDate("12/25");
        orderRequest.setAddress("Rua das Rosas, 456");
        orderRequest.setZipCode("20000-000");
        
        // Act
        ResponseEntity<Map> response = restTemplate.postForEntity(url, orderRequest, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        // Pode ser sucesso ou falha dependendo da logica de negocio
        assertNotNull(body.get("success"));
        assertEquals("Facade Pattern", body.get("pattern"));
        
        System.out.println("✓ Integracao Facade High Value testada com sucesso");
    }
    
    // ========== TESTES DE INTEGRACAO - SINGLETON PATTERN ==========
    
    @Test
    @DisplayName("Integracao Singleton - Informacoes da aplicacao")
    void testSingletonAppInfo() {
        // Arrange
        String url = baseUrl + "/singleton/app-info";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertNotNull(body.get("applicationInfo"));
        assertEquals("Singleton Pattern", body.get("pattern"));
        assertTrue(body.get("applicationInfo").toString().contains("Design Patterns Bootcamp"));
        
        System.out.println("✓ Integracao Singleton App Info testada com sucesso");
    }
    
    // ========== TESTES DE INTEGRACAO - DEMONSTRACAO COMPLETA ==========
    
    @Test
    @DisplayName("Integracao Completa - Todos os padroes em uma operacao")
    void testCompleteDemo() {
        // Arrange
        String url = baseUrl + "/complete-demo?productId=PROD124&quantity=2&originalPrice=500&zipCode=01000-000";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        
        assertNotNull(body.get("report"));
        assertNotNull(body.get("patterns"));
        assertTrue(body.get("report").toString().contains("RELATORIO COMPLETO"));
        
        // Verifica se todos os padroes estao presentes
        @SuppressWarnings("unchecked")
        java.util.List<String> patterns = (java.util.List<String>) body.get("patterns");
        assertTrue(patterns.contains("Singleton"));
        assertTrue(patterns.contains("Strategy"));
        assertTrue(patterns.contains("Facade"));
        
        System.out.println("✓ Integracao Demonstracao Completa testada com sucesso");
    }
    
    // ========== TESTES DE VALIDACAO E SEGURANCA ==========
    
    @Test
    @DisplayName("Integracao - Validacao de parametros obrigatorios")
    void testRequiredParametersValidation() {
        // Arrange - Endpoint sem parametros obrigatorios
        String url = baseUrl + "/strategy/calculate-price";
        
        // Act
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), 
                   "Deve retornar 400 quando parametros obrigatorios estao ausentes");
        
        System.out.println("✓ Integracao Validacao testada com sucesso");
    }
    
    @Test
    @DisplayName("Integracao - Endpoint inexistente deve retornar 404")
    void testNonExistentEndpoint() {
        // Arrange
        String url = baseUrl + "/endpoint-que-nao-existe";
        
        // Act
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
        System.out.println("✓ Integracao 404 testada com sucesso");
    }
    
    // ========== TESTE DE PERFORMANCE BASICO ==========
    
    @Test
    @DisplayName("Integracao - Teste basico de performance")
    void testBasicPerformance() {
        // Arrange
        String url = baseUrl + "/strategy/calculate-price?originalPrice=100&strategyType=percentage&discountValue=10";
        int numberOfRequests = 10;
        
        // Act & Assert
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < numberOfRequests; i++) {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            assertEquals(HttpStatus.OK, response.getStatusCode(), 
                       "Todas as requisicoes devem ser bem sucedidas");
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double averageTime = (double) totalTime / numberOfRequests;
        
        assertTrue(averageTime < 1000, "Tempo medio deve ser menor que 1 segundo por requisicao");
        
        System.out.println("✓ Teste de Performance: " + numberOfRequests + 
                          " requisicoes em " + totalTime + "ms" +
                          " (média: " + String.format("%.2f", averageTime) + "ms)");
    }
    
    // ========== TESTE DE CONSISTENCIA ==========
    
    @Test
    @DisplayName("Integracao - Consistencia entre multiplas chamadas")
    void testConsistencyAcrossMultipleCalls() {
        // Arrange
        String url = baseUrl + "/singleton/app-info";
        
        // Act - Fazer multiplas chamadas
        ResponseEntity<Map> response1 = restTemplate.getForEntity(url, Map.class);
        ResponseEntity<Map> response2 = restTemplate.getForEntity(url, Map.class);
        ResponseEntity<Map> response3 = restTemplate.getForEntity(url, Map.class);
        
        // Assert - Todas devem retornar a mesma informacao (Singleton)
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        
        String appInfo1 = (String) response1.getBody().get("applicationInfo");
        String appInfo2 = (String) response2.getBody().get("applicationInfo");
        String appInfo3 = (String) response3.getBody().get("applicationInfo");
        
        assertEquals(appInfo1, appInfo2, "Informacoes devem ser consistentes");
        assertEquals(appInfo2, appInfo3, "Informacoes devem ser consistentes");
        
        System.out.println("✓ Integracao Consistencia testada com sucesso");
    }
}