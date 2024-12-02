package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DisplayName("Pruebas de la clase Venta")
public class VentaTest {
    
    private Venta venta;
    private Producto producto;
    private Date fechaPrueba;
    
    @BeforeEach
    void setUp() {
        venta = new Venta();
        producto = new Producto();
        producto.setId_ropa(1);
        producto.setNombre("Polo Test");
        producto.setPrecio(new BigDecimal("29.99"));
        fechaPrueba = new Date();
        System.out.println("Iniciando prueba de Venta...");
    }
    
    @Test
    @DisplayName("Test de creación básica de venta")
    public void testCreacionVenta() {
        System.out.println("Ejecutando prueba de creación básica de venta");
        
        venta.setId("V001");
        venta.setFecha(fechaPrueba);
        venta.setNombreCliente("Juan Pérez");
        venta.setEstado("COMPLETADA");
        venta.setTotal(29.99);
        
        assertAll("Propiedades de la venta",
            () -> assertEquals("V001", venta.getId(), "El ID debe ser V001"),
            () -> assertEquals(fechaPrueba, venta.getFecha(), "La fecha debe coincidir"),
            () -> assertEquals("Juan Pérez", venta.getNombreCliente(), "El nombre del cliente debe coincidir"),
            () -> assertEquals("COMPLETADA", venta.getEstado(), "El estado debe ser COMPLETADA"),
            () -> assertEquals(29.99, venta.getTotal(), 0.01, "El total debe ser 29.99")
        );
    }
    
    @Test
    @DisplayName("Test de detalles de venta")
    public void testDetallesVenta() {
        System.out.println("Ejecutando prueba de detalles de venta");
        
        List<Venta.DetalleVenta> detalles = new ArrayList<>();
        Venta.DetalleVenta detalle = new Venta.DetalleVenta(producto, 2, new BigDecimal("29.99").doubleValue());
        detalles.add(detalle);
        venta.setDetalles(detalles);
        
        assertAll("Propiedades del detalle de venta",
            () -> assertNotNull(venta.getDetalles(), "La lista de detalles no debe ser nula"),
            () -> assertEquals(1, venta.getDetalles().size(), "Debe haber un detalle"),
            () -> assertEquals(2, detalle.getCantidad(), "La cantidad debe ser 2"),
            () -> assertEquals(29.99, detalle.getPrecioUnitario(), 0.01, "El precio unitario debe ser 29.99"),
            () -> assertEquals(59.98, detalle.getSubtotal(), 0.01, "El subtotal debe ser 59.98")
        );
    }
    
    @Test
    @DisplayName("Test de cálculo de subtotal")
    public void testCalculoSubtotal() {
        System.out.println("Ejecutando prueba de cálculo de subtotal");
        
        Venta.DetalleVenta detalle = new Venta.DetalleVenta(producto, 3, new BigDecimal("29.99").doubleValue());
        assertEquals(89.97, detalle.getSubtotal(), 0.01, "El subtotal debe ser 89.97");
    }
    
    @Test
    @DisplayName("Test de múltiples detalles")
    public void testMultiplesDetalles() {
        System.out.println("Ejecutando prueba de múltiples detalles");
        
        List<Venta.DetalleVenta> detalles = new ArrayList<>();
        detalles.add(new Venta.DetalleVenta(producto, 2, new BigDecimal("29.99").doubleValue()));
        
        Producto producto2 = new Producto();
        producto2.setId_ropa(2);
        producto2.setNombre("Pantalón Test");
        producto2.setPrecio(new BigDecimal("49.99"));
        detalles.add(new Venta.DetalleVenta(producto2, 1, new BigDecimal("49.99").doubleValue()));
        
        venta.setDetalles(detalles);
        
        assertEquals(2, venta.getDetalles().size(), "Debe haber dos detalles");
        assertEquals(109.97, detalles.stream()
                .mapToDouble(Venta.DetalleVenta::getSubtotal)
                .sum(), 0.01, "El total debe ser 109.97");
    }
} 