package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.ProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import java.sql.SQLException;

public class ProveedorServicio {
    private ProveedorDAO proveedorDAO;

    public ProveedorServicio() {
        this.proveedorDAO = new ProveedorDAO();
    }

    public Proveedor obtenerProveedorPorId(int id) throws SQLException {
        return proveedorDAO.obtenerPorId(id);
    }
} 