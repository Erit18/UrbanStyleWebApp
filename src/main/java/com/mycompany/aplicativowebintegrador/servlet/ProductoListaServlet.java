package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.dao.IProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/productos-lista")
public class ProductoListaServlet extends HttpServlet {
    private final IProductoDAO productoDAO;
    private final Gson gson;

    public ProductoListaServlet() {
        this.productoDAO = new ProductoDAO();
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        try {
            var productos = productoDAO.obtenerTodos();
            String json = gson.toJson(productos);
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
