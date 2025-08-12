package com.bootcamp.designpatterns.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estrategia concreta para desconto fixo
 * 
 * Implementa um desconto com valor fixo, independente do valor original.
 */
public class FixedDiscountStrategy implements DiscountStrategy {
    
    // Valor fixo de desconto
    private final BigDecimal fixedAmount;
    
    /**
     * Construtor da estrategia de desconto fixo
     * 
     * @param fixedAmount valor fixo do desconto
     */
    public FixedDiscountStrategy(BigDecimal fixedAmount) {
        if (fixedAmount == null || fixedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Valor fixo deve ser maior ou igual a zero");
        }
        this.fixedAmount = fixedAmount;
    }
    
    /**
     * Calcula o desconto fixo, limitado ao valor original
     * 
     * @param originalValue valor original do produto
     * @return valor do desconto (nao pode exceder o valor original)
     */
    @Override
    public BigDecimal calculateDiscount(BigDecimal originalValue) {
        if (originalValue == null || originalValue.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        
        // O desconto nao pode ser maior que o valor original
        return fixedAmount.min(originalValue).setScale(2, RoundingMode.HALF_UP);
    }
    
    /**
     * Retorna a descricao do desconto
     * 
     * @return descricao do desconto fixo
     */
    @Override
    public String getDescription() {
        return "Desconto Fixo";
    }
    
    /**
     * Retorna informacao sobre o valor fixo
     * 
     * @return valor fixo formatado
     */
    @Override
    public String getDiscountInfo() {
        return "R$ " + fixedAmount.setScale(2, RoundingMode.HALF_UP);
    }
}