package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.mycompany.aplicativowebintegrador.dao.PedidoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Pedido;
import com.mycompany.aplicativowebintegrador.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/PedidosServlet")
public class PedidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new Exception("No se pudo establecer la conexión a la base de datos.");
            }

            PedidoDAO pedidoDAO = new PedidoDAO(con);
            List<Pedido> pedidos = pedidoDAO.obtenerPedidos();

            if (pedidos == null || pedidos.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                response.getWriter().write("[]"); // Retorna una lista vacía
                return;
            }

            // Convertir la lista a JSON y enviar como respuesta
            String json = new Gson().toJson(pedidos);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al obtener los pedidos.\"}");
        }
    }
}
