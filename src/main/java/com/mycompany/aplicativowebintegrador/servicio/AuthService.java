package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.dao.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UsuarioDAO usuarioDAO;

    public AuthService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String password) throws Exception {
        logger.debug("Intentando autenticar usuario: {}", email);
        
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Email y contraseña son requeridos");
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null) {
            logger.warn("Intento de login fallido: usuario no encontrado - {}", email);
            throw new Exception("Credenciales inválidas");
        }

        if (!BCrypt.checkpw(password, usuario.getContraseña())) {
            logger.warn("Intento de login fallido: contraseña incorrecta - {}", email);
            throw new Exception("Credenciales inválidas");
        }

        logger.info("Usuario autenticado exitosamente: {}", email);
        return usuario;
    }

    public void cerrarSesion(String email) {
        logger.info("Sesión cerrada para usuario: {}", email);
    }
} 