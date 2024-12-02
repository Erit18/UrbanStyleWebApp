package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Usuario")
public class UsuarioTest {
    
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        System.out.println("Iniciando prueba de Usuario...");
    }
    
    @Test
    @DisplayName("Test de creación básica de usuario")
    public void testCreacionUsuario() {
        System.out.println("Ejecutando prueba de creación básica de usuario");
        
        usuario.setId_usuario(1);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setContraseña("password123");
        usuario.setRol("cliente");
        
        assertAll("Propiedades del usuario",
            () -> assertEquals(1, usuario.getId_usuario(), "El ID debe ser 1"),
            () -> assertEquals("Juan Pérez", usuario.getNombre(), "El nombre debe ser 'Juan Pérez'"),
            () -> assertEquals("juan@example.com", usuario.getEmail(), "El email debe ser 'juan@example.com'"),
            () -> assertEquals("password123", usuario.getContraseña(), "La contraseña debe ser 'password123'"),
            () -> assertEquals("cliente", usuario.getRol(), "El rol debe ser 'cliente'")
        );
        
        System.out.println("Prueba de creación básica completada exitosamente");
    }
    
    @Test
    @DisplayName("Test de usuario administrador")
    public void testUsuarioAdministrador() {
        System.out.println("Ejecutando prueba de usuario administrador");
        
        usuario.setId_usuario(2);
        usuario.setNombre("Admin");
        usuario.setEmail("admin@system.com");
        usuario.setContraseña("admin123");
        usuario.setRol("administrador");
        
        assertAll("Propiedades del usuario administrador",
            () -> assertEquals(2, usuario.getId_usuario()),
            () -> assertEquals("Admin", usuario.getNombre()),
            () -> assertEquals("admin@system.com", usuario.getEmail()),
            () -> assertEquals("admin123", usuario.getContraseña()),
            () -> assertEquals("administrador", usuario.getRol())
        );
        
        System.out.println("Prueba de usuario administrador completada exitosamente");
    }
} 