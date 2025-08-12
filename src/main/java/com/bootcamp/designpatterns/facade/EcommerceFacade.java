package com.bootcamp.designpatterns.facade;

import java.math.BigDecimal;

/**
 * Classe Facade para o sistema de e-commerce
 * 
 * O padrao Facade fornece uma interface unificada para um conjunto
 * de interfaces em um subsistema. Define uma interface de nivel mais
 * alto que torna o subsistema mais facil de usar.
 * 
 * Esta classe simplifica as operacoes complexas de:
 * - Verificacao de estoque
 * - Processamento de pagamento  
 * - Agendamento de entrega
 */
public class EcommerceFacade {
    
    // Referencias para os subsistemas
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final DeliveryService deliveryService;
    
    /**
     * Construtor que inicializa todos os subsistemas
     */
    public EcommerceFacade() {
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.deliveryService = new DeliveryService();
    }
    
    /**
     * Construtor para injecao de dependencias (util para testes)
     * 
     * @param inventoryService servico de estoque
     * @param paymentService servico de pagamento
     * @param deliveryService servico de entrega
     */
    public EcommerceFacade(InventoryService inventoryService, 
                          PaymentService paymentService, 
                          DeliveryService deliveryService) {
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }
    
    /**
     * Metodo principal que processa um pedido completo
     * Orquestra todas as operacoes necessarias de forma transparente
     * 
     * @param order dados do pedido
     * @return resultado do processamento
     */
    public OrderResult processOrder(OrderRequest order) {
        System.out.println("=== INICIANDO PROCESSAMENTO DO PEDIDO ===");
        
        try {
            // Etapa 1: Verificar e reservar estoque
            if (!inventoryService.checkStock(order.getProductId(), order.getQuantity())) {
                return new OrderResult(false, "Estoque insuficiente", null, null, null);
            }
            
            if (!inventoryService.reserveItems(order.getProductId(), order.getQuantity())) {
                return new OrderResult(false, "Falha na reserva do estoque", null, null, null);
            }
            
            // Etapa 2: Validar e processar pagamento
            if (!paymentService.validateCard(order.getCardNumber(), order.getCvv(), order.getExpiryDate())) {
                return new OrderResult(false, "Dados do cartao invalidos", null, null, null);
            }
            
            // Calcular frete
            double shippingCost = deliveryService.calculateShipping(order.getZipCode());
            BigDecimal totalAmount = order.getAmount().add(BigDecimal.valueOf(shippingCost));
            
            String transactionId = paymentService.processPayment(totalAmount, order.getCardNumber());
            if (transactionId == null) {
                return new OrderResult(false, "Pagamento rejeitado", null, null, null);
            }
            
            // Etapa 3: Agendar entrega
            String orderId = "ORD" + System.currentTimeMillis();
            String trackingCode = deliveryService.scheduleDelivery(orderId, order.getAddress(), order.getZipCode());
            
            // Etapa 4: Atualizar estoque
            inventoryService.updateStock(order.getProductId(), order.getQuantity());
            
            System.out.println("=== PEDIDO PROCESSADO COM SUCESSO ===");
            
            return new OrderResult(true, "Pedido processado com sucesso", 
                                 orderId, transactionId, trackingCode);
            
        } catch (Exception e) {
            System.err.println("Erro no processamento do pedido: " + e.getMessage());
            return new OrderResult(false, "Erro interno no processamento", null, null, null);
        }
    }
    
    /**
     * Verifica disponibilidade de um produto
     * Interface simplificada para consulta de estoque
     * 
     * @param productId ID do produto
     * @param quantity quantidade desejada
     * @return informacoes de disponibilidade
     */
    public ProductAvailability checkProductAvailability(String productId, int quantity) {
        System.out.println("=== VERIFICANDO DISPONIBILIDADE ===");
        
        boolean available = inventoryService.checkStock(productId, quantity);
        return new ProductAvailability(productId, quantity, available);
    }
    
    /**
     * Calcula custos de entrega
     * Interface simplificada para calculo de frete
     * 
     * @param zipCode CEP de destino
     * @return informacoes de entrega
     */
    public ShippingInfo getShippingInfo(String zipCode) {
        System.out.println("=== CALCULANDO INFORMACOES DE ENTREGA ===");
        
        double cost = deliveryService.calculateShipping(zipCode);
        int days = deliveryService.estimateDeliveryDays(zipCode);
        
        return new ShippingInfo(zipCode, cost, days);
    }
    
    /**
     * Consulta status completo de um pedido
     * Agrega informacoes de pagamento e entrega
     * 
     * @param orderId ID do pedido
     * @param transactionId ID da transacao
     * @param trackingCode codigo de rastreamento
     * @return status completo do pedido
     */
    public String getOrderStatus(String orderId, String transactionId, String trackingCode) {
        System.out.println("=== CONSULTANDO STATUS DO PEDIDO ===");
        
        String paymentStatus = paymentService.getTransactionStatus(transactionId);
        String deliveryStatus = deliveryService.getDeliveryStatus(trackingCode);
        
        return String.format("Pedido %s | Pagamento: %s | Entrega: %s", 
                           orderId, paymentStatus, deliveryStatus);
    }
}