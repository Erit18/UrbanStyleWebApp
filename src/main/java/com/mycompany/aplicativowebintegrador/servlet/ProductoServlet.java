package com.mycompany.aplicativowebintegrador.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.mycompany.aplicativowebintegrador.servicio.ProductoServicio;
import com.mycompany.aplicativowebintegrador.modelo.Producto;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {
    private ProductoServicio productoServicio = new ProductoServicio();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("productos", productoServicio.obtenerProductos());
        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        Producto producto = new Producto(nombre, precio);
        productoServicio.agregarProducto(producto);
        response.sendRedirect(request.getContextPath() + "/productos");
    }
}
