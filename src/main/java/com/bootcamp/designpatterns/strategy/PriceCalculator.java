package com.bootcamp.designpatterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Contexto que utiliza as estrategias de desconto
 * 
 * Esta classe encapsula o uso das diferentes estrategias de desconto,
 * permitindo trocar o algoritmo de calculo em tempo de execucao.
 */
public class PriceCalculator {
    
    // Estrategia atual de desconto
    private DiscountStrategy discountStrategy;
    
    /**
     * Construtor com estrategia inicial
     * 
     * @param discountStrategy estrategia de desconto a ser utilizada
     */
    public PriceCalculator(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }
    
    /**
     * Construtor padrao sem estrategia (sem desconto)
     */
    public PriceCalculator() {
        // Estrategia padrao: sem desconto
        this.discountStrategy = new NoDiscountStrategy();
    }
    
    /**
     * Altera a estrategia de desconto em tempo de execucao
     * 
     * @param discountStrategy nova estrategia de desconto
     */
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy != null ? discountStrategy : new NoDiscountStrategy();
    }
    
    /**
     * Calcula o preco final aplicando a estrategia de desconto
     * 
     * @param originalPrice preco original do produto
     * @return preco final com desconto aplicado
     */
    public BigDecimal calculateFinalPrice(BigDecimal originalPrice) {
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal discount = discountStrategy.calculateDiscount(originalPrice);
        return originalPrice.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Calcula apenas o valor do desconto
     * 
     * @param originalPrice preco original do produto
     * @return valor do desconto
     */
    public BigDecimal calculateDiscountAmount(BigDecimal originalPrice) {
        return discountStrategy.calculateDiscount(originalPrice);
    }
    
    /**
     * Obtem informacoes detalhadas sobre o calculo
     * 
     * @param originalPrice preco original
     * @return detalhes do calculo em formato string
     */
    public String getCalculationDetails(BigDecimal originalPrice) {
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return "Preco invalido";
        }
        
        BigDecimal discount = calculateDiscountAmount(originalPrice);
        BigDecimal finalPrice = calculateFinalPrice(originalPrice);
        
        return String.format(
            "Tipo: %s (%s) | Preco Original: R$ %.2f | Desconto: R$ %.2f | Preco Final: R$ %.2f",
            discountStrategy.getDescription(),
            discountStrategy.getDiscountInfo(),
            originalPrice,
            discount,
            finalPrice
        );
    }
    
    /**
     * Retorna a estrategia atual
     * 
     * @return estrategia de desconto em uso
     */
    public DiscountStrategy getCurrentStrategy() {
        return discountStrategy;
    }
    
    /**
     * Estrategia padrao para quando nao ha desconto
     */
    private static class NoDiscountStrategy implements DiscountStrategy {
        @Override
        public BigDecimal calculateDiscount(BigDecimal originalValue) {
            return BigDecimal.ZERO;
        }
        
        @Override
        public String getDescription() {
            return "Sem Desconto";
        }
        
        @Override
        public String getDiscountInfo() {
            return "0%";
        }
    }
}