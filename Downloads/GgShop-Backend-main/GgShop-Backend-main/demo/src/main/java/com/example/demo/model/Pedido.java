package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference; // Opcional, ayuda con recursión
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String clienteId;

    // 1. ¡CORRECCIÓN VITAL! Inicializamos la fecha aquí mismo para que nunca sea null.
    private LocalDateTime fechaCreacion = LocalDateTime.now(); 
    
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDIENTE; // Inicializamos estado por defecto

    private BigDecimal total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> items = new ArrayList<>(); 

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<ItemPedido> getItems() { return items; }

    // 2. ¡CORRECCIÓN VITAL! 
    // Cuando Jackson (la librería que lee el JSON) llama a este método,
    // debemos asegurar que cada hijo sepa quién es su padre.
    public void setItems(List<ItemPedido> items) {
        this.items = items;
        if (items != null) {
            for (ItemPedido item : items) {
                item.setPedido(this); // <--- AQUÍ ESTÁ LA MAGIA. Sin esto, falla.
            }
        }
    }

    public void agregarItem(ItemPedido item) {
        items.add(item);
        item.setPedido(this);
    }
}