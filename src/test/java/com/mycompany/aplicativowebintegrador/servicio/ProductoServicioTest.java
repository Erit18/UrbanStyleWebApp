package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductoServicioTest {

    @Mock
    private ProductoDAO productoDAO;

    @InjectMocks
    private ProductoServicio productoServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerProductoPorId() throws SQLException {
        // Configurar el comportamiento simulado del DAO
        Producto productoEsperado = new Producto(
            1, 
            "Producto de prueba", 
            "Descripción de prueba", 
            new BigDecimal("99.99"), 
            "Categoría de prueba", 
            10, 
            new Date(), 
            new BigDecimal("10.00"), 
            1, 
            new Date()
        );

        when(productoDAO.obtenerPorId(1)).thenReturn(productoEsperado);

        // Llamar al método a probar
        Producto productoObtenido = productoServicio.obtenerProductoPorId(1);

        // Verificar el resultado
        assertEquals(productoEsperado, productoObtenido);
    }
} 