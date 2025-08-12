-- Arquivo de inicializacao do banco H2
-- Este arquivo e executado automaticamente pelo Spring Boot na inicializacao

-- Criacao da tabela de produtos (caso nao seja criada automaticamente)
CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(12,2) NOT NULL,
    stock_quantity INTEGER NOT NULL,
    category VARCHAR(50) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insercao de dados de exemplo para demonstracao
INSERT INTO produtos (nome, description, price, stock_quantity, category, active, created_at, updated_at) VALUES
('Smartphone Galaxy S23', 'Smartphone Samsung Galaxy S23 128GB', 2499.99, 50, 'Eletrônicos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Notebook Dell Inspiron', 'Notebook Dell Inspiron 15 Intel i5 8GB RAM', 3299.90, 25, 'Informática', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tênis Nike Air Max', 'Tênis Nike Air Max masculino preto', 399.99, 100, 'Calçados', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cafeteira Nespresso', 'Cafeteira Nespresso Essenza Mini', 199.90, 30, 'Eletrodomésticos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Livro Clean Code', 'Livro Clean Code - Robert C. Martin', 89.90, 200, 'Livros', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Mouse Gamer Logitech', 'Mouse Gamer Logitech G502 RGB', 179.99, 75, 'Informática', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fone JBL Tune 510BT', 'Fone de ouvido JBL Tune 510BT Bluetooth', 149.90, 60, 'Eletrônicos', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Camiseta Polo', 'Camiseta Polo masculina algodão', 79.90, 150, 'Roupas', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HD Externo 1TB', 'HD Externo Seagate 1TB USB 3.0', 299.99, 40, 'Informática', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Panela de Pressão Tramontina', 'Panela de Pressão Tramontina 4.5L', 119.90, 35, 'Casa e Cozinha', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Comentarios explicativos sobre os dados
-- Os produtos foram escolhidos para demonstrar diferentes faixas de preco
-- e categorias, permitindo testar adequadamente os padroes implementados:
--
-- 1. Diferentes precos para testar Strategy Pattern com descontos progressivos
-- 2. Variedade de estoque para testar Facade Pattern com verificacao de disponibilidade  
-- 3. Produtos com IDs pares e impares para simular logica de negocio no estoque
-- 4. Categorias diversas para demonstrar flexibilidade do sistema

-- Dados adicionais para logs e auditoria
INSERT INTO produtos (nome, description, price, stock_quantity, category, active, created_at, updated_at) VALUES
('Produto Teste Estoque Zero', 'Produto para testar cenário sem estoque', 99.99, 0, 'Teste', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Produto Inativo', 'Produto desativado para teste', 199.99, 50, 'Teste', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Comando para verificar se os dados foram inseridos
-- SELECT COUNT(*) as total_produtos FROM produtos;
-- SELECT * FROM produtos WHERE active = true ORDER BY price;