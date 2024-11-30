package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.IAlertaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import java.util.List;

public class AlertaService extends ServicioBase<Alerta> {
    private final IAlertaDAO alertaDAO;
    
    public AlertaService(IAlertaDAO alertaDAO) {
        this.alertaDAO = alertaDAO;
    }
    
    @Override
    public Alerta obtenerPorId(Integer id) throws Exception {
        validarId(id);
        return alertaDAO.obtenerPorId(id);
    }
    
    @Override
    public List<Alerta> listarTodos() throws Exception {
        return alertaDAO.listarTodas();
    }
    
    @Override
    public void guardar(Alerta alerta) throws Exception {
        validarEntidad(alerta);
        validarAlerta(alerta);
        alertaDAO.crear(alerta);
    }
    
    @Override
    public void actualizar(Alerta alerta) throws Exception {
        validarEntidad(alerta);
        validarId(alerta.getId_alerta());
        validarAlerta(alerta);
        alertaDAO.actualizar(alerta);
    }
    
    @Override
    public void eliminar(Integer id) throws Exception {
        validarId(id);
        alertaDAO.eliminar(id);
    }
    
    private void validarAlerta(Alerta alerta) {
        if (alerta.getMensaje() == null || alerta.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la alerta es requerido");
        }
        if (alerta.getTipo_alerta() == null || alerta.getTipo_alerta().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de alerta es requerido");
        }
        if (alerta.getId_ropa() <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }
        if (alerta.getUmbral() < 0) {
            throw new IllegalArgumentException("El umbral no puede ser negativo");
        }
    }
} 