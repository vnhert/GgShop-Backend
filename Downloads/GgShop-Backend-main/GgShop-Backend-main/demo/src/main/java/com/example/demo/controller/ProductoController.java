package com.example.demo.controller;
import com.example.demo.model.Producto;
import com.example.demo.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
@Tag(name = "Productos", description = "CRUD completo para gestión de inventario (Requisito EFT)")
public class ProductoController {

    @Autowired
    private ProductoService productService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Retorna el catálogo completo.")
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener por ID", description = "Busca un producto específico.")
    public ResponseEntity<Producto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Añade un nuevo producto al catálogo.")
    public ResponseEntity<Producto> create(@RequestBody Producto product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Modifica un producto existente.")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto details) {
        return ResponseEntity.ok(productService.updateProduct(id, details));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Borra un producto permanentemente.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}