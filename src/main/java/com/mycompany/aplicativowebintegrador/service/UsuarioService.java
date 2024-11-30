package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IUsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import java.util.List;

public class UsuarioService extends ServicioBase<Usuario> {
    private final IUsuarioDAO usuarioDAO;
    
    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    @Override
    public Usuario obtenerPorId(Integer id) throws Exception {
        validarId(id);
        return usuarioDAO.obtenerPorId(id);
    }
    
    @Override
    public List<Usuario> listarTodos() throws Exception {
        return usuarioDAO.obtenerTodos();
    }
    
    @Override
    public void guardar(Usuario usuario) throws Exception {
        validarEntidad(usuario);
        validarUsuario(usuario);
        if (usuarioDAO.existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        usuarioDAO.registrarUsuario(usuario);
    }
    
    @Override
    public void actualizar(Usuario usuario) throws Exception {
        validarEntidad(usuario);
        validarId(usuario.getId());
        validarUsuario(usuario);
        usuarioDAO.actualizar(usuario);
    }
    
    @Override
    public void eliminar(Integer id) throws Exception {
        validarId(id);
        usuarioDAO.eliminar(id);
    }
    
    // Métodos específicos de UsuarioService
    private void validarUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (usuario.getEmail() == null || !usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es requerido");
        }
    }
    
    public Usuario buscarPorEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        return usuarioDAO.buscarPorEmail(email);
    }
} 