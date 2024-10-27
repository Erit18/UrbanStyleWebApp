package com.mycompany.aplicativowebintegrador.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String categoria;
    private int stock;
    private Date fechaCaducidad;
    private BigDecimal descuento;
    private int idProveedor;
    private Date fechaAgregado;

    // Constructor completo
    public Producto(int id, String nombre, String descripcion, BigDecimal precio, String categoria, int stock, Date fechaCaducidad, BigDecimal descuento, int idProveedor, Date fechaAgregado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.fechaCaducidad = fechaCaducidad;
        this.descuento = descuento;
        this.idProveedor = idProveedor;
        this.fechaAgregado = fechaAgregado;
    }

    // Constructor sin ID (para nuevos productos)
    public Producto(String nombre, String descripcion, BigDecimal precio, String categoria, int stock, Date fechaCaducidad, BigDecimal descuento, int idProveedor, Date fechaAgregado) {
        this(0, nombre, descripcion, precio, categoria, stock, fechaCaducidad, descuento, idProveedor, fechaAgregado);
    }

    // Constructor vacío
    public Producto() {}

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
