package com.mycompany.aplicativowebintegrador.validador;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Validador de Usuario")
public class UsuarioValidadorTest {
    
    private UsuarioValidador validador;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        validador = new UsuarioValidador();
        usuario = new Usuario();
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setContraseña("password123");
        usuario.setRol("cliente");
        System.out.println("Iniciando prueba de validación de usuario...");
    }
    
    @Test
    @DisplayName("Validación exitosa de registro")
    public void testValidacionExitosa() {
        System.out.println("Ejecutando prueba de validación exitosa");
        assertDoesNotThrow(() -> validador.validarRegistro(usuario));
    }
    
    @Test
    @DisplayName("Validación de nombre vacío")
    public void testValidacionNombreVacio() {
        System.out.println("Ejecutando prueba de nombre vacío");
        usuario.setNombre("");
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> validador.validarRegistro(usuario));
        assertEquals("El nombre es requerido", exception.getMessage());
    }
    
    @Test
    @DisplayName("Validación de email inválido")
    public void testValidacionEmailInvalido() {
        System.out.println("Ejecutando prueba de email inválido");
        usuario.setEmail("emailinvalido");
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> validador.validarRegistro(usuario));
        assertEquals("El formato del email no es válido", exception.getMessage());
    }
    
    @Test
    @DisplayName("Validación de contraseña corta")
    public void testValidacionPasswordCorta() {
        System.out.println("Ejecutando prueba de contraseña corta");
        usuario.setContraseña("123");
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> validador.validarRegistro(usuario));
        assertEquals("La contraseña debe tener al menos 6 caracteres", exception.getMessage());
    }
    
    @Test
    @DisplayName("Validación de rol inválido")
    public void testValidacionRolInvalido() {
        System.out.println("Ejecutando prueba de rol inválido");
        usuario.setRol("superuser");
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> validador.validarRegistro(usuario));
        assertEquals("Rol inválido", exception.getMessage());
    }
} 