package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import java.sql.SQLException;
import java.util.List;

public interface IUsuarioDAO {
    Usuario buscarPorEmail(String email) throws SQLException;
    boolean existeEmail(String email) throws SQLException;
    void registrarUsuario(Usuario usuario) throws SQLException;
    List<Usuario> obtenerTodos() throws SQLException;
    Usuario obtenerPorId(int id) throws SQLException;
    void actualizar(Usuario usuario) throws SQLException;
    void eliminar(int id) throws SQLException;
    long contarUsuariosActivos() throws SQLException;
} 