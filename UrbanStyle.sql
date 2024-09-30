-- Crear la base de datos
CREATE DATABASE UrbanStyle;

-- Usar la base de datos
USE UrbanStyle;

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    direccion TEXT,
    telefono VARCHAR(20),
    tipo_usuario ENUM('cliente', 'empleado', 'administrador') NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Proveedores
CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proveedor VARCHAR(100) NOT NULL,
    contacto_proveedor VARCHAR(100),
    telefono_proveedor VARCHAR(20),
    correo_proveedor VARCHAR(100),
    direccion_proveedor TEXT
);

-- Tabla de Productos
CREATE TABLE Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT NOT NULL,
    categoria VARCHAR(50),
    temporada ENUM('primavera', 'verano', 'otoño', 'invierno'),
    id_proveedor INT,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

-- Tabla de Detalle de Producto (Con fecha de caducidad)
CREATE TABLE Detalle_Producto (
    id_detalle_producto INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    talla ENUM('XS', 'S', 'M', 'L', 'XL', 'XXL'),
    color VARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    fecha_caducidad DATE,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Tabla de Carrito de Compras
CREATE TABLE Carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_detalle_producto INT NOT NULL,
    cantidad INT NOT NULL,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_detalle_producto) REFERENCES Detalle_Producto(id_detalle_producto)
);

-- Tabla de Pedidos
CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) NOT NULL,
    tipo_pago ENUM('tarjeta', 'yape') NOT NULL,
    tipo_comprobante ENUM('boleta', 'factura') NOT NULL,
    direccion_envio TEXT,
    estado ENUM('pendiente', 'en proceso', 'enviado', 'entregado') DEFAULT 'pendiente',
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla de Detalle de Pedidos
CREATE TABLE Detalle_Pedido (
    id_detalle_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_detalle_producto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_detalle_producto) REFERENCES Detalle_Producto(id_detalle_producto)
);

-- Tabla de Notificaciones (con alertas por caducidad)
CREATE TABLE Notificaciones (
    id_notificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    id_usuario INT NOT NULL,
    mensaje TEXT NOT NULL,
    tipo_notificacion ENUM('pedido', 'alerta_stock', 'alerta_caducidad') NOT NULL,
    leido BOOLEAN DEFAULT FALSE,
    fecha_notificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Tabla de Seguimiento de Envíos
CREATE TABLE Seguimiento_Envios (
    id_seguimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    estado_envio ENUM('en preparación', 'en tránsito', 'entregado') DEFAULT 'en preparación',
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);

-- Tabla de Reseñas
CREATE TABLE Reseñas (
    id_resena INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    id_usuario INT NOT NULL,
    calificacion INT CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT,
    fecha_resena TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla de Cupones
CREATE TABLE Cupones (
    id_cupon INT AUTO_INCREMENT PRIMARY KEY,
    codigo_cupon VARCHAR(50) NOT NULL UNIQUE,
    descuento DECIMAL(5, 2) CHECK (descuento > 0),
    fecha_expiracion DATE NOT NULL,
    cantidad_max_uso INT NOT NULL,
    cantidad_usada INT DEFAULT 0
);

-- Tabla de Cupones Aplicados en Pedidos
CREATE TABLE Pedido_Cupon (
    id_pedido INT NOT NULL,
    id_cupon INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_cupon) REFERENCES Cupones(id_cupon)
);

-- Tabla de Chat de Soporte
CREATE TABLE Chat_Soporte (
    id_chat INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    mensaje TEXT NOT NULL,
    respuesta TEXT,
    fecha_mensaje TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_respuesta TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla de Recomendaciones
CREATE TABLE Recomendaciones (
    id_recomendacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_producto INT NOT NULL,
    fecha_recomendacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Tabla de Fidelización (Acumulación de Puntos)
CREATE TABLE Puntos_Fidelizacion (
    id_fidelizacion INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    puntos_acumulados INT DEFAULT 0,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla de Canje de Puntos
CREATE TABLE Canje_Puntos (
    id_canje INT AUTO_INCREMENT PRIMARY KEY,
    id_fidelizacion INT NOT NULL,
    puntos_canjeados INT NOT NULL,
    fecha_canje TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_fidelizacion) REFERENCES Puntos_Fidelizacion(id_fidelizacion)
);