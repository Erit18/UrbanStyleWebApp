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


-- Desactivar temporalmente el modo seguro
SET SQL_SAFE_UPDATES = 0;

-- Limpiamos las tablas en orden para evitar problemas con las llaves foráneas
DELETE FROM Alertas;
DELETE FROM Pagos;
DELETE FROM Detalle_Pedido;
DELETE FROM Pedidos;
DELETE FROM Carrito;
DELETE FROM Ropa;
DELETE FROM Proveedores;

-- Reiniciamos los auto_increment
ALTER TABLE Alertas AUTO_INCREMENT = 1;
ALTER TABLE Pagos AUTO_INCREMENT = 1;
ALTER TABLE Detalle_Pedido AUTO_INCREMENT = 1;
ALTER TABLE Pedidos AUTO_INCREMENT = 1;
ALTER TABLE Carrito AUTO_INCREMENT = 1;
ALTER TABLE Ropa AUTO_INCREMENT = 1;
ALTER TABLE Proveedores AUTO_INCREMENT = 1;

-- Volver a activar el modo seguro
SET SQL_SAFE_UPDATES = 1;

-- Insertar Proveedores reales
INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) VALUES 
('Gamarra Fashion SAC', 'Roberto Gómez', '951234567', 'rgomez@gamarrafashion.pe', 'Jr. Gamarra 1253, La Victoria, Lima'),
('Urban Textiles Perú', 'Carmen Paredes', '962345678', 'cparedes@urbantextiles.pe', 'Jr. Antonio Bazo 789, La Victoria, Lima'),
('Lima Street Wholesale', 'Miguel Chang', '973456789', 'mchang@limastreet.pe', 'Av. Grau 423, La Victoria, Lima');

-- Insertar Ropa con categorías variadas
INSERT INTO Ropa (nombre, descripcion, precio, categoria, stock, id_proveedor) VALUES 
-- Poleras/Hoodies
('Urban Hoodie Oversize', 'Polera oversize de algodón premium con diseño urbano exclusivo', 159.90, 'hombre', 45, 1),
('Hoodie Graffiti Lima', 'Polera con diseño exclusivo de artista urbano limeño', 149.90, 'unisex', 38, 1),
('Pullover Crop Urban', 'Polera corta sin capucha, perfect fit', 129.90, 'mujer', 50, 2),
('Hoodie Retro 90s', 'Polera estilo retro con diseño de los 90s', 139.90, 'hombre', 30, 2),

-- Pantalones
('Cargo Pants Urban', 'Pantalón cargo con bolsillos laterales', 179.90, 'hombre', 40, 1),
('Jogger Premium Black', 'Jogger negro con acabado premium', 149.90, 'unisex', 35, 2),
('Mom Jeans Vintage', 'Jean mom fit de cintura alta', 189.90, 'mujer', 42, 3),
('Pants Drill Skater', 'Pantalón drill para skaters', 159.90, 'hombre', 28, 3),

-- Polos y Tops
('Polo Oversize Basic', 'Polo básico oversize de algodón pima', 69.90, 'hombre', 60, 1),
('Crop Top Urban Art', 'Top corto con arte urbano de artistas locales', 79.90, 'mujer', 45, 2),
('Tank Top Street Style', 'Polo sin mangas con diseño urbano', 74.90, 'mujer', 55, 3),
('Polo Skate Life', 'Polo con diseño de cultura skater', 69.90, 'unisex', 50, 1),

-- Más variedad
('Falda Cargo Street', 'Falda cargo con bolsillos', 129.90, 'mujer', 35, 2),
('Shorts Basketball Pro', 'Shorts estilo basketball', 99.90, 'hombre', 40, 1),
('Crop Hoodie Fashion', 'Polera corta con capucha', 139.90, 'mujer', 30, 3),
('Baggy Jeans Classic', 'Jeans holgados estilo urbano', 169.90, 'unisex', 45, 2);


-- Insertar Usuarios iniciales
INSERT INTO Usuarios (nombre, email, contraseña, rol) VALUES
('Admin Sistema', 'admin@urbanstyle.pe', 'admin123', 'administrador'),
('José Martínez', 'jose@gmail.com', 'cliente123', 'cliente'),
('Ana López', 'ana@gmail.com', 'cliente123', 'cliente');

-- Insertar Pedidos de ejemplo
INSERT INTO Pedidos (id_usuario, total, estado) VALUES
(2, 389.70, 'completado'),
(3, 259.80, 'pagado');

-- Insertar Detalles de Pedidos
INSERT INTO Detalle_Pedido (id_pedido, id_ropa, cantidad, precio_unitario) VALUES
(1, 1, 2, 159.90),
(1, 5, 1, 69.90),
(2, 2, 1, 149.90),
(2, 6, 1, 109.90);

-- Insertar Alertas para productos con stock bajo
-- Insertar Alertas para productos con stock bajo
INSERT INTO Alertas (id_ropa, mensaje)
SELECT id_ropa, CONCAT('Stock bajo: Quedan solo ', stock, ' unidades de ', nombre)
FROM Ropa
WHERE stock <= 30;

