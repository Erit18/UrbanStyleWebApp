package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {
    private Connection conexion;

    public AlertaDAO() {
        try {
            conexion = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Alerta> listarAlertas() {
        System.out.println("=== DEBUG - AlertaDAO.listarAlertas() - INICIO ===");
        List<Alerta> alertas = new ArrayList<>();
        
        String sql = "SELECT a.*, r.nombre as nombre_producto FROM Alertas a " +
                    "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                    "ORDER BY a.fecha_alerta DESC";
        
        System.out.println("SQL a ejecutar: " + sql);
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión establecida correctamente");
            
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                System.out.println("Consulta ejecutada, procesando resultados...");
                
                while (rs.next()) {
                    Alerta alerta = new Alerta();
                    alerta.setId_alerta(rs.getInt("id_alerta"));
                    alerta.setId_ropa(rs.getInt("id_ropa"));
                    alerta.setMensaje(rs.getString("mensaje"));
                    alerta.setFecha_alerta(rs.getTimestamp("fecha_alerta"));
                    alerta.setNombre_producto(rs.getString("nombre_producto"));
                    
                    System.out.println("Alerta cargada: " + 
                        "ID=" + alerta.getId_alerta() + 
                        ", Producto=" + alerta.getNombre_producto() + 
                        ", Mensaje=" + alerta.getMensaje());
                    
                    alertas.add(alerta);
                }
                
                System.out.println("Total de alertas encontradas: " + alertas.size());
            }
        } catch (SQLException e) {
            System.err.println("ERROR en AlertaDAO.listarAlertas(): " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("=== DEBUG - AlertaDAO.listarAlertas() - FIN ===");
        return alertas;
    }

    public boolean crearAlerta(Alerta alerta) {
        String sql = "INSERT INTO Alertas (id_ropa, mensaje) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, alerta.getId_ropa());
            ps.setString(2, alerta.getMensaje());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAlerta(Alerta alerta) {
        String sql = "UPDATE Alertas SET mensaje = ? WHERE id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, alerta.getMensaje());
            ps.setInt(2, alerta.getId_alerta());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAlerta(int id_alerta) {
        String sql = "DELETE FROM Alertas WHERE id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id_alerta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Alerta obtenerAlertaPorId(int id_alerta) {
        String sql = "SELECT a.*, r.nombre as nombre_producto FROM Alertas a " +
                    "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                    "WHERE a.id_alerta = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id_alerta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Alerta alerta = new Alerta();
                    alerta.setId_alerta(rs.getInt("id_alerta"));
                    alerta.setId_ropa(rs.getInt("id_ropa"));
                    alerta.setMensaje(rs.getString("mensaje"));
                    alerta.setFecha_alerta(rs.getTimestamp("fecha_alerta"));
                    alerta.setNombre_producto(rs.getString("nombre_producto"));
                    return alerta;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
