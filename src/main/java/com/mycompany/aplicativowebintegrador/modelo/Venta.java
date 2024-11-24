package com.mycompany.aplicativowebintegrador.modelo;

import java.util.Date;
import java.util.List;

public class Venta {
    private String id;
    private Date fecha;
    private String nombreCliente;
    private List<DetalleVenta> detalles;
    private String productos;
    private double total;
    private String estado;

    public static class DetalleVenta {
        private Producto producto;
        private int cantidad;
        private double precioUnitario;

        // Constructor
        public DetalleVenta(Producto producto, int cantidad, double precioUnitario) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
        }

        // Getters y setters
        public Producto getProducto() { return producto; }
        public void setProducto(Producto producto) { this.producto = producto; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
        public double getSubtotal() { return cantidad * precioUnitario; }
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getProductos() { return productos; }
    public void setProductos(String productos) { this.productos = productos; }
}
