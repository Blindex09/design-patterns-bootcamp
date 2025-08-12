# Script PowerShell para executar testes no Windows
# Design Patterns Bootcamp - Test Runner

param(
    [Parameter(Position=0)]
    [ValidateSet("unit", "integration", "controller", "all", "coverage", "clean", "help")]
    [string]$TestType = "all"
)

# Configuracao de cores
$SuccessColor = "Green"
$ErrorColor = "Red"
$InfoColor = "Cyan"
$WarningColor = "Yellow"

function Show-Header {
    Write-Host "=================================================" -ForegroundColor $InfoColor
    Write-Host "    DESIGN PATTERNS BOOTCAMP - TEST RUNNER" -ForegroundColor $InfoColor
    Write-Host "=================================================" -ForegroundColor $InfoColor
}

function Show-Help {
    Write-Host "Uso: .\run-tests.ps1 [OPCAO]" -ForegroundColor $InfoColor
    Write-Host ""
    Write-Host "Opcoes:" -ForegroundColor $WarningColor
    Write-Host "  unit        Executa apenas testes unitarios" -ForegroundColor White
    Write-Host "  integration Executa apenas testes de integracao" -ForegroundColor White
    Write-Host "  controller  Executa apenas testes de controller" -ForegroundColor White
    Write-Host "  all         Executa todos os testes (padrao)" -ForegroundColor White
    Write-Host "  coverage    Executa todos os testes com relatorio de cobertura" -ForegroundColor White
    Write-Host "  clean       Limpa e executa todos os testes" -ForegroundColor White
    Write-Host "  help        Mostra esta mensagem de ajuda" -ForegroundColor White
    Write-Host ""
    Write-Host "Exemplos:" -ForegroundColor $WarningColor
    Write-Host "  .\run-tests.ps1 unit" -ForegroundColor White
    Write-Host "  .\run-tests.ps1 integration" -ForegroundColor White
    Write-Host "  .\run-tests.ps1 coverage" -ForegroundColor White
}

function Test-Prerequisites {
    # Verificar se Maven esta instalado
    try {
        $mavenVersion = mvn -version 2>$null
        if ($LASTEXITCODE -ne 0) {
            throw "Maven nao encontrado"
        }
        Write-Host "✅ Maven encontrado" -ForegroundColor $SuccessColor
    }
    catch {
        Write-Host "❌ Maven nao encontrado. Instale o Maven para continuar." -ForegroundColor $ErrorColor
        exit 1
    }

    # Verificar se estamos no diretorio correto
    if (-not (Test-Path "pom.xml")) {
        Write-Host "❌ Arquivo pom.xml nao encontrado. Execute o script do diretorio raiz do projeto." -ForegroundColor $ErrorColor
        exit 1
    }
    Write-Host "✅ Projeto encontrado" -ForegroundColor $SuccessColor
}

function Invoke-UnitTests {
    Write-Host "🧪 Executando Testes Unitarios..." -ForegroundColor $InfoColor
    Write-Host "Incluindo: Singleton, Strategy, Facade, Service" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "test", "-Punit-tests", "-Dtest=**/*UnitTest,**/*ServiceTest", "-q" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host "✅ Testes unitarios concluidos com sucesso!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha nos testes unitarios!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Invoke-IntegrationTests {
    Write-Host "🔗 Executando Testes de Integracao..." -ForegroundColor $InfoColor
    Write-Host "Incluindo: API REST, Spring Boot, H2 Database" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "verify", "-Pintegration-tests", "-Dit.test=**/*IntegrationTest", "-q" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host "✅ Testes de integracao concluidos com sucesso!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha nos testes de integracao!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Invoke-ControllerTests {
    Write-Host "🎮 Executando Testes de Controller..." -ForegroundColor $InfoColor
    Write-Host "Incluindo: MockMvc, Endpoints REST, Validacoes" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "test", "-Pcontroller-tests", "-Dtest=**/*ControllerTest", "-q" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host "✅ Testes de controller concluidos com sucesso!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha nos testes de controller!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Invoke-AllTests {
    Write-Host "🚀 Executando Todos os Testes..." -ForegroundColor $InfoColor
    Write-Host "Incluindo: Unit + Controller + Integration" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "clean", "verify", "-Pall-tests", "-q" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host "✅ Todos os testes concluidos com sucesso!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha em alguns testes!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Invoke-CoverageTests {
    Write-Host "📊 Executando Testes com Cobertura..." -ForegroundColor $InfoColor
    Write-Host "Gerando relatorio JaCoCo" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "clean", "verify", "jacoco:report", "-Pall-tests" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host ""
        Write-Host "📈 Relatorio de cobertura gerado em:" -ForegroundColor $SuccessColor
        Write-Host "   target\site\jacoco\index.html" -ForegroundColor White
        Write-Host "✅ Testes com cobertura concluidos!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha na execucao dos testes com cobertura!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Invoke-CleanTests {
    Write-Host "🧹 Limpeza e Teste Completo..." -ForegroundColor $InfoColor
    Write-Host "Compilando e testando do zero" -ForegroundColor White
    Write-Host "-------------------------------------------------" -ForegroundColor $InfoColor
    
    $result = Start-Process -FilePath "mvn" -ArgumentList "clean", "compile", "test", "verify", "-Pall-tests" -Wait -NoNewWindow -PassThru
    
    if ($result.ExitCode -eq 0) {
        Write-Host "✅ Limpeza e testes concluidos com sucesso!" -ForegroundColor $SuccessColor
    } else {
        Write-Host "❌ Falha na limpeza e execucao dos testes!" -ForegroundColor $ErrorColor
        return $false
    }
    return $true
}

function Show-Summary {
    param([bool]$Success)
    
    Write-Host ""
    Write-Host "=================================================" -ForegroundColor $InfoColor
    Write-Host "               RESUMO DOS TESTES" -ForegroundColor $InfoColor
    Write-Host "=================================================" -ForegroundColor $InfoColor
    
    if ($Success) {
        Write-Host "✅ Todos os testes passaram com sucesso!" -ForegroundColor $SuccessColor
        Write-Host ""
        Write-Host "📁 Arquivos gerados:" -ForegroundColor $WarningColor
        Write-Host "   target\surefire-reports\     (Relatorios de teste)" -ForegroundColor White
        Write-Host "   target\site\jacoco\          (Cobertura de codigo)" -ForegroundColor White
        Write-Host ""
        Write-Host "🔍 Para ver detalhes dos testes:" -ForegroundColor $WarningColor
        Write-Host "   Get-Content target\surefire-reports\*.txt" -ForegroundColor White
    } else {
        Write-Host "❌ Alguns testes falharam. Verifique os logs acima." -ForegroundColor $ErrorColor
        Write-Host ""
        Write-Host "🔍 Para debug:" -ForegroundColor $WarningColor
        Write-Host "   mvn test -X  (modo verbose)" -ForegroundColor White
        Write-Host "   Verifique target\surefire-reports\ para detalhes" -ForegroundColor White
    }
    
    Write-Host ""
    Write-Host "🎯 Projeto: Design Patterns Bootcamp" -ForegroundColor $InfoColor
    Write-Host "📅 Data: $(Get-Date -Format 'dd/MM/yyyy HH:mm:ss')" -ForegroundColor $InfoColor
    Write-Host "=================================================" -ForegroundColor $InfoColor
}

# Execucao principal
Show-Header
Test-Prerequisites

$success = $true

switch ($TestType) {
    "unit" {
        $success = Invoke-UnitTests
    }
    "integration" {
        $success = Invoke-IntegrationTests
    }
    "controller" {
        $success = Invoke-ControllerTests
    }
    "all" {
        $success = Invoke-AllTests
    }
    "coverage" {
        $success = Invoke-CoverageTests
    }
    "clean" {
        $success = Invoke-CleanTests
    }
    "help" {
        Show-Help
        exit 0
    }
    default {
        Write-Host "❌ Opcao invalida: $TestType" -ForegroundColor $ErrorColor
        Write-Host ""
        Show-Help
        exit 1
    }
}

Show-Summary -Success $success

if (-not $success) {
    exit 1
}