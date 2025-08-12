package com.bootcamp.designpatterns.facade;

/**
 * Classe que representa o resultado do processamento de um pedido
 */
public class OrderResult {
    
    private boolean success;
    private String message;
    private String orderId;
    private String transactionId;
    private String trackingCode;
    
    public OrderResult(boolean success, String message, String orderId, 
                      String transactionId, String trackingCode) {
        this.success = success;
        this.message = message;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.trackingCode = trackingCode;
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getOrderId() { return orderId; }
    public String getTransactionId() { return transactionId; }
    public String getTrackingCode() { return trackingCode; }
    
    @Override
    public String toString() {
        return String.format("OrderResult{success=%s, message='%s', orderId='%s', transactionId='%s', trackingCode='%s'}", 
                           success, message, orderId, transactionId, trackingCode);
    }
}