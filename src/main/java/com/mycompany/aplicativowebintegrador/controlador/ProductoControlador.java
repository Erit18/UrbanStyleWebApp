package com.mycompany.aplicativowebintegrador.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mycompany.aplicativowebintegrador.modelo.Producto;

public class ProductoControlador {
    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    public void procesarProducto(Producto producto) {
        logger.info("Procesando producto: {}", producto.getNombre());
        // Lógica del método
        logger.debug("Producto procesado exitosamente");
    }
}
