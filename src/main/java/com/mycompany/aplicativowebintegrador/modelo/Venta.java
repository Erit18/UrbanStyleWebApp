package com.mycompany.aplicativowebintegrador.modelo;

import java.util.Date;

public class Venta {
    private Producto producto;
    private int cantidad;
    private Date fecha;

    // Constructor, getters y setters
    public Venta(Producto producto, int cantidad, Date fecha) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
