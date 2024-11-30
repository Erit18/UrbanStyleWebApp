package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.modelo.EntidadBase;
import java.util.List;

public interface ICrudService<T extends EntidadBase> {
    T obtenerPorId(Integer id) throws Exception;
    List<T> listarTodos() throws Exception;
    void guardar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Integer id) throws Exception;
} 