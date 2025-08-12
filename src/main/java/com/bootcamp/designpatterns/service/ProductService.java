package com.bootcamp.designpatterns.service;

import com.bootcamp.designpatterns.model.Product;
import com.bootcamp.designpatterns.singleton.ConfigurationManager;
import com.bootcamp.designpatterns.singleton.DatabaseConnection;
import com.bootcamp.designpatterns.strategy.DiscountStrategy;
import com.bootcamp.designpatterns.strategy.PriceCalculator;
import com.bootcamp.designpatterns.facade.EcommerceFacade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.Optional;

/**
 * Servico que demonstra a integracao dos padroes GoF com Spring
 * 
 * Este servico combina:
 * - Singleton: ConfigurationManager e DatabaseConnection
 * - Strategy: PriceCalculator com diferentes estrategias de desconto
 * - Facade: EcommerceFacade para operacoes complexas
 * 
 * O Spring gerencia o ciclo de vida desta classe como Singleton
 */
@Service
@Transactional
public class ProductService {
    
    // Facade para operacoes de e-commerce
    private final EcommerceFacade ecommerceFacade;
    
    // Calculator para aplicacao de descontos (Strategy)
    private final PriceCalculator priceCalculator;
    
    /**
     * Construtor que inicializa as dependencias
     * O Spring ira injetar automaticamente se houver beans configurados
     */
    public ProductService() {
        this.ecommerceFacade = new EcommerceFacade();
        this.priceCalculator = new PriceCalculator();
        
        // Configura o singleton de configuracao
        ConfigurationManager.INSTANCE.loadConfiguration(
            "Design Patterns Bootcamp API",
            "1.0.0",
            "production"
        );
    }
    
    /**
     * Calcula preco com desconto usando Strategy Pattern
     * 
     * @param originalPrice preco original
     * @param strategy estrategia de desconto a ser aplicada
     * @return preco final com desconto
     */
    public BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, DiscountStrategy strategy) {
        // Utiliza o padrao Strategy para calcular desconto
        priceCalculator.setDiscountStrategy(strategy);
        BigDecimal finalPrice = priceCalculator.calculateFinalPrice(originalPrice);
        
        // Log usando Singleton de configuracao
        String appn = ConfigurationManager.INSTANCE.getProperty("n");
        System.out.println(String.format("[%s] Calculando preco com desconto: R$ %.2f -> R$ %.2f", 
                                        appn, originalPrice, finalPrice));
        
        return finalPrice;
    }
    
    /**
     * Obtem detalhes de calculo de preco
     * 
     * @param originalPrice preco original
     * @param strategy estrategia de desconto
     * @return detalhes formatados do calculo
     */
    public String getPriceCalculationDetails(BigDecimal originalPrice, DiscountStrategy strategy) {
        priceCalculator.setDiscountStrategy(strategy);
        return priceCalculator.getCalculationDetails(originalPrice);
    }
    
    /**
     * Simula verificacao de disponibilidade usando Facade
     * 
     * @param productId ID do produto
     * @param quantity quantidade desejada
     * @return informacoes de disponibilidade
     */
    public String checkProductAvailability(String productId, int quantity) {
        // Utiliza Facade para simplificar operacao complexa
        var availability = ecommerceFacade.checkProductAvailability(productId, quantity);
        
        // Log usando Singleton de conexao
        DatabaseConnection db = DatabaseConnection.getInstance();
        String queryResult = db.executeQuery("SELECT * FROM products WHERE id = '" + productId + "'");
        System.out.println("Consulta executada: " + queryResult);
        
        return availability.toString();
    }
    
    /**
     * Calcula informacoes de entrega usando Facade
     * 
     * @param zipCode CEP de destino
     * @return informacoes de entrega formatadas
     */
    public String calculateShippingInfo(String zipCode) {
        var shippingInfo = ecommerceFacade.getShippingInfo(zipCode);
        return shippingInfo.toString();
    }
    
    /**
     * Obtem configuracoes da aplicacao usando Singleton
     * 
     * @return configuracoes atuais
     */
    public String getApplicationInfo() {
        ConfigurationManager config = ConfigurationManager.INSTANCE;
        
        return String.format("Aplicacao: %s | Versao: %s | Ambiente: %s", 
                           config.getProperty("n"),
                           config.getProperty("version"),
                           config.getProperty("environment"));
    }
    
    /**
     * Demonstra uso de multiplas estrategias de desconto
     * 
     * @param originalPrice preco original
     * @return lista com precos calculados por diferentes estrategias
     */
    public List<String> demonstrateAllStrategies(BigDecimal originalPrice) {
        // Importa as estrategias (normalmente seria injetado pelo Spring)
        var percentageStrategy = new com.bootcamp.designpatterns.strategy.PercentageDiscountStrategy(new BigDecimal("0.10"));
        var fixedStrategy = new com.bootcamp.designpatterns.strategy.FixedDiscountStrategy(new BigDecimal("50.00"));
        var progressiveStrategy = new com.bootcamp.designpatterns.strategy.ProgressiveDiscountStrategy();
        
        return Arrays.asList(
            getPriceCalculationDetails(originalPrice, percentageStrategy),
            getPriceCalculationDetails(originalPrice, fixedStrategy),
            getPriceCalculationDetails(originalPrice, progressiveStrategy)
        );
    }
    
    /**
     * Metodo que simula operacao complexa usando todos os padroes
     * 
     * @param productId ID do produto
     * @param quantity quantidade
     * @param originalPrice preco original
     * @param zipCode CEP para entrega
     * @return relatorio completo da operacao
     */
    public String performCompleteOperation(String productId, int quantity, 
                                         BigDecimal originalPrice, String zipCode) {
        
        StringBuilder report = new StringBuilder();
        report.append("=== RELATORIO COMPLETO DA OPERACAO ===\n");
        
        // Informacoes da aplicacao (Singleton)
        report.append("1. ").append(getApplicationInfo()).append("\n");
        
        // Verificacao de disponibilidade (Facade)
        report.append("2. Disponibilidade: ").append(checkProductAvailability(productId, quantity)).append("\n");
        
        // Calculo de frete (Facade)
        report.append("3. Entrega: ").append(calculateShippingInfo(zipCode)).append("\n");
        
        // Demonstracao de estrategias (Strategy)
        report.append("4. Estrategias de Desconto:\n");
        List<String> strategies = demonstrateAllStrategies(originalPrice);
        for (int i = 0; i < strategies.size(); i++) {
            report.append("   ").append(i + 1).append(". ").append(strategies.get(i)).append("\n");
        }
        
        return report.toString();
    }
}