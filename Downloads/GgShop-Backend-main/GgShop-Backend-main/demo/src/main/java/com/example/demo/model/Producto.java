package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private int precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;
    
    @Column(name = "imagen_url")
    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imagenUrl;

    // CONSTRUCTOR VACÍO
    public Producto() {}

    // CONSTRUCTOR COMPLETO (Usado en DataInitializer)
    public Producto(Long id, String nombre, String descripcion, int precio, int stock, String categoria, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.imagenUrl = imagenUrl;
    }

    // GETTERS Y SETTERS MANUALES
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}