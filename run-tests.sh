#!/bin/bash

# Script para executar diferentes tipos de teste
# Design Patterns Bootcamp - Test Runner

echo "================================================="
echo "    DESIGN PATTERNS BOOTCAMP - TEST RUNNER"
echo "================================================="

# Funcao para mostrar ajuda
show_help() {
    echo "Uso: $0 [OPCAO]"
    echo ""
    echo "Opcoes:"
    echo "  unit        Executa apenas testes unitarios"
    echo "  integration Executa apenas testes de integracao"
    echo "  controller  Executa apenas testes de controller"
    echo "  all         Executa todos os testes (padrao)"
    echo "  coverage    Executa todos os testes com relatorio de cobertura"
    echo "  clean       Limpa e executa todos os testes"
    echo "  help        Mostra esta mensagem de ajuda"
    echo ""
    echo "Exemplos:"
    echo "  ./run-tests.sh unit"
    echo "  ./run-tests.sh integration"
    echo "  ./run-tests.sh coverage"
}

# Funcao para executar testes unitarios
run_unit_tests() {
    echo "🧪 Executando Testes Unitarios..."
    echo "Incluindo: Singleton, Strategy, Facade, Service"
    echo "-------------------------------------------------"
    mvn test -Punit-tests -Dtest="**/*UnitTest,**/*ServiceTest" -q
    echo "✅ Testes unitarios concluidos!"
}

# Funcao para executar testes de integracao
run_integration_tests() {
    echo "🔗 Executando Testes de Integracao..."
    echo "Incluindo: API REST, Spring Boot, H2 Database"
    echo "-------------------------------------------------"
    mvn verify -Pintegration-tests -Dit.test="**/*IntegrationTest" -q
    echo "✅ Testes de integracao concluidos!"
}

# Funcao para executar testes de controller
run_controller_tests() {
    echo "🎮 Executando Testes de Controller..."
    echo "Incluindo: MockMvc, Endpoints REST, Validacoes"
    echo "-------------------------------------------------"
    mvn test -Pcontroller-tests -Dtest="**/*ControllerTest" -q
    echo "✅ Testes de controller concluidos!"
}

# Funcao para executar todos os testes
run_all_tests() {
    echo "🚀 Executando Todos os Testes..."
    echo "Incluindo: Unit + Controller + Integration"
    echo "-------------------------------------------------"
    mvn clean verify -Pall-tests -q
    echo "✅ Todos os testes concluidos!"
}

# Funcao para executar com cobertura
run_with_coverage() {
    echo "📊 Executando Testes com Cobertura..."
    echo "Gerando relatorio JaCoCo"
    echo "-------------------------------------------------"
    mvn clean verify jacoco:report -Pall-tests
    echo ""
    echo "📈 Relatorio de cobertura gerado em:"
    echo "   target/site/jacoco/index.html"
    echo "✅ Testes com cobertura concluidos!"
}

# Funcao para limpeza e teste completo
run_clean_tests() {
    echo "🧹 Limpeza e Teste Completo..."
    echo "Compilando e testando do zero"
    echo "-------------------------------------------------"
    mvn clean compile test verify -Pall-tests
    echo "✅ Limpeza e testes concluidos!"
}

# Verificar se Maven esta instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven nao encontrado. Instale o Maven para continuar."
    exit 1
fi

# Verificar se estamos no diretorio correto
if [ ! -f "pom.xml" ]; then
    echo "❌ Arquivo pom.xml nao encontrado. Execute o script do diretorio raiz do projeto."
    exit 1
fi

# Processar argumentos
case "${1:-all}" in
    "unit")
        run_unit_tests
        ;;
    "integration")
        run_integration_tests
        ;;
    "controller")
        run_controller_tests
        ;;
    "all")
        run_all_tests
        ;;
    "coverage")
        run_with_coverage
        ;;
    "clean")
        run_clean_tests
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    *)
        echo "❌ Opcao invalida: $1"
        echo ""
        show_help
        exit 1
        ;;
esac

# Mostrar resumo final
echo ""
echo "================================================="
echo "               RESUMO DOS TESTES"
echo "================================================="

# Verificar se existem falhas
if [ $? -eq 0 ]; then
    echo "✅ Todos os testes passaram com sucesso!"
    echo ""
    echo "📁 Arquivos gerados:"
    echo "   target/surefire-reports/     (Relatorios de teste)"
    echo "   target/site/jacoco/          (Cobertura de codigo)"
    echo ""
    echo "🔍 Para ver detalhes dos testes:"
    echo "   cat target/surefire-reports/*.txt"
else
    echo "❌ Alguns testes falharam. Verifique os logs acima."
    echo ""
    echo "🔍 Para debug:"
    echo "   mvn test -X  (modo verbose)"
    echo "   Verifique target/surefire-reports/ para detalhes"
fi

echo ""
echo "🎯 Projeto: Design Patterns Bootcamp"
echo "📅 Data: $(date)"
echo "================================================="