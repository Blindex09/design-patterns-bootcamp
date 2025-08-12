package com.bootcamp.designpatterns.facade;

/**
 * Subsistema: Servico de Entrega
 * 
 * Componente interno responsavel pelo gerenciamento de entregas.
 */
public class DeliveryService {
    
    /**
     * Calcula o frete baseado no CEP de destino
     * 
     * @param zipCode CEP de destino
     * @return valor do frete
     */
    public double calculateShipping(String zipCode) {
        System.out.println("DeliveryService: Calculando frete para CEP " + zipCode);
        
        // Simula calculo de frete baseado no CEP
        double shippingCost;
        
        if (zipCode != null && zipCode.startsWith("0")) {
            shippingCost = 15.50; // Regiao metropolitana
        } else if (zipCode != null && zipCode.startsWith("1")) {
            shippingCost = 25.00; // Interior
        } else {
            shippingCost = 35.00; // Outras regioes
        }
        
        System.out.println("DeliveryService: Frete calculado: R$ " + shippingCost);
        return shippingCost;
    }
    
    /**
     * Agenda a entrega do pedido
     * 
     * @param orderId ID do pedido
     * @param address endereco de entrega
     * @param zipCode CEP de entrega
     * @return codigo de rastreamento
     */
    public String scheduleDelivery(String orderId, String address, String zipCode) {
        System.out.println("DeliveryService: Agendando entrega para o pedido " + orderId);
        System.out.println("DeliveryService: Endereco: " + address + ", CEP: " + zipCode);
        
        // Simula agendamento
        String trackingCode = "TRACK" + orderId + System.currentTimeMillis() % 10000;
        
        System.out.println("DeliveryService: Entrega agendada - Codigo de rastreamento: " + trackingCode);
        
        return trackingCode;
    }
    
    /**
     * Consulta o status da entrega
     * 
     * @param trackingCode codigo de rastreamento
     * @return status da entrega
     */
    public String getDeliveryStatus(String trackingCode) {
        System.out.println("DeliveryService: Consultando status da entrega " + trackingCode);
        
        if (trackingCode != null && trackingCode.startsWith("TRACK")) {
            // Simula diferentes status baseado no codigo
            int hash = trackingCode.hashCode() % 4;
            
            switch (hash) {
                case 0: return "EM_PREPARACAO";
                case 1: return "EM_TRANSITO";
                case 2: return "ENTREGUE";
                default: return "AGUARDANDO_COLETA";
            }
        } else {
            return "CODIGO_INVALIDO";
        }
    }
    
    /**
     * Estima o prazo de entrega
     * 
     * @param zipCode CEP de destino
     * @return prazo em dias uteis
     */
    public int estimateDeliveryDays(String zipCode) {
        System.out.println("DeliveryService: Estimando prazo de entrega para CEP " + zipCode);
        
        int days;
        
        if (zipCode != null && zipCode.startsWith("0")) {
            days = 2; // Regiao metropolitana
        } else if (zipCode != null && zipCode.startsWith("1")) {
            days = 5; // Interior
        } else {
            days = 10; // Outras regioes
        }
        
        System.out.println("DeliveryService: Prazo estimado: " + days + " dias uteis");
        return days;
    }
}