package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.IVentaDAO;
import com.mycompany.aplicativowebintegrador.dao.VentaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Venta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class VentaService {
    private static final Logger logger = LoggerFactory.getLogger(VentaService.class);
    private final IVentaDAO ventaDAO;

    public VentaService() {
        this.ventaDAO = new VentaDAO();
    }

    public VentaService(IVentaDAO ventaDAO) {
        this.ventaDAO = ventaDAO;
    }

    public List<Venta> buscarVentas(Date fechaInicio, Date fechaFin, String categoria) throws Exception {
        logger.debug("Buscando ventas con filtros - Fecha Inicio: {}, Fecha Fin: {}, Categoría: {}", 
                    fechaInicio, fechaFin, categoria);
        
        try {
            return ventaDAO.buscarVentas(fechaInicio, fechaFin, categoria);
        } catch (Exception e) {
            logger.error("Error al buscar ventas", e);
            throw new Exception("Error al buscar ventas: " + e.getMessage());
        }
    }
} 