-- Creación de la base de datos
CREATE DATABASE UrbanStyleDB;
USE UrbanStyleDB;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    contraseña VARCHAR(255),
    rol ENUM('cliente', 'administrador'),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Proveedores
CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Ropa
CREATE TABLE Ropa (
    id_ropa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    precio DECIMAL(10, 2),
    categoria ENUM('hombre', 'mujer', 'unisex'),
    stock INT,
    fecha_caducidad DATE,
    descuento DECIMAL(5, 2) DEFAULT 0,
    id_proveedor INT,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

-- Tabla Pedidos
CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    total DECIMAL(10, 2),
    estado ENUM('pendiente', 'pagado', 'enviado', 'completado', 'cancelado'),
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabla Detalle_Pedido
CREATE TABLE Detalle_Pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    id_ropa INT,
    cantidad INT,
    precio_unitario DECIMAL(10, 2),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);

-- Tabla Carrito
CREATE TABLE Carrito (
    id_carrito INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_ropa INT,
    cantidad INT,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);

-- Tabla Pagos
CREATE TABLE Pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    metodo_pago ENUM('tarjeta_credito', 'tarjeta_debito', 'paypal', 'transferencia_bancaria'),
    estado_pago ENUM('pendiente', 'completado', 'fallido'),
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido)
);

-- Tabla Alertas
CREATE TABLE Alertas (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    id_ropa INT,
    mensaje TEXT,
    fecha_alerta DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);


-- Primero, limpiamos las tablas existentes
DELETE FROM Alertas;
DELETE FROM Ropa;
DELETE FROM Proveedores;

-- Reiniciamos los auto_increment
ALTER TABLE Alertas AUTO_INCREMENT = 1;
ALTER TABLE Ropa AUTO_INCREMENT = 1;
ALTER TABLE Proveedores AUTO_INCREMENT = 1;

-- 1. Insertar proveedor
INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) 
VALUES ('Proveedor Test', 'Juan Pérez', '123456789', 'juan@test.com', 'Calle Test 123');

-- 2. Insertar productos (ahora empezarán desde id_ropa = 1)
INSERT INTO Ropa (nombre, descripcion, precio, categoria, stock, id_proveedor) 
VALUES 
('Camiseta Básica', 'Camiseta de algodón', 29.99, 'unisex', 5, 1),
('Jeans Clásicos', 'Jeans azules', 59.99, 'unisex', 3, 1),
('Vestido Verano', 'Vestido ligero', 45.99, 'mujer', 2, 1);

-- 3. Insertar alertas (usando los nuevos IDs correctos)
INSERT INTO Alertas (id_ropa, mensaje) 
VALUES 
(1, 'Stock bajo: Quedan solo 5 unidades de Camiseta Básica'),
(2, 'Stock crítico: Quedan solo 3 unidades de Jeans Clásicos'),
(3, 'Stock crítico: Quedan solo 2 unidades de Vestido Verano');
