package com.bootcamp.designpatterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estrategia concreta para desconto progressivo
 * 
 * Implementa um desconto que aumenta conforme o valor da compra.
 * Faixas de desconto:
 * - Ate R$ 100: 5%
 * - De R$ 100 a R$ 500: 10%
 * - Acima de R$ 500: 15%
 */
public class ProgressiveDiscountStrategy implements DiscountStrategy {
    
    // Faixas de valor para desconto progressivo
    private static final BigDecimal FIRST_TIER = new BigDecimal("100.00");
    private static final BigDecimal SECOND_TIER = new BigDecimal("500.00");
    
    // Percentuais de desconto por faixa
    private static final BigDecimal FIRST_PERCENTAGE = new BigDecimal("0.05");  // 5%
    private static final BigDecimal SECOND_PERCENTAGE = new BigDecimal("0.10"); // 10%
    private static final BigDecimal THIRD_PERCENTAGE = new BigDecimal("0.15");  // 15%
    
    /**
     * Calcula o desconto progressivo baseado no valor
     * 
     * @param originalValue valor original do produto
     * @return valor do desconto calculado progressivamente
     */
    @Override
    public BigDecimal calculateDiscount(BigDecimal originalValue) {
        if (originalValue == null || originalValue.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal percentage;
        
        // Determina o percentual baseado na faixa de valor
        if (originalValue.compareTo(FIRST_TIER) <= 0) {
            percentage = FIRST_PERCENTAGE;
        } else if (originalValue.compareTo(SECOND_TIER) <= 0) {
            percentage = SECOND_PERCENTAGE;
        } else {
            percentage = THIRD_PERCENTAGE;
        }
        
        return originalValue.multiply(percentage).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Retorna a descricao do desconto
     * 
     * @return descricao do desconto progressivo
     */
    @Override
    public String getDescription() {
        return "Desconto Progressivo";
    }
    
    /**
     * Retorna informacao sobre as faixas de desconto
     * 
     * @return explicacao das faixas
     */
    @Override
    public String getDiscountInfo() {
        return "5% ate R$100, 10% ate R$500, 15% acima de R$500";
    }
    
    /**
     * Metodo auxiliar para obter o percentual aplicado a um valor especifico
     * 
     * @param value valor para verificar o percentual
     * @return percentual que seria aplicado
     */
    public String getAppliedPercentage(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            return "0%";
        }
        
        if (value.compareTo(FIRST_TIER) <= 0) {
            return "5%";
        } else if (value.compareTo(SECOND_TIER) <= 0) {
            return "10%";
        } else {
            return "15%";
        }
    }
}