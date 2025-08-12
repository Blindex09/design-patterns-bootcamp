package com.bootcamp.designpatterns.facade;

/**
 * Classe que representa a disponibilidade de um produto
 */
public class ProductAvailability {
    private String productId;
    private int requestedQuantity;
    private boolean available;
    
    public ProductAvailability(String productId, int requestedQuantity, boolean available) {
        this.productId = productId;
        this.requestedQuantity = requestedQuantity;
        this.available = available;
    }
    
    // Getters
    public String getProductId() { return productId; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public boolean isAvailable() { return available; }
    
    @Override
    public String toString() {
        return String.format("ProductAvailability{productId='%s', quantity=%d, available=%s}", 
                           productId, requestedQuantity, available);
    }
}