package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.dao.ProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/proveedor/*")
public class ProveedorServlet extends HttpServlet {
    private ProveedorDAO proveedorDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.proveedorDAO = new ProveedorDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
                String json = gson.toJson(proveedores);
                response.getWriter().write(json);
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Proveedor proveedor = proveedorDAO.obtenerPorId(id);
                if (proveedor != null) {
                    String json = gson.toJson(proveedor);
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson("Proveedor no encontrado"));
                }
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Proveedor proveedor = gson.fromJson(request.getReader(), Proveedor.class);
            proveedorDAO.insertar(proveedor);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson("Proveedor creado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Proveedor proveedor = gson.fromJson(request.getReader(), Proveedor.class);
            proveedorDAO.actualizar(proveedor);
            response.getWriter().write(gson.toJson("Proveedor actualizado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            proveedorDAO.eliminar(id);
            response.getWriter().write(gson.toJson("Proveedor eliminado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
