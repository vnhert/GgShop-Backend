package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
// import java.util.UUID; // Ya no usamos UUID para el item, usamos Long para evitar líos

@Entity
@Table(name = "items_pedido")
public class ItemPedido { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usamos IDENTITY para autoincrementar (1, 2, 3...)
    private Long id;

    // RELACIÓN CON EL PEDIDO (PADRE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonIgnore 
    private Pedido pedido;

    // RELACIÓN CON EL PRODUCTO (LO QUE FALTABA)
    // Esto arregla el error "setProducto undefined"
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; 

    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // Constructor vacío
    public ItemPedido() {}

    // --- GETTERS Y SETTERS ---
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    // --- AQUÍ ESTÁ LA CORRECCIÓN CLAVE ---
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    // -------------------------------------

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}