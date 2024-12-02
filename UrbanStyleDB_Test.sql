-- Crear base de datos de prueba
CREATE DATABASE IF NOT EXISTS UrbanStyleDB_Test;
USE UrbanStyleDB_Test;

-- Recrear las tablas necesarias para pruebas
DROP TABLE IF EXISTS Alertas;
DROP TABLE IF EXISTS Pagos;
DROP TABLE IF EXISTS Detalle_Pedido;
DROP TABLE IF EXISTS Pedidos;
DROP TABLE IF EXISTS Carrito;
DROP TABLE IF EXISTS Ropa;
DROP TABLE IF EXISTS Proveedores;
DROP TABLE IF EXISTS Usuarios;

-- Crear las tablas necesarias (versión simplificada para pruebas)
CREATE TABLE IF NOT EXISTS Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT
);

CREATE TABLE IF NOT EXISTS Ropa (
    id_ropa INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    categoria VARCHAR(50),
    tipo_producto VARCHAR(50),
    stock INT NOT NULL DEFAULT 0,
    fecha_caducidad DATE,
    descuento DECIMAL(4,2),
    id_proveedor INT,
    fecha_agregado TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE IF NOT EXISTS Alertas (
    id_alerta INT AUTO_INCREMENT PRIMARY KEY,
    id_ropa INT,
    mensaje TEXT,
    fecha_alerta TIMESTAMP,
    tipo_alerta VARCHAR(50),
    estado VARCHAR(20),
    umbral INT,
    FOREIGN KEY (id_ropa) REFERENCES Ropa(id_ropa)
);

CREATE TABLE IF NOT EXISTS Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Datos de prueba
INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) VALUES 
('Proveedor Test 1', 'Contacto Test 1', '999888777', 'test1@test.com', 'Dirección Test 1'),
('Proveedor Test 2', 'Contacto Test 2', '999888666', 'test2@test.com', 'Dirección Test 2');