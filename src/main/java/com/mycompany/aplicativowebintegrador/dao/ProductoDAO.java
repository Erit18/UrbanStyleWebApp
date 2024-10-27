package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ProductoDAO {
    
    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Ropa";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getInt("id_ropa"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBigDecimal("precio"),
                    rs.getString("categoria"),
                    rs.getInt("stock"),
                    rs.getDate("fecha_caducidad"),
                    rs.getBigDecimal("descuento"),
                    rs.getInt("id_proveedor"),
                    rs.getTimestamp("fecha_agregado")
                );
                productos.add(producto);
            }
        }
        return productos;
    }
    
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Ropa (nombre, descripcion, precio, categoria, stock, fecha_caducidad, descuento, id_proveedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setString(4, producto.getCategoria());
            pstmt.setInt(5, producto.getStock());
            
            // Manejo de fecha
            if (producto.getFechaCaducidad() != null) {
                pstmt.setDate(6, new java.sql.Date(producto.getFechaCaducidad().getTime()));
            } else {
                pstmt.setNull(6, Types.DATE);
            }
            
            pstmt.setBigDecimal(7, producto.getDescuento());
            pstmt.setInt(8, producto.getIdProveedor());
            
            pstmt.executeUpdate();
        }
    }
    
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE Ropa SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, stock = ?, fecha_caducidad = ?, descuento = ?, id_proveedor = ? WHERE id_ropa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setString(4, producto.getCategoria());
            pstmt.setInt(5, producto.getStock());
            pstmt.setDate(6, new java.sql.Date(producto.getFechaCaducidad().getTime()));
            pstmt.setBigDecimal(7, producto.getDescuento());
            pstmt.setInt(8, producto.getIdProveedor());
            pstmt.setInt(9, producto.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Ropa WHERE id_ropa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public Producto obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Ropa WHERE id_ropa = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                        rs.getInt("id_ropa"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock"),
                        rs.getDate("fecha_caducidad"),
                        rs.getBigDecimal("descuento"),
                        rs.getInt("id_proveedor"),
                        rs.getTimestamp("fecha_agregado")
                    );
                }
            }
        }
        return null; // Retorna null si no se encuentra el producto
    }
}
