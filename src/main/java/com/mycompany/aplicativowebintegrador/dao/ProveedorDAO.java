package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO implements IProveedorDAO {
    
    @Override
    public List<Proveedor> obtenerTodos() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";
        
        System.out.println("DEBUG - ProveedorDAO.obtenerTodos() - Iniciando consulta");
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("DEBUG - ProveedorDAO.obtenerTodos() - Conexión establecida");
            
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                    rs.getInt("id_proveedor"),
                    rs.getString("nombre"),
                    rs.getString("contacto"),
                    rs.getString("telefono"),
                    rs.getString("email"),
                    rs.getString("direccion")
                );
                System.out.println("DEBUG - Proveedor cargado -> ID: " + proveedor.getId_proveedor() + 
                                 ", Nombre: " + proveedor.getNombre());
                proveedores.add(proveedor);
            }
            
            System.out.println("DEBUG - Total proveedores cargados: " + proveedores.size());
        } catch (SQLException e) {
            System.err.println("ERROR - ProveedorDAO.obtenerTodos(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return proveedores;
    }
    
    @Override
    public Proveedor obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Proveedores WHERE id_proveedor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Proveedor(
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre"),
                        rs.getString("contacto"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                    );
                }
            }
        }
        return null;
    }
    
    @Override
    public void insertar(Proveedor proveedor) throws SQLException {
        String sql = "INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getEmail());
            pstmt.setString(5, proveedor.getDireccion());
            
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public void actualizar(Proveedor proveedor) throws SQLException {
        String sql = "UPDATE Proveedores SET nombre = ?, contacto = ?, telefono = ?, email = ?, direccion = ? WHERE id_proveedor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, proveedor.getNombre());
            pstmt.setString(2, proveedor.getContacto());
            pstmt.setString(3, proveedor.getTelefono());
            pstmt.setString(4, proveedor.getEmail());
            pstmt.setString(5, proveedor.getDireccion());
            pstmt.setInt(6, proveedor.getId_proveedor());
            
            pstmt.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Proveedores WHERE id_proveedor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
