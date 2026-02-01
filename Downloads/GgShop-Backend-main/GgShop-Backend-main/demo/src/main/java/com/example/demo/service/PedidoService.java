package com.example.demo.service;

import com.example.demo.model.EstadoPedido;
import com.example.demo.model.ItemPedido;
import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Pedido guardarPedido(Pedido pedido) {
        // 1. Validar que el cliente exista
        usuarioRepository.findByUsername(pedido.getClienteId())
                .orElseThrow(() -> new RuntimeException("Error: El usuario '" + pedido.getClienteId() + "' no existe."));

        // 2. Datos automáticos
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado(EstadoPedido.PENDIENTE);

        // 3. Procesar items y calcular total
        BigDecimal totalCalculado = BigDecimal.ZERO;

        if (pedido.getItems() != null) {
            for (ItemPedido item : pedido.getItems()) {
                item.setPedido(pedido); // Vinculación obligatoria JPA

                // Cálculo seguro con BigDecimal
                if (item.getPrecioUnitario() != null && item.getCantidad() != null) {
                    BigDecimal subtotal = item.getPrecioUnitario().multiply(new BigDecimal(item.getCantidad()));
                    item.setSubtotal(subtotal);
                    totalCalculado = totalCalculado.add(subtotal);
                }
            }
        }
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> obtenerPorId(UUID id) {
        return pedidoRepository.findById(id);
    }

    // --- AQUÍ ESTÁ EL CAMBIO IMPORTANTE ---
    
    // Renombrado a 'obtenerPorUsuario' para que coincida con el Controller.
    // Usamos 'stream' para filtrar, así funciona aunque el Repository no tenga el método findByClienteId.
    public List<Pedido> obtenerPorUsuario(String clienteId) {
        return pedidoRepository.findAll().stream()
                .filter(p -> p.getClienteId().equals(clienteId))
                .collect(Collectors.toList());
    }
}