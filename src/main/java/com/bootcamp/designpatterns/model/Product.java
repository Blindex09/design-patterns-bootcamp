package com.bootcamp.designpatterns.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa um Produto
 * 
 * Demonstra o padrao Active Record integrado com JPA,
 * onde a entidade encapsula tanto dados quanto comportamentos.
 */
@Entity
@Table(name = "produtos")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nome do produto e obrigatorio")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Descricao nao pode exceder 500 caracteres")
    @Column(length = 500)
    private String description;
    
    @NotNull(message = "Preco e obrigatorio")
    @DecimalMin(value = "0.01", message = "Preco deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Preco deve ter no maximo 10 digitos inteiros e 2 decimais")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    
    @NotNull(message = "Quantidade em estoque e obrigatoria")
    @Min(value = 0, message = "Estoque nao pode ser negativo")
    @Column(nullable = false)
    private Integer stockQuantity;
    
    @NotBlank(message = "Categoria e obrigatoria")
    @Size(min = 2, max = 50, message = "Categoria deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, length = 50)
    private String category;
    
    @Column(nullable = false)
    private Boolean active = true;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Construtor padrao exigido pelo JPA
    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Construtor com parametros principais
    public Product(String name, String description, BigDecimal price, 
                  Integer stockQuantity, String category) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
    }
    
    // Metodos de negocio
    
    /**
     * Verifica se o produto esta disponivel para venda
     * 
     * @param requestedQuantity quantidade solicitada
     * @return true se ha estoque suficiente e produto ativo
     */
    public boolean isAvailable(int requestedQuantity) {
        return active && stockQuantity >= requestedQuantity;
    }
    
    /**
     * Reduz o estoque do produto
     * 
     * @param quantity quantidade a ser reduzida
     * @throws IllegalArgumentException se nao ha estoque suficiente
     */
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        
        if (stockQuantity < quantity) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        
        this.stockQuantity -= quantity;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Adiciona estoque ao produto
     * 
     * @param quantity quantidade a ser adicionada
     */
    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        }
        
        this.stockQuantity += quantity;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Ativa o produto para venda
     */
    public void activate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Desativa o produto para venda
     */
    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    // Callback JPA para atualizar timestamp
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; this.updatedAt = LocalDateTime.now(); }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; this.updatedAt = LocalDateTime.now(); }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; this.updatedAt = LocalDateTime.now(); }
    
    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; this.updatedAt = LocalDateTime.now(); }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; this.updatedAt = LocalDateTime.now(); }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; this.updatedAt = LocalDateTime.now(); }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', price=%s, stock=%d, category='%s', active=%s}", 
                           id, name, price, stockQuantity, category, active);
    }
}