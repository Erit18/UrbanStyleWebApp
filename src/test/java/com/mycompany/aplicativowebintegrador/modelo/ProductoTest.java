package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.math.BigDecimal;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Producto")
public class ProductoTest {
    
    private Producto producto;
    private Date fechaPrueba;
    
    @BeforeEach
    void setUp() {
        producto = new Producto();
        fechaPrueba = new Date();
        System.out.println("Iniciando prueba de Producto...");
    }
    
    @Test
    @DisplayName("Test de creación básica de producto")
    public void testCreacionProducto() {
        System.out.println("Ejecutando prueba de creación básica de producto");
        
        // Configuración del producto
        producto.setId_ropa(1);
        producto.setNombre("Polo Básico");
        producto.setPrecio(new BigDecimal("29.99"));
        producto.setStock(100);
        
        // Verificaciones
        assertAll("Propiedades del producto",
            () -> assertEquals(1, producto.getId_ropa(), "El ID debe ser 1"),
            () -> assertEquals("Polo Básico", producto.getNombre(), "El nombre debe ser 'Polo Básico'"),
            () -> assertEquals(new BigDecimal("29.99"), producto.getPrecio(), "El precio debe ser 29.99"),
            () -> assertEquals(100, producto.getStock(), "El stock debe ser 100")
        );
        
        System.out.println("Prueba de creación básica completada exitosamente");
    }
    
    @Test
    @DisplayName("Test de producto con todos los atributos")
    public void testProductoCompleto() {
        System.out.println("Ejecutando prueba de producto completo");
        
        // Configuración del producto completo
        producto.setId_ropa(1);
        producto.setNombre("Polo Básico");
        producto.setDescripcion("Polo de algodón");
        producto.setPrecio(new BigDecimal("29.99"));
        producto.setCategoria("Ropa");
        producto.setTipo_producto("Polo");
        producto.setStock(100);
        producto.setFecha_caducidad(fechaPrueba);
        producto.setDescuento(new BigDecimal("0.10"));
        producto.setId_proveedor(1);
        producto.setFecha_agregado(fechaPrueba);
        
        // Verificaciones detalladas
        assertAll("Verificación completa del producto",
            () -> assertNotNull(producto, "El producto no debe ser nulo"),
            () -> assertEquals("Polo Básico", producto.getNombre(), "Nombre incorrecto"),
            () -> assertEquals("Polo de algodón", producto.getDescripcion(), "Descripción incorrecta"),
            () -> assertEquals(new BigDecimal("29.99"), producto.getPrecio(), "Precio incorrecto"),
            () -> assertEquals("Ropa", producto.getCategoria(), "Categoría incorrecta"),
            () -> assertEquals("Polo", producto.getTipo_producto(), "Tipo de producto incorrecto"),
            () -> assertEquals(100, producto.getStock(), "Stock incorrecto"),
            () -> assertEquals(fechaPrueba, producto.getFecha_caducidad(), "Fecha de caducidad incorrecta"),
            () -> assertEquals(new BigDecimal("0.10"), producto.getDescuento(), "Descuento incorrecto"),
            () -> assertEquals(1, producto.getId_proveedor(), "ID de proveedor incorrecto"),
            () -> assertEquals(fechaPrueba, producto.getFecha_agregado(), "Fecha de agregado incorrecta")
        );
        
        System.out.println("Prueba de producto completo finalizada exitosamente");
    }
    
    @Test
    @DisplayName("Test de valores límite de precio")
    public void testValoresLimitePrecio() {
        System.out.println("Ejecutando prueba de valores límite de precio");
        
        // Prueba con precio mínimo
        producto.setPrecio(BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, producto.getPrecio(), "El precio mínimo debe ser 0");
        
        // Prueba con precio grande
        producto.setPrecio(new BigDecimal("9999.99"));
        assertEquals(new BigDecimal("9999.99"), producto.getPrecio(), "El precio debe manejar valores grandes");
        
        System.out.println("Prueba de valores límite de precio completada");
    }
    
    @Test
    @DisplayName("Test de valores límite de stock")
    public void testValoresLimiteStock() {
        System.out.println("Ejecutando prueba de valores límite de stock");
        
        // Prueba con stock en cero
        producto.setStock(0);
        assertEquals(0, producto.getStock(), "El stock debe permitir valor cero");
        
        // Prueba con stock máximo
        producto.setStock(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, producto.getStock(), "El stock debe manejar valores máximos");
        
        System.out.println("Prueba de valores límite de stock completada");
    }
}