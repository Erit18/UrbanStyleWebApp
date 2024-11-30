package com.mycompany.aplicativowebintegrador.modelo;

import java.io.Serializable;
import java.util.Date;

public abstract class EntidadBase implements Serializable {
    private Integer id;
    private Date fechaCreacion;
    private Date fechaModificacion;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public Date getFechaModificacion() {
        return fechaModificacion;
    }
    
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
} 