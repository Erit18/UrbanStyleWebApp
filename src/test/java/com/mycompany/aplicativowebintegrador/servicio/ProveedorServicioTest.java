package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.ProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProveedorServicioTest {

    @Mock
    private ProveedorDAO proveedorDAO;

    @InjectMocks
    private ProveedorServicio proveedorServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerProveedorPorId() throws SQLException {
        // Configurar el comportamiento simulado del DAO
        Proveedor proveedorEsperado = new Proveedor(
            1, 
            "Proveedor de prueba", 
            "Contacto de prueba", 
            "123456789", 
            "proveedor@ejemplo.com", 
            "Dirección de prueba"
        );

        when(proveedorDAO.obtenerPorId(1)).thenReturn(proveedorEsperado);

        // Llamar al método a probar
        Proveedor proveedorObtenido = proveedorServicio.obtenerProveedorPorId(1);

        // Verificar el resultado
        assertEquals(proveedorEsperado, proveedorObtenido);
    }
} 