package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import java.util.List;

public class ProveedorService extends ServicioBase<Proveedor> {
    private final IProveedorDAO proveedorDAO;
    
    public ProveedorService(IProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }
    
    @Override
    public Proveedor obtenerPorId(Integer id) throws Exception {
        validarId(id);
        return proveedorDAO.obtenerPorId(id);
    }
    
    @Override
    public List<Proveedor> listarTodos() throws Exception {
        return proveedorDAO.obtenerTodos();
    }
    
    @Override
    public void guardar(Proveedor proveedor) throws Exception {
        validarEntidad(proveedor);
        validarProveedor(proveedor);
        proveedorDAO.insertar(proveedor);
    }
    
    @Override
    public void actualizar(Proveedor proveedor) throws Exception {
        validarEntidad(proveedor);
        validarId(proveedor.getId());
        validarProveedor(proveedor);
        proveedorDAO.actualizar(proveedor);
    }
    
    @Override
    public void eliminar(Integer id) throws Exception {
        validarId(id);
        proveedorDAO.eliminar(id);
    }
    
    private void validarProveedor(Proveedor proveedor) {
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proveedor es requerido");
        }
        if (proveedor.getTelefono() == null || !proveedor.getTelefono().matches("\\d{9}")) {
            throw new IllegalArgumentException("El teléfono debe tener 9 dígitos");
        }
        if (proveedor.getEmail() != null && !proveedor.getEmail().isEmpty() && 
            !proveedor.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
    }
} 