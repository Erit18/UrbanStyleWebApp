package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;
import org.owasp.encoder.Encode;

public class SecurityUtils {
    
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    public boolean isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuario") != null;
    }
    
    public boolean hasRole(HttpServletRequest request, String requiredRole) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            return usuario != null && usuario.getRol().equals(requiredRole);
        }
        return false;
    }
    
    public String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return Encode.forHtml(input);
    }
    
    public String sanitizeSQLInput(String input) {
        if (input == null) {
            return null;
        }
        // Eliminar caracteres peligrosos para SQL
        return input.replaceAll("[';\"\\\\]", "");
    }
    
    public String generateCSRFToken() {
        return UUID.randomUUID().toString();
    }
    
    public boolean validateCSRFToken(HttpServletRequest request, String token) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String storedToken = (String) session.getAttribute("csrf_token");
            return storedToken != null && storedToken.equals(token);
        }
        return false;
    }
} 