package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/api/ventas")
public class VentasServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(VentasServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            String fechaInicio = request.getParameter("fechaInicio");
            String fechaFin = request.getParameter("fechaFin");
            String categoria = request.getParameter("categoria");
            
            logger.info("Filtros recibidos - Fecha Inicio: {}, Fecha Fin: {}, Categoría: {}", 
                       fechaInicio, fechaFin, categoria);
            
            Connection conn = DatabaseConnection.getConnection();
            
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    CONCAT('VTA-', LPAD(p.id_pedido, 3, '0')) as id_venta,
                    DATE_FORMAT(p.fecha_pedido, '%Y-%m-%d') as fecha,
                    u.nombre as cliente,
                    GROUP_CONCAT(
                        CONCAT(r.nombre, 
                        CASE 
                            WHEN dp.cantidad > 1 THEN CONCAT(' x', dp.cantidad)
                            ELSE ''
                        END)
                        SEPARATOR ', '
                    ) as productos,
                    p.total,
                    p.estado
                FROM 
                    Pedidos p
                    INNER JOIN Usuarios u ON p.id_usuario = u.id_usuario
                    INNER JOIN Detalle_Pedido dp ON p.id_pedido = dp.id_pedido
                    INNER JOIN Ropa r ON dp.id_ropa = r.id_ropa
                WHERE 
                    p.estado IN ('pagado', 'completado')
            """);
            
            List<Object> params = new ArrayList<>();
            
            if (fechaInicio != null && !fechaInicio.isEmpty()) {
                sql.append(" AND DATE(p.fecha_pedido) >= DATE(?)");
                params.add(fechaInicio);
            }
            
            if (fechaFin != null && !fechaFin.isEmpty()) {
                sql.append(" AND DATE(p.fecha_pedido) <= DATE(?)");
                params.add(fechaFin);
            }
            
            if (categoria != null && !categoria.isEmpty() && !"Todas".equals(categoria)) {
                sql.append(" AND r.categoria = ?");
                params.add(categoria);
            }
            
            sql.append("""
                GROUP BY 
                    p.id_pedido, p.fecha_pedido, u.nombre, p.total, p.estado
                ORDER BY 
                    p.fecha_pedido DESC
            """);
            
            logger.info("SQL Query: {}", sql.toString());
            logger.info("Params: {}", params);
            
            PreparedStatement stmt = conn.prepareStatement(sql.toString());
            
            // Establecer parámetros
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = stmt.executeQuery();
            
            JSONArray ventas = new JSONArray();
            while (rs.next()) {
                JSONObject venta = new JSONObject();
                String fecha = rs.getString("fecha");
                logger.info("Fecha encontrada: {}", fecha);
                
                venta.put("id", rs.getString("id_venta"));
                venta.put("fecha", fecha);
                venta.put("cliente", rs.getString("cliente"));
                venta.put("productos", rs.getString("productos"));
                venta.put("total", rs.getDouble("total"));
                venta.put("estado", rs.getString("estado"));
                ventas.put(venta);
            }
            
            logger.info("Total de ventas encontradas: {}", ventas.length());
            out.print(ventas.toString());
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException e) {
            logger.error("Error al obtener las ventas", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("error", "Error al obtener las ventas: " + e.getMessage());
            out.print(error.toString());
        }
    }
} 