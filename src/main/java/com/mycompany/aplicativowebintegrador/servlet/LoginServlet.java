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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String contraseña = request.getParameter("contraseña");

        logger.info("Intento de inicio de sesión para el email: {}", email);

        try {
            Usuario usuario = usuarioDAO.autenticar(email, contraseña);

            if (usuario != null) {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                logger.info("Usuario autenticado: {}", usuario.getNombre());
                logger.info("Redirigiendo a: {}", request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            } else {
                logger.warn("Autenticación fallida para el email: {}", email);
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("Error durante la autenticación", e);
            request.setAttribute("error", "Error en el servidor");
            request.getRequestDispatcher("/views/Intranet/Intranet.jsp").forward(request, response);
        }
    }
}
