# Exemplos de Requests para Testar a API

Este arquivo contém exemplos práticos de como testar todos os endpoints da API do projeto Design Patterns Bootcamp.

## Configuração Base
- **URL Base**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console**: http://localhost:8080/api/h2-console

## 1. Padrão Strategy - Cálculo de Descontos

### 1.1 Desconto Percentual (15%)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=200&strategyType=percentage&discountValue=15" \
  -H "Accept: application/json"
```

**Resposta Esperada:**
```json
{
  "originalPrice": 200.00,
  "finalPrice": 170.00,
  "discount": 30.00,
  "strategy": "Desconto Percentual",
  "strategyInfo": "15.0%",
  "details": "Tipo: Desconto Percentual (15.0%) | Preco Original: R$ 200.00 | Desconto: R$ 30.00 | Preco Final: R$ 170.00"
}
```

### 1.2 Desconto Fixo (R$ 50)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=300&strategyType=fixed&discountValue=50" \
  -H "Accept: application/json"
```

### 1.3 Desconto Progressivo
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=600&strategyType=progressive" \
  -H "Accept: application/json"
```

### 1.4 Comparar Todas as Estratégias
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/compare-all?originalPrice=400" \
  -H "Accept: application/json"
```

## 2. Padrão Facade - Operações de E-commerce

### 2.1 Verificar Disponibilidade de Produto
```bash
curl -X GET "http://localhost:8080/api/design-patterns/facade/check-availability?productId=PROD124&quantity=5" \
  -H "Accept: application/json"
```

**Resposta Esperada:**
```json
{
  "productId": "PROD124",
  "requestedQuantity": 5,
  "availability": "ProductAvailability{productId='PROD124', quantity=5, available=true}",
  "pattern": "Facade Pattern",
  "description": "Fornece interface simplificada para subsistemas complexos"
}
```

### 2.2 Calcular Informações de Entrega

#### CEP Região Metropolitana (mais barato)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/facade/shipping-info?zipCode=01000-000" \
  -H "Accept: application/json"
```

#### CEP Interior
```bash
curl -X GET "http://localhost:8080/api/design-patterns/facade/shipping-info?zipCode=13000-000" \
  -H "Accept: application/json"
```

#### CEP Outras Regiões (mais caro)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/facade/shipping-info?zipCode=80000-000" \
  -H "Accept: application/json"
```

### 2.3 Processar Pedido Completo - Caso de Sucesso
```bash
curl -X POST "http://localhost:8080/api/design-patterns/facade/process-order" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "productId": "PROD124",
    "quantity": 3,
    "amount": 299.99,
    "cardNumber": "1234567890123456",
    "cvv": "123",
    "expiryDate": "12/25",
    "address": "Rua das Flores, 123, São Paulo, SP",
    "zipCode": "01000-000"
  }'
```

### 2.4 Processar Pedido - Caso de Falha (Valor Alto)
```bash
curl -X POST "http://localhost:8080/api/design-patterns/facade/process-order" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "productId": "PROD124",
    "quantity": 2,
    "amount": 1500.00,
    "cardNumber": "1234567890123456",
    "cvv": "123",
    "expiryDate": "12/25",
    "address": "Rua das Rosas, 456, Rio de Janeiro, RJ",
    "zipCode": "20000-000"
  }'
```

## 3. Padrão Singleton - Configurações Globais

### 3.1 Obter Informações da Aplicação
```bash
curl -X GET "http://localhost:8080/api/design-patterns/singleton/app-info" \
  -H "Accept: application/json"
```

**Resposta Esperada:**
```json
{
  "applicationInfo": "Aplicacao: Design Patterns Bootcamp API | Versao: 1.0.0 | Ambiente: production",
  "pattern": "Singleton Pattern",
  "description": "Garante uma unica instancia de configuracao global"
}
```

## 4. Demonstração Completa - Todos os Padrões

### 4.1 Execução Completa com Produto Disponível
```bash
curl -X GET "http://localhost:8080/api/design-patterns/complete-demo?productId=PROD124&quantity=2&originalPrice=500&zipCode=01000-000" \
  -H "Accept: application/json"
```

### 4.2 Execução com Produto Indisponível
```bash
curl -X GET "http://localhost:8080/api/design-patterns/complete-demo?productId=PROD123&quantity=15&originalPrice=800&zipCode=13000-000" \
  -H "Accept: application/json"
```

## 5. Testando Diferentes Cenários

### 5.1 Desconto Progressivo - Diferentes Faixas

#### Faixa 1: Até R$ 100 (5% de desconto)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=75&strategyType=progressive"
```

#### Faixa 2: R$ 100 a R$ 500 (10% de desconto)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=350&strategyType=progressive"
```

#### Faixa 3: Acima de R$ 500 (15% de desconto)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=750&strategyType=progressive"
```

### 5.2 Testando Validações

#### Preço Inválido (deve retornar erro)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=-10&strategyType=percentage&discountValue=10"
```

#### Estratégia Inválida (deve retornar erro)
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=100&strategyType=invalid&discountValue=10"
```

#### Pedido com Dados Inválidos
```bash
curl -X POST "http://localhost:8080/api/design-patterns/facade/process-order" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "",
    "quantity": -1,
    "amount": -100,
    "cardNumber": "123",
    "cvv": "12",
    "expiryDate": "invalid",
    "address": "",
    "zipCode": ""
  }'
```

## 6. Monitoramento e Debug

### 6.1 Health Check (Spring Actuator - se habilitado)
```bash
curl -X GET "http://localhost:8080/api/actuator/health" \
  -H "Accept: application/json"
```

### 6.2 Verificar Console H2
1. Acesse: http://localhost:8080/api/h2-console
2. Use as configurações:
   - **JDBC URL**: jdbc:h2:mem:testdb
   - **User Name**: sa
   - **Password**: (deixe em branco)

### 6.3 Logs da Aplicação
Os logs da aplicação mostrarão:
- Execução dos padrões Singleton
- Cálculos das estratégias
- Operações do Facade

## 7. Scripts de Teste Automatizado

### 7.1 Script Bash para Testar Todos os Endpoints
```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/design-patterns"

echo "=== Testando Strategy Pattern ==="
curl -s "$BASE_URL/strategy/calculate-price?originalPrice=100&strategyType=percentage&discountValue=10" | jq .

echo -e "\n=== Testando Facade Pattern ==="
curl -s "$BASE_URL/facade/check-availability?productId=PROD124&quantity=5" | jq .

echo -e "\n=== Testando Singleton Pattern ==="
curl -s "$BASE_URL/singleton/app-info" | jq .

echo -e "\n=== Demonstração Completa ==="
curl -s "$BASE_URL/complete-demo?productId=PROD124&quantity=2&originalPrice=300&zipCode=01000-000" | jq .
```

### 7.2 PowerShell Script (Windows)
```powershell
$baseUrl = "http://localhost:8080/api/design-patterns"

Write-Host "=== Testando Strategy Pattern ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/strategy/calculate-price?originalPrice=100&strategyType=percentage&discountValue=10" | ConvertTo-Json

Write-Host "`n=== Testando Facade Pattern ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/facade/check-availability?productId=PROD124&quantity=5" | ConvertTo-Json

Write-Host "`n=== Testando Singleton Pattern ===" -ForegroundColor Green
Invoke-RestMethod -Uri "$baseUrl/singleton/app-info" | ConvertTo-Json
```

## 8. Notas Importantes

- **Produtos com ID par**: Simulam ter estoque disponível
- **Produtos com ID ímpar**: Simulam estoque insuficiente
- **Valores acima de R$ 1000**: Simulam falha no pagamento
- **CEPs iniciados com '0'**: Região metropolitana (frete mais barato)
- **CEPs iniciados com '1'**: Interior (frete médio)
- **Outros CEPs**: Outras regiões (frete mais caro)

## 9. Troubleshooting

### Aplicação não inicia
1. Verifique se o Java 17 está instalado
2. Execute `mvn clean install` antes de iniciar
3. Verifique se a porta 8080 está disponível

### Endpoints retornam 404
1. Verifique se a aplicação iniciou corretamente
2. Use a URL base correta: http://localhost:8080/api
3. Consulte a documentação Swagger

### Erros de validação
1. Verifique se todos os parâmetros obrigatórios foram fornecidos
2. Verifique os tipos de dados (números devem ser válidos)
3. Consulte as mensagens de erro retornadas pela API