package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.servicio.AuthService;
import com.mycompany.aplicativowebintegrador.util.ResponseBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private static final int SESSION_MAX_INACTIVE_INTERVAL = 30 * 60; // 30 minutos
    
    private AuthService authService;
    private ResponseBuilder responseBuilder;

    @Override
    public void init() throws ServletException {
        this.authService = new AuthService();
        this.responseBuilder = new ResponseBuilder();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            Usuario usuario = authService.autenticar(email, password);
            
            // Configurar sesión
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(SESSION_MAX_INACTIVE_INTERVAL);
            session.setAttribute("usuario", usuario);
            session.setAttribute("lastAccessTime", System.currentTimeMillis());
            
            // Redirigir según rol
            String rolNormalizado = usuario.getRol().trim().toLowerCase();
            if ("administrador".equals(rolNormalizado)) {
                response.sendRedirect(request.getContextPath() + "/views/intranex/Dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            
        } catch (Exception e) {
            logger.error("Error en login", e);
            HttpSession session = request.getSession();
            session.setAttribute("error", "Email o contraseña incorrectos");
            response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
    }
}
