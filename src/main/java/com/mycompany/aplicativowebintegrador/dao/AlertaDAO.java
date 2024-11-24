package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO implements IAlertaDAO {
    private static final Logger logger = LoggerFactory.getLogger(AlertaDAO.class);

    @Override
    public List<Alerta> listarTodas() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        String sql = "SELECT a.*, r.nombre as nombre_producto FROM Alertas a " +
                    "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                    "ORDER BY a.fecha_alerta DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alertas.add(mapearAlerta(rs));
            }
        }
        return alertas;
    }

    @Override
    public Alerta obtenerPorId(int id) throws SQLException {
        String sql = "SELECT a.*, r.nombre as nombre_producto FROM Alertas a " +
                    "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                    "WHERE a.id_alerta = ?";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAlerta(rs);
                }
            }
        }
        return null;
    }

    @Override
    public boolean crear(Alerta alerta) throws SQLException {
        String sql = "INSERT INTO Alertas (id_ropa, mensaje) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, alerta.getId_ropa());
            stmt.setString(2, alerta.getMensaje());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean actualizar(Alerta alerta) throws SQLException {
        String sql = "UPDATE Alertas SET mensaje = ?, id_ropa = ? WHERE id_alerta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, alerta.getMensaje());
            stmt.setInt(2, alerta.getId_ropa());
            stmt.setInt(3, alerta.getId_alerta());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Alertas WHERE id_alerta = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private Alerta mapearAlerta(ResultSet rs) throws SQLException {
        Alerta alerta = new Alerta();
        alerta.setId_alerta(rs.getInt("id_alerta"));
        alerta.setId_ropa(rs.getInt("id_ropa"));
        alerta.setMensaje(rs.getString("mensaje"));
        alerta.setFecha_alerta(rs.getTimestamp("fecha_alerta"));
        alerta.setNombre_producto(rs.getString("nombre_producto"));
        return alerta;
    }
}
