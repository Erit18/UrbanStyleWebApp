package com.mycompany.aplicativowebintegrador.integration;

import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.dao.TestProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import com.mycompany.aplicativowebintegrador.service.TestProveedorService;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import com.mycompany.aplicativowebintegrador.config.TestDatabaseConfig;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Pruebas de Integración de Proveedor")
public class ProveedorIntegrationTest {
    
    private static IProveedorDAO proveedorDAO;
    private static TestProveedorService proveedorService;
    private static Proveedor proveedorPrueba;
    
    @BeforeAll
    static void setUp() {
        DatabaseConnection.setConfig(new TestDatabaseConfig());
        
        proveedorDAO = new TestProveedorDAO();
        proveedorService = new TestProveedorService(proveedorDAO);
        
        proveedorPrueba = new Proveedor();
        proveedorPrueba.setNombre("Proveedor Integración");
        proveedorPrueba.setContacto("Contacto Integración");
        proveedorPrueba.setTelefono("987654321");
        proveedorPrueba.setEmail("integracion@test.com");
        proveedorPrueba.setDireccion("Dirección de prueba");
        
        System.out.println("Iniciando pruebas de integración...");
    }
    
    @Test
    @Order(1)
    @DisplayName("Test de integración: Insertar proveedor")
    void testIntegracionInsertar() throws Exception {
        System.out.println("Ejecutando prueba de integración: Insertar proveedor");
        
        int cantidadInicial = proveedorService.obtenerTodos().size();
        proveedorService.guardar(proveedorPrueba);
        
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        assertEquals(cantidadInicial + 1, proveedores.size());
        
        Proveedor proveedorGuardado = proveedores.stream()
            .filter(p -> p.getNombre().equals("Proveedor Integración"))
            .findFirst()
            .orElseThrow();
        
        assertNotNull(proveedorGuardado.getId_proveedor(), "El ID no debería ser nulo");
        proveedorPrueba.setId_proveedor(proveedorGuardado.getId_proveedor());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test de integración: Obtener proveedor")
    void testIntegracionObtener() throws Exception {
        System.out.println("Ejecutando prueba de integración: Obtener proveedor");
        
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        assertFalse(proveedores.isEmpty());
        
        // Obtener el primer proveedor
        Proveedor primerProveedor = proveedores.get(0);
        Proveedor proveedorObtenido = proveedorService.obtenerPorId(primerProveedor.getId_proveedor());
        
        assertNotNull(proveedorObtenido);
        assertEquals(primerProveedor.getNombre(), proveedorObtenido.getNombre());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test de integración: Actualizar proveedor")
    void testIntegracionActualizar() throws Exception {
        System.out.println("Ejecutando prueba de integración: Actualizar proveedor");
        
        // Primero insertamos un proveedor nuevo
        Proveedor proveedorParaActualizar = new Proveedor();
        proveedorParaActualizar.setNombre("Proveedor Para Actualizar");
        proveedorParaActualizar.setContacto("Contacto Original");
        proveedorParaActualizar.setTelefono("987654321");
        proveedorParaActualizar.setEmail("actualizar@test.com");
        proveedorParaActualizar.setDireccion("Dirección Original");
        
        // Insertamos usando el servicio en lugar del DAO
        proveedorService.guardar(proveedorParaActualizar);
        System.out.println("Proveedor insertado con ID: " + proveedorParaActualizar.getId_proveedor());
        
        // Verificamos que se guardó correctamente
        assertNotNull(proveedorParaActualizar.getId_proveedor(), "El ID no debería ser nulo después de guardar");
        assertTrue(proveedorParaActualizar.getId_proveedor() > 0, "El ID debería ser mayor que 0");
        
        // Obtenemos el proveedor recién insertado
        Proveedor proveedorRecuperado = proveedorService.obtenerPorId(proveedorParaActualizar.getId_proveedor());
        assertNotNull(proveedorRecuperado, "Debería poder recuperar el proveedor insertado");
        
        // Modificamos los datos
        proveedorRecuperado.setNombre("Proveedor Actualizado");
        proveedorRecuperado.setContacto("Contacto Actualizado");
        
        try {
            // Imprimimos información de debug
            System.out.println("Intentando actualizar proveedor con ID: " + proveedorRecuperado.getId_proveedor());
            System.out.println("Datos del proveedor antes de actualizar: " + proveedorRecuperado);
            
            // Realizamos la actualización
            proveedorService.actualizar(proveedorRecuperado);
            System.out.println("Actualización exitosa para el ID: " + proveedorRecuperado.getId_proveedor());
            
            // Verificamos la actualización
            Proveedor proveedorVerificacion = proveedorService.obtenerPorId(proveedorRecuperado.getId_proveedor());
            assertNotNull(proveedorVerificacion, "El proveedor debería existir después de actualizar");
            assertEquals("Proveedor Actualizado", proveedorVerificacion.getNombre());
            assertEquals("Contacto Actualizado", proveedorVerificacion.getContacto());
            
        } catch (Exception e) {
            System.err.println("Error durante la actualización: " + e.getMessage());
            System.err.println("ID del proveedor: " + proveedorRecuperado.getId_proveedor());
            System.err.println("Detalles del proveedor: " + proveedorRecuperado);
            throw e;
        }
    }
    
    @Test
    @Order(4)
    @DisplayName("Test de integración: Eliminar proveedor")
    void testIntegracionEliminar() throws Exception {
        System.out.println("Ejecutando prueba de integración: Eliminar proveedor");
        
        // Primero insertamos un nuevo proveedor para eliminarlo
        Proveedor proveedorParaEliminar = new Proveedor();
        proveedorParaEliminar.setNombre("Proveedor Para Eliminar");
        proveedorParaEliminar.setContacto("Contacto Eliminar");
        proveedorParaEliminar.setTelefono("987654321");
        proveedorParaEliminar.setEmail("eliminar@test.com");
        proveedorParaEliminar.setDireccion("Dirección Eliminar");
        
        proveedorService.guardar(proveedorParaEliminar);
        int cantidadInicial = proveedorService.obtenerTodos().size();
        
        proveedorService.eliminar(proveedorParaEliminar.getId_proveedor());
        
        List<Proveedor> proveedoresDespues = proveedorService.obtenerTodos();
        assertEquals(cantidadInicial - 1, proveedoresDespues.size());
    }
} 