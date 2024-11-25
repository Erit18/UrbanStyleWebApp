package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import java.sql.SQLException;
import java.util.List;

public interface IProveedorDAO {
    List<Proveedor> obtenerTodos() throws SQLException;
    Proveedor obtenerPorId(int id) throws SQLException;
    void insertar(Proveedor proveedor) throws SQLException;
    void actualizar(Proveedor proveedor) throws SQLException;
    void eliminar(int id) throws SQLException;
} 