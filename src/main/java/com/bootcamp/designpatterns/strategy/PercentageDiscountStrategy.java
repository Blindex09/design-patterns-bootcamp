package com.bootcamp.designpatterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estrategia concreta para desconto percentual
 * 
 * Implementa um desconto baseado em percentual do valor original.
 */
public class PercentageDiscountStrategy implements DiscountStrategy {
    
    // Percentual de desconto (ex: 0.10 para 10%)
    private final BigDecimal percentage;
    
    /**
     * Construtor da estrategia de desconto percentual
     * 
     * @param percentage percentual de desconto (0.0 a 1.0)
     */
    public PercentageDiscountStrategy(BigDecimal percentage) {
        if (percentage.compareTo(BigDecimal.ZERO) < 0 || percentage.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentage = percentage;
    }
    
    /**
     * Calcula o desconto baseado no percentual
     * 
     * @param originalValue valor original do produto
     * @return valor do desconto calculado
     */
    @Override
    public BigDecimal calculateDiscount(BigDecimal originalValue) {
        if (originalValue == null || originalValue.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        return originalValue.multiply(percentage).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Retorna a descricao do desconto
     * 
     * @return descricao do desconto percentual
     */
    @Override
    public String getDescription() {
        return "Desconto Percentual";
    }
    
    /**
     * Retorna informacao sobre o percentual aplicado
     * 
     * @return percentual formatado
     */
    @Override
    public String getDiscountInfo() {
        return percentage.multiply(new BigDecimal("100")).setScale(1, RoundingMode.HALF_UP) + "%";
    }
}