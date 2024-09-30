# DB_CONFIG.md

## Índice
1. [Información General](#información-general)
2. [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
3. [Variables de Entorno](#variables-de-entorno)
4. [Estructura de las Tablas](#estructura-de-las-tablas)
5. [Relaciones entre Tablas](#relaciones-entre-tablas)

## Información General
**Urban Style** es una tienda de moda urbana dedicada a la venta de ropa y accesorios que reflejan las últimas tendencias del estilo callejero. La base de datos está diseñada para optimizar la gestión de ventas, mejorar la experiencia de compra de los clientes y potenciar la presencia digital de la tienda.

## Configuración de la Base de Datos
- **Motor de Base de Datos**: MySQL
- **Versión**: 8.0 o superior
- **Codificación**: UTF-8
- **Autenticación**: Utilizar credenciales seguras para acceder a la base de datos.

## Variables de Entorno
- **DB_HOST**: localhost
- **DB_USER**: tu_usuario
- **DB_PASSWORD**: tu_contraseña
- **DB_NAME**: urban_style

## Estructura de las Tablas

1. **Usuarios**
   - `id_usuario` (INT, PK)
   - `nombre` (NVARCHAR(100))
   - `correo` (NVARCHAR(100), UNIQUE)
   - `contrasena` (NVARCHAR(255))
   - `direccion` (NVARCHAR(MAX))
   - `telefono` (NVARCHAR(20))
   - `tipo_usuario` (NVARCHAR(20))
   - `fecha_registro` (DATETIME)

2. **Proveedores**
   - `id_proveedor` (INT, PK)
   - `nombre_proveedor` (NVARCHAR(100))
   - `contacto_proveedor` (NVARCHAR(100))
   - `telefono_proveedor` (NVARCHAR(20))
   - `correo_proveedor` (NVARCHAR(100))
   - `direccion_proveedor` (NVARCHAR(MAX))

3. **Productos**
   - `id_producto` (INT, PK)
   - `nombre` (NVARCHAR(100))
   - `descripcion` (NVARCHAR(MAX))
   - `categoria` (NVARCHAR(50))
   - `temporada` (NVARCHAR(20))
   - `id_proveedor` (INT, FK)

4. **Detalle_Producto**
   - `id_detalle_producto` (INT, PK)
   - `id_producto` (INT, FK)
   - `talla` (NVARCHAR(10))
   - `color` (NVARCHAR(50))
   - `precio` (DECIMAL(10,2))
   - `stock` (INT)
   - `fecha_caducidad` (DATE)

5. **Carrito**
   - `id_carrito` (INT, PK)
   - `id_usuario` (INT, FK)
   - `id_detalle_producto` (INT, FK)
   - `cantidad` (INT)
   - `fecha_agregado` (DATETIME)

6. **Pedidos**
   - `id_pedido` (INT, PK)
   - `id_usuario` (INT, FK)
   - `fecha_pedido` (DATETIME)
   - `total` (DECIMAL(10,2))
   - `tipo_pago` (NVARCHAR(20))
   - `tipo_comprobante` (NVARCHAR(20))
   - `direccion_envio` (NVARCHAR(MAX))
   - `estado` (NVARCHAR(20))

7. **Detalle_Pedido**
   - `id_detalle_pedido` (INT, PK)
   - `id_pedido` (INT, FK)
   - `id_detalle_producto` (INT, FK)
   - `cantidad` (INT)
   - `subtotal` (DECIMAL(10,2))

8. **Notificaciones**
   - `id_notificacion` (INT, PK)
   - `id_producto` (INT, FK)
   - `id_usuario` (INT, FK)
   - `mensaje` (NVARCHAR(MAX))
   - `tipo_notificacion` (NVARCHAR(20))
   - `leido` (BIT)
   - `fecha_notificacion` (DATETIME)

9. **Seguimiento_Envios**
   - `id_seguimiento` (INT, PK)
   - `id_pedido` (INT, FK)
   - `estado_envio` (NVARCHAR(20))
   - `fecha_actualizacion` (DATETIME)

10. **Reseñas**
    - `id_resena` (INT, PK)
    - `id_producto` (INT, FK)
    - `id_usuario` (INT, FK)
    - `calificacion` (INT)
    - `comentario` (NVARCHAR(MAX))
    - `fecha_resena` (DATETIME)

11. **Cupones**
    - `id_cupon` (INT, PK)
    - `codigo_cupon` (NVARCHAR(50))
    - `descuento` (DECIMAL(5, 2))
    - `fecha_expiracion` (DATE)
    - `cantidad_max_uso` (INT)
    - `cantidad_usada` (INT)

12. **Chat_Soporte**
    - `id_chat` (INT, PK)
    - `id_usuario` (INT, FK)
    - `mensaje` (NVARCHAR(MAX))
    - `respuesta` (NVARCHAR(MAX))
    - `fecha_mensaje` (DATETIME)
    - `fecha_respuesta` (DATETIME)

13. **Recomendaciones**
    - `id_recomendacion` (INT, PK)
    - `id_usuario` (INT, FK)
    - `id_producto` (INT, FK)
    - `fecha_recomendacion` (DATETIME)

14. **Puntos_Fidelizacion**
    - `id_fidelizacion` (INT, PK)
    - `id_usuario` (INT, FK)
    - `puntos_acumulados` (INT)
    - `fecha_actualizacion` (DATETIME)

15. **Canje_Puntos**
    - `id_canje` (INT, PK)
    - `id_usuario` (INT, FK)
    - `puntos_canjados` (INT)
    - `fecha_canje` (DATETIME)
    - `descripcion` (NVARCHAR(MAX))

## Relaciones entre Tablas
- **Usuarios** (1) --- (N) **Carrito**
- **Usuarios** (1) --- (N) **Pedidos**
- **Usuarios** (1) --- (N) **Notificaciones**
- **Usuarios** (1) --- (N) **Reseñas**
- **Usuarios** (1) --- (N) **Chat_Soporte**
- **Productos** (1) --- (N) **Detalle_Producto**
- **Productos** (1) --- (N) **Detalle_Pedido**
- **Proveedores** (1) --- (N) **Productos**
- **Pedidos** (1) --- (N) **Detalle_Pedido**
- **Pedidos** (1) --- (1) **Seguimiento_Envios**