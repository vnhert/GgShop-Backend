package com.example.demo.repository;

import com.example.demo.model.Pedido;
import com.example.demo.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    // Buscar todos los pedidos de un cliente (Gene, Admin, etc.)
    List<Pedido> findByClienteId(String clienteId);

    // Buscar por estado (PENDIENTE, ENVIADO, etc.)
    List<Pedido> findByEstado(EstadoPedido estado);

    // Buscar combinando cliente y estado
    List<Pedido> findByClienteIdAndEstado(String clienteId, EstadoPedido estado);
}