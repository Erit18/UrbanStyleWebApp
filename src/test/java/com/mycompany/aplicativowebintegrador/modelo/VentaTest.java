package com.mycompany.aplicativowebintegrador.modelo;

import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VentaTest {

    @Test
    public void testVentaConstructorAndGetters() {
        Producto producto = new Producto(); // Asegúrate de que Producto tiene un constructor adecuado
        int cantidad = 5;
        Date fecha = new Date();

        Venta venta = new Venta(producto, cantidad, fecha);

        assertEquals(producto, venta.getProducto());
        assertEquals(cantidad, venta.getCantidad());
        assertEquals(fecha, venta.getFecha());
    }

    @Test
    public void testSetters() {
        Producto producto = new Producto();
        int cantidad = 5;
        Date fecha = new Date();

        Venta venta = new Venta(null, 0, null);

        venta.setProducto(producto);
        venta.setCantidad(cantidad);
        venta.setFecha(fecha);

        assertEquals(producto, venta.getProducto());
        assertEquals(cantidad, venta.getCantidad());
        assertEquals(fecha, venta.getFecha());
    }
} 