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
```sql
CREATE TABLE Usuarios (
   id_usuario INT IDENTITY(1,1) PRIMARY KEY,
   nombre NVARCHAR(100) NOT NULL,
   correo NVARCHAR(100) UNIQUE NOT NULL,
   contrasena NVARCHAR(255) NOT NULL,
   direccion NVARCHAR(MAX),
   telefono NVARCHAR(20),
   tipo_usuario NVARCHAR(20) CHECK (tipo_usuario IN ('cliente', 'empleado', 'administrador')),
   fecha_registro DATETIME DEFAULT GETDATE()
   );
   ```
   

2. **Proveedores**
```sql
CREATE TABLE Proveedores (
    id_proveedor INT IDENTITY(1,1) PRIMARY KEY,
    nombre_proveedor NVARCHAR(100) NOT NULL,
    contacto_proveedor NVARCHAR(100),
    telefono_proveedor NVARCHAR(20),
    correo_proveedor NVARCHAR(100),
    direccion_proveedor NVARCHAR(MAX)
);
   ```

3. **Productos**
```sql
CREATE TABLE Productos (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100) NOT NULL,
    descripcion NVARCHAR(MAX) NOT NULL,
    categoria NVARCHAR(50),
    temporada NVARCHAR(20) CHECK (temporada IN ('primavera', 'verano', 'otoño', 'invierno')),
    id_proveedor INT,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);
   ```

4. **Detalle_Producto**
```sql
CREATE TABLE Detalle_Producto (
    id_detalle_producto INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT NOT NULL,
    talla NVARCHAR(10) CHECK (talla IN ('XS', 'S', 'M', 'L', 'XL', 'XXL')),
    color NVARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    fecha_caducidad DATE,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);
   ```

5. **Carrito de Compras**
```sql
CREATE TABLE Carrito (
    id_carrito INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_detalle_producto INT NOT NULL,
    cantidad INT NOT NULL,
    fecha_agregado DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_detalle_producto) REFERENCES Detalle_Producto(id_detalle_producto)
);
   ```

6. **Pedidos**
```sql
CREATE TABLE Pedidos (
    id_pedido INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_pedido DATETIME DEFAULT GETDATE(),
    total DECIMAL(10, 2) NOT NULL,
    tipo_pago NVARCHAR(20) CHECK (tipo_pago IN ('tarjeta', 'yape')) NOT NULL,
    tipo_comprobante NVARCHAR(20) CHECK (tipo_comprobante IN ('boleta', 'factura')) NOT NULL,
    direccion_envio NVARCHAR(MAX),
    estado NVARCHAR(20) DEFAULT 'pendiente' CHECK (estado IN ('pendiente', 'en proceso', 'enviado', 'entregado')),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);
   ```

7. **Detalle de Pedidos**
```sql
CREATE TABLE Detalle_Pedido (
    id_detalle_pedido INT IDENTITY(1,1) PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_detalle_producto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_detalle_producto) REFERENCES Detalle_Producto(id_detalle_producto)
);
   ```

8. **Notificaciones**
```sql
CREATE TABLE Notificaciones (
    id_notificacion INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT,
    id_usuario INT NOT NULL,
    mensaje NVARCHAR(MAX) NOT NULL,
    tipo_notificacion NVARCHAR(20) CHECK (tipo_notificacion IN ('pedido', 'alerta_stock', 'alerta_caducidad')) NOT NULL,
    leido BIT DEFAULT 0,
    fecha_notificacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);
   ```

9. **Seguimiento_Envios**
```sql
CREATE TABLE Seguimiento_Envios (
    id_seguimiento INT IDENTITY(1,1) PRIMARY KEY,
    id_pedido INT NOT NULL,
    estado_envio NVARCHAR(20) DEFAULT 'en preparación' CHECK (estado_envio IN ('en preparación', 'en tránsito', 'entregado')),
    fecha_actualizacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);
   ```

10. **Reseñas**
```sql
CREATE TABLE Reseñas (
    id_resena INT IDENTITY(1,1) PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    comentario NVARCHAR(MAX),
    fecha_resena DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);
   ```

11. **Cupones**
```sql
CREATE TABLE Cupones (
    id_cupon INT IDENTITY(1,1) PRIMARY KEY,
    codigo_cupon NVARCHAR(50) NOT NULL UNIQUE,
    descuento DECIMAL(5, 2) CHECK (descuento > 0),
    fecha_expiracion DATE NOT NULL,
    cantidad_max_uso INT NOT NULL,
    cantidad_usada INT DEFAULT 0
);
   ```

12. **Cupones Aplicados en Pedidos**
```sql
CREATE TABLE Pedido_Cupon (
    id_pedido INT NOT NULL,
    id_cupon INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_cupon) REFERENCES Cupones(id_cupon)
);
   ```

13. **Chat_Soporte**
```sql
CREATE TABLE Chat_Soporte (
    id_chat INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    mensaje NVARCHAR(MAX) NOT NULL,
    respuesta NVARCHAR(MAX),
    fecha_mensaje DATETIME DEFAULT GETDATE(),
    fecha_respuesta DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);
   ```

14. **Recomendaciones**
```sql
CREATE TABLE Recomendaciones (
    id_recomendacion INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_producto INT NOT NULL,
    fecha_recomendacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);
   ```

15. **Puntos_Fidelizacion**
```sql
CREATE TABLE Puntos_Fidelizacion (
    id_fidelizacion INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    puntos_acumulados INT DEFAULT 0,
    fecha_actualizacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);
   ```

16. **Canje_Puntos**
```sql
CREATE TABLE Canje_Puntos (
    id_canje INT IDENTITY(1,1) PRIMARY KEY,
    id_fidelizacion INT NOT NULL,
    puntos_canjeados INT NOT NULL,
    fecha_canje DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_fidelizacion) REFERENCES Puntos_Fidelizacion(id_fidelizacion)
);
   ```

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