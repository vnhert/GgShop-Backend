# 🖥️ GgShop - Spring Boot REST API

Este es el núcleo del ecosistema GgShop, encargado de la persistencia de datos y la exposición de servicios REST para el cliente móvil.

## 📋 Funcionalidades de la API
* **Gestión de Productos:** CRUD completo para el inventario de la tienda.
* **Sistema de Usuarios:** Registro, inicio de sesión y perfiles.
* **Manejo de Pedidos:** Procesamiento de compras y asociación con clientes.
* **Inicialización de Datos:** Configuración automática de catálogo base al arrancar.

## 🏗️ Arquitectura del Sistema
El backend sigue una estructura de capas para facilitar el mantenimiento:
* **Controllers:** Endpoints RESTful documentados.
* **Services:** Lógica de negocio y validaciones.
* **Repositories:** Capa de acceso a datos mediante Spring Data JPA.
* **Models:** Entidades JPA para el mapeo relacional.

## 🔧 Tecnologías Utilizadas
* **Framework:** Spring Boot 3.x
* **Persistencia:** Hibernate / JPA
* **Base de Datos:** Configurable vía `application.properties` (MySQL/PostgreSQL)
* **Documentación:** Swagger / OpenAPI
