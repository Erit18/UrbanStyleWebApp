-- Creación de la base de datosssssssssssaaaaaaa
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
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa),
    tipo_alerta ENUM('stock_bajo', 'caducidad_proxima', 'manual') NOT NULL DEFAULT 'manual',
    estado ENUM('activa', 'resuelta') NOT NULL DEFAULT 'activa',
    umbral INT DEFAULT NULL
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

-- Insertar Ropa con categorías variadas y fechas de caducidad
INSERT INTO Ropa (nombre, descripcion, precio, categoria, stock, fecha_caducidad, id_proveedor) VALUES 
-- Poleras/Hoodies
('Urban Hoodie Oversize', 'Polera oversize de algodón premium con diseño urbano exclusivo', 159.90, 'hombre', 45, '2025-06-15', 1),
('Hoodie Graffiti Lima', 'Polera con diseño exclusivo de artista urbano limeño', 149.90, 'unisex', 38, '2025-07-20', 1),
('Pullover Crop Urban', 'Polera corta sin capucha, perfect fit', 129.90, 'mujer', 50, '2025-05-30', 2),
('Hoodie Retro 90s', 'Polera estilo retro con diseño de los 90s', 139.90, 'hombre', 30, '2025-08-10', 2),

-- Pantalones
('Cargo Pants Urban', 'Pantalón cargo con bolsillos laterales', 179.90, 'hombre', 40, '2025-09-25', 1),
('Jogger Premium Black', 'Jogger negro con acabado premium', 149.90, 'unisex', 35, '2025-07-15', 2),
('Mom Jeans Vintage', 'Jean mom fit de cintura alta', 189.90, 'mujer', 42, '2025-06-30', 3),
('Pants Drill Skater', 'Pantalón drill para skaters', 159.90, 'hombre', 28, '2025-08-20', 3),

-- Polos y Tops
('Polo Oversize Basic', 'Polo básico oversize de algodón pima', 69.90, 'hombre', 60, '2025-05-15', 1),
('Crop Top Urban Art', 'Top corto con arte urbano de artistas locales', 79.90, 'mujer', 45, '2025-07-10', 2),
('Tank Top Street Style', 'Polo sin mangas con diseño urbano', 74.90, 'mujer', 55, '2025-06-20', 3),
('Polo Skate Life', 'Polo con diseño de cultura skater', 69.90, 'unisex', 50, '2025-08-30', 1),

-- Más variedad
('Falda Cargo Street', 'Falda cargo con bolsillos', 129.90, 'mujer', 35, '2025-09-15', 2),
('Shorts Basketball Pro', 'Shorts estilo basketball', 99.90, 'hombre', 40, '2025-07-25', 1),
('Crop Hoodie Fashion', 'Polera corta con capucha', 139.90, 'mujer', 30, '2025-08-15', 3),
('Baggy Jeans Classic', 'Jeans holgados estilo urbano', 169.90, 'unisex', 45, '2025-06-25', 2);


-- Insertar pedidos de prueba usando los usuarios existentes
INSERT INTO Pedidos (id_usuario, total, estado) VALUES 
(33, 458.70, 'completado'),  -- José Martínez
(34, 269.80, 'pagado');      -- Ana López

-- Insertar detalles de pedido
INSERT INTO Detalle_Pedido (id_pedido, id_ropa, cantidad, precio_unitario) VALUES 
(1, 1, 2, 159.90),  -- 2 Urban Hoodie Oversize para José Martínez
(1, 5, 1, 179.90),  -- 1 Cargo Pants Urban para José Martínez
(2, 7, 1, 189.90),  -- 1 Mom Jeans Vintage para Ana López
(2, 10, 1, 79.90);  -- 1 Crop Top Urban Art para Ana López


-- Consulta para obtener el reporte de ventas
SELECT 
    CONCAT('VTA-', LPAD(p.id_pedido, 3, '0')) as id_venta,
    DATE_FORMAT(p.fecha_pedido, '%d/%m/%Y') as fecha,
    u.nombre as cliente,
    GROUP_CONCAT(
        CONCAT(r.nombre, 
        CASE 
            WHEN dp.cantidad > 1 THEN CONCAT(' x', dp.cantidad)
            ELSE ''
        END)
        SEPARATOR ', '
    ) as productos,
    p.total,
    p.estado
FROM 
    Pedidos p
    INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario
    INNER JOIN Detalle_Pedido dp ON p.id_pedido = dp.id_pedido
    INNER JOIN Ropa r ON dp.id_ropa = r.id_ropa
WHERE 
    p.estado IN ('pagado', 'completado')
GROUP BY 
    p.id_pedido, p.fecha_pedido, u.nombre, p.total, p.estado
ORDER BY 
    p.fecha_pedido DESC;


-- Consulta para ver las fechas actuales
SELECT id_pedido, fecha_pedido FROM Pedidos;

-- Si necesitas actualizar las fechas:
UPDATE Pedidos SET fecha_pedido = '2024-11-13' WHERE id_pedido = 1;
UPDATE Pedidos SET fecha_pedido = '2024-11-14' WHERE id_pedido = 2;


DELIMITER //

CREATE PROCEDURE GenerarAlertasAutomaticas()
BEGIN
    -- Primero, actualizar el estado de las alertas existentes
    UPDATE Alertas a
    INNER JOIN Ropa r ON a.id_ropa = r.id_ropa
    SET a.estado = 'resuelta'
    WHERE a.estado = 'activa' 
    AND (
        (a.tipo_alerta = 'stock_bajo' AND r.stock > 30) OR
        (a.tipo_alerta = 'caducidad_proxima' AND DATEDIFF(r.fecha_caducidad, CURDATE()) > 30)
    );

    -- Luego, generar nuevas alertas por stock bajo
    INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral)
    SELECT 
        id_ropa,
        CONCAT('Stock bajo: ', nombre, ' (', stock, ' unidades)'),
        'stock_bajo',
        'activa',
        30
    FROM Ropa
    WHERE stock <= 30
    AND id_ropa NOT IN (
        SELECT id_ropa 
        FROM Alertas 
        WHERE tipo_alerta = 'stock_bajo' 
        AND estado = 'activa'
    );

    -- Generar alertas por caducidad próxima
    INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral)
    SELECT 
        id_ropa,
        CONCAT('¡Producto próximo a caducar! ', nombre, ' caduca el ', DATE_FORMAT(fecha_caducidad, '%d/%m/%Y')),
        'caducidad_proxima',
        'activa',
        30
    FROM Ropa
    WHERE DATEDIFF(fecha_caducidad, CURDATE()) <= 30
    AND id_ropa NOT IN (
        SELECT id_ropa 
        FROM Alertas 
        WHERE tipo_alerta = 'caducidad_proxima' 
        AND estado = 'activa'
    );
END //

DELIMITER ;

-- 4. Crear el nuevo trigger
DELIMITER //

CREATE TRIGGER after_ropa_update 
AFTER UPDATE ON Ropa
FOR EACH ROW
BEGIN
    IF NEW.stock != OLD.stock OR NEW.fecha_caducidad != OLD.fecha_caducidad THEN
        CALL GenerarAlertasAutomaticas();
    END IF;
END //

DELIMITER ;