package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador para gestionar las operaciones de usuarios y autenticación.
 * Utiliza UsuarioService para aplicar las reglas de negocio de GgShop.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuarios", description = "Endpoints para registro, login y gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna todos los usuarios registrados (Solo para propósitos de administración).")
    public List<Usuario> listarUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    @PostMapping("/registro")
    @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario. Si no se especifica rol, se asigna CLIENTE por defecto.")
    public ResponseEntity<Usuario> registrar(@Valid @RequestBody Usuario usuario) {
        // La lógica de asignar rol por defecto y validar duplicados ahora reside en el Service
        Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario mediante username y password.")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> credenciales) {
        String username = credenciales.get("username");
        String password = credenciales.get("password");

        // El Service maneja la validación y lanza ResourceNotFoundException si fallan las credenciales
        Usuario usuarioAutenticado = usuarioService.login(username, password);
        return ResponseEntity.ok(usuarioAutenticado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID", description = "Busca los detalles de un usuario específico.")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }
}