package com.mycompany.aplicativowebintegrador.validador;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import java.math.BigDecimal;

public class ProductoValidador {
    
    public void validar(Producto producto) throws IllegalArgumentException {
        validarNombre(producto);
        validarPrecio(producto);
        validarStock(producto);
        validarProveedor(producto);
    }
    
    private void validarNombre(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es requerido");
        }
    }
    
    private void validarPrecio(Producto producto) {
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio debe ser mayor o igual a 0");
        }
    }
    
    private void validarStock(Producto producto) {
        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
    }
    
    private void validarProveedor(Producto producto) {
        if (producto.getId_proveedor() <= 0) {
            throw new IllegalArgumentException("Debe seleccionar un proveedor válido");
        }
    }
} 