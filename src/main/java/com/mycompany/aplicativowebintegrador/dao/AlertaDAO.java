package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO extends BaseDAO implements IAlertaDAO {
    
    private Alerta mapearAlerta(ResultSet rs) throws SQLException {
        Alerta alerta = new Alerta();
        alerta.setId_alerta(rs.getInt("id_alerta"));
        alerta.setId_ropa(rs.getInt("id_ropa"));
        alerta.setMensaje(rs.getString("mensaje"));
        alerta.setFecha_alerta(rs.getTimestamp("fecha_alerta"));
        alerta.setNombre_producto(rs.getString("nombre_producto"));
        alerta.setTipo_alerta(rs.getString("tipo_alerta"));
        alerta.setEstado(rs.getString("estado"));
        alerta.setUmbral(rs.getInt("umbral"));
        return alerta;
    }
    
    @Override
    public List<Alerta> listarTodas() throws SQLException {
        List<Alerta> alertas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "SELECT a.*, r.nombre as nombre_producto " +
                "FROM Alertas a " +
                "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                "WHERE a.estado = 'activa' " +
                "ORDER BY a.fecha_alerta DESC"
            );
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                alertas.add(mapearAlerta(rs));
            }
            
            return alertas;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public boolean eliminar(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "UPDATE Alertas SET estado = 'resuelta' WHERE id_alerta = ?"
            );
            stmt.setInt(1, id);
            
            return stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    @Override
    public boolean actualizar(Alerta alerta) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "UPDATE Alertas SET mensaje = ?, tipo_alerta = ?, " +
                "estado = ?, umbral = ?, id_ropa = ? " +
                "WHERE id_alerta = ?"
            );
            
            stmt.setString(1, alerta.getMensaje());
            stmt.setString(2, alerta.getTipo_alerta());
            stmt.setString(3, alerta.getEstado());
            stmt.setInt(4, alerta.getUmbral());
            stmt.setInt(5, alerta.getId_ropa());
            stmt.setInt(6, alerta.getId_alerta());
            
            return stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    @Override
    public boolean crear(Alerta alerta) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, " +
                "estado, umbral, fecha_alerta) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)"
            );
            
            stmt.setInt(1, alerta.getId_ropa());
            stmt.setString(2, alerta.getMensaje());
            stmt.setString(3, alerta.getTipo_alerta());
            stmt.setString(4, alerta.getEstado());
            stmt.setInt(5, alerta.getUmbral());
            
            return stmt.executeUpdate() > 0;
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    @Override
    public Alerta obtenerPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "SELECT a.*, r.nombre as nombre_producto " +
                "FROM Alertas a " +
                "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                "WHERE a.id_alerta = ?"
            );
            
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearAlerta(rs);
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    public void actualizarEstadoAlertasPorProducto(int idRopa, int nuevoStock) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(
                "UPDATE Alertas SET " +
                "estado = CASE " +
                "   WHEN tipo_alerta = 'stock_bajo' AND ? <= umbral THEN 'activa' " +
                "   WHEN tipo_alerta = 'stock_bajo' AND ? > umbral THEN 'resuelta' " +
                "   ELSE estado " +
                "END " +
                "WHERE id_ropa = ? AND tipo_alerta = 'stock_bajo'"
            );
            
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, nuevoStock);
            stmt.setInt(3, idRopa);
            
            stmt.executeUpdate();
        } finally {
            closeResources(conn, stmt);
        }
    }
    
    public void verificarYCrearAlertas() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            stmt = conn.prepareStatement(
                "UPDATE Alertas a " +
                "INNER JOIN Ropa r ON a.id_ropa = r.id_ropa " +
                "SET a.estado = 'activa', a.fecha_alerta = CURRENT_TIMESTAMP " +
                "WHERE a.estado = 'resuelta' " +
                "AND ((a.tipo_alerta = 'stock_bajo' AND r.stock <= a.umbral) " +
                "     OR (a.tipo_alerta = 'caducidad' AND r.fecha_caducidad BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY)))"
            );
            stmt.executeUpdate();
            
            stmt = conn.prepareStatement(
                "INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral, fecha_alerta) " +
                "SELECT r.id_ropa, " +
                "CONCAT('Stock bajo: ', r.nombre, ' (', r.stock, ' unidades)'), " +
                "'stock_bajo', 'activa', 30, CURRENT_TIMESTAMP " +
                "FROM Ropa r " +
                "WHERE r.stock <= 30 " +
                "AND NOT EXISTS (SELECT 1 FROM Alertas a " +
                "               WHERE a.id_ropa = r.id_ropa " +
                "               AND a.tipo_alerta = 'stock_bajo')"
            );
            stmt.executeUpdate();
            
            stmt = conn.prepareStatement(
                "INSERT INTO Alertas (id_ropa, mensaje, tipo_alerta, estado, umbral, fecha_alerta) " +
                "SELECT r.id_ropa, " +
                "CONCAT('Próximo a caducar: ', r.nombre, ' (', DATE_FORMAT(r.fecha_caducidad, '%d/%m/%Y'), ')'), " +
                "'caducidad', 'activa', 30, CURRENT_TIMESTAMP " +
                "FROM Ropa r " +
                "WHERE r.fecha_caducidad IS NOT NULL " +
                "AND r.fecha_caducidad BETWEEN CURRENT_DATE AND DATE_ADD(CURRENT_DATE, INTERVAL 30 DAY) " +
                "AND NOT EXISTS (SELECT 1 FROM Alertas a " +
                "               WHERE a.id_ropa = r.id_ropa " +
                "               AND a.tipo_alerta = 'caducidad')"
            );
            stmt.executeUpdate();
            
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    // ... resto de métodos siguiendo el mismo patrón
}
