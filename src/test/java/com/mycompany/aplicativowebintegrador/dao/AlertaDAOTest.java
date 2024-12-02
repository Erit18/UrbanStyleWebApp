package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import com.mycompany.aplicativowebintegrador.config.TestDatabaseConfig;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AlertaDAOTest {
    
    private static AlertaDAO alertaDAO;
    private static Connection conn;
    private static int idProductoPrueba;
    
    @BeforeAll
    static void setUp() throws SQLException {
        DatabaseConnection.setConfig(new TestDatabaseConfig());
        alertaDAO = new AlertaDAO();
        conn = alertaDAO.getConnection();
        
        // Limpiar datos existentes
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Alertas");
            stmt.execute("DELETE FROM Ropa");
            stmt.execute("DELETE FROM Proveedores WHERE id_proveedor = 1");
            
            // Insertar proveedor de prueba
            stmt.execute("INSERT INTO Proveedores (id_proveedor, nombre) VALUES (1, 'Proveedor Test')");
            
            // Insertar producto de prueba con todos los campos requeridos
            stmt.execute("INSERT INTO Ropa (nombre, descripcion, precio, stock, categoria, tipo_producto, id_proveedor) " +
                        "VALUES ('Producto Test', 'Descripción Test', 100.00, 10, 'Categoria Test', 'Tipo Test', 1)");
            
            // Obtener el ID del producto insertado
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                idProductoPrueba = rs.getInt(1);
            }
        }
    }
    
    @Test
    @Order(1)
    void testCrear() throws SQLException {
        Alerta alerta = new Alerta();
        alerta.setId_ropa(idProductoPrueba);
        alerta.setMensaje("Test mensaje");
        alerta.setTipo_alerta("STOCK_BAJO");
        alerta.setEstado("ACTIVA");
        alerta.setUmbral(10);
        
        boolean resultado = alertaDAO.crear(alerta);
        assertTrue(resultado);
    }
    
    @Test
    @Order(2)
    void testListarTodas() throws SQLException {
        List<Alerta> alertas = alertaDAO.listarTodas();
        assertTrue(!alertas.isEmpty());
        assertEquals("Test mensaje", alertas.get(0).getMensaje());
    }
    
    @Test
    @Order(3)
    void testObtenerPorId() throws SQLException {
        // Primero crear una alerta si no existe
        testCrear();
        
        List<Alerta> alertas = alertaDAO.listarTodas();
        assertFalse(alertas.isEmpty(), "Debe haber al menos una alerta");
        
        int id = alertas.get(0).getId_alerta();
        Alerta alerta = alertaDAO.obtenerPorId(id);
        
        assertNotNull(alerta);
        assertEquals("Test mensaje", alerta.getMensaje());
    }
    
    @Test
    @Order(4)
    void testActualizar() throws SQLException {
        // Primero crear una alerta si no existe
        testCrear();
        
        List<Alerta> alertas = alertaDAO.listarTodas();
        assertFalse(alertas.isEmpty(), "Debe haber al menos una alerta");
        
        Alerta alerta = alertas.get(0);
        alerta.setMensaje("Mensaje actualizado");
        
        boolean resultado = alertaDAO.actualizar(alerta);
        assertTrue(resultado);
        
        Alerta alertaActualizada = alertaDAO.obtenerPorId(alerta.getId_alerta());
        assertEquals("Mensaje actualizado", alertaActualizada.getMensaje());
    }
    
    @Test
    @Order(5)
    void testEliminar() throws SQLException {
        // Primero crear una alerta si no existe
        testCrear();
        
        List<Alerta> alertas = alertaDAO.listarTodas();
        assertFalse(alertas.isEmpty(), "Debe haber al menos una alerta");
        
        int id = alertas.get(0).getId_alerta();
        boolean resultado = alertaDAO.eliminar(id);
        
        assertTrue(resultado);
        assertNull(alertaDAO.obtenerPorId(id));
    }
    
    @AfterAll
    static void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM Alertas");
                stmt.execute("DELETE FROM Ropa");
                stmt.execute("DELETE FROM Proveedores WHERE id_proveedor = 1");
            }
            conn.close();
        }
    }
} 