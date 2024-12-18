package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.AlertaDAO;
import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.dao.IProductoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.validador.ProductoValidador;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductoServicio {
    private static final Logger logger = LoggerFactory.getLogger(ProductoServicio.class);
    
    private final IProductoDAO productoDAO;
    private final ProductoValidador validador;
    private final AlertaDAO alertaDAO;

    public ProductoServicio() {
        this.productoDAO = new ProductoDAO();
        this.validador = new ProductoValidador();
        this.alertaDAO = new AlertaDAO();
    }

    public ProductoServicio(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
        this.validador = new ProductoValidador();
        this.alertaDAO = new AlertaDAO();
    }

    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        logger.debug("Obteniendo todos los productos");
        return productoDAO.obtenerTodos();
    }

    public Producto obtenerProductoPorId(int id) throws SQLException {
        logger.debug("Obteniendo producto con ID: {}", id);
        return productoDAO.obtenerPorId(id);
    }

    public void agregarProducto(Producto producto) throws SQLException {
        logger.debug("Agregando nuevo producto: {}", producto.getNombre());
        validador.validar(producto);
        productoDAO.insertar(producto);
    }

    public void actualizarProducto(Producto producto) throws SQLException {
        logger.debug("Actualizando producto con ID: {}", producto.getId_ropa());
        validador.validar(producto);
        productoDAO.actualizar(producto);
        alertaDAO.actualizarEstadoAlertasPorProducto(producto.getId_ropa(), producto.getStock());
    }

    public void eliminarProducto(int id) throws SQLException {
        logger.debug("Eliminando producto con ID: {}", id);
        productoDAO.eliminar(id);
    }
}
