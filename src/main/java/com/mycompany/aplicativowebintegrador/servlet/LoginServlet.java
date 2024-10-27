package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.dao.UsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final int SESSION_MAX_INACTIVE_INTERVAL = 30 * 60; // 30 minutos

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Por favor complete todos los campos");
            request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
            return;
        }
        
        try {
            Usuario usuario = usuarioDAO.autenticar(email, password);
            
            if (usuario != null) {
                // Configurar sesión
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
                session.setAttribute("usuario", usuario);
                session.setAttribute("lastAccessTime", System.currentTimeMillis());
                
                String rolNormalizado = usuario.getRol().trim().toLowerCase();
                if ("administrador".equals(rolNormalizado)) {
                    response.sendRedirect(request.getContextPath() + "/views/intranex/Dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            } else {
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error en el servidor");
            request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
    }
}
