package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import java.sql.SQLException;
import java.util.List;

public class ProductoServicio {
    private ProductoDAO productoDAO;

    public ProductoServicio() {
        this.productoDAO = new ProductoDAO();
    }

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        return productoDAO.obtenerTodos();
    }

    public Producto obtenerProductoPorId(int id) throws SQLException {
        return productoDAO.obtenerPorId(id);
    }

    public void agregarProducto(Producto producto) throws SQLException {
        // Aquí podrías agregar validaciones antes de insertar
        productoDAO.insertar(producto);
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        // Aquí podrías agregar validaciones antes de actualizar
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(int id) throws SQLException {
        productoDAO.eliminar(id);
    }
}
