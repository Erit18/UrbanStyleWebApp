package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setRol(rs.getString("rol"));
                    return usuario;
                }
            }
        }
        return null;
    }

    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public void registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña, rol) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getRol());
            
            stmt.executeUpdate();
        }
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
                // No incluimos la contraseña por seguridad
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setRol(rs.getString("rol"));
                    // No incluimos la contraseña por seguridad
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
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            
            if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
                stmt.setString(3, usuario.getContraseña());
                stmt.setString(4, usuario.getRol());
                stmt.setInt(5, usuario.getId());
            } else {
                stmt.setString(3, usuario.getRol());
                stmt.setInt(4, usuario.getId());
            }
            
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
