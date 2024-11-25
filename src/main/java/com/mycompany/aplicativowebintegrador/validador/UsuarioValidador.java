package com.mycompany.aplicativowebintegrador.validador;

import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import java.util.regex.Pattern;

public class UsuarioValidador {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public void validarRegistro(Usuario usuario) {
        validarNombre(usuario.getNombre());
        validarEmail(usuario.getEmail());
        validarPassword(usuario.getContraseña());
        validarRol(usuario.getRol());
    }

    public void validarActualizacion(Usuario usuario) {
        validarNombre(usuario.getNombre());
        validarEmail(usuario.getEmail());
        validarRol(usuario.getRol());
        
        if (usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
            validarPassword(usuario.getContraseña());
        }
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (nombre.length() < 2 || nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre debe tener entre 2 y 50 caracteres");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
    }

    private void validarPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }
    }

    private void validarRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new IllegalArgumentException("El rol es requerido");
        }
        String rolNormalizado = rol.toLowerCase().trim();
        if (!rolNormalizado.equals("administrador") && !rolNormalizado.equals("cliente")) {
            throw new IllegalArgumentException("Rol inválido");
        }
    }
} 