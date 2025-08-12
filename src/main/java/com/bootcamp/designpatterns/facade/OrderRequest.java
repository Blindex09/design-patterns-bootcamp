package com.bootcamp.designpatterns.facade;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Classe que representa uma requisicao de pedido
 * 
 * Encapsula todos os dados necessarios para processar um pedido.
 */
public class OrderRequest {
    
    @NotBlank(message = "ID do produto e obrigatorio")
    private String productId;
    
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private int quantity;
    
    @NotNull(message = "Valor e obrigatorio")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal amount;
    
    @NotBlank(message = "Numero do cartao e obrigatorio")
    private String cardNumber;
    
    @NotBlank(message = "CVV e obrigatorio")
    @Size(min = 3, max = 4, message = "CVV deve ter 3 ou 4 digitos")
    private String cvv;
    
    @NotBlank(message = "Data de validade e obrigatoria")
    private String expiryDate;
    
    @NotBlank(message = "Endereco e obrigatorio")
    private String address;
    
    @NotBlank(message = "CEP e obrigatorio")
    private String zipCode;
    
    // Construtor completo
    public OrderRequest(String productId, int quantity, BigDecimal amount,
                       String cardNumber, String cvv, String expiryDate,
                       String address, String zipCode) {
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.address = address;
        this.zipCode = zipCode;
    }
    
    // Construtor vazio para frameworks
    public OrderRequest() {}
    
    // Getters e Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    
    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    
    @Override
    public String toString() {
        return String.format("OrderRequest{productId='%s', quantity=%d, amount=%s, address='%s', zipCode='%s'}", 
                           productId, quantity, amount, address, zipCode);
    }
}