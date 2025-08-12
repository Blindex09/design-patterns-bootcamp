package com.bootcamp.designpatterns.facade;

/**
 * Classe que representa informacoes de entrega
 */
public class ShippingInfo {
    private String zipCode;
    private double cost;
    private int estimatedDays;
    
    public ShippingInfo(String zipCode, double cost, int estimatedDays) {
        this.zipCode = zipCode;
        this.cost = cost;
        this.estimatedDays = estimatedDays;
    }
    
    // Getters
    public String getZipCode() { return zipCode; }
    public double getCost() { return cost; }
    public int getEstimatedDays() { return estimatedDays; }
    
    @Override
    public String toString() {
        return String.format("ShippingInfo{zipCode='%s', cost=R$%.2f, days=%d}", 
                           zipCode, cost, estimatedDays);
    }
}