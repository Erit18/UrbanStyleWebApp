package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Venta;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IVentaDAO {
    List<Venta> buscarVentas(Date fechaInicio, Date fechaFin, String categoria) throws SQLException;
} 