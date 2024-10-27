package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/productos-lista")
public class ProductoListaServlet extends HttpServlet {
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        try {
            var productos = productoDAO.obtenerTodos();
            System.out.println("DEBUG - ProductoListaServlet: Productos encontrados: " + productos.size());
            
            // Verificar cada producto
            for (var producto : productos) {
                System.out.println("DEBUG - ProductoListaServlet: Producto -> " +
                                 "ID: " + producto.getId_ropa() + 
                                 ", Nombre: " + producto.getNombre());
            }
            
            String json = gson.toJson(productos);
            System.out.println("DEBUG - ProductoListaServlet: JSON a enviar: " + json);
            
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
