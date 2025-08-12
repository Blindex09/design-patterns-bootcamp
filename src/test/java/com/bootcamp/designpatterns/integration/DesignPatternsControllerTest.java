package com.bootcamp.designpatterns.integration;

import com.bootcamp.designpatterns.controller.DesignPatternsController;
import com.bootcamp.designpatterns.service.ProductService;
import com.bootcamp.designpatterns.facade.OrderRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de Controller com MockMvc
 * 
 * Esta classe testa especificamente a camada de controle:
 * - Endpoints REST
 * - Validacao de parametros
 * - Serializacao JSON
 * - Status codes HTTP
 * - Headers de resposta
 * 
 * Usa mocks para isolar a camada de controller.
 */
@WebMvcTest(DesignPatternsController.class)
@Tag("controller")
public class DesignPatternsControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        System.out.println("=== INICIANDO TESTES DE CONTROLLER ===");
    }
    
    // ========== TESTES DE CONTROLLER - STRATEGY PATTERN ==========
    
    @Test
    @DisplayName("Controller Strategy - Calculo de preco com parametros validos")
    void testCalculatePriceWithStrategyValidParameters() throws Exception {
        // Arrange
        when(productService.calculateDiscountedPrice(any(BigDecimal.class), any()))
            .thenReturn(new BigDecimal("85.00"));
        when(productService.getPriceCalculationDetails(any(BigDecimal.class), any()))
            .thenReturn("Tipo: Desconto Percentual (15.0%) | Preco Original: R$ 100.00 | Desconto: R$ 15.00 | Preco Final: R$ 85.00");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/strategy/calculate-price")
                .param("originalPrice", "100.00")
                .param("strategyType", "percentage")
                .param("discountValue", "15")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.originalPrice").value(100.00))
                .andExpect(jsonPath("$.finalPrice").value(85.00))
                .andExpect(jsonPath("$.discount").value(15.00))
                .andExpect(jsonPath("$.strategy").value("Desconto Percentual"))
                .andExpect(jsonPath("$.strategyInfo").value("15.0%"));
        
        System.out.println("✓ Controller Strategy Valid Parameters testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller Strategy - Parametros invalidos devem retornar 400")
    void testCalculatePriceInvalidParameters() throws Exception {
        // Act & Assert - Preco negativo
        mockMvc.perform(get("/design-patterns/strategy/calculate-price")
                .param("originalPrice", "-10.00")
                .param("strategyType", "percentage")
                .param("discountValue", "15"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        
        // Act & Assert - Parametro obrigatorio ausente
        mockMvc.perform(get("/design-patterns/strategy/calculate-price")
                .param("strategyType", "percentage")
                .param("discountValue", "15"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Controller Strategy Invalid Parameters testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller Strategy - Comparacao de todas as estrategias")
    void testCompareAllStrategies() throws Exception {
        // Arrange
        when(productService.demonstrateAllStrategies(any(BigDecimal.class)))
            .thenReturn(Arrays.asList(
                "Desconto Percentual: R$ 90.00",
                "Desconto Fixo: R$ 50.00", 
                "Desconto Progressivo: R$ 85.00"
            ));
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/strategy/compare-all")
                .param("originalPrice", "100.00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalPrice").value(100.00))
                .andExpect(jsonPath("$.pattern").value("Strategy Pattern"))
                .andExpected(jsonPath("$.strategies").isArray())
                .andExpect(jsonPath("$.strategies.length()").value(3));
        
        System.out.println("✓ Controller Strategy Compare All testado com sucesso");
    }
    
    // ========== TESTES DE CONTROLLER - FACADE PATTERN ==========
    
    @Test
    @DisplayName("Controller Facade - Verificacao de disponibilidade")
    void testCheckAvailability() throws Exception {
        // Arrange
        when(productService.checkProductAvailability(anyString(), anyInt()))
            .thenReturn("ProductAvailability{productId='PROD123', quantity=5, available=true}");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/facade/check-availability")
                .param("productId", "PROD123")
                .param("quantity", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("PROD123"))
                .andExpect(jsonPath("$.requestedQuantity").value(5))
                .andExpect(jsonPath("$.pattern").value("Facade Pattern"));
        
        System.out.println("✓ Controller Facade Availability testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller Facade - Informacoes de entrega")
    void testGetShippingInfo() throws Exception {
        // Arrange
        when(productService.calculateShippingInfo(anyString()))
            .thenReturn("ShippingInfo{zipCode='01000-000', cost=R$15.50, days=2}");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/facade/shipping-info")
                .param("zipCode", "01000-000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zipCode").value("01000-000"))
                .andExpect(jsonPath("$.pattern").value("Facade Pattern"));
        
        System.out.println("✓ Controller Facade Shipping testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller Facade - Processamento de pedido via POST")
    void testProcessOrder() throws Exception {
        // Arrange
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setProductId("PROD124");
        orderRequest.setQuantity(2);
        orderRequest.setAmount(new BigDecimal("299.99"));
        orderRequest.setCardNumber("1234567890123456");
        orderRequest.setCvv("123");
        orderRequest.setExpiryDate("12/25");
        orderRequest.setAddress("Rua das Flores, 123");
        orderRequest.setZipCode("01000-000");
        
        String requestJson = objectMapper.writeValueAsString(orderRequest);
        
        // Act & Assert
        mockMvc.perform(post("/design-patterns/facade/process-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pattern").value("Facade Pattern"))
                .andExpect(jsonPath("$.description").exists());
        
        System.out.println("✓ Controller Facade Process Order testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller Facade - Validacao de dados do pedido")
    void testProcessOrderInvalidData() throws Exception {
        // Arrange - OrderRequest com dados invalidos
        OrderRequest invalidOrder = new OrderRequest();
        invalidOrder.setProductId(""); // Vazio - invalido
        invalidOrder.setQuantity(-1);  // Negativo - invalido
        invalidOrder.setAmount(new BigDecimal("-100")); // Negativo - invalido
        
        String requestJson = objectMapper.writeValueAsString(invalidOrder);
        
        // Act & Assert
        mockMvc.perform(post("/design-patterns/facade/process-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
        
        System.out.println("✓ Controller Facade Validation testado com sucesso");
    }
    
    // ========== TESTES DE CONTROLLER - SINGLETON PATTERN ==========
    
    @Test
    @DisplayName("Controller Singleton - Informacoes da aplicacao")
    void testGetApplicationInfo() throws Exception {
        // Arrange
        when(productService.getApplicationInfo())
            .thenReturn("Aplicacao: Design Patterns Bootcamp API | Versao: 1.0.0 | Ambiente: test");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/singleton/app-info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pattern").value("Singleton Pattern"))
                .andExpect(jsonPath("$.applicationInfo").exists())
                .andExpect(jsonPath("$.description").exists());
        
        System.out.println("✓ Controller Singleton App Info testado com sucesso");
    }
    
    // ========== TESTES DE CONTROLLER - DEMONSTRACAO COMPLETA ==========
    
    @Test
    @DisplayName("Controller - Demonstracao completa de todos os padroes")
    void testCompleteDemo() throws Exception {
        // Arrange
        String mockReport = "=== RELATORIO COMPLETO DA OPERACAO ===\n" +
                          "1. Aplicacao: Design Patterns Bootcamp API | Versao: 1.0.0 | Ambiente: test\n" +
                          "2. Disponibilidade: ProductAvailability{productId='PROD124', quantity=2, available=true}\n" +
                          "3. Entrega: ShippingInfo{zipCode='01000-000', cost=R$15.50, days=2}\n" +
                          "4. Estrategias de Desconto:\n" +
                          "   1. Desconto Percentual: R$ 450.00\n" +
                          "   2. Desconto Fixo: R$ 400.00\n" +
                          "   3. Desconto Progressivo: R$ 425.00";
        
        when(productService.performCompleteOperation(anyString(), anyInt(), any(BigDecimal.class), anyString()))
            .thenReturn(mockReport);
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/complete-demo")
                .param("productId", "PROD124")
                .param("quantity", "2")
                .param("originalPrice", "500.00")
                .param("zipCode", "01000-000"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.report").exists())
                .andExpect(jsonPath("$.patterns").isArray())
                .andExpect(jsonPath("$.patterns.length()").value(3))
                .andExpect(jsonPath("$.patterns[0]").value("Singleton"))
                .andExpect(jsonPath("$.patterns[1]").value("Strategy"))
                .andExpect(jsonPath("$.patterns[2]").value("Facade"))
                .andExpect(jsonPath("$.description").exists());
        
        System.out.println("✓ Controller Complete Demo testado com sucesso");
    }
    
    // ========== TESTES DE VALIDACAO E HEADERS ==========
    
    @Test
    @DisplayName("Controller - Validacao de headers Content-Type")
    void testContentTypeHeaders() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/design-patterns/singleton/app-info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"));
        
        System.out.println("✓ Controller Content-Type Headers testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller - Tratamento de metodo HTTP nao suportado")
    void testUnsupportedHttpMethod() throws Exception {
        // Act & Assert - DELETE nao e suportado neste endpoint
        mockMvc.perform(delete("/design-patterns/singleton/app-info"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
        
        System.out.println("✓ Controller HTTP Method testado com sucesso");
    }
    
    @Test
    @DisplayName("Controller - Endpoint nao encontrado")
    void testEndpointNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/design-patterns/endpoint-inexistente"))
                .andDo(print())
                .andExpect(status().isNotFound());
        
        System.out.println("✓ Controller 404 testado com sucesso");
    }
    
    // ========== TESTES DE SERIALIZACAO JSON ==========
    
    @Test
    @DisplayName("Controller - Serializacao de resposta JSON")
    void testJsonSerialization() throws Exception {
        // Arrange
        when(productService.getApplicationInfo())
            .thenReturn("Test Application Info");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/singleton/app-info")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.applicationInfo").isString())
                .andExpect(jsonPath("$.pattern").isString())
                .andExpect(jsonPath("$.description").isString());
        
        System.out.println("✓ Controller JSON Serialization testado com sucesso");
    }
    
    // ========== TESTE DE PARAMETROS DE QUERY ==========
    
    @Test
    @DisplayName("Controller - Manipulacao de parametros de query complexos")
    void testComplexQueryParameters() throws Exception {
        // Arrange
        when(productService.calculateDiscountedPrice(any(BigDecimal.class), any()))
            .thenReturn(new BigDecimal("85.50"));
        when(productService.getPriceCalculationDetails(any(BigDecimal.class), any()))
            .thenReturn("Test calculation details");
        
        // Act & Assert
        mockMvc.perform(get("/design-patterns/strategy/calculate-price")
                .param("originalPrice", "123.45")
                .param("strategyType", "percentage")
                .param("discountValue", "12.5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalPrice").value(123.45))
                .andExpect(jsonPath("$.finalPrice").value(85.50));
        
        System.out.println("✓ Controller Complex Query Parameters testado com sucesso");
    }
}