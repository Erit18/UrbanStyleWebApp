package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.dao.UsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contraseña = request.getParameter("contraseña");
        
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(contraseña);
        nuevoUsuario.setRol("cliente"); // Establecemos el rol por defecto
        
        try {
            usuarioDAO.registrarUsuario(nuevoUsuario);
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp?registro=exitoso");
        } catch (SQLException e) {
            e.printStackTrace();
            String errorMessage = "Error al registrar usuario: " + e.getMessage();
            System.out.println(errorMessage);
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp?error=true&message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
        }
    }
}
