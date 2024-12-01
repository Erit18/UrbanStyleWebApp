package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ProductoDAO extends BaseDAO implements IProductoDAO {
    
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId_ropa(rs.getInt("id_ropa"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getBigDecimal("precio"));
        producto.setCategoria(rs.getString("categoria"));
        producto.setTipo_producto(rs.getString("tipo_producto"));
        producto.setStock(rs.getInt("stock"));
        producto.setFecha_caducidad(rs.getDate("fecha_caducidad"));
        producto.setDescuento(rs.getBigDecimal("descuento"));
        producto.setId_proveedor(rs.getInt("id_proveedor"));
        producto.setFecha_agregado(rs.getTimestamp("fecha_agregado"));
        return producto;
    }
    
    @Override
    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Ropa ORDER BY id_ropa");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            
            return productos;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public Producto obtenerPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Ropa WHERE id_ropa = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearProducto(rs);
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Ropa (nombre, descripcion, precio, categoria, tipo_producto, stock, fecha_caducidad, descuento, id_proveedor) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria());
            stmt.setString(5, producto.getTipo_producto());
            stmt.setInt(6, producto.getStock());
            stmt.setDate(7, producto.getFecha_caducidad() != null ? new java.sql.Date(producto.getFecha_caducidad().getTime()) : null);
            stmt.setBigDecimal(8, producto.getDescuento());
            stmt.setInt(9, producto.getId_proveedor());
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE Ropa SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, " +
                    "tipo_producto = ?, stock = ?, fecha_caducidad = ?, descuento = ?, id_proveedor = ? " +
                    "WHERE id_ropa = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setBigDecimal(3, producto.getPrecio());
            stmt.setString(4, producto.getCategoria());
            stmt.setString(5, producto.getTipo_producto());
            stmt.setInt(6, producto.getStock());
            stmt.setDate(7, producto.getFecha_caducidad() != null ? new java.sql.Date(producto.getFecha_caducidad().getTime()) : null);
            stmt.setBigDecimal(8, producto.getDescuento());
            stmt.setInt(9, producto.getId_proveedor());
            stmt.setInt(10, producto.getId_ropa());
            
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Iniciamos transacción
            
            // Primero eliminamos las alertas asociadas
            String deleteAlertas = "DELETE FROM alertas WHERE id_ropa = ?";
            try (PreparedStatement pstmtAlertas = conn.prepareStatement(deleteAlertas)) {
                pstmtAlertas.setInt(1, id);
                pstmtAlertas.executeUpdate();
            }
            
            // Luego eliminamos el producto
            String deleteProducto = "DELETE FROM ropa WHERE id_ropa = ?";
            try (PreparedStatement pstmtProducto = conn.prepareStatement(deleteProducto)) {
                pstmtProducto.setInt(1, id);
                pstmtProducto.executeUpdate();
            }
            
            conn.commit(); // Confirmamos la transacción
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Si hay error, revertimos los cambios
                } catch (SQLException ex) {
                    throw new SQLException("Error al hacer rollback: " + ex.getMessage());
                }
            }
            throw new SQLException("Error al eliminar el producto: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restauramos autoCommit
                    conn.close();
                } catch (SQLException e) {
                    // Log error
                }
            }
        }
    }

    @Override
    public List<Producto> obtenerProductosDestacados(int limite) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Ropa ORDER BY RAND() LIMIT ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limite);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            
            return productos;
        }
    }

    @Override
    public String obtenerRutaImagen(Producto producto) {
        String baseImagePath = "views/Intranet/imagenes/productos/";
        String defaultImage = "views/Intranet/imagenes/default-product.jpg";
        
        if (producto == null) {
            return defaultImage;
        }
        
        // Array de extensiones posibles
        String[] extensiones = {".jpg", ".JPG", ".jpeg", ".JPEG"};
        
        // Devolver la primera ruta con extensión .jpg (la más común)
        return baseImagePath + producto.getId_ropa() + extensiones[0];
    }

    @Override
    public List<Producto> obtenerProductosPorTipoYCategoria(String tipo, String categoria) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Ropa WHERE tipo_producto = ? AND categoria = ? ORDER BY fecha_agregado DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipo);
            stmt.setString(2, categoria);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }
            
            return productos;
        }
    }
}
