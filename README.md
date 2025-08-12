# Design Patterns com Java: Dos Clássicos (GoF) ao Spring Framework

## Visão Geral

Este projeto demonstra a implementação prática dos padrões de projeto clássicos do Gang of Four (GoF) integrados com o Spring Framework. O objetivo é mostrar como esses padrões fundamentais podem ser aplicados em uma aplicação moderna usando Java 17 e Spring Boot 3.

## Padrões Implementados

### 1. Singleton Pattern
- **DatabaseConnection**: Implementação thread-safe com Double-Checked Locking
- **ConfigurationManager**: Implementação usando Enum (melhor prática)
- **Uso**: Gerenciamento de configurações globais e conexões de banco

### 2. Strategy Pattern  
- **DiscountStrategy**: Interface para diferentes algoritmos de desconto
- **Implementações**:
  - `PercentageDiscountStrategy`: Desconto percentual
  - `FixedDiscountStrategy`: Desconto com valor fixo
  - `ProgressiveDiscountStrategy`: Desconto por faixas de valor
- **Uso**: Cálculo de preços com diferentes estratégias de desconto

### 3. Facade Pattern
- **EcommerceFacade**: Interface simplificada para operações de e-commerce
- **Subsistemas**:
  - `InventoryService`: Gerenciamento de estoque
  - `PaymentService`: Processamento de pagamentos
  - `DeliveryService`: Cálculo de frete e entregas
- **Uso**: Simplificação de operações complexas do sistema

## Arquitetura do Projeto

```
src/
├── main/
│   ├── java/com/bootcamp/designpatterns/
│   │   ├── singleton/          # Padrão Singleton
│   │   ├── strategy/           # Padrão Strategy
│   │   ├── facade/             # Padrão Facade
│   │   ├── controller/         # Controllers REST
│   │   ├── service/           # Serviços Spring
│   │   ├── model/             # Entidades JPA
│   │   └── DesignPatternsBootcampApplication.java
│   └── resources/
│       ├── application.yml    # Configurações
│       └── data.sql          # Dados iniciais
└── test/
    └── java/com/bootcamp/designpatterns/
        └── DesignPatternsTest.java
```

## Tecnologias Utilizadas

- **Java 17**: Linguagem principal
- **Spring Boot 3.2.0**: Framework base
- **Spring Data JPA**: Persistência de dados
- **H2 Database**: Banco em memória para demonstração
- **Swagger/OpenAPI**: Documentação da API
- **JUnit 5**: Testes unitários
- **Maven**: Gerenciamento de dependências

## Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6 ou superior

### Passos

1. **Clone o repositório**
```bash
git clone https://github.com/Blindex09/design-patterns-bootcamp.git
cd design-patterns-bootcamp
```

2. **Execute os testes**
```bash
# Todos os testes
./run-tests.sh all  # Linux/Mac
.\run-tests.ps1 all # Windows

# Ou usando Maven diretamente
mvn clean verify
```

3. **Compile o projeto**
```bash
mvn clean compile
```

4. **Inicie a aplicação**
```bash
mvn spring-boot:run
```

5. **Acesse a aplicação**
- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- H2 Console: http://localhost:8080/api/h2-console

## 🧪 Estrutura de Testes

O projeto inclui uma suíte completa de testes automatizados:

### Tipos de Teste
- **Testes Unitários** (@Tag("unit")): Testam padrões GoF em isolamento
- **Testes de Integração** (@Tag("integration")): Testam API REST completa  
- **Testes de Controller** (@Tag("controller")): Testam endpoints com MockMvc

### Execução de Testes
```bash
# Executar todos os testes
./run-tests.sh all

# Apenas testes unitários
./run-tests.sh unit

# Apenas testes de integração
./run-tests.sh integration

# Com relatório de cobertura
./run-tests.sh coverage
```

### Cobertura de Código
- **Meta geral**: > 85%
- **Padrões GoF**: > 95%
- **Services**: > 90%
- **Controllers**: > 80%

### CI/CD Automatizado
Os testes executam automaticamente via GitHub Actions em:
- Push para `main` ou `develop`
- Pull Requests
- Múltiplas versões Java (17, 21)

**📖 Guia Completo**: [TESTING_GUIDE.md](TESTING_GUIDE.md)

## Endpoints da API

### Padrão Strategy
- `GET /api/design-patterns/strategy/calculate-price` - Calcula preço com desconto
- `GET /api/design-patterns/strategy/compare-all` - Compara todas as estratégias

### Padrão Facade
- `GET /api/design-patterns/facade/check-availability` - Verifica disponibilidade
- `GET /api/design-patterns/facade/shipping-info` - Calcula frete
- `POST /api/design-patterns/facade/process-order` - Processa pedido completo

### Padrão Singleton
- `GET /api/design-patterns/singleton/app-info` - Informações da aplicação

### Demonstração Completa
- `GET /api/design-patterns/complete-demo` - Executa todos os padrões

## Exemplos de Uso

### 1. Calculando Desconto Percentual
```bash
curl -X GET "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=100&strategyType=percentage&discountValue=15"
```

### 2. Verificando Disponibilidade
```bash
curl -X GET "http://localhost:8080/api/design-patterns/facade/check-availability?productId=PROD123&quantity=5"
```

### 3. Processando Pedido Completo
```bash
curl -X POST "http://localhost:8080/api/design-patterns/facade/process-order" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": "PROD124",
    "quantity": 2,
    "amount": 150.00,
    "cardNumber": "1234567890123456",
    "cvv": "123",
    "expiryDate": "12/25",
    "address": "Rua das Flores, 123",
    "zipCode": "01000-000"
  }'
```

## Detalhes dos Padrões

### Singleton Pattern

**Problema**: Necessidade de uma única instância global de configuração.

**Solução**: 
- `DatabaseConnection`: Thread-safe com lazy initialization
- `ConfigurationManager`: Enum singleton (thread-safe por natureza)

**Vantagens**:
- Controle de acesso a recursos compartilhados
- Economia de memória
- Thread safety garantido

### Strategy Pattern

**Problema**: Necessidade de trocar algoritmos de desconto em tempo de execução.

**Solução**: 
- Interface `DiscountStrategy` com implementações específicas
- Context `PriceCalculator` que usa as estratégias

**Vantagens**:
- Algoritmos intercambiáveis
- Facilita adição de novas estratégias
- Princípio Aberto/Fechado respeitado

### Facade Pattern

**Problema**: Complexidade de múltiplos subsistemas para processar um pedido.

**Solução**:
- `EcommerceFacade` simplifica interações com subsistemas
- Interface unificada para operações complexas

**Vantagens**:
- Reduz complexidade para o cliente
- Desacoplamento entre cliente e subsistemas
- Interface mais limpa e intuitiva

## Estrutura de Testes

O projeto inclui testes abrangentes que verificam:

- **Singleton**: Garantia de instância única
- **Strategy**: Cálculos corretos para cada estratégia
- **Facade**: Operações simplificadas funcionando
- **Integração**: Todos os padrões trabalhando juntos

## Conceitos Demonstrados

### Princípios SOLID
- **Single Responsibility**: Cada classe tem uma responsabilidade específica
- **Open/Closed**: Fácil extensão sem modificação (Strategy)
- **Dependency Inversion**: Dependência de abstrações, não concretizações

### Clean Code
- Nomes expressivos e auto-explicativos
- Métodos pequenos e focados
- Comentários descritivos quando necessário
- Tratamento adequado de exceções

### Spring Framework Integration
- **Dependency Injection**: Gerenciamento automático de dependências
- **Annotations**: Configuração baseada em anotações
- **Auto Configuration**: Configuração automática do Spring Boot

## Melhorias e Extensões Possíveis

1. **Novos Padrões**: Observer, Factory, Builder
2. **Persistência Real**: PostgreSQL/MySQL
3. **Cache**: Redis para otimização
4. **Segurança**: Spring Security
5. **Monitoramento**: Actuator + Micrometer
6. **Containerização**: Docker + Docker Compose

## Contribuição

Este projeto foi desenvolvido como parte do bootcamp de Design Patterns. 
Sugestões e melhorias são bem-vindas!

## Licença

MIT License - Veja o arquivo LICENSE para detalhes.

## Autor

Desenvolvido para o Bootcamp DIO - Design Patterns com Java

## Referências

- Gang of Four (GoF) Design Patterns
- Spring Framework Documentation
- Clean Code by Robert C. Martin
- Effective Java by Joshua Bloch