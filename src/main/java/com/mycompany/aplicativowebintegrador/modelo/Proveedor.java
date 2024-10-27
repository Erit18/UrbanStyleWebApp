package com.mycompany.aplicativowebintegrador.modelo;

import java.util.Date;

public class Proveedor {
    private int id_proveedor; // Cambiar de 'id' a 'id_proveedor'
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private Date fechaRegistro;

    // Constructor completo
    public Proveedor(int id_proveedor, String nombre, String contacto, String telefono, String email, String direccion) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    // Constructor sin ID (para nuevos proveedores)
    public Proveedor(String nombre, String contacto, String telefono, String email, String direccion) {
        this(0, nombre, contacto, telefono, email, direccion);
    }

    // Constructor vacío
    public Proveedor() {
    }

    // Getters y setters
    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
