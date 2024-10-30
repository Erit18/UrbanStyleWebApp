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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistroServlet.class);
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== Debug Registro ===");
        System.out.println("Todos los parámetros recibidos:");
        request.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + Arrays.toString(value));
        });
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        System.out.println("\nParámetros procesados:");
        System.out.println("Nombre: [" + nombre + "]");
        System.out.println("Email: [" + email + "]");
        System.out.println("Password recibido: [" + (password != null ? "NO NULO" : "NULO") + "]");
        
        if (nombre == null || email == null || password == null || 
            nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            
            request.setAttribute("error", "Todos los campos son requeridos");
            request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
            return;
        }
        
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setContraseña(password);
            usuario.setRol("cliente");
            
            System.out.println("\nIntentando registrar usuario:");
            System.out.println("Email: " + email);
            System.out.println("Rol: " + usuario.getRol());
            
            usuarioDAO.registrarUsuario(usuario);
            System.out.println("Usuario registrado exitosamente");
            
            // Agregar mensaje de éxito y redirigir a Intranet.jsp
            request.getSession().setAttribute("mensaje", "Registro exitoso. Por favor, inicie sesión.");
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
            
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
            String mensaje = e.getMessage().contains("email ya está registrado") ? 
                "El email ya está registrado" : "Error al registrar usuario";
                
            request.getSession().setAttribute("error", mensaje);
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
            
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Error inesperado al registrar usuario");
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir GET requests al formulario de registro
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
    }
}
