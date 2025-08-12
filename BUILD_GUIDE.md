# Guia de Build e Execução

Este guia fornece instruções detalhadas para compilar, testar e executar o projeto Design Patterns Bootcamp.

## Pré-requisitos

### Software Necessário

1. **Java Development Kit (JDK) 17 ou superior**
   ```bash
   # Verificar versão do Java
   java -version
   javac -version
   ```

2. **Apache Maven 3.6 ou superior**
   ```bash
   # Verificar versão do Maven
   mvn -version
   ```

3. **Git** (para clonagem do repositório)
   ```bash
   # Verificar versão do Git
   git --version
   ```

### Instalação dos Pré-requisitos

#### Windows
```powershell
# Usando Chocolatey
choco install openjdk17 maven git

# Ou usando winget
winget install Microsoft.OpenJDK.17
winget install Apache.Maven
winget install Git.Git
```

#### Linux (Ubuntu/Debian)
```bash
# Atualizar repositórios
sudo apt update

# Instalar Java 17
sudo apt install openjdk-17-jdk

# Instalar Maven
sudo apt install maven

# Instalar Git
sudo apt install git
```

#### macOS
```bash
# Usando Homebrew
brew install openjdk@17 maven git

# Configurar JAVA_HOME (adicionar ao ~/.zshrc ou ~/.bash_profile)
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
```

## Clonagem e Preparação

### 1. Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/design-patterns-bootcamp.git
cd design-patterns-bootcamp
```

### 2. Verificar Estrutura do Projeto
```bash
# Listar arquivos principais
ls -la

# Verificar estrutura de diretórios
tree src/ || find src/ -type f -name "*.java" | head -20
```

## Build do Projeto

### 1. Limpeza e Compilação
```bash
# Limpar artefatos anteriores
mvn clean

# Compilar o projeto
mvn compile

# Ou fazer tudo de uma vez
mvn clean compile
```

### 2. Execução dos Testes
```bash
# Executar todos os testes
mvn test

# Executar testes com relatório detalhado
mvn test -Dtest=DesignPatternsTest

# Executar testes com cobertura (se configurado)
mvn test jacoco:report
```

### 3. Empacotamento
```bash
# Gerar o JAR executável
mvn package

# Pular testes durante o empacotamento (não recomendado)
mvn package -DskipTests

# Build completo (limpar, compilar, testar, empacotar)
mvn clean package
```

## Execução da Aplicação

### 1. Usando Maven (Desenvolvimento)
```bash
# Executar diretamente com Maven
mvn spring-boot:run

# Executar com profile específico
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Executar com propriedades customizadas
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

### 2. Usando JAR Executável (Produção)
```bash
# Após mvn package, executar o JAR
java -jar target/design-patterns-bootcamp-1.0.0.jar

# Com propriedades customizadas
java -jar target/design-patterns-bootcamp-1.0.0.jar --server.port=8081

# Com profile específico
java -jar target/design-patterns-bootcamp-1.0.0.jar --spring.profiles.active=prod
```

### 3. Execução da Demo Standalone
```bash
# Compilar e executar a classe de demonstração
mvn compile exec:java -Dexec.mainClass="com.bootcamp.designpatterns.DesignPatternsDemo"

# Ou após compilação
java -cp target/classes com.bootcamp.designpatterns.DesignPatternsDemo
```

## Verificação da Aplicação

### 1. Health Check
```bash
# Verificar se a aplicação está rodando
curl http://localhost:8080/api/design-patterns/singleton/app-info

# Ou usando wget
wget -qO- http://localhost:8080/api/design-patterns/singleton/app-info
```

### 2. Acessar Interfaces Web
- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console**: http://localhost:8080/api/h2-console
- **API Base**: http://localhost:8080/api

### 3. Teste Rápido dos Endpoints
```bash
# Testar Strategy Pattern
curl "http://localhost:8080/api/design-patterns/strategy/calculate-price?originalPrice=100&strategyType=percentage&discountValue=10"

# Testar Facade Pattern
curl "http://localhost:8080/api/design-patterns/facade/check-availability?productId=PROD124&quantity=5"

# Testar demonstração completa
curl "http://localhost:8080/api/design-patterns/complete-demo?productId=PROD124&quantity=2&originalPrice=300&zipCode=01000-000"
```

## Configurações Avançadas

### 1. Variáveis de Ambiente
```bash
# Definir porta customizada
export SERVER_PORT=8081

# Definir profile
export SPRING_PROFILES_ACTIVE=prod

# Executar com as variáveis
mvn spring-boot:run
```

### 2. Configuração do H2
```yaml
# application-dev.yml (para desenvolvimento)
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
      settings:
        web-allow-others: true
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password: devpass
```

### 3. Logs Detalhados
```bash
# Executar com logs DEBUG
mvn spring-boot:run -Dspring-boot.run.arguments="--logging.level.com.bootcamp.designpatterns=DEBUG"

# Ou via JAR
java -jar target/design-patterns-bootcamp-1.0.0.jar --logging.level.com.bootcamp.designpatterns=DEBUG
```

## Scripts de Automação

### 1. Script de Build Completo (build.sh)
```bash
#!/bin/bash
echo "=== Build Completo do Design Patterns Bootcamp ==="

echo "1. Limpando projeto..."
mvn clean

echo "2. Compilando..."
mvn compile

echo "3. Executando testes..."
mvn test

echo "4. Empacotando..."
mvn package

echo "5. Verificando artefatos..."
ls -la target/*.jar

echo "=== Build concluído com sucesso! ==="
```

### 2. Script de Execução (run.sh)
```bash
#!/bin/bash
echo "=== Iniciando Design Patterns Bootcamp ==="

# Verificar se o JAR existe
if [ ! -f "target/design-patterns-bootcamp-1.0.0.jar" ]; then
    echo "JAR não encontrado. Executando build..."
    ./build.sh
fi

echo "Iniciando aplicação..."
java -jar target/design-patterns-bootcamp-1.0.0.jar

echo "=== Aplicação finalizada ==="
```

### 3. Script PowerShell (Windows)
```powershell
# build.ps1
Write-Host "=== Build Completo do Design Patterns Bootcamp ===" -ForegroundColor Green

Write-Host "1. Limpando projeto..." -ForegroundColor Yellow
mvn clean

Write-Host "2. Compilando..." -ForegroundColor Yellow
mvn compile

Write-Host "3. Executando testes..." -ForegroundColor Yellow
mvn test

Write-Host "4. Empacotando..." -ForegroundColor Yellow
mvn package

Write-Host "5. Verificando artefatos..." -ForegroundColor Yellow
Get-ChildItem target\*.jar

Write-Host "=== Build concluído com sucesso! ===" -ForegroundColor Green
```

## Troubleshooting

### Problemas Comuns

#### 1. Erro: "JAVA_HOME not found"
```bash
# Linux/Mac
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
```

#### 2. Erro: "Port 8080 already in use"
```bash
# Usar porta diferente
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# Ou identificar e parar processo
# Linux/Mac
lsof -ti:8080 | xargs kill -9

# Windows
netstat -ano | findstr :8080
taskkill /F /PID <PID>
```

#### 3. Erro de Memória
```bash
# Aumentar memória heap
export MAVEN_OPTS="-Xmx1024m -Xms512m"
mvn clean package

# Ou para execução do JAR
java -Xmx1024m -jar target/design-patterns-bootcamp-1.0.0.jar
```

#### 4. Erro de Encoding
```bash
# Definir encoding UTF-8
export MAVEN_OPTS="-Dfile.encoding=UTF-8"
mvn clean compile

# Ou no sistema
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
```

### Verificações de Diagnóstico

#### 1. Verificar Dependências
```bash
# Listar dependências
mvn dependency:tree

# Verificar conflitos
mvn dependency:analyze

# Baixar dependências
mvn dependency:resolve
```

#### 2. Verificar Compilação
```bash
# Compilar com debug verboso
mvn compile -X

# Verificar classes compiladas
find target/classes -name "*.class" | wc -l
```

#### 3. Verificar Testes
```bash
# Executar teste específico
mvn test -Dtest=DesignPatternsTest#testSingletonPattern

# Executar com stack trace completo
mvn test -Dmaven.test.failure.ignore=true
```

## Integração com IDEs

### 1. IntelliJ IDEA
1. Open Project → Selecionar pasta do projeto
2. IDE detectará automaticamente como projeto Maven
3. Configurar JDK 17 em Project Structure
4. Executar aplicação: clique direito em `DesignPatternsBootcampApplication` → Run

### 2. Eclipse
1. File → Import → Existing Maven Projects
2. Selecionar pasta do projeto
3. Configurar Java Build Path para JDK 17
4. Run As → Java Application

### 3. VS Code
1. Instalar extensões: Extension Pack for Java
2. Abrir pasta do projeto
3. Configurar java.home nas settings
4. F5 para executar com debug

## Comandos Úteis de Desenvolvimento

```bash
# Executar aplicação com hot reload (se DevTools estiver configurado)
mvn spring-boot:run

# Gerar relatório de dependências
mvn project-info-reports:dependencies

# Verificar versões desatualizadas
mvn versions:display-dependency-updates

# Formatar código (se plugin estiver configurado)
mvn fmt:format

# Verificar estilo de código
mvn checkstyle:check

# Executar análise estática
mvn compile pmd:check spotbugs:check
```

## Conclusão

Este guia fornece todas as informações necessárias para compilar, testar e executar o projeto Design Patterns Bootcamp. Para dúvidas específicas, consulte:

- **README.md**: Visão geral do projeto
- **EXEMPLOS_REQUESTS.md**: Exemplos de uso da API
- **CHANGELOG.md**: Histórico de versões
- **Swagger UI**: Documentação interativa da API

Em caso de problemas não cobertos neste guia, verifique os logs da aplicação e consulte a documentação oficial do Spring Boot.