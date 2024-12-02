package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

public class ProveedorDAOTest {

    private final IProveedorDAO proveedorDAO = new ProveedorDAO(); // Asegúrate de tener una implementación real

    @Test
    public void testInsertar() throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor Test");
        proveedor.setContacto("Contacto Test");
        proveedor.setTelefono("123456789");
        proveedor.setEmail("test@test.com");
        proveedor.setDireccion("Dirección Test");

        proveedorDAO.insertar(proveedor);
        assertNotNull(proveedor.getId_proveedor(), "El ID del proveedor no debería ser nulo después de insertar");
    }

    @Test
    public void testObtenerPorId() throws SQLException {
        Proveedor proveedor = proveedorDAO.obtenerPorId(1); // Asegúrate de que este ID exista
        assertNotNull(proveedor, "Debería obtener un proveedor con ID 1");
    }

    @Test
    public void testActualizar() throws SQLException {
        Proveedor proveedor = proveedorDAO.obtenerPorId(1); // Asegúrate de que este ID exista
        assertNotNull(proveedor, "Debería obtener un proveedor con ID 1");

        proveedor.setNombre("Proveedor Actualizado");
        proveedorDAO.actualizar(proveedor);

        Proveedor proveedorActualizado = proveedorDAO.obtenerPorId(1);
        assertEquals("Proveedor Actualizado", proveedorActualizado.getNombre(), "El nombre del proveedor debería haberse actualizado");
    }

    @Test
    public void testEliminar() throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor Para Eliminar");
        proveedor.setContacto("Contacto Eliminar");
        proveedor.setTelefono("987654321");
        proveedor.setEmail("eliminar@test.com");
        proveedor.setDireccion("Dirección Eliminar");

        proveedorDAO.insertar(proveedor);
        int id = proveedor.getId_proveedor();
        assertNotNull(id, "El ID del proveedor no debería ser nulo después de insertar");

        proveedorDAO.eliminar(id);
        Proveedor proveedorEliminado = proveedorDAO.obtenerPorId(id);
        assertNull(proveedorEliminado, "El proveedor debería ser nulo después de eliminar");
    }

    @Test
    public void testObtenerTodos() throws SQLException {
        List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
        assertFalse(proveedores.isEmpty(), "La lista de proveedores no debería estar vacía");
    }
} 