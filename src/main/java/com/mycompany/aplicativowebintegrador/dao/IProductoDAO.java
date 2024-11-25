package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import java.sql.SQLException;
import java.util.List;

public interface IProductoDAO {
    List<Producto> obtenerTodos() throws SQLException;
    Producto obtenerPorId(int id) throws SQLException;
    void insertar(Producto producto) throws SQLException;
    void actualizar(Producto producto) throws SQLException;
    void eliminar(int id) throws SQLException;
    List<Producto> obtenerProductosDestacados(int limite) throws SQLException;
    String obtenerRutaImagen(Producto producto);
} 