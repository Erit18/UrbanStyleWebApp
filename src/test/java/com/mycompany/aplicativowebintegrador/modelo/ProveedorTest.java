package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Proveedor")
public class ProveedorTest {
    
    private Proveedor proveedor;
    
    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        System.out.println("Iniciando prueba de Proveedor...");
    }
    
    @Test
    @DisplayName("Test de creación básica de proveedor")
    public void testCreacionProveedor() {
        System.out.println("Ejecutando prueba de creación básica de proveedor");
        
        proveedor.setId_proveedor(1);
        proveedor.setNombre("Textiles S.A.");
        proveedor.setContacto("Juan Pérez");
        proveedor.setTelefono("987654321");
        proveedor.setEmail("textiles@example.com");
        proveedor.setDireccion("Av. Industrial 123");
        
        assertAll("Propiedades del proveedor",
            () -> assertEquals(1, proveedor.getId_proveedor(), "El ID debe ser 1"),
            () -> assertEquals("Textiles S.A.", proveedor.getNombre(), "El nombre debe ser 'Textiles S.A.'"),
            () -> assertEquals("Juan Pérez", proveedor.getContacto(), "El contacto debe ser 'Juan Pérez'"),
            () -> assertEquals("987654321", proveedor.getTelefono(), "El teléfono debe ser '987654321'"),
            () -> assertEquals("textiles@example.com", proveedor.getEmail(), "El email debe ser 'textiles@example.com'"),
            () -> assertEquals("Av. Industrial 123", proveedor.getDireccion(), "La dirección debe ser 'Av. Industrial 123'")
        );
        
        System.out.println("Prueba de creación básica completada exitosamente");
    }
    
    @Test
    @DisplayName("Test de proveedor sin email")
    public void testProveedorSinEmail() {
        System.out.println("Ejecutando prueba de proveedor sin email");
        
        proveedor.setId_proveedor(2);
        proveedor.setNombre("Proveedor Local");
        proveedor.setContacto("María López");
        proveedor.setTelefono("999888777");
        proveedor.setDireccion("Jr. Comercio 456");
        
        assertAll("Propiedades del proveedor sin email",
            () -> assertEquals(2, proveedor.getId_proveedor()),
            () -> assertEquals("Proveedor Local", proveedor.getNombre()),
            () -> assertEquals("María López", proveedor.getContacto()),
            () -> assertEquals("999888777", proveedor.getTelefono()),
            () -> assertNull(proveedor.getEmail()),
            () -> assertEquals("Jr. Comercio 456", proveedor.getDireccion())
        );
        
        System.out.println("Prueba de proveedor sin email completada exitosamente");
    }
} 