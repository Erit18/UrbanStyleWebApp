package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.config.TestDatabaseConfig;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductoDAOTest {
    
    private static ProductoDAO productoDAO;
    private static Connection conn;
    private static int idProductoPrueba;
    
    @BeforeAll
    static void setUp() throws SQLException {
        DatabaseConnection.setConfig(new TestDatabaseConfig());
        productoDAO = new ProductoDAO();
        conn = productoDAO.getConnection();
        
        // Limpiar datos existentes
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Alertas");
            stmt.execute("DELETE FROM Ropa");
            stmt.execute("DELETE FROM Proveedores WHERE id_proveedor = 1");
            
            // Insertar proveedor de prueba
            stmt.execute("INSERT INTO Proveedores (id_proveedor, nombre) VALUES (1, 'Proveedor Test')");
            
            // Insertar producto de prueba inicial
            stmt.execute("INSERT INTO Ropa (nombre, descripcion, precio, categoria, tipo_producto, stock, id_proveedor) " +
                        "VALUES ('Producto Test', 'Descripción Test', 100.00, 'Categoria Test', 'Tipo Test', 10, 1)");
            
            // Obtener el ID del producto insertado
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                idProductoPrueba = rs.getInt(1);
            }
        }
    }
    
    @Test
    @Order(1)
    void testInsertar() throws SQLException {
        // Primero verificamos cuántos productos hay inicialmente
        List<Producto> productosIniciales = productoDAO.obtenerTodos();
        int cantidadInicial = productosIniciales.size();
        
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción Test");
        producto.setPrecio(new BigDecimal("99.99"));
        producto.setStock(10);
        producto.setCategoria("Categoria Test");
        producto.setTipo_producto("Tipo Test");
        producto.setId_proveedor(1);
        
        productoDAO.insertar(producto);
        
        // Verificamos que se haya insertado obteniendo todos los productos
        List<Producto> productosDespues = productoDAO.obtenerTodos();
        assertEquals(cantidadInicial + 1, productosDespues.size());
        
        // Obtenemos el último producto insertado
        Producto ultimoProducto = productosDespues.get(productosDespues.size() - 1);
        idProductoPrueba = ultimoProducto.getId_ropa();
        assertNotNull(idProductoPrueba);
        assertTrue(idProductoPrueba > 0);
        
        // Guardamos el ID para las siguientes pruebas
        System.out.println("ID del producto insertado: " + idProductoPrueba);
    }
    
    @Test
    @Order(2)
    void testObtenerPorId() throws SQLException {
        System.out.println("Buscando producto con ID: " + idProductoPrueba);
        Producto producto = productoDAO.obtenerPorId(idProductoPrueba);
        assertNotNull(producto, "El producto no debería ser null");
        assertEquals("Producto Test", producto.getNombre());
    }
    
    @Test
    @Order(3)
    void testActualizar() throws SQLException {
        System.out.println("Actualizando producto con ID: " + idProductoPrueba);
        Producto producto = productoDAO.obtenerPorId(idProductoPrueba);
        assertNotNull(producto, "El producto no debería ser null antes de actualizar");
        
        producto.setNombre("Producto Actualizado");
        productoDAO.actualizar(producto);
        
        Producto productoActualizado = productoDAO.obtenerPorId(idProductoPrueba);
        assertNotNull(productoActualizado, "El producto actualizado no debería ser null");
        assertEquals("Producto Actualizado", productoActualizado.getNombre());
    }
    
    @Test
    @Order(4)
    void testObtenerTodos() throws SQLException {
        List<Producto> productos = productoDAO.obtenerTodos();
        assertFalse(productos.isEmpty());
        assertTrue(productos.size() >= 1);
    }
    
    @Test
    @Order(5)
    void testObtenerProductosPorTipoYCategoria() throws SQLException {
        List<Producto> productos = productoDAO.obtenerProductosPorTipoYCategoria("Tipo Test", "Categoria Test");
        assertFalse(productos.isEmpty());
        assertEquals("Categoria Test", productos.get(0).getCategoria());
        assertEquals("Tipo Test", productos.get(0).getTipo_producto());
    }
    
    @Test
    @Order(6)
    void testEliminar() throws SQLException {
        productoDAO.eliminar(idProductoPrueba);
        
        Producto productoEliminado = productoDAO.obtenerPorId(idProductoPrueba);
        assertNull(productoEliminado);
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