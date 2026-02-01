package com.example.demo.controller;

import com.example.demo.model.Pedido;
import com.example.demo.model.ItemPedido;
import com.example.demo.model.Producto; // Asegúrate de importar tu modelo Producto
import com.example.demo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "*") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // --- 1. CLASES AUXILIARES (DTOs) ---
    // Estas clases sirven para leer el JSON exactamente como lo manda Android
    // y evitar errores de formato.
    
    static class PedidoRequestDTO {
        public String clienteId;
        public BigDecimal total;
        public List<ItemRequestDTO> items;
    }

    static class ItemRequestDTO {
        public Long productoId; // Android manda "productoId" (número)
        public Integer cantidad;
        public Double precioUnitario;
        public Double subtotal;
    }
    // -----------------------------------

    // 2. CREAR PEDIDO (MODIFICADO PARA USAR EL DTO)
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequestDTO solicitud) {
        try {
            System.out.println("Recibiendo pedido para cliente: " + solicitud.clienteId);

            // A. Creamos la Entidad Pedido real
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.setClienteId(solicitud.clienteId);
            nuevoPedido.setTotal(solicitud.total);
            // La fecha y estado se ponen solos en el modelo que corregimos antes

            // B. Convertimos los items del JSON a Entidades ItemPedido
            if (solicitud.items != null) {
                List<ItemPedido> listaItems = new ArrayList<>();
                
                for (ItemRequestDTO itemDto : solicitud.items) {
                    ItemPedido itemReal = new ItemPedido();
                    itemReal.setCantidad(itemDto.cantidad);
                    
                    // Convertimos Double a BigDecimal para el precio
                    if (itemDto.precioUnitario != null) {
                        itemReal.setPrecioUnitario(BigDecimal.valueOf(itemDto.precioUnitario));
                    }
                    if (itemDto.subtotal != null) {
                        itemReal.setSubtotal(BigDecimal.valueOf(itemDto.subtotal));
                    }

                    // *** TRUCO DEL PRODUCTO ***
                    // Android manda un ID, pero Backend quiere un objeto Producto.
                    // Creamos un producto "falso" solo con el ID para engañar a Hibernate.
                    Producto p = new Producto();
                    p.setId(itemDto.productoId); 
                    itemReal.setProducto(p);

                    // Vinculamos con el padre
                    itemReal.setPedido(nuevoPedido);
                    
                    listaItems.add(itemReal);
                }
                // Guardamos la lista en el pedido
                nuevoPedido.setItems(listaItems);
            }

            // C. Guardamos en base de datos
            Pedido pedidoGuardado = pedidoService.guardarPedido(nuevoPedido);
            return new ResponseEntity<>(pedidoGuardado, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace(); // Verás el error exacto en la consola de Java
            return new ResponseEntity<>("Error en el servidor: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ... (El resto de tus métodos GET se mantienen igual) ...
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        return new ResponseEntity<>(pedidoService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable UUID id) {
        return pedidoService.obtenerPorId(id)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/usuario/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorUsuario(@PathVariable String clienteId) {
        return new ResponseEntity<>(pedidoService.obtenerPorUsuario(clienteId), HttpStatus.OK);
    }
}