package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Producto;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getAllProducts() {
        return productoRepository.findAll();
    }

    public Producto getProductById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    public Producto saveProduct(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProduct(Long id, Producto details) {
        Producto p = getProductById(id);
        p.setNombre(details.getNombre());
        p.setDescripcion(details.getDescripcion());
        p.setPrecio(details.getPrecio());
        p.setStock(details.getStock());
        p.setCategoria(details.getCategoria());
        p.setImagenUrl(details.getImagenUrl());
        return productoRepository.save(p);
    }

    public void deleteProduct(Long id) {
        productoRepository.deleteById(id);
    }
}