package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private final Connection con;

    public PedidoDAO(Connection con) {
        this.con = con;
    }

    public List<Pedido> obtenerPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.id_usuario, u.nombre AS nombre_cliente, p.total, p.estado, p.fecha_pedido " +
                     "FROM Pedidos p JOIN Usuarios u ON p.id_usuario = u.id_usuario";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("id_pedido"));
                pedido.setIdUsuario(rs.getInt("id_usuario"));
                pedido.setNombreCliente(rs.getString("nombre_cliente"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setFechaPedido(rs.getTimestamp("fecha_pedido"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }
}
