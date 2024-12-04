package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.IUsuarioDAO;
import com.mycompany.aplicativowebintegrador.dao.UsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.validador.UsuarioValidador;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    private final IUsuarioDAO usuarioDAO;
    private final UsuarioValidador validador;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.validador = new UsuarioValidador();
    }

    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.validador = new UsuarioValidador();
    }

    public void registrarUsuario(Usuario usuario) throws Exception {
        logger.debug("Iniciando registro de usuario: {}", usuario.getEmail());
        
        validador.validarRegistro(usuario);
        
        if (usuarioDAO.existeEmail(usuario.getEmail())) {
            throw new Exception("El email ya está registrado");
        }

        String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
        usuario.setContraseña(hashedPassword);
        
        usuarioDAO.registrarUsuario(usuario);
        logger.info("Usuario registrado exitosamente: {}", usuario.getEmail());
    }

    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioDAO.obtenerTodos();
    }

    public Usuario obtenerUsuario(int id) throws Exception {
        return usuarioDAO.obtenerPorId(id);
    }

    public void actualizarUsuario(Usuario usuario) throws Exception {
        validador.validarActualizacion(usuario);
        
        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
            usuario.setContraseña(hashedPassword);
        }
        
        usuarioDAO.actualizar(usuario);
        logger.info("Usuario actualizado: {}", usuario.getEmail());
    }

    public void eliminarUsuario(int id) throws Exception {
        usuarioDAO.eliminar(id);
        logger.info("Usuario eliminado: {}", id);
    }

    public long contarUsuariosActivos() {
        try {
            return usuarioDAO.contarUsuariosActivos();
        } catch (Exception e) {
            logger.error("Error al contar usuarios activos", e);
            return 0;
        }
    }
} 