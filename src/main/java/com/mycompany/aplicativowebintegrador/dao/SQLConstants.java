package com.mycompany.aplicativowebintegrador.dao;

public final class SQLConstants {
    // Queries Usuario
    public static final String SELECT_USUARIO_BY_EMAIL = "SELECT * FROM Usuarios WHERE email = ?";
    public static final String INSERT_USUARIO = "INSERT INTO Usuarios (nombre, email, contraseña, rol) VALUES (?, ?, ?, ?)";
    
    // Queries Producto
    public static final String SELECT_ALL_PRODUCTOS = "SELECT * FROM Ropa ORDER BY fecha_agregado ASC";
    public static final String SELECT_PRODUCTO_BY_ID = "SELECT * FROM Ropa WHERE id_ropa = ?";
    
    // Queries Proveedor
    public static final String SELECT_ALL_PROVEEDORES = "SELECT * FROM Proveedores";
    public static final String SELECT_PROVEEDOR_BY_ID = "SELECT * FROM Proveedores WHERE id_proveedor = ?";
    
    private SQLConstants() {
        // Constructor privado para evitar instanciación
    }
} 