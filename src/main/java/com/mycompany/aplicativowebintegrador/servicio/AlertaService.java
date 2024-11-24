package com.mycompany.aplicativowebintegrador.servicio;

import com.mycompany.aplicativowebintegrador.dao.IAlertaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AlertaService {
    private static final Logger logger = LoggerFactory.getLogger(AlertaService.class);
    private final IAlertaDAO alertaDAO;

    public AlertaService(IAlertaDAO alertaDAO) {
        this.alertaDAO = alertaDAO;
    }

    public List<Alerta> listarAlertas() throws Exception {
        logger.debug("Obteniendo lista de alertas");
        try {
            return alertaDAO.listarTodas();
        } catch (Exception e) {
            logger.error("Error al listar alertas", e);
            throw new Exception("Error al obtener las alertas: " + e.getMessage());
        }
    }

    public Alerta obtenerAlerta(int id) throws Exception {
        logger.debug("Obteniendo alerta con ID: {}", id);
        try {
            Alerta alerta = alertaDAO.obtenerPorId(id);
            if (alerta == null) {
                throw new Exception("Alerta no encontrada");
            }
            return alerta;
        } catch (Exception e) {
            logger.error("Error al obtener alerta", e);
            throw new Exception("Error al obtener la alerta: " + e.getMessage());
        }
    }

    public boolean crearAlerta(Alerta alerta) throws Exception {
        logger.debug("Creando nueva alerta");
        try {
            return alertaDAO.crear(alerta);
        } catch (Exception e) {
            logger.error("Error al crear alerta", e);
            throw new Exception("Error al crear la alerta: " + e.getMessage());
        }
    }

    public boolean actualizarAlerta(Alerta alerta) throws Exception {
        logger.debug("Actualizando alerta ID: {}", alerta.getId_alerta());
        try {
            return alertaDAO.actualizar(alerta);
        } catch (Exception e) {
            logger.error("Error al actualizar alerta", e);
            throw new Exception("Error al actualizar la alerta: " + e.getMessage());
        }
    }

    public boolean eliminarAlerta(int id) throws Exception {
        logger.debug("Eliminando alerta ID: {}", id);
        try {
            return alertaDAO.eliminar(id);
        } catch (Exception e) {
            logger.error("Error al eliminar alerta", e);
            throw new Exception("Error al eliminar la alerta: " + e.getMessage());
        }
    }
} 