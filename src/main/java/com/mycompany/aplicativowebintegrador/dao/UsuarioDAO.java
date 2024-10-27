package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public Usuario autenticar(String email, String contraseña) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("contraseña");
                    if (BCrypt.checkpw(contraseña, hashedPassword)) {
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("id_usuario"));
                        usuario.setNombre(rs.getString("nombre"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setRol(rs.getString("rol"));
                        return usuario;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error al autenticar usuario", e);
        }
        return null;
    }

    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña, rol) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, "cliente");
            
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("Crear usuario falló, ninguna fila afectada.");
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                logger.warn("Intento de registro con email duplicado: {}", usuario.getEmail());
                throw new SQLException("El email ya está registrado.");
            } else {
                logger.error("Error al registrar usuario", e);
                throw e;
            }
        }
    }
}
