package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Reclamo;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReclamoDAO {
    private Connection conexion;
    
    public ReclamoDAO() {
        try {
            this.conexion = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            // Considera lanzar una RuntimeException si la conexión es crítica
            throw new RuntimeException("Error al establecer la conexión con la base de datos", e);
        }
    }
    
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean registrarReclamo(Reclamo reclamo) {
        String sql = "INSERT INTO reclamos (tipo_documento, numero_documento, nombre, apellido, " +
                    "departamento, provincia, distrito, telefono, correo_electronico, " +
                    "tipo_reclamo, fecha_compra, numero_boleta, detalle_reclamo, fecha_registro, estado) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), 'Pendiente')";
        
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, reclamo.getTipoDocumento());
            ps.setString(2, reclamo.getNumeroDocumento());
            ps.setString(3, reclamo.getNombre());
            ps.setString(4, reclamo.getApellido());
            ps.setString(5, reclamo.getDepartamento());
            ps.setString(6, reclamo.getProvincia());
            ps.setString(7, reclamo.getDistrito());
            ps.setString(8, reclamo.getTelefono());
            ps.setString(9, reclamo.getCorreoElectronico());
            ps.setString(10, reclamo.getTipoReclamo());
            ps.setDate(11, new java.sql.Date(reclamo.getFechaCompra().getTime()));
            ps.setString(12, reclamo.getNumeroBoleta());
            ps.setString(13, reclamo.getDetalleReclamo());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Reclamo> listarReclamos() {
        List<Reclamo> reclamos = new ArrayList<>();
        String sql = "SELECT * FROM reclamos ORDER BY fecha_registro DESC";
        
        try (Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            
            while (rs.next()) {
                Reclamo reclamo = new Reclamo();
                reclamo.setId(rs.getInt("id"));
                reclamo.setTipoDocumento(rs.getString("tipo_documento"));
                reclamo.setNumeroDocumento(rs.getString("numero_documento"));
                reclamo.setNombre(rs.getString("nombre"));
                reclamo.setApellido(rs.getString("apellido"));
                // ... establecer todos los campos
                reclamos.add(reclamo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamos;
    }

  
  
public Reclamo obtenerReclamoPorId(int id) {
    String sql = "SELECT * FROM reclamos WHERE id = ?";
    Reclamo reclamo = null;
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                reclamo = new Reclamo();
                reclamo.setId(rs.getInt("id"));
                reclamo.setTipoDocumento(rs.getString("tipo_documento"));
                reclamo.setNumeroDocumento(rs.getString("numero_documento"));
                reclamo.setNombre(rs.getString("nombre"));
                reclamo.setApellido(rs.getString("apellido"));
                reclamo.setDepartamento(rs.getString("departamento"));
                reclamo.setProvincia(rs.getString("provincia"));
                reclamo.setDistrito(rs.getString("distrito"));
                reclamo.setTelefono(rs.getString("telefono"));
                reclamo.setCorreoElectronico(rs.getString("correo_electronico"));
                reclamo.setTipoReclamo(rs.getString("tipo_reclamo"));
                reclamo.setFechaCompra(rs.getDate("fecha_compra"));
                reclamo.setNumeroBoleta(rs.getString("numero_boleta"));
                reclamo.setDetalleReclamo(rs.getString("detalle_reclamo"));
                reclamo.setEstado(rs.getString("estado"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return reclamo;
}

public boolean actualizarEstadoReclamo(int id, String estado) {
    String sql = "UPDATE reclamos SET estado = ? WHERE id = ?";
    
    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, estado);
        pstmt.setInt(2, id);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}






