package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.servicio.ProductoServicio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.BufferedReader;

@WebServlet("/api/productos/*")
public class ProductoServlet extends HttpServlet {
    private ProductoServicio productoServicio;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.productoServicio = new ProductoServicio();
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Producto> productos = productoServicio.obtenerTodosLosProductos();
                
                // Verificar cada producto antes de convertirlo a JSON
                for (Producto p : productos) {
                    System.out.println("DEBUG - Verificando producto antes de JSON:");
                    System.out.println("  id_ropa: " + p.getId_ropa());
                    System.out.println("  nombre: " + p.getNombre());
                    System.out.println("  toString: " + p.toString());
                }
                
                String json = gson.toJson(productos);
                System.out.println("DEBUG - JSON final: " + json);
                
                response.getWriter().write(json);
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Producto producto = productoServicio.obtenerProductoPorId(id);
                if (producto != null) {
                    String json = gson.toJson(producto);
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson("Producto no encontrado"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error: " + e.getMessage());
            response.getWriter().write(gson.toJson(error));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String requestBody = sb.toString();
            System.out.println("DEBUG - Cuerpo de la solicitud: " + requestBody);
            
            Producto producto = gson.fromJson(requestBody, Producto.class);
            System.out.println("DEBUG - Producto parseado: " + producto);
            
            // Validaciones básicas
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del producto es requerido");
            }
            if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El precio debe ser mayor o igual a 0");
            }
            if (producto.getStock() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo");
            }
            if (producto.getId_proveedor() <= 0) {
                throw new IllegalArgumentException("Debe seleccionar un proveedor válido");
            }

            productoServicio.agregarProducto(producto);
            response.setStatus(HttpServletResponse.SC_CREATED);
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Producto creado exitosamente");
            response.getWriter().write(gson.toJson(successResponse));
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al crear producto: " + e.getMessage());
            response.getWriter().write(gson.toJson(errorResponse));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Producto producto = gson.fromJson(request.getReader(), Producto.class);
        try {
            productoServicio.actualizarProducto(producto);
            response.getWriter().write(gson.toJson("Producto actualizado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error al actualizar producto: " + e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        try {
            productoServicio.eliminarProducto(id);
            response.getWriter().write(gson.toJson("Producto eliminado exitosamente"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error al eliminar producto: " + e.getMessage()));
        }
    }
}
