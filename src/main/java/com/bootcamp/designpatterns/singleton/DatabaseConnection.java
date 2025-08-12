package com.bootcamp.designpatterns.singleton;

/**
 * Implementacao do padrao Singleton - Lazy Initialization Thread-Safe
 * 
 * Este padrao garante que uma classe tenha apenas uma instancia
 * e fornece um ponto global de acesso a ela.
 * 
 * Utilizamos a tecnica de Double-Checked Locking para garantir
 * thread safety sem impacto significativo na performance.
 */
public class DatabaseConnection {
    
    // Instancia unica da classe, marcada como volatile para thread safety
    private static volatile DatabaseConnection instance;
    
    // URL de conexao simulada
    private String connectionUrl;
    
    // Construtor privado impede instanciacao externa
    private DatabaseConnection() {
        // Simula uma operacao custosa de inicializacao
        this.connectionUrl = "jdbc:h2:mem:testdb";
        System.out.println("Conexao com banco criada: " + connectionUrl);
    }
    
    /**
     * Metodo para obter a instancia unica da classe
     * Implementa Double-Checked Locking para thread safety
     * 
     * @return instancia unica de DatabaseConnection
     */
    public static DatabaseConnection getInstance() {
        // Primeira verificacao sem sincronizacao (performance)
        if (instance == null) {
            // Sincronizacao apenas quando necessario
            synchronized (DatabaseConnection.class) {
                // Segunda verificacao dentro do bloco sincronizado
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Simula uma operacao de consulta no banco
     * 
     * @param query consulta SQL
     * @return resultado simulado
     */
    public String executeQuery(String query) {
        return "Executando query: " + query + " na conexao " + connectionUrl;
    }
    
    /**
     * Getter para URL de conexao
     * 
     * @return URL da conexao
     */
    public String getConnectionUrl() {
        return connectionUrl;
    }
}