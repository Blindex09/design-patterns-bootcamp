package com.bootcamp.designpatterns.strategy;

import java.math.BigDecimal;

/**
 * Interface Strategy para diferentes tipos de desconto
 * 
 * O padrao Strategy permite definir uma familia de algoritmos,
 * encapsula cada um deles e os torna intercambiaveis.
 * 
 * Esta interface define o contrato para calcular descontos.
 */
public interface DiscountStrategy {
    
    /**
     * Calcula o desconto baseado no valor original
     * 
     * @param originalValue valor original do produto
     * @return valor do desconto a ser aplicado
     */
    BigDecimal calculateDiscount(BigDecimal originalValue);
    
    /**
     * Retorna a descricao do tipo de desconto
     * 
     * @return descricao do desconto
     */
    String getDescription();
    
    /**
     * Retorna o percentual ou valor do desconto para exibicao
     * 
     * @return representacao string do desconto
     */
    String getDiscountInfo();
}