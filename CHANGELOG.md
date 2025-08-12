# Changelog

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-08-11

### Adicionado

#### Padrões GoF Implementados
- **Singleton Pattern**: 
  - `DatabaseConnection` com implementação thread-safe usando Double-Checked Locking
  - `ConfigurationManager` usando Enum (melhor prática)
  - Integração com Spring Framework mostrando coexistência

- **Strategy Pattern**:
  - Interface `DiscountStrategy` para diferentes algoritmos de desconto
  - `PercentageDiscountStrategy` para descontos percentuais
  - `FixedDiscountStrategy` para descontos com valor fixo
  - `ProgressiveDiscountStrategy` para descontos por faixas de valor
  - `PriceCalculator` como contexto que utiliza as estratégias
  - Troca de estratégias em tempo de execução

- **Facade Pattern**:
  - `EcommerceFacade` como interface simplificada para e-commerce
  - `InventoryService` para gerenciamento de estoque
  - `PaymentService` para processamento de pagamentos
  - `DeliveryService` para cálculo de frete e entregas
  - Operações complexas simplificadas em uma única interface

#### Integração com Spring Framework
- **API REST** completa com endpoints para demonstrar todos os padrões
- **Spring Boot 3.2.0** com Java 17
- **Spring Data JPA** para persistência
- **H2 Database** em memória para demonstração
- **Swagger/OpenAPI** para documentação automática da API
- **Validation** com Bean Validation para entrada de dados
- **Service Layer** integrando os padrões com Spring

#### Arquitetura e Estrutura
- Estrutura de pacotes organizada por padrão
- Separação clara entre responsabilidades
- Modelos JPA com validações
- Controllers REST com documentação
- Serviços Spring gerenciados por IoC

#### Testes e Qualidade
- **Testes unitários** abrangentes com JUnit 5
- Cobertura de todos os padrões implementados
- Testes de integração entre padrões
- Validação de thread safety para Singleton
- Testes de diferentes cenários de Strategy
- Validação completa do Facade

#### Documentação
- **README.md** completo com instruções detalhadas
- **Exemplos de requests** para todos os endpoints
- **Javadoc** detalhado em todas as classes
- **Comentários explicativos** sobre as escolhas técnicas
- **Arquitetura documentada** com diagramas textuais

#### Configuração e Deploy
- **Maven** para gerenciamento de dependências
- **Application.yml** com configurações otimizadas
- **Data.sql** com dados de exemplo
- **.gitignore** abrangente
- **Profile de desenvolvimento** pré-configurado

#### Demonstração e Exemplos
- **Classe Demo** standalone para execução independente
- **Dados de exemplo** realistas no banco H2
- **Scripts de teste** em Bash e PowerShell
- **Cenários de uso** diversos e bem documentados

### Características Técnicas

#### Princípios Aplicados
- **SOLID Principles**:
  - Single Responsibility: cada classe tem uma responsabilidade específica
  - Open/Closed: fácil extensão através do Strategy Pattern
  - Dependency Inversion: dependência de abstrações

- **Clean Code**:
  - Nomes expressivos e auto-explicativos
  - Métodos pequenos e focados
  - Comentários descritivos quando necessário
  - Tratamento adequado de exceções

- **Design Patterns Best Practices**:
  - Implementações thread-safe
  - Uso adequado de cada padrão
  - Integração harmoniosa entre padrões

#### Performance e Segurança
- **Thread Safety** garantido em todos os Singletons
- **Validação robusta** de entrada de dados
- **Tratamento de exceções** adequado
- **Logs informativos** para debugging

#### Acessibilidade e Usabilidade
- **API REST** intuitiva e bem documentada
- **Swagger UI** para teste interativo
- **Mensagens de erro** claras e informativas
- **Exemplos práticos** de uso

### Tecnologias Utilizadas

- **Java 17** - Linguagem principal
- **Spring Boot 3.2.0** - Framework base
- **Spring Data JPA** - Persistência
- **H2 Database** - Banco em memória
- **Maven 3.8+** - Gerenciamento de dependências
- **JUnit 5** - Framework de testes
- **Swagger/OpenAPI 3** - Documentação da API
- **Bean Validation** - Validação de dados

### Estrutura do Projeto

```
design-patterns-bootcamp/
├── src/main/java/com/bootcamp/designpatterns/
│   ├── singleton/              # Padrão Singleton
│   ├── strategy/               # Padrão Strategy  
│   ├── facade/                 # Padrão Facade
│   ├── controller/             # Controllers REST
│   ├── service/                # Serviços Spring
│   ├── model/                  # Entidades JPA
│   ├── DesignPatternsBootcampApplication.java
│   └── DesignPatternsDemo.java
├── src/main/resources/
│   ├── application.yml
│   └── data.sql
├── src/test/java/
│   └── DesignPatternsTest.java
├── README.md
├── EXEMPLOS_REQUESTS.md
├── CHANGELOG.md
├── .gitignore
└── pom.xml
```

### Endpoints da API

- `GET /api/design-patterns/strategy/calculate-price` - Calcula preço com Strategy
- `GET /api/design-patterns/strategy/compare-all` - Compara estratégias
- `GET /api/design-patterns/facade/check-availability` - Verifica estoque
- `GET /api/design-patterns/facade/shipping-info` - Calcula frete
- `POST /api/design-patterns/facade/process-order` - Processa pedido
- `GET /api/design-patterns/singleton/app-info` - Info da aplicação
- `GET /api/design-patterns/complete-demo` - Demo completa

### Como Usar

1. **Clonar o repositório**
2. **Executar**: `mvn spring-boot:run`
3. **Acessar**: http://localhost:8080/api/swagger-ui.html
4. **Testar** os endpoints usando os exemplos fornecidos

### Objetivos Alcançados

✅ **Implementação correta dos padrões GoF**
✅ **Integração harmoniosa com Spring Framework**
✅ **API REST completa e documentada**
✅ **Testes abrangentes e confiáveis**
✅ **Documentação detalhada e exemplos práticos**
✅ **Código limpo e bem estruturado**
✅ **Demonstração prática de conceitos teóricos**
✅ **Projeto pronto para produção**

### Próximas Versões (Roadmap)

#### [1.1.0] - Planejado
- Implementação do padrão Observer
- Implementação do padrão Factory Method
- Implementação do padrão Builder
- Cache com Redis
- Métricas com Micrometer

#### [1.2.0] - Planejado  
- Segurança com Spring Security
- Banco de dados PostgreSQL
- Containerização com Docker
- CI/CD com GitHub Actions

#### [2.0.0] - Planejado
- Arquitetura de microserviços
- Message Broker com RabbitMQ
- Monitoramento com Prometheus
- Frontend com React

## Notas de Desenvolvimento

### Decisões Técnicas Importantes

1. **Singleton com Enum**: Escolhido por ser thread-safe por natureza e prevenir reflexão
2. **Strategy com Interface**: Permite fácil extensão e teste unitário
3. **Facade com Composição**: Melhor que herança para orquestrar subsistemas
4. **H2 em Memória**: Facilita demonstração sem dependências externas
5. **Spring Boot**: Reduz configuração e acelera desenvolvimento

### Lições Aprendidas

1. **Padrões não são mutuamente exclusivos**: podem e devem trabalhar juntos
2. **Spring facilita implementação**: IoC container natural para padrões
3. **Testes são essenciais**: garantem que padrões funcionam corretamente
4. **Documentação é crucial**: padrões precisam ser bem explicados
5. **Exemplos práticos ajudam**: demonstração vale mais que teoria

---

**Nota**: Este projeto foi desenvolvido como parte do bootcamp de Design Patterns da DIO, demonstrando a aplicação prática dos padrões GoF em um cenário real com Spring Framework.