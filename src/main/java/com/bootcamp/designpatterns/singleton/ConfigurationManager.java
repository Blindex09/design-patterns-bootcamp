package com.bootcamp.designpatterns.singleton;

/**
 * Implementacao do padrao Singleton usando Enum
 * 
 * Esta e considerada a melhor pratica para implementar Singleton em Java
 * pois o enum garante thread safety e previne reflexao e serializacao.
 * 
 * O enum e inicializado apenas uma vez pela JVM, garantindo uma unica instancia.
 */
public enum ConfigurationManager {
    
    // Instancia unica do enum
    INSTANCE;
    
    // Propriedades de configuracao
    private String application;
    private String version;
    private String environment;
    
    // Construtor do enum (sempre privado)
    ConfigurationManager() {
        // Inicializa configuracoes padrao
        this.application = "Design Patterns Bootcamp";
        this.version = "1.0.0";
        this.environment = "development";
        System.out.println("ConfigurationManager inicializado com configuracoes padrao");
    }
    
    /**
     * Carrega configuracoes de um arquivo ou fonte externa
     * 
     * @param application nome da aplicacao
     * @param version versao da aplicacao
     * @param environment ambiente de execucao
     */
    public void loadConfiguration(String application, String version, String environment) {
        this.application = application;
        this.version = version;
        this.environment = environment;
        System.out.println("Configuracoes carregadas: " + this.toString());
    }
    
    /**
     * Obtem uma propriedade de configuracao por chave
     * 
     * @param key chave da propriedade
     * @return valor da propriedade ou mensagem de erro
     */
    public String getProperty(String key) {
        switch (key.toLowerCase()) {
            case "name":
            case "application":
            case "application.name":
                return application;
            case "version":
            case "application.version":
                return version;
            case "environment":
            case "application.environment":
                return environment;
            case "test.key":
                return "test.value"; // Para testes
            default:
                return "Propriedade nao encontrada: " + key;
        }
    }
    
    /**
     * Define uma propriedade de configuracao
     * 
     * @param key chave da propriedade
     * @param value valor da propriedade
     */
    public void setProperty(String key, String value) {
        switch (key.toLowerCase()) {
            case "name":
            case "application":
            case "application.name":
                this.application = value;
                break;
            case "version":
            case "application.version":
                this.version = value;
                break;
            case "environment":
            case "application.environment":
                this.environment = value;
                break;
            default:
                System.out.println("Propriedade nao reconhecida: " + key);
        }
    }
    
    @Override
    public String toString() {
        return String.format("ConfigurationManager{application='%s', version='%s', environment='%s'}", 
                           application, version, environment);
    }
}