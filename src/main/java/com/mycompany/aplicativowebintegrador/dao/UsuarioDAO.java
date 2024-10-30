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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);
    private static final int BCRYPT_ROUNDS = 12; // Configurar la complejidad del hash

    public Usuario autenticar(String email, String password) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashedPasswordFromDB = rs.getString("contraseña");
                    
                    if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("id_usuario"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setNombre(rs.getString("nombre"));
                        usuario.setRol(rs.getString("rol"));
                        return usuario;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error en autenticación", e);
        }
        return null;
    }

    public void registrarUsuario(Usuario usuario) throws SQLException {
        if (existeEmail(usuario.getEmail())) {
            throw new SQLException("El email ya está registrado");
        }

        String sql = "INSERT INTO Usuarios (nombre, email, contraseña, rol) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail().toLowerCase().trim());
            
            String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt(BCRYPT_ROUNDS));
            pstmt.setString(3, hashedPassword);
            
            String rol = "administrador".equals(usuario.getRol().toLowerCase().trim()) ? 
                "administrador" : "cliente";
            pstmt.setString(4, rol);
            
            pstmt.executeUpdate();
        }
    }

    private boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, email.toLowerCase().trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public void actualizar(Usuario usuario) throws SQLException {
        String sql = usuario.getContraseña() != null && !usuario.getContraseña().isEmpty() ?
            "UPDATE Usuarios SET nombre = ?, email = ?, contraseña = ?, rol = ? WHERE id_usuario = ?" :
            "UPDATE Usuarios SET nombre = ?, email = ?, rol = ? WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            
            if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
                pstmt.setString(3, hashedPassword);
                pstmt.setString(4, usuario.getRol());
                pstmt.setInt(5, usuario.getId());
            } else {
                pstmt.setString(3, usuario.getRol());
                pstmt.setInt(4, usuario.getId());
            }
            
            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
