package com.mycompany.aplicativowebintegrador.service;

public class TestServicioBase {
    protected void validarId(int id) {
        // Para pruebas, aceptamos cualquier ID positivo
        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
    }
} 