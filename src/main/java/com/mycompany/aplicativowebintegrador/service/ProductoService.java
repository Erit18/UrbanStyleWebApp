package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IProductoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import java.util.List;

public class ProductoService extends ServicioBase<Producto> {
    private final IProductoDAO productoDAO;
    
    public ProductoService(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }
    
    @Override
    public Producto obtenerPorId(Integer id) throws Exception {
        validarId(id);
        return productoDAO.obtenerPorId(id);
    }
    
    @Override
    public List<Producto> listarTodos() throws Exception {
        return productoDAO.obtenerTodos();
    }
    
    @Override
    public void guardar(Producto producto) throws Exception {
        validarEntidad(producto);
        validarProducto(producto);
        productoDAO.insertar(producto);
    }
    
    @Override
    public void actualizar(Producto producto) throws Exception {
        validarEntidad(producto);
        validarId(producto.getId());
        validarProducto(producto);
        productoDAO.actualizar(producto);
    }
    
    @Override
    public void eliminar(Integer id) throws Exception {
        validarId(id);
        productoDAO.eliminar(id);
    }
    
    // Métodos específicos de ProductoService
    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
        if (producto.getPrecio() == null || producto.getPrecio().doubleValue() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
    
    public List<Producto> obtenerProductosDestacados(int limite) throws Exception {
        return productoDAO.obtenerProductosDestacados(limite);
    }
} 