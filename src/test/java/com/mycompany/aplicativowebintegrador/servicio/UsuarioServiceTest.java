package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.IUsuarioDAO;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@DisplayName("Pruebas del Servicio de Usuario")
public class UsuarioServiceTest {
    
    @Mock
    private IUsuarioDAO usuarioDAO;
    
    private UsuarioService usuarioService;
    private Usuario usuarioTest;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioDAO);
        
        usuarioTest = new Usuario();
        usuarioTest.setId_usuario(1);
        usuarioTest.setNombre("Test User");
        usuarioTest.setEmail("test@example.com");
        usuarioTest.setContraseña("password123");
        usuarioTest.setRol("cliente");
        
        System.out.println("Iniciando prueba de servicio de usuario...");
    }
    
    @Test
    @DisplayName("Registro de usuario exitoso")
    public void testRegistroUsuarioExitoso() throws Exception {
        System.out.println("Ejecutando prueba de registro exitoso");
        when(usuarioDAO.existeEmail(anyString())).thenReturn(false);
        doNothing().when(usuarioDAO).registrarUsuario(any(Usuario.class));
        
        assertDoesNotThrow(() -> usuarioService.registrarUsuario(usuarioTest));
        verify(usuarioDAO).registrarUsuario(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Registro con email duplicado")
    public void testRegistroEmailDuplicado() throws Exception {
        System.out.println("Ejecutando prueba de email duplicado");
        when(usuarioDAO.existeEmail(anyString())).thenReturn(true);
        
        Exception exception = assertThrows(Exception.class, 
            () -> usuarioService.registrarUsuario(usuarioTest));
        assertEquals("El email ya está registrado", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar todos los usuarios")
    public void testListarUsuarios() throws Exception {
        System.out.println("Ejecutando prueba de listar usuarios");
        List<Usuario> usuarios = Arrays.asList(usuarioTest);
        when(usuarioDAO.obtenerTodos()).thenReturn(usuarios);
        
        List<Usuario> resultado = usuarioService.listarUsuarios();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioDAO).obtenerTodos();
    }
    
    @Test
    @DisplayName("Obtener usuario por ID")
    public void testObtenerUsuarioPorId() throws Exception {
        System.out.println("Ejecutando prueba de obtener usuario por ID");
        when(usuarioDAO.obtenerPorId(1)).thenReturn(usuarioTest);
        
        Usuario resultado = usuarioService.obtenerUsuario(1);
        
        assertNotNull(resultado);
        assertEquals(usuarioTest.getId_usuario(), resultado.getId_usuario());
        verify(usuarioDAO).obtenerPorId(1);
    }
} 