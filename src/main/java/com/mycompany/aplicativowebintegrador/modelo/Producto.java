package com.mycompany.aplicativowebintegrador.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Producto {
    private int id_ropa;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private int stock;
    private Date fecha_caducidad;
    private BigDecimal descuento;
    private int id_proveedor;
    private Date fecha_agregado;

    // Constructor completo
    public Producto(int id_ropa, String nombre, String descripcion, BigDecimal precio, String categoria, int stock, Date fecha_caducidad, BigDecimal descuento, int id_proveedor, Date fecha_agregado) {
        this.id_ropa = id_ropa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.fecha_caducidad = fecha_caducidad;
        this.descuento = descuento;
        this.id_proveedor = id_proveedor;
        this.fecha_agregado = fecha_agregado;
    }

    // Constructor sin ID (para nuevos productos)
    public Producto(String nombre, String descripcion, BigDecimal precio, String categoria, int stock, Date fecha_caducidad, BigDecimal descuento, int id_proveedor, Date fecha_agregado) {
        this(0, nombre, descripcion, precio, categoria, stock, fecha_caducidad, descuento, id_proveedor, fecha_agregado);
    }

    // Constructor vacío
    public Producto() {}

    // Getters y setters
    public int getId_ropa() {
        return id_ropa;
    }

    public void setId_ropa(int id_ropa) {
        this.id_ropa = id_ropa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
    }

    public void setFecha_caducidad(Date fecha_caducidad) {
        this.fecha_caducidad = fecha_caducidad;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public Date getFecha_agregado() {
        return fecha_agregado;
    }

    public void setFecha_agregado(Date fecha_agregado) {
        this.fecha_agregado = fecha_agregado;
    }
}