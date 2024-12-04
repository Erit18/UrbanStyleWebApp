package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.dao.IUsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import com.mycompany.aplicativowebintegrador.config.IDatabaseConfig;
import com.mycompany.aplicativowebintegrador.config.DatabaseConfig;
import com.mycompany.aplicativowebintegrador.dao.BaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación DAO para la gestión de usuarios en la base de datos.
 * Maneja operaciones CRUD y validaciones específicas de usuarios.
 * 
 * @author TuNombre
 * @version 1.0
 */
public class UsuarioDAO extends BaseDAO implements IUsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAO.class);

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email Correo electrónico del usuario a buscar
     * @return Usuario encontrado o null si no existe
     * @throws SQLException si ocurre un error en la consulta
     */
    @Override
    public Usuario buscarPorEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Usuarios WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
            return null;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
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

    @Override
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

    @Override
    public List<Usuario> obtenerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Usuarios ORDER BY nombre");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
            return usuarios;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }

    @Override
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

    @Override
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

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Usuarios WHERE id_usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public long contarUsuariosActivos() throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE estado = 'activo'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0;
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setContraseña(rs.getString("contraseña"));
        usuario.setRol(rs.getString("rol"));
        return usuario;
    }
}
