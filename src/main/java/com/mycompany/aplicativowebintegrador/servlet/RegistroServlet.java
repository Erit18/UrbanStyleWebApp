package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.servicio.UsuarioService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RegistroServlet.class);
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        logger.debug("=== Debug Registro ===");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        logger.debug("Nombre: [{}], Email: [{}], Password: [{}]", 
            nombre, 
            email, 
            password != null ? "NO NULO" : "NULO"
        );
        
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
            usuario.setRol("administrador");
            
            logger.debug("Intentando registrar usuario: {}", email);
            
            usuarioService.registrarUsuario(usuario);
            logger.info("Usuario registrado exitosamente: {}", email);
            
            // Agregar mensaje de éxito y redirigir
            request.getSession().setAttribute("mensaje", "Registro exitoso. Por favor, inicie sesión.");
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
            
        } catch (Exception e) {
            logger.error("Error al registrar usuario", e);
            String mensaje = e.getMessage().contains("email ya está registrado") ? 
                "El email ya está registrado" : "Error al registrar usuario";
                
            request.getSession().setAttribute("error", mensaje);
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
    }
}
