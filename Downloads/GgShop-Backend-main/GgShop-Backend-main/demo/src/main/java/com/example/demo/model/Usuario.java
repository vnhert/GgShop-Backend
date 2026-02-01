package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

enum RolNombre {
    ADMIN, CLIENTE
}

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 20, message = "El username debe tener entre 4 y 20 caracteres")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolNombre rol; 

    @NotBlank(message = "El nombre completo es obligatorio")
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    // CONSTRUCTOR VACÍO
    public Usuario() {}

    // GETTERS Y SETTERS MANUALES (Para que no falle la compilación)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public RolNombre getRol() { return rol; }
    public void setRol(RolNombre rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    // Método auxiliar especial
    public void setRolString(String rolRecibido) {
        try {
            this.rol = RolNombre.valueOf(rolRecibido.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.rol = RolNombre.CLIENTE;
        }
    }
}