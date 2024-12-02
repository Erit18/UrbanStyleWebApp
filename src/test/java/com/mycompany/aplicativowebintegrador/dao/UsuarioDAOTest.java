package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import com.mycompany.aplicativowebintegrador.config.TestDatabaseConfig;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioDAOTest {
    
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioPrueba;
    
    @BeforeAll
    void setUp() {
        DatabaseConnection.setConfig(new TestDatabaseConfig());
        usuarioDAO = new UsuarioDAO();
        usuarioPrueba = new Usuario();
        usuarioPrueba.setNombre("Usuario Test");
        usuarioPrueba.setEmail("test@example.com");
        usuarioPrueba.setContraseña("password123");
        usuarioPrueba.setRol("USUARIO");
    }
    
    @BeforeEach
    void limpiarBaseDeDatos() throws SQLException {
        // Limpia la tabla de usuarios antes de cada prueba
        try (var conn = DatabaseConnection.getConnection();
             var stmt = conn.prepareStatement("DELETE FROM Usuarios")) {
            stmt.executeUpdate();
        }
    }
    
    @Test
    void testRegistrarYBuscarUsuario() throws SQLException {
        // Registrar usuario
        usuarioDAO.registrarUsuario(usuarioPrueba);
        
        // Buscar por email
        Usuario usuarioEncontrado = usuarioDAO.buscarPorEmail("test@example.com");
        
        assertNotNull(usuarioEncontrado);
        assertEquals(usuarioPrueba.getNombre(), usuarioEncontrado.getNombre());
        assertEquals(usuarioPrueba.getEmail(), usuarioEncontrado.getEmail());
        assertEquals(usuarioPrueba.getRol(), usuarioEncontrado.getRol());
    }
    
    @Test
    void testExisteEmail() throws SQLException {
        // Verificar que el email no existe inicialmente
        assertFalse(usuarioDAO.existeEmail("test@example.com"));
        
        // Registrar usuario
        usuarioDAO.registrarUsuario(usuarioPrueba);
        
        // Verificar que ahora el email existe
        assertTrue(usuarioDAO.existeEmail("test@example.com"));
    }
    
    @Test
    void testObtenerTodos() throws SQLException {
        // Registrar varios usuarios
        usuarioDAO.registrarUsuario(usuarioPrueba);
        
        Usuario segundoUsuario = new Usuario();
        segundoUsuario.setNombre("Usuario Test 2");
        segundoUsuario.setEmail("test2@example.com");
        segundoUsuario.setContraseña("password456");
        segundoUsuario.setRol("USUARIO");
        usuarioDAO.registrarUsuario(segundoUsuario);
        
        // Obtener todos los usuarios
        List<Usuario> usuarios = usuarioDAO.obtenerTodos();
        
        assertEquals(2, usuarios.size());
    }
    
    @Test
    void testActualizarUsuario() throws SQLException {
        // Registrar usuario
        usuarioDAO.registrarUsuario(usuarioPrueba);
        Usuario usuarioEncontrado = usuarioDAO.buscarPorEmail("test@example.com");
        
        // Actualizar datos
        usuarioEncontrado.setNombre("Nombre Actualizado");
        usuarioDAO.actualizar(usuarioEncontrado);
        
        // Verificar actualización
        Usuario usuarioActualizado = usuarioDAO.buscarPorEmail("test@example.com");
        assertEquals("Nombre Actualizado", usuarioActualizado.getNombre());
    }
    
    @Test
    void testEliminarUsuario() throws SQLException {
        // Registrar usuario
        usuarioDAO.registrarUsuario(usuarioPrueba);
        Usuario usuarioEncontrado = usuarioDAO.buscarPorEmail("test@example.com");
        
        // Eliminar usuario
        usuarioDAO.eliminar(usuarioEncontrado.getId());
        
        // Verificar que el usuario ya no existe
        assertNull(usuarioDAO.buscarPorEmail("test@example.com"));
    }
} 