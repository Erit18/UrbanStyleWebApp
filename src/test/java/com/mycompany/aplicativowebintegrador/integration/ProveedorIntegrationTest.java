package com.mycompany.aplicativowebintegrador.integration;

import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.dao.ProveedorDAO;
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
        
        proveedorDAO = new ProveedorDAO();
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
        
        Proveedor proveedorInsertado = proveedores.stream()
            .filter(p -> p.getNombre().equals("Proveedor Integración"))
            .findFirst()
            .orElse(null);
        
        assertNotNull(proveedorInsertado, "El proveedor debería existir en la base de datos");
        assertNotNull(proveedorInsertado.getId_proveedor(), "El ID no debería ser nulo");
        proveedorPrueba.setId_proveedor(proveedorInsertado.getId_proveedor());
    }
    
    @Test
    @Order(2)
    @DisplayName("Test de integración: Obtener proveedor")
    void testIntegracionObtener() throws Exception {
        System.out.println("Ejecutando prueba de integración: Obtener proveedor");
        
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        assertFalse(proveedores.isEmpty(), "Debería haber al menos un proveedor");
        
        Proveedor primerProveedor = proveedores.get(0);
        Proveedor proveedorObtenido = proveedorService.obtenerPorId(primerProveedor.getId_proveedor());
        
        assertNotNull(proveedorObtenido, "Debería poder obtener el proveedor por ID");
        assertEquals(primerProveedor.getNombre(), proveedorObtenido.getNombre());
    }
    
    @Test
    @Order(3)
    @DisplayName("Test de integración: Actualizar proveedor")
    void testIntegracionActualizar() throws Exception {
        System.out.println("Ejecutando prueba de integración: Actualizar proveedor");
        
        // Primero insertamos un proveedor nuevo
        Proveedor proveedorParaActualizar = new Proveedor();
        proveedorParaActualizar.setNombre("Proveedor Original");
        proveedorParaActualizar.setContacto("Contacto Original");
        proveedorParaActualizar.setTelefono("987654321");
        proveedorParaActualizar.setEmail("actualizar@test.com");
        proveedorParaActualizar.setDireccion("Dirección Original");
        
        // Insertamos usando el servicio
        proveedorService.guardar(proveedorParaActualizar);
        
        // Buscamos el proveedor recién insertado para obtener su ID
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        Proveedor proveedorInsertado = proveedores.stream()
            .filter(p -> p.getNombre().equals("Proveedor Original"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("No se encontró el proveedor insertado"));
        
        // Verificamos que tenga ID válido
        assertNotNull(proveedorInsertado.getId_proveedor(), "El ID no debería ser nulo");
        assertTrue(proveedorInsertado.getId_proveedor() > 0, "El ID debería ser mayor que 0");
        
        // Guardamos el nombre original y el ID
        String nombreOriginal = proveedorInsertado.getNombre();
        int idProveedor = proveedorInsertado.getId_proveedor();
        
        // Modificamos los datos
        proveedorInsertado.setNombre("Proveedor Actualizado");
        proveedorInsertado.setContacto("Contacto Actualizado");
        
        // Realizamos la actualización
        proveedorService.actualizar(proveedorInsertado);
        
        // Verificamos la actualización
        Proveedor proveedorActualizado = proveedorService.obtenerPorId(idProveedor);
        assertNotNull(proveedorActualizado, "El proveedor debería existir después de actualizar");
        assertEquals("Proveedor Actualizado", proveedorActualizado.getNombre());
        assertNotEquals(nombreOriginal, proveedorActualizado.getNombre(), 
                       "El nombre actualizado debería ser diferente al original");
        
        System.out.println("Proveedor insertado con ID: " + idProveedor);
    }
    
    @Test
    @Order(4)
    @DisplayName("Test de integración: Eliminar proveedor")
    void testIntegracionEliminar() throws Exception {
        System.out.println("Ejecutando prueba de integración: Eliminar proveedor");
        
        List<Proveedor> proveedores = proveedorService.obtenerTodos();
        assertFalse(proveedores.isEmpty(), "Debería haber al menos un proveedor");
        
        Proveedor proveedorParaEliminar = proveedores.get(0);
        int idParaEliminar = proveedorParaEliminar.getId_proveedor();
        
        proveedorService.eliminar(idParaEliminar);
        
        Proveedor proveedorEliminado = proveedorService.obtenerPorId(idParaEliminar);
        assertNull(proveedorEliminado, "El proveedor debería haber sido eliminado");
    }
} 