package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

@DisplayName("Pruebas de la clase Alerta")
public class AlertaTest {
    
    private Alerta alerta;
    private Date fechaPrueba;
    
    @BeforeEach
    void setUp() {
        alerta = new Alerta();
        fechaPrueba = new Date();
        System.out.println("Iniciando prueba de Alerta...");
    }
    
    @Test
    @DisplayName("Test de creación básica de alerta")
    public void testCreacionAlerta() {
        System.out.println("Ejecutando prueba de creación básica de alerta");
        
        alerta.setId_alerta(1);
        alerta.setId_ropa(100);
        alerta.setMensaje("Stock bajo");
        alerta.setFecha_alerta(fechaPrueba);
        alerta.setNombre_producto("Polo Básico");
        alerta.setTipo_alerta("STOCK_BAJO");
        alerta.setEstado("ACTIVO");
        alerta.setUmbral(5);
        
        assertAll("Propiedades de la alerta",
            () -> assertEquals(1, alerta.getId_alerta(), "El ID de alerta debe ser 1"),
            () -> assertEquals(100, alerta.getId_ropa(), "El ID de ropa debe ser 100"),
            () -> assertEquals("Stock bajo", alerta.getMensaje(), "El mensaje debe coincidir"),
            () -> assertEquals(fechaPrueba, alerta.getFecha_alerta(), "La fecha debe coincidir"),
            () -> assertEquals("Polo Básico", alerta.getNombre_producto(), "El nombre del producto debe coincidir"),
            () -> assertEquals("STOCK_BAJO", alerta.getTipo_alerta(), "El tipo de alerta debe coincidir"),
            () -> assertEquals("ACTIVO", alerta.getEstado(), "El estado debe coincidir"),
            () -> assertEquals(5, alerta.getUmbral(), "El umbral debe ser 5")
        );
    }
    
    @Test
    @DisplayName("Test de validación de umbral")
    public void testValidacionUmbral() {
        System.out.println("Ejecutando prueba de validación de umbral");
        
        alerta.setUmbral(0);
        assertEquals(0, alerta.getUmbral(), "Debe permitir umbral en 0");
        
        alerta.setUmbral(100);
        assertEquals(100, alerta.getUmbral(), "Debe permitir umbral en 100");
    }
    
    @Test
    @DisplayName("Test de estados de alerta")
    public void testEstadosAlerta() {
        System.out.println("Ejecutando prueba de estados de alerta");
        
        alerta.setEstado("ACTIVO");
        assertEquals("ACTIVO", alerta.getEstado());
        
        alerta.setEstado("INACTIVO");
        assertEquals("INACTIVO", alerta.getEstado());
    }
} 