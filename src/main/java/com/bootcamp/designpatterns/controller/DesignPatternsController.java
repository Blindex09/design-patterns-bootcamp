package com.bootcamp.designpatterns.controller;

import com.bootcamp.designpatterns.service.ProductService;
import com.bootcamp.designpatterns.strategy.*;
import com.bootcamp.designpatterns.facade.OrderRequest;
import com.bootcamp.designpatterns.facade.EcommerceFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Controller REST que demonstra os padroes GoF em acao
 * 
 * Este controller expoe endpoints que utilizam:
 * - Singleton: Para configuracoes e conexoes
 * - Strategy: Para calculos de preco com diferentes estrategias
 * - Facade: Para operacoes complexas de e-commerce
 */
@RestController
@RequestMapping("/design-patterns")
@Tag(name = "Design Patterns", description = "APIs demonstrando padroes GoF com Spring")
@Validated
public class DesignPatternsController {
    
    @Autowired
    private ProductService productService;
    
    private final EcommerceFacade ecommerceFacade = new EcommerceFacade();
    
    /**
     * Endpoint que demonstra o padrao Strategy
     * Calcula precos com diferentes estrategias de desconto
     */
    @GetMapping("/strategy/calculate-price")
    @Operation(summary = "Calcula preco com desconto usando Strategy Pattern", 
               description = "Aplica diferentes estrategias de desconto: percentual, fixo ou progressivo")
    public ResponseEntity<Map<String, Object>> calculatePriceWithStrategy(
            @Parameter(description = "Preco original do produto")
            @RequestParam @DecimalMin(value = "0.01", message = "Preco deve ser maior que zero") BigDecimal originalPrice,
            
            @Parameter(description = "Tipo de estrategia: percentage, fixed, progressive")
            @RequestParam @NotBlank String strategyType,
            
            @Parameter(description = "Valor do desconto (percentual de 0-100 ou valor fixo)")
            @RequestParam(required = false) BigDecimal discountValue) {
        
        try {
            DiscountStrategy strategy = createStrategy(strategyType, discountValue);
            
            BigDecimal finalPrice = productService.calculateDiscountedPrice(originalPrice, strategy);
            String details = productService.getPriceCalculationDetails(originalPrice, strategy);
            
            Map<String, Object> response = new HashMap<>();
            response.put("originalPrice", originalPrice);
            response.put("finalPrice", finalPrice);
            response.put("discount", originalPrice.subtract(finalPrice));
            response.put("strategy", strategy.getDescription());
            response.put("strategyInfo", strategy.getDiscountInfo());
            response.put("details", details);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Erro ao calcular preco: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Endpoint que demonstra multiplas estrategias
     */
    @GetMapping("/strategy/compare-all")
    @Operation(summary = "Compara todas as estrategias de desconto", 
               description = "Mostra como o mesmo preco fica com diferentes estrategias")
    public ResponseEntity<Map<String, Object>> compareAllStrategies(
            @Parameter(description = "Preco original para comparacao")
            @RequestParam @DecimalMin(value = "0.01") BigDecimal originalPrice) {
        
        List<String> strategies = productService.demonstrateAllStrategies(originalPrice);
        
        Map<String, Object> response = new HashMap<>();
        response.put("originalPrice", originalPrice);
        response.put("strategies", strategies);
        response.put("pattern", "Strategy Pattern");
        response.put("description", "Permite trocar algoritmos de desconto em tempo de execucao");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint que demonstra o padrao Facade
     * Verifica disponibilidade de produto de forma simplificada
     */
    @GetMapping("/facade/check-availability")
    @Operation(summary = "Verifica disponibilidade usando Facade Pattern", 
               description = "Simplifica operacoes complexas de verificacao de estoque")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @Parameter(description = "ID do produto")
            @RequestParam @NotBlank String productId,
            
            @Parameter(description = "Quantidade desejada")
            @RequestParam @Min(value = 1, message = "Quantidade deve ser maior que zero") int quantity) {
        
        String availability = productService.checkProductAvailability(productId, quantity);
        
        Map<String, Object> response = new HashMap<>();
        response.put("productId", productId);
        response.put("requestedQuantity", quantity);
        response.put("availability", availability);
        response.put("pattern", "Facade Pattern");
        response.put("description", "Fornece interface simplificada para subsistemas complexos");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint que demonstra calculo de frete via Facade
     */
    @GetMapping("/facade/shipping-info")
    @Operation(summary = "Calcula informacoes de entrega usando Facade", 
               description = "Obtem custos e prazos de entrega de forma simplificada")
    public ResponseEntity<Map<String, Object>> getShippingInfo(
            @Parameter(description = "CEP de destino")
            @RequestParam @NotBlank String zipCode) {
        
        String shippingInfo = productService.calculateShippingInfo(zipCode);
        
        Map<String, Object> response = new HashMap<>();
        response.put("zipCode", zipCode);
        response.put("shippingInfo", shippingInfo);
        response.put("pattern", "Facade Pattern");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint que processa pedido completo usando Facade
     */
    @PostMapping("/facade/process-order")
    @Operation(summary = "Processa pedido completo usando Facade Pattern", 
               description = "Orquestra estoque, pagamento e entrega em uma unica operacao")
    public ResponseEntity<Map<String, Object>> processOrder(@Valid @RequestBody OrderRequest orderRequest) {
        
        try {
            var result = ecommerceFacade.processOrder(orderRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", result.isSuccess());
            response.put("message", result.getMessage());
            response.put("orderId", result.getOrderId());
            response.put("transactionId", result.getTransactionId());
            response.put("trackingCode", result.getTrackingCode());
            response.put("pattern", "Facade Pattern");
            response.put("description", "Simplifica processo complexo de pedido");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Erro ao processar pedido: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    /**
     * Endpoint que demonstra o padrao Singleton
     */
    @GetMapping("/singleton/app-info")
    @Operation(summary = "Obtem informacoes da aplicacao usando Singleton Pattern", 
               description = "Mostra configuracoes globais mantidas em instancia unica")
    public ResponseEntity<Map<String, Object>> getApplicationInfo() {
        
        String appInfo = productService.getApplicationInfo();
        
        Map<String, Object> response = new HashMap<>();
        response.put("applicationInfo", appInfo);
        response.put("pattern", "Singleton Pattern");
        response.put("description", "Garante uma unica instancia de configuracao global");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint que demonstra todos os padroes em uma operacao complexa
     */
    @GetMapping("/complete-demo")
    @Operation(summary = "Demonstracao completa de todos os padroes", 
               description = "Executa operacao que utiliza Singleton, Strategy e Facade")
    public ResponseEntity<Map<String, Object>> completeDemo(
            @Parameter(description = "ID do produto") @RequestParam @NotBlank String productId,
            @Parameter(description = "Quantidade") @RequestParam @Min(1) int quantity,
            @Parameter(description = "Preco original") @RequestParam @DecimalMin("0.01") BigDecimal originalPrice,
            @Parameter(description = "CEP para entrega") @RequestParam @NotBlank String zipCode) {
        
        String report = productService.performCompleteOperation(productId, quantity, originalPrice, zipCode);
        
        Map<String, Object> response = new HashMap<>();
        response.put("report", report);
        response.put("patterns", List.of("Singleton", "Strategy", "Facade"));
        response.put("description", "Demonstracao completa dos padroes GoF com Spring Framework");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Metodo auxiliar para criar estrategias baseado no tipo
     */
    private DiscountStrategy createStrategy(String type, BigDecimal value) {
        switch (type.toLowerCase()) {
            case "percentage":
                BigDecimal percentage = value != null ? value.divide(new BigDecimal("100")) : new BigDecimal("0.10");
                return new PercentageDiscountStrategy(percentage);
            case "fixed":
                BigDecimal fixedValue = value != null ? value : new BigDecimal("50.00");
                return new FixedDiscountStrategy(fixedValue);
            case "progressive":
                return new ProgressiveDiscountStrategy();
            default:
                throw new IllegalArgumentException("Tipo de estrategia nao suportado: " + type);
        }
    }
}