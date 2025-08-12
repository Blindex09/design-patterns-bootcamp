package com.bootcamp.designpatterns.facade;

import java.math.BigDecimal;

/**
 * Subsistema: Servico de Pagamento
 * 
 * Componente interno responsavel pelo processamento de pagamentos.
 */
public class PaymentService {
    
    /**
     * Valida os dados do cartao de credito
     * 
     * @param cardNumber numero do cartao
     * @param cvv codigo de seguranca
     * @param expiryDate data de expiracao
     * @return true se os dados sao validos
     */
    public boolean validateCard(String cardNumber, String cvv, String expiryDate) {
        System.out.println("PaymentService: Validando dados do cartao");
        
        // Validacoes basicas simuladas
        boolean isValid = cardNumber != null && cardNumber.length() >= 16 &&
                         cvv != null && cvv.length() == 3 &&
                         expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}");
        
        System.out.println("PaymentService: Dados do cartao " + 
                          (isValid ? "validos" : "invalidos"));
        
        return isValid;
    }
    
    /**
     * Processa o pagamento
     * 
     * @param amount valor a ser cobrado
     * @param cardNumber numero do cartao
     * @return ID da transacao se bem sucedida, null caso contrario
     */
    public String processPayment(BigDecimal amount, String cardNumber) {
        System.out.println("PaymentService: Processando pagamento de R$ " + amount);
        
        // Simula processamento
        try {
            Thread.sleep(500); // Simula delay do processamento
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simula sucesso se o valor for menor que R$ 1000
        boolean success = amount.compareTo(new BigDecimal("1000")) < 0;
        
        if (success) {
            String transactionId = "TXN" + System.currentTimeMillis();
            System.out.println("PaymentService: Pagamento aprovado - ID: " + transactionId);
            return transactionId;
        } else {
            System.out.println("PaymentService: Pagamento rejeitado - valor muito alto");
            return null;
        }
    }
    
    /**
     * Verifica o status de uma transacao
     * 
     * @param transactionId ID da transacao
     * @return status da transacao
     */
    public String getTransactionStatus(String transactionId) {
        System.out.println("PaymentService: Consultando status da transacao " + transactionId);
        
        if (transactionId != null && transactionId.startsWith("TXN")) {
            return "APPROVED";
        } else {
            return "NOT_FOUND";
        }
    }
}