package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.modelo.EntidadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServicioBase<T extends EntidadBase> implements ICrudService<T> {
    protected final Logger logger;
    
    protected ServicioBase() {
        this.logger = LoggerFactory.getLogger(getClass());
    }
    
    protected void validarEntidad(T entidad) {
        if (entidad == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
    }
    
    protected void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }
} 