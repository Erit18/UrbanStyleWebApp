package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import java.util.List;

public interface IAlertaDAO {
    List<Alerta> listarTodas() throws Exception;
    Alerta obtenerPorId(int id) throws Exception;
    boolean crear(Alerta alerta) throws Exception;
    boolean actualizar(Alerta alerta) throws Exception;
    boolean eliminar(int id) throws Exception;
} 