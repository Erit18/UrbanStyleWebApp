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
        String sql = "SELECT * FROM Ropa ORDER BY fecha_agregado ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("DEBUG - ProductoDAO: Iniciando obtenerTodos()");
            
            while (rs.next()) {
                Producto producto = new Producto();
                int id = rs.getInt("id_ropa");
                producto.setId_ropa(id);
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                producto.setCategoria(rs.getString("categoria"));
                producto.setStock(rs.getInt("stock"));
                producto.setFecha_caducidad(rs.getDate("fecha_caducidad"));
                producto.setDescuento(rs.getBigDecimal("descuento"));
                producto.setId_proveedor(rs.getInt("id_proveedor"));
                producto.setFecha_agregado(rs.getTimestamp("fecha_agregado"));
                
                System.out.println("DEBUG - ProductoDAO: Producto cargado -> ID: " + id + 
                                 ", Nombre: " + producto.getNombre() + 
                                 ", getId_ropa(): " + producto.getId_ropa());
                
                productos.add(producto);
            }
            
            System.out.println("DEBUG - ProductoDAO: Total productos cargados: " + productos.size());
            return productos;
        }
    }
    
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Ropa (nombre, descripcion, precio, categoria, stock, fecha_caducidad, descuento, id_proveedor, fecha_agregado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setBigDecimal(3, producto.getPrecio());
            pstmt.setString(4, producto.getCategoria());
            pstmt.setInt(5, producto.getStock());
            
            if (producto.getFecha_caducidad() != null) {
                pstmt.setDate(6, new java.sql.Date(producto.getFecha_caducidad().getTime()));
            } else {
                pstmt.setNull(6, Types.DATE);
            }
            
            pstmt.setBigDecimal(7, producto.getDescuento());
            pstmt.setInt(8, producto.getId_proveedor());
            
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
            pstmt.setDate(6, new java.sql.Date(producto.getFecha_caducidad().getTime()));
            pstmt.setBigDecimal(7, producto.getDescuento());
            pstmt.setInt(8, producto.getId_proveedor());
            pstmt.setInt(9, producto.getId_ropa());
            
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
