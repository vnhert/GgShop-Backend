package com.example.demo.config;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        return args -> {
            // Limpiamos las tablas para evitar duplicados al reiniciar el servidor
            productoRepository.deleteAll();
            usuarioRepository.deleteAll();

            // --- SECCIÓN USUARIOS (Sincronizados con App Android GgShop) ---
            
            // Usuario Administrador para el Panel de Gestión
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setNombreCompleto("Administrador GgShop");
            // Usamos el método robusto que creamos en el modelo
            admin.setRolString("ADMIN"); 

            // Usuario Cliente para navegación estándar
            Usuario cliente = new Usuario();
            cliente.setUsername("user");
            cliente.setPassword("user123");
            cliente.setNombreCompleto("Juan Pérez");
            cliente.setRolString("CLIENTE");

            usuarioRepository.save(admin);
            usuarioRepository.save(cliente);

            // --- SECCIÓN PRODUCTOS (Catálogo Real Gaming & Mobile) ---
            
            List<Producto> productos = new ArrayList<>();

            // Hardware y Periféricos Gaming
            productos.add(crearProducto(
                "Logitech G502 HERO", 
                "Sensor HERO 25K, 11 botones programables y pesas ajustables.", 
                55000, 15, "GAMING", 
                "https://resource.logitechg.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/g502-hero/g502-hero-gallery-1.png?v=1"));
            
            productos.add(crearProducto(
                "Razer BlackWidow V4 Pro", 
                "Teclado mecánico con switches Green y retroiluminación Chroma RGB.", 
                185000, 8, "GAMING", 
                "https://i.postimg.cc/yNT77Dq5/razer.webp"));
            
            productos.add(crearProducto(
                "Samsung Odyssey G7", 
                "Monitor curvo de 27 pulgadas, QLED, 240Hz y 1ms de respuesta.", 
                420000, 5, "GAMING", 
                "https://i.postimg.cc/6pJKRrtP/samsung.webp"));
            
            productos.add(crearProducto(
                "HyperX Cloud II Wireless", 
                "Auriculares con sonido envolvente 7.1 y 30 horas de batería.", 
                95000, 12, "GAMING", 
                "https://row.hyperx.com/cdn/shop/products/hyperx_cloud_ii_wireless_1_main_900x.jpg?v=1662443315"));

            productos.add(crearProducto(
                "NVIDIA GeForce RTX 4080", 
                "Arquitectura Ada Lovelace, 16GB GDDR6X y trazado de rayos extremo.", 
                1250000, 3, "GAMING", 
                "https://i.postimg.cc/wx13QkYK/grafica.jpg"));

            productos.add(crearProducto(
                "ASUS ROG Ally Z1 Extreme", 
                "Consola portátil con Windows 11, pantalla 120Hz y procesador Ryzen.", 
                690000, 7, "GAMING", 
                "https://i.postimg.cc/zDHXgXTy/consola-portatil-rog-ally-z1-extreme-16gb-ram-512gb-ssd-openbox-2744325.webp"));

            // Dispositivos Móviles de Última Generación
            productos.add(crearProducto(
                "iPhone 15 Pro Max", 
                "Titanio natural, Chip A17 Pro y sistema de cámaras Pro de 48MP.", 
                1250000, 10, "CELULARES", 
                "https://i.postimg.cc/zXXsqvzr/apple-1-34-3.webp"));
            
            productos.add(crearProducto(
                "Samsung Galaxy S24 Ultra", 
                "Titanium Black, 256GB, Inteligencia Artificial Galaxy AI y S-Pen.", 
                1150000, 7, "CELULARES", 
                "https://i.postimg.cc/rmNHkX7G/w-1200-h-1200-fit-pad.webp"));
            
            productos.add(crearProducto(
                "Xiaomi 14 Ultra", 
                "Lente óptica Leica Summilux y sensor de 1 pulgada para fotografía.", 
                980000, 4, "CELULARES", 
                "https://i.postimg.cc/BvxYTfSh/xiaomi-14t-pro-negro-01-337x671.jpg"));
            
            productos.add(crearProducto(
                "Google Pixel 8 Pro", 
                "El mejor teléfono de Google con procesador Tensor G3 y cámara pro.", 
                890000, 6, "CELULARES", 
                "https://i.postimg.cc/Nf9x8vbd/w-1200-h-1200-fit-pad-(1).webp"));

            productos.add(crearProducto(
                "Motorola Razr 40 Ultra", 
                "Pantalla plegable pOLED de 6.9 pulgadas y diseño icónico.", 
                750000, 5, "CELULARES", 
                "https://i.postimg.cc/1zjGVW6p/w-1200-h-1200-fit-pad-(2).webp"));

            productos.add(crearProducto(
                "Nothing Phone (2)", 
                "Interfaz Glyph única, Snapdragon 8+ Gen 1 y diseño transparente.", 
                580000, 9, "CELULARES", 
                "https://i.postimg.cc/pXHDj591/w-1200-h-1200-fit-pad-(3).webp"));

            productoRepository.saveAll(productos);
            
            System.out.println("---------------------------------------------------------");
            System.out.println(">> ÉXITO: Entorno GgShop inicializado correctamente");
            System.out.println(">> Usuarios: admin (ADMIN), user (CLIENTE)");
            System.out.println(">> Productos cargados: " + productoRepository.count());
            System.out.println("---------------------------------------------------------");
        };
    }

    private Producto crearProducto(String nombre, String desc, int precio, int stock, String cat, String img) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setDescripcion(desc);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setCategoria(cat);
        p.setImagenUrl(img);
        return p;
    }
}