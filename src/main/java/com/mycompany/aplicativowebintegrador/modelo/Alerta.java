package com.mycompany.aplicativowebintegrador.modelo;

import java.util.Date;

public class Alerta {
    private int id_alerta;
    private int id_ropa;
    private String mensaje;
    private Date fecha_alerta;
    private String nombre_producto; // Campo adicional para mostrar el nombre del producto
    private String tipo_alerta;
    private String estado;
    private Integer umbral;

    // Enums para los tipos de alerta
    public enum TipoAlerta {
        STOCK_BAJO("stock_bajo"),
        CADUCIDAD_PROXIMA("caducidad_proxima"),
        MANUAL("manual");

        private final String valor;
        TipoAlerta(String valor) { this.valor = valor; }
        public String getValor() { return valor; }
    }

    public enum EstadoAlerta {
        ACTIVA("activa"),
        RESUELTA("resuelta");

        private final String valor;
        EstadoAlerta(String valor) { this.valor = valor; }
        public String getValor() { return valor; }
    }

    // Constructor vacío
    public Alerta() {
    }

    // Constructor completo
    public Alerta(int id_alerta, int id_ropa, String mensaje, Date fecha_alerta) {
        this.id_alerta = id_alerta;
        this.id_ropa = id_ropa;
        this.mensaje = mensaje;
        this.fecha_alerta = fecha_alerta;
    }

    // Getters y setters
    public int getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(int id_alerta) {
        this.id_alerta = id_alerta;
    }

    public int getId_ropa() {
        return id_ropa;
    }

    public void setId_ropa(int id_ropa) {
        this.id_ropa = id_ropa;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha_alerta() {
        return fecha_alerta;
    }

    public void setFecha_alerta(Date fecha_alerta) {
        this.fecha_alerta = fecha_alerta;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getTipo_alerta() { return tipo_alerta; }
    public void setTipo_alerta(String tipo_alerta) { this.tipo_alerta = tipo_alerta; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Integer getUmbral() { return umbral; }
    public void setUmbral(Integer umbral) { this.umbral = umbral; }
}
