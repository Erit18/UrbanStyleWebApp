package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO extends BaseDAO implements IProveedorDAO {
    
    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setId_proveedor(rs.getInt("id_proveedor"));
        proveedor.setNombre(rs.getString("nombre"));
        proveedor.setContacto(rs.getString("contacto"));
        proveedor.setTelefono(rs.getString("telefono"));
        proveedor.setEmail(rs.getString("email"));
        proveedor.setDireccion(rs.getString("direccion"));
        return proveedor;
    }
    
    @Override
    public List<Proveedor> obtenerTodos() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Proveedores ORDER BY nombre");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                proveedores.add(mapearProveedor(rs));
            }
            
            return proveedores;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public Proveedor obtenerPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Proveedores WHERE id_proveedor = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearProveedor(rs);
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public void insertar(Proveedor proveedor) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "INSERT INTO Proveedores (nombre, contacto, telefono, email, direccion) " +
                "VALUES (?, ?, ?, ?, ?)"
            );
            
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getContacto());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.setString(5, proveedor.getDireccion());
            
            stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    @Override
    public void actualizar(Proveedor proveedor) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "UPDATE Proveedores SET nombre = ?, contacto = ?, telefono = ?, " +
                "email = ?, direccion = ? WHERE id_proveedor = ?"
            );
            
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getContacto());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.setString(5, proveedor.getDireccion());
            stmt.setInt(6, proveedor.getId_proveedor());
            
            stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    @Override
    public void eliminar(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("DELETE FROM Proveedores WHERE id_proveedor = ?");
            stmt.setInt(1, id);
            
            stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt);
        }
    }
}
