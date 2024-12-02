package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@DisplayName("Pruebas del Servicio de Proveedor")
public class ProveedorServiceTest {
    
    @Mock
    private IProveedorDAO proveedorDAO;
    
    private ProveedorService proveedorService;
    private Proveedor proveedorTest;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        proveedorService = new ProveedorService(proveedorDAO);
        
        proveedorTest = new Proveedor();
        proveedorTest.setId_proveedor(1);
        proveedorTest.setNombre("Textiles Test S.A.");
        proveedorTest.setContacto("Juan Test");
        proveedorTest.setTelefono("987654321");
        proveedorTest.setEmail("test@textiles.com");
        proveedorTest.setDireccion("Av. Test 123");
        
        System.out.println("Iniciando prueba de servicio de proveedor...");
    }
    
    @Test
    @DisplayName("Guardar proveedor exitosamente")
    public void testGuardarProveedorExitoso() throws Exception {
        System.out.println("Ejecutando prueba de guardar proveedor");
        doNothing().when(proveedorDAO).insertar(any(Proveedor.class));
        
        assertDoesNotThrow(() -> proveedorService.guardar(proveedorTest));
        verify(proveedorDAO).insertar(proveedorTest);
    }
    
    @Test
    @DisplayName("Validar teléfono inválido")
    public void testValidarTelefonoInvalido() {
        System.out.println("Ejecutando prueba de teléfono inválido");
        proveedorTest.setTelefono("12345");
        
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> proveedorService.guardar(proveedorTest));
        assertEquals("El teléfono debe tener 9 dígitos", exception.getMessage());
    }
    
    @Test
    @DisplayName("Validar email inválido")
    public void testValidarEmailInvalido() {
        System.out.println("Ejecutando prueba de email inválido");
        proveedorTest.setEmail("emailinvalido");
        
        Exception exception = assertThrows(IllegalArgumentException.class, 
            () -> proveedorService.guardar(proveedorTest));
        assertEquals("Email inválido", exception.getMessage());
    }
    
    @Test
    @DisplayName("Listar todos los proveedores")
    public void testListarProveedores() throws Exception {
        System.out.println("Ejecutando prueba de listar proveedores");
        List<Proveedor> proveedores = Arrays.asList(proveedorTest);
        when(proveedorDAO.obtenerTodos()).thenReturn(proveedores);
        
        List<Proveedor> resultado = proveedorService.listarTodos();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proveedorDAO).obtenerTodos();
    }
    
    @Test
    @DisplayName("Obtener proveedor por ID")
    public void testObtenerProveedorPorId() throws Exception {
        System.out.println("Ejecutando prueba de obtener proveedor por ID");
        when(proveedorDAO.obtenerPorId(1)).thenReturn(proveedorTest);
        
        Proveedor resultado = proveedorService.obtenerPorId(1);
        
        assertNotNull(resultado);
        assertEquals(proveedorTest.getId_proveedor(), resultado.getId_proveedor());
        verify(proveedorDAO).obtenerPorId(1);
    }
    
    @Test
    @DisplayName("Eliminar proveedor")
    public void testEliminarProveedor() throws Exception {
        System.out.println("Ejecutando prueba de eliminar proveedor");
        doNothing().when(proveedorDAO).eliminar(1);
        
        assertDoesNotThrow(() -> proveedorService.eliminar(1));
        verify(proveedorDAO).eliminar(1);
    }
} 