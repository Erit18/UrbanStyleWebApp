package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.mycompany.aplicativowebintegrador.dao.UsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.usuarioDAO = new UsuarioDAO();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Usuario> usuarios = usuarioDAO.obtenerTodos();
                String json = gson.toJson(usuarios);
                response.getWriter().write(json);
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Usuario usuario = usuarioDAO.obtenerPorId(id);
                if (usuario != null) {
                    String json = gson.toJson(usuario);
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson("Usuario no encontrado"));
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
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            // Asegurarse de que el rol se mantenga como viene del formulario
            String rol = usuario.getRol() != null ? usuario.getRol().toLowerCase().trim() : "cliente";
            usuario.setRol(rol);
            
            usuarioDAO.registrarUsuario(usuario);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson("Usuario creado exitosamente"));
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
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            usuarioDAO.actualizar(usuario);
            response.getWriter().write(gson.toJson("Usuario actualizado exitosamente"));
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
            usuarioDAO.eliminar(id);
            response.getWriter().write(gson.toJson("Usuario eliminado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
