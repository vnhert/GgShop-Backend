package com.example.demo.repository;

import com.example.demo.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// CORRECCIÓN: Aquí cambiamos UUID por Long para que coincida con tu nuevo modelo
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    
}