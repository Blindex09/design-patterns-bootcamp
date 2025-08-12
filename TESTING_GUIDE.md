# Guia Completo de Testes

Este documento detalha a estrutura completa de testes automatizados do projeto Design Patterns Bootcamp.

## 📁 Estrutura de Testes

```
src/test/java/com/bootcamp/designpatterns/
├── unit/                           # Testes Unitarios
│   ├── DesignPatternsUnitTest.java    # Testes dos padroes GoF
│   └── ProductServiceTest.java        # Testes da camada Service
├── integration/                    # Testes de Integracao
│   ├── DesignPatternsIntegrationTest.java  # Testes da API REST completa
│   └── DesignPatternsControllerTest.java   # Testes de Controller com MockMvc
└── resources/
    └── application-test.yml        # Configuracoes para testes
```

## 🎯 Tipos de Teste

### 1. Testes Unitarios (@Tag("unit"))
**Objetivo**: Testar componentes individuais em isolamento

**Características**:
- Execucao rapida (< 1s por teste)
- Sem dependencias externas
- Mocks para dependencias
- Foco na logica de negocio

**Cobertura**:
- ✅ Singleton Pattern (DatabaseConnection, ConfigurationManager)
- ✅ Strategy Pattern (PercentageDiscount, FixedDiscount, ProgressiveDiscount)
- ✅ Facade Pattern (EcommerceFacade, subsistemas)
- ✅ Service Layer (ProductService)

### 2. Testes de Integracao (@Tag("integration"))
**Objetivo**: Testar a aplicacao completa com Spring Boot

**Características**:
- Spring Boot Test com servidor real
- Banco H2 em memoria
- TestRestTemplate para chamadas HTTP
- Teste end-to-end da API

**Cobertura**:
- ✅ Endpoints REST funcionando
- ✅ Serializacao JSON
- ✅ Validacoes de entrada
- ✅ Integracao entre camadas
- ✅ Banco de dados H2

### 3. Testes de Controller (@Tag("controller"))
**Objetivo**: Testar apenas a camada de controle

**Características**:
- @WebMvcTest para teste isolado
- MockMvc para requisicoes simuladas
- Mocks para Service Layer
- Foco em endpoints e validacoes

**Cobertura**:
- ✅ Mapeamento de URLs
- ✅ Parametros de requisicao
- ✅ Status codes HTTP
- ✅ Headers de resposta
- ✅ Validacao de dados

## 🚀 Como Executar os Testes

### Execucao Local

#### 1. Todos os Testes
```bash
# Linux/Mac
./run-tests.sh all

# Windows
.\run-tests.ps1 all

# Maven direto
mvn clean verify
```

#### 2. Apenas Testes Unitarios
```bash
# Script
./run-tests.sh unit

# Maven
mvn test -Punit-tests
```

#### 3. Apenas Testes de Integracao
```bash
# Script
./run-tests.sh integration

# Maven
mvn verify -Pintegration-tests
```

#### 4. Apenas Testes de Controller
```bash
# Script
./run-tests.sh controller

# Maven
mvn test -Pcontroller-tests
```

#### 5. Com Relatorio de Cobertura
```bash
# Script
./run-tests.sh coverage

# Maven
mvn clean verify jacoco:report
```

### Execucao Automatizada (CI/CD)

#### GitHub Actions
Os testes executam automaticamente em:
- ✅ Push para `main` ou `develop`
- ✅ Pull Requests para `main`
- ✅ Execucao manual via GitHub UI

#### Pipeline de CI/CD
1. **Testes Unitarios** (paralelo)
2. **Testes de Integracao** (após unitarios)
3. **Testes de Controller** (após unitarios)
4. **Cobertura de Codigo** (após todos)
5. **Analise de Qualidade**
6. **Build e Empacotamento**

## 📊 Relatorios e Cobertura

### JaCoCo - Cobertura de Codigo
```bash
# Gerar relatorio
mvn jacoco:report

# Visualizar
open target/site/jacoco/index.html
```

**Metas de Cobertura**:
- ✅ Cobertura geral: > 85%
- ✅ Cobertura de padroes: > 95%
- ✅ Cobertura de services: > 90%
- ✅ Cobertura de controllers: > 80%

### Surefire - Relatorios de Teste
```bash
# Visualizar relatorios
cat target/surefire-reports/*.txt
open target/surefire-reports/index.html
```

### Failsafe - Testes de Integracao
```bash
# Visualizar relatorios de integracao
cat target/failsafe-reports/*.txt
```

## 🎛️ Configuracoes de Teste

### Profiles Maven
```xml
<!-- Apenas testes unitarios -->
mvn test -Punit-tests

<!-- Apenas testes de integracao -->
mvn verify -Pintegration-tests

<!-- Apenas testes de controller -->
mvn test -Pcontroller-tests

<!-- Todos os testes -->
mvn verify -Pall-tests
```

### Configuracao de Ambiente
```yaml
# application-test.yml
spring:
  profiles.active: test
  datasource.url: jdbc:h2:mem:testdb-${random.uuid}
  jpa.hibernate.ddl-auto: create-drop
  h2.console.enabled: false
```

### Tags JUnit 5
```java
@Tag("unit")        // Testes unitarios
@Tag("integration") // Testes de integracao
@Tag("controller")  // Testes de controller
@Tag("slow")        // Testes lentos (opcional)
```

## 🔧 Ferramentas e Frameworks

### Framework de Teste
- **JUnit 5**: Framework principal
- **AssertJ**: Assertions expressivas
- **Mockito**: Mocks e stubs
- **TestContainers**: Containers para teste (opcional)

### Spring Test
- **@SpringBootTest**: Teste de integracao completa
- **@WebMvcTest**: Teste de controller isolado
- **TestRestTemplate**: Cliente HTTP para testes
- **MockMvc**: Simulacao de requisicoes HTTP

### Banco de Dados
- **H2 Database**: Banco em memoria para testes
- **URL unica**: Evita conflitos entre testes
- **DDL auto**: Cria/destroi esquema automaticamente

## 📈 Metricas e Qualidade

### Tempo de Execucao (Metas)
- **Testes Unitarios**: < 30 segundos
- **Testes Controller**: < 45 segundos  
- **Testes Integracao**: < 2 minutos
- **Todos os Testes**: < 5 minutos

### Cobertura por Componente
```
Singleton Pattern     ████████████ 95%
Strategy Pattern      ████████████ 98%
Facade Pattern        ████████████ 92%
Controllers          ██████████   85%
Services             ████████████ 90%
Models               ████████     80%
```

### Estabilidade
- ✅ Zero testes flaky (instáveis)
- ✅ Determinismo total
- ✅ Limpeza de recursos
- ✅ Isolamento entre testes

## 🚨 Troubleshooting

### Problemas Comuns

#### 1. Testes Falhando Localmente
```bash
# Limpar e recompilar
mvn clean compile

# Executar com debug
mvn test -X

# Verificar logs
cat target/surefire-reports/*.txt
```

#### 2. Problemas de Porta em Testes de Integracao
```yaml
# application-test.yml
server:
  port: 0  # Porta aleatoria
```

#### 3. Problemas de Memoria
```bash
# Aumentar memoria para testes
export MAVEN_OPTS="-Xmx2048m"
```

#### 4. Falha em CI/CD
- Verificar logs do GitHub Actions
- Comparar com execucao local
- Verificar versao do Java (17)

### Debug de Testes

#### Logs Detalhados
```bash
# Ativar logs de teste
mvn test -Dlogging.level.com.bootcamp.designpatterns=DEBUG
```

#### Executar Teste Específico
```bash
# Teste específico
mvn test -Dtest="DesignPatternsUnitTest#testSingletonPattern"

# Classe específica
mvn test -Dtest="DesignPatternsUnitTest"
```

#### Profile de Debug
```bash
# Executar com profile de debug
mvn test -Pdebug -Dspring.profiles.active=test,debug
```

## 📋 Checklist de Qualidade

### Antes de Commit
- [ ] Todos os testes passando localmente
- [ ] Cobertura > 85%
- [ ] Sem warnings de compilacao
- [ ] Logs limpos (sem erros)

### Antes de Pull Request
- [ ] Testes de integracao passando
- [ ] Documentacao atualizada
- [ ] Exemplos funcionando
- [ ] Performance aceitavel

### Antes de Release
- [ ] Todos os pipelines verdes
- [ ] Testes em multiplas versoes Java
- [ ] Build de producao funcionando
- [ ] Artefatos gerados corretamente

## 🎯 Boas Praticas

### Nomenclatura de Testes
```java
// ✅ Bom
@Test
@DisplayName("Strategy - Desconto percentual deve calcular corretamente")
void testPercentageDiscountStrategy() { }

// ❌ Ruim  
@Test
void test1() { }
```

### Estrutura AAA (Arrange-Act-Assert)
```java
@Test
void testCalculateDiscount() {
    // Arrange
    BigDecimal price = new BigDecimal("100.00");
    DiscountStrategy strategy = new PercentageDiscountStrategy(new BigDecimal("0.10"));
    
    // Act
    BigDecimal result = calculator.calculateFinalPrice(price);
    
    // Assert
    assertEquals(new BigDecimal("90.00"), result);
}
```

### Dados de Teste
```java
// ✅ Use dados realistas
BigDecimal price = new BigDecimal("299.99");
String productId = "NOTEBOOK_DELL_001";

// ❌ Evite dados genéricos
BigDecimal price = new BigDecimal("1");
String productId = "A";
```

### Assertions Expressivas
```java
// ✅ AssertJ (mais expressivo)
assertThat(result)
    .isNotNull()
    .isEqualTo(expected)
    .isInstanceOf(BigDecimal.class);

// ✅ JUnit (tradicional)
assertNotNull(result, "Resultado nao deve ser nulo");
assertEquals(expected, result, "Valores devem ser iguais");
```

## 🔄 Manutencao Continua

### Revisao Mensal
- [ ] Analisar tempo de execucao
- [ ] Verificar cobertura de codigo
- [ ] Remover testes obsoletos
- [ ] Atualizar dependencias de teste

### Metricas a Acompanhar
- **Tempo total de execucao**
- **Percentual de testes que passam**
- **Cobertura por modulo**
- **Numero de testes por tipo**

---

## 💡 Dicas Avancadas

### Execucao Paralela
```xml
<!-- pom.xml -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>4</threadCount>
    </configuration>
</plugin>
```

### Categorias de Teste
```java
@Tag("fast")    // < 1 segundo
@Tag("medium")  // 1-10 segundos  
@Tag("slow")    // > 10 segundos
@Tag("smoke")   // Testes criticos
```

### Execucao Condicional
```bash
# Apenas testes rapidos
mvn test -Dgroups="fast"

# Excluir testes lentos
mvn test -DexcludedGroups="slow"
```

---

**📚 Para mais informacoes sobre testes em Spring Boot:**
- [Spring Boot Testing Documentation](https://spring.io/guides/gs/testing-web/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)