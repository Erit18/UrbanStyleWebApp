package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class VentaDAO extends BaseDAO implements IVentaDAO {
    
    @Override
    public List<Venta> buscarVentas(Date fechaInicio, Date fechaFin, String categoria) throws SQLException {
        List<Venta> ventas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            StringBuilder sql = new StringBuilder(
                "SELECT p.id_pedido, p.fecha_pedido, p.total, p.estado, " +
                "u.nombre as cliente, " +
                "GROUP_CONCAT(CONCAT(r.nombre, " +
                "CASE WHEN dp.cantidad > 1 THEN CONCAT(' x', dp.cantidad) ELSE '' END) " +
                "SEPARATOR ', ') as productos " +
                "FROM Pedidos p " +
                "INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario " +
                "INNER JOIN Detalle_Pedido dp ON p.id_pedido = dp.id_pedido " +
                "INNER JOIN Ropa r ON dp.id_ropa = r.id_ropa " +
                "WHERE p.estado IN ('pagado', 'completado')"
            );
            
            List<Object> params = new ArrayList<>();
            
            if (fechaInicio != null) {
                sql.append(" AND DATE(p.fecha_pedido) >= ?");
                params.add(new java.sql.Date(fechaInicio.getTime()));
            }
            
            if (fechaFin != null) {
                sql.append(" AND DATE(p.fecha_pedido) <= ?");
                params.add(new java.sql.Date(fechaFin.getTime()));
            }
            
            if (categoria != null && !categoria.isEmpty() && !"Todas".equals(categoria)) {
                sql.append(" AND r.categoria = ?");
                params.add(categoria);
            }
            
            sql.append(" GROUP BY p.id_pedido, p.fecha_pedido, u.nombre, p.total, p.estado " +
                      "ORDER BY p.fecha_pedido DESC");
            
            stmt = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                ventas.add(mapearVenta(rs));
            }
            
            return ventas;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    private Venta mapearVenta(ResultSet rs) throws SQLException {
        Venta venta = new Venta();
        venta.setId(String.format("VTA-%03d", rs.getInt("id_pedido")));
        venta.setFecha(rs.getTimestamp("fecha_pedido"));
        venta.setTotal(rs.getDouble("total"));
        venta.setEstado(rs.getString("estado"));
        
        String nombreCliente = rs.getString("cliente");
        venta.setNombreCliente(nombreCliente != null ? nombreCliente : "Cliente no especificado");
        venta.setProductos(rs.getString("productos"));
        
        return venta;
    }
} 