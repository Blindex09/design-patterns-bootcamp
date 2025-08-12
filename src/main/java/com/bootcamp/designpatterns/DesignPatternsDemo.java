package com.bootcamp.designpatterns;

import com.bootcamp.designpatterns.singleton.DatabaseConnection;
import com.bootcamp.designpatterns.singleton.ConfigurationManager;
import com.bootcamp.designpatterns.strategy.*;
import com.bootcamp.designpatterns.facade.*;

import java.math.BigDecimal;

/**
 * Classe de demonstração dos padrões GoF
 * 
 * Esta classe pode ser executada independentemente para mostrar
 * como os padrões funcionam sem necessidade do Spring Boot.
 * 
 * Execute: java -cp target/classes com.bootcamp.designpatterns.DesignPatternsDemo
 */
public class DesignPatternsDemo {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("      DEMONSTRAÇÃO DOS PADRÕES DESIGN PATTERNS");
        System.out.println("      Gang of Four (GoF) com Java");
        System.out.println("=".repeat(60));
        
        // Demonstração do padrão Singleton
        demonstrateSingleton();
        
        System.out.println("\n" + "=".repeat(60));
        
        // Demonstração do padrão Strategy
        demonstrateStrategy();
        
        System.out.println("\n" + "=".repeat(60));
        
        // Demonstração do padrão Facade
        demonstrateFacade();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("      DEMONSTRAÇÃO COMPLETA FINALIZADA!");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Demonstra o padrão Singleton
     */
    private static void demonstrateSingleton() {
        System.out.println("🔄 PADRÃO SINGLETON");
        System.out.println("-".repeat(40));
        
        System.out.println("\n1. Testando DatabaseConnection Singleton:");
        
        // Obtém múltiplas instâncias e verifica se são a mesma
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        DatabaseConnection db3 = DatabaseConnection.getInstance();
        
        System.out.println("   Instância 1: " + db1.hashCode());
        System.out.println("   Instância 2: " + db2.hashCode());
        System.out.println("   Instância 3: " + db3.hashCode());
        System.out.println("   São a mesma instância? " + (db1 == db2 && db2 == db3 ? "✅ SIM" : "❌ NÃO"));
        
        // Testa funcionalidade
        String queryResult = db1.executeQuery("SELECT * FROM usuarios WHERE ativo = true");
        System.out.println("   Resultado da query: " + queryResult);
        
        System.out.println("\n2. Testando ConfigurationManager Enum Singleton:");
        
        // Testa singleton enum
        ConfigurationManager config = ConfigurationManager.INSTANCE;
        config.loadConfiguration("Demo App", "2.0.0", "demonstration");
        
        System.out.println("   Configuração carregada:");
        System.out.println("   - Aplicação: " + config.getProperty("name"));
        System.out.println("   - Versão: " + config.getProperty("version"));
        System.out.println("   - Ambiente: " + config.getProperty("environment"));
        
        // Verifica se a instância é sempre a mesma
        ConfigurationManager config2 = ConfigurationManager.INSTANCE;
        System.out.println("   Mesma instância? " + (config == config2 ? "✅ SIM" : "❌ NÃO"));
        
        System.out.println("\n✅ Padrão Singleton verificado com sucesso!");
        System.out.println("   • Garante uma única instância global");
        System.out.println("   • Thread-safe e eficiente");
        System.out.println("   • Controla acesso a recursos compartilhados");
    }
    
    /**
     * Demonstra o padrão Strategy
     */
    private static void demonstrateStrategy() {
        System.out.println("🎯 PADRÃO STRATEGY");
        System.out.println("-".repeat(40));
        
        BigDecimal originalPrice = new BigDecimal("500.00");
        System.out.println("\nPreço original do produto: R$ " + originalPrice);
        
        PriceCalculator calculator = new PriceCalculator();
        
        System.out.println("\n1. Aplicando diferentes estratégias de desconto:");
        
        // Estratégia 1: Desconto Percentual
        System.out.println("\n   📊 Estratégia Percentual (20%):");
        DiscountStrategy percentageStrategy = new PercentageDiscountStrategy(new BigDecimal("0.20"));
        calculator.setDiscountStrategy(percentageStrategy);
        BigDecimal finalPrice1 = calculator.calculateFinalPrice(originalPrice);
        BigDecimal discount1 = calculator.calculateDiscountAmount(originalPrice);
        System.out.println("      Desconto: R$ " + discount1);
        System.out.println("      Preço final: R$ " + finalPrice1);
        System.out.println("      Detalhes: " + calculator.getCalculationDetails(originalPrice));
        
        // Estratégia 2: Desconto Fixo
        System.out.println("\n   💰 Estratégia Desconto Fixo (R$ 100):");
        DiscountStrategy fixedStrategy = new FixedDiscountStrategy(new BigDecimal("100.00"));
        calculator.setDiscountStrategy(fixedStrategy);
        BigDecimal finalPrice2 = calculator.calculateFinalPrice(originalPrice);
        BigDecimal discount2 = calculator.calculateDiscountAmount(originalPrice);
        System.out.println("      Desconto: R$ " + discount2);
        System.out.println("      Preço final: R$ " + finalPrice2);
        System.out.println("      Detalhes: " + calculator.getCalculationDetails(originalPrice));
        
        // Estratégia 3: Desconto Progressivo
        System.out.println("\n   📈 Estratégia Progressiva (baseada no valor):");
        DiscountStrategy progressiveStrategy = new ProgressiveDiscountStrategy();
        calculator.setDiscountStrategy(progressiveStrategy);
        BigDecimal finalPrice3 = calculator.calculateFinalPrice(originalPrice);
        BigDecimal discount3 = calculator.calculateDiscountAmount(originalPrice);
        System.out.println("      Desconto: R$ " + discount3);
        System.out.println("      Preço final: R$ " + finalPrice3);
        System.out.println("      Detalhes: " + calculator.getCalculationDetails(originalPrice));
        
        System.out.println("\n2. Demonstrando flexibilidade do padrão:");
        
        // Mudança de estratégia em tempo de execução
        BigDecimal testPrice = new BigDecimal("150.00");
        System.out.println("\n   Testando com preço R$ " + testPrice + ":");
        
        calculator.setDiscountStrategy(progressiveStrategy);
        System.out.println("   • Progressivo: R$ " + calculator.calculateFinalPrice(testPrice));
        
        calculator.setDiscountStrategy(new PercentageDiscountStrategy(new BigDecimal("0.10")));
        System.out.println("   • Percentual 10%: R$ " + calculator.calculateFinalPrice(testPrice));
        
        calculator.setDiscountStrategy(new FixedDiscountStrategy(new BigDecimal("25.00")));
        System.out.println("   • Fixo R$ 25: R$ " + calculator.calculateFinalPrice(testPrice));
        
        System.out.println("\n✅ Padrão Strategy verificado com sucesso!");
        System.out.println("   • Permite trocar algoritmos em tempo de execução");
        System.out.println("   • Facilita adição de novas estratégias");
        System.out.println("   • Respeita o princípio Aberto/Fechado");
    }
    
    /**
     * Demonstra o padrão Facade
     */
    private static void demonstrateFacade() {
        System.out.println("🏢 PADRÃO FACADE");
        System.out.println("-".repeat(40));
        
        EcommerceFacade facade = new EcommerceFacade();
        
        System.out.println("\n1. Verificando disponibilidade de produtos:");
        
        // Teste com produto disponível
        String productId1 = "NOTEBOOK_DELL_001";
        int quantity1 = 3;
        var availability1 = facade.checkProductAvailability(productId1, quantity1);
        System.out.println("\n   📦 Produto: " + productId1);
        System.out.println("      Quantidade solicitada: " + quantity1);
        System.out.println("      Resultado: " + availability1);
        
        // Teste com produto indisponível  
        String productId2 = "SMARTPHONE_APPLE_999";
        int quantity2 = 20;
        var availability2 = facade.checkProductAvailability(productId2, quantity2);
        System.out.println("\n   📦 Produto: " + productId2);
        System.out.println("      Quantidade solicitada: " + quantity2);
        System.out.println("      Resultado: " + availability2);
        
        System.out.println("\n2. Calculando informações de entrega:");
        
        // Teste com diferentes CEPs
        String[] ceps = {"01000-000", "13000-000", "80000-000"};
        String[] regioes = {"Região Metropolitana", "Interior", "Outras Regiões"};
        
        for (int i = 0; i < ceps.length; i++) {
            var shippingInfo = facade.getShippingInfo(ceps[i]);
            System.out.println("\n   🚚 " + regioes[i] + " (" + ceps[i] + "):");
            System.out.println("      " + shippingInfo);
        }
        
        System.out.println("\n3. Processando pedido completo:");
        
        // Criando um pedido de exemplo
        OrderRequest order = new OrderRequest();
        order.setProductId("NOTEBOOK_DELL_002"); // Produto par (tem estoque)
        order.setQuantity(2);
        order.setAmount(new BigDecimal("2499.99"));
        order.setCardNumber("4532123456789012");
        order.setCvv("123");
        order.setExpiryDate("12/26");
        order.setAddress("Av. Paulista, 1000, São Paulo, SP");
        order.setZipCode("01310-100");
        
        System.out.println("\n   🛒 Dados do pedido:");
        System.out.println("      " + order);
        
        System.out.println("\n   ⚙️ Processando pedido...");
        OrderResult result = facade.processOrder(order);
        
        System.out.println("\n   📋 Resultado do processamento:");
        System.out.println("      " + result);
        
        if (result.isSuccess()) {
            System.out.println("\n   ✅ Pedido processado com sucesso!");
            System.out.println("      ID do Pedido: " + result.getOrderId());
            System.out.println("      ID da Transação: " + result.getTransactionId());
            System.out.println("      Código de Rastreamento: " + result.getTrackingCode());
        } else {
            System.out.println("\n   ❌ Falha no processamento:");
            System.out.println("      Motivo: " + result.getMessage());
        }
        
        System.out.println("\n✅ Padrão Facade verificado com sucesso!");
        System.out.println("   • Simplifica operações complexas");
        System.out.println("   • Oculta complexidade dos subsistemas");
        System.out.println("   • Fornece interface unificada e intuitiva");
    }
}