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
CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    contacto VARCHAR(100),
    telefono VARCHAR(20),
    email VARCHAR(100),
    direccion TEXT
);

-- Datos de prueba
INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) VALUES 
('Proveedor Test 1', 'Contacto Test 1', '999888777', 'test1@test.com', 'Dirección Test 1'),
('Proveedor Test 2', 'Contacto Test 2', '999888666', 'test2@test.com', 'Dirección Test 2');