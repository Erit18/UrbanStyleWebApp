package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import java.util.List;

public class TestProveedorService extends TestServicioBase {
    private final IProveedorDAO proveedorDAO;
    
    public TestProveedorService(IProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }
    
    public void guardar(Proveedor proveedor) throws Exception {
        proveedorDAO.insertar(proveedor);
    }
    
    public void actualizar(Proveedor proveedor) throws Exception {
        validarId(proveedor.getId_proveedor());
        proveedorDAO.actualizar(proveedor);
    }
    
    public void eliminar(int id) throws Exception {
        validarId(id);
        proveedorDAO.eliminar(id);
    }
    
    public Proveedor obtenerPorId(int id) throws Exception {
        validarId(id);
        return proveedorDAO.obtenerPorId(id);
    }
    
    public List<Proveedor> obtenerTodos() throws Exception {
        return proveedorDAO.obtenerTodos();
    }
} 