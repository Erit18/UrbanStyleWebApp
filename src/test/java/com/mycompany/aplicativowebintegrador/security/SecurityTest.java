package com.mycompany.aplicativowebintegrador.security;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.util.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas de Seguridad")
public class SecurityTest {

    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private HttpSession session;
    
    private SecurityUtils securityUtils;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityUtils = new SecurityUtils();
        when(request.getSession(false)).thenReturn(session);
    }
    
    @Test
    @DisplayName("Test de hash de contraseña")
    void testPasswordHashing() {
        String password = "miContraseña123";
        String hashedPassword = securityUtils.hashPassword(password);
        
        assertNotNull(hashedPassword, "El hash no debe ser nulo");
        assertNotEquals(password, hashedPassword, "El hash debe ser diferente a la contraseña original");
        assertTrue(securityUtils.verifyPassword(password, hashedPassword), 
                  "La verificación de la contraseña debe ser exitosa");
    }
    
    @Test
    @DisplayName("Test de validación de sesión")
    void testSessionValidation() {
        // Simular sesión válida
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setRol("administrador");
        
        when(session.getAttribute("usuario")).thenReturn(usuario);
        
        assertTrue(securityUtils.isValidSession(request), 
                  "La sesión debe ser válida con usuario autenticado");
        
        // Simular sesión inválida
        when(session.getAttribute("usuario")).thenReturn(null);
        assertFalse(securityUtils.isValidSession(request), 
                   "La sesión debe ser inválida sin usuario");
    }
    
    @Test
    @DisplayName("Test de autorización de roles")
    void testRoleAuthorization() {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(1);
        usuario.setRol("administrador");
        
        when(session.getAttribute("usuario")).thenReturn(usuario);
        
        assertTrue(securityUtils.hasRole(request, "administrador"), 
                  "Usuario administrador debe tener acceso a rol administrador");
        assertFalse(securityUtils.hasRole(request, "vendedor"), 
                   "Usuario administrador no debe tener acceso a rol vendedor");
    }
    
    @Test
    @DisplayName("Test de protección contra XSS")
    void testXSSProtection() {
        String inputMalicioso = "<script>alert('XSS')</script>";
        String inputLimpio = securityUtils.sanitizeInput(inputMalicioso);
        
        assertNotEquals(inputMalicioso, inputLimpio, 
                       "El input malicioso debe ser sanitizado");
        assertFalse(inputLimpio.contains("<script>"), 
                   "El input sanitizado no debe contener tags de script");
    }
    
    @Test
    @DisplayName("Test de protección contra SQL Injection")
    void testSQLInjectionProtection() {
        String inputMalicioso = "' OR '1'='1";
        String inputLimpio = securityUtils.sanitizeSQLInput(inputMalicioso);
        
        assertNotEquals(inputMalicioso, inputLimpio, 
                       "El input malicioso SQL debe ser sanitizado");
        assertFalse(inputLimpio.contains("'"), 
                   "El input sanitizado no debe contener comillas simples");
    }
    
    @Test
    @DisplayName("Test de token CSRF")
    void testCSRFToken() {
        String token = securityUtils.generateCSRFToken();
        when(session.getAttribute("csrf_token")).thenReturn(token);
        
        assertNotNull(token, "El token CSRF no debe ser nulo");
        assertTrue(securityUtils.validateCSRFToken(request, token), 
                  "El token CSRF debe ser válido");
        assertFalse(securityUtils.validateCSRFToken(request, "token_invalido"), 
                   "Un token CSRF inválido debe ser rechazado");
    }
} 