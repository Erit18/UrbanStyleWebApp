package com.mycompany.aplicativowebintegrador.modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contraseña;
    private String rol;

    // Constructor
    public Usuario() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email.toLowerCase().trim();
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        if (contraseña != null && contraseña.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        if (rol != null) {
            rol = rol.toLowerCase().trim();
            if (!"administrador".equals(rol) && !"cliente".equals(rol)) {
                throw new IllegalArgumentException("Rol inválido");
            }
            this.rol = rol;
        }
    }
}
