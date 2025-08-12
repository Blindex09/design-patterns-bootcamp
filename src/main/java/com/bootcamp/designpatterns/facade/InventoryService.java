package com.bootcamp.designpatterns.facade;

/**
 * Subsistema: Servico de Estoque
 * 
 * Componente interno responsavel pelo controle de estoque de produtos.
 */
public class InventoryService {
    
    /**
     * Verifica se ha estoque suficiente para um produto
     * 
     * @param productId ID do produto
     * @param quantity quantidade desejada
     * @return true se ha estoque suficiente
     */
    public boolean checkStock(String productId, int quantity) {
        // Simula verificacao de estoque
        System.out.println("InventoryService: Verificando estoque do produto " + productId);
        
        // Simula que produtos com ID par tem estoque suficiente
        boolean hasStock = productId.hashCode() % 2 == 0 && quantity <= 10;
        
        System.out.println("InventoryService: Estoque " + 
                          (hasStock ? "disponivel" : "insuficiente") + 
                          " para " + quantity + " unidades");
        
        return hasStock;
    }
    
    /**
     * Reserva itens no estoque
     * 
     * @param productId ID do produto
     * @param quantity quantidade a reservar
     * @return true se a reserva foi bem sucedida
     */
    public boolean reserveItems(String productId, int quantity) {
        System.out.println("InventoryService: Reservando " + quantity + 
                          " unidades do produto " + productId);
        
        // Simula reserva bem sucedida se ha estoque
        boolean reserved = checkStock(productId, quantity);
        
        if (reserved) {
            System.out.println("InventoryService: Itens reservados com sucesso");
        } else {
            System.out.println("InventoryService: Falha na reserva - estoque insuficiente");
        }
        
        return reserved;
    }
    
    /**
     * Atualiza o estoque apos venda
     * 
     * @param productId ID do produto
     * @param quantity quantidade vendida
     */
    public void updateStock(String productId, int quantity) {
        System.out.println("InventoryService: Atualizando estoque - removendo " + 
                          quantity + " unidades do produto " + productId);
    }
}