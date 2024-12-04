package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.modelo.Producto;
import com.mycompany.aplicativowebintegrador.servicio.ProductoServicio;
import com.mycompany.aplicativowebintegrador.validador.ProductoValidador;
import com.mycompany.aplicativowebintegrador.util.ResponseBuilder;
import com.mycompany.aplicativowebintegrador.dao.ProductoDAO;
import com.mycompany.aplicativowebintegrador.dao.IProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.instrument.Counter;
import com.mycompany.aplicativowebintegrador.config.MetricsConfig;

@WebServlet("/api/productos/*")
public class ProductoServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductoServlet.class);
    
    private ProductoServicio productoServicio;
    private ProductoValidador validador;
    private ResponseBuilder responseBuilder;

    private final Counter productViews = Counter
        .builder("product_views_total")
        .description("Total de vistas de productos")
        .register(MetricsConfig.getRegistry());
        
    private final Counter productCreates = Counter
        .builder("product_operations_total")
        .tags("operation", "create")
        .description("Operaciones de creación de productos")
        .register(MetricsConfig.getRegistry());
        
    private final Counter productUpdates = Counter
        .builder("product_operations_total")
        .tags("operation", "update")
        .description("Operaciones de actualización de productos")
        .register(MetricsConfig.getRegistry());
        
    private final Counter productDeletes = Counter
        .builder("product_operations_total")
        .tags("operation", "delete")
        .description("Operaciones de eliminación de productos")
        .register(MetricsConfig.getRegistry());

    @Override
    public void init() throws ServletException {
        super.init();
        IProductoDAO productoDAO = new ProductoDAO();
        this.productoServicio = new ProductoServicio(productoDAO);
        this.validador = new ProductoValidador();
        this.responseBuilder = new ResponseBuilder();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Producto> productos = productoServicio.obtenerTodosLosProductos();
                response.getWriter().write(responseBuilder.getGson().toJson(productos));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Producto producto = productoServicio.obtenerProductoPorId(id);
                if (producto != null) {
                    response.getWriter().write(responseBuilder.getGson().toJson(producto));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    responseBuilder.enviarRespuestaError(response, new Exception("Producto no encontrado"));
                }
            }
            productViews.increment();
        } catch (Exception e) {
            logger.error("Error al obtener productos", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Producto producto = obtenerProductoDeRequest(request);
            validador.validar(producto);
            productoServicio.agregarProducto(producto);
            responseBuilder.enviarRespuestaCreada(response, "Producto creado exitosamente");
            productCreates.increment();
        } catch (Exception e) {
            logger.error("Error al crear producto", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Producto producto = obtenerProductoDeRequest(request);
            validador.validar(producto);
            productoServicio.actualizarProducto(producto);
            responseBuilder.enviarRespuestaExitosa(response, "Producto actualizado exitosamente");
            productUpdates.increment();
        } catch (Exception e) {
            logger.error("Error al actualizar producto", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            productDeletes.increment();
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            productoServicio.eliminarProducto(id);
            responseBuilder.enviarRespuestaExitosa(response, "Producto eliminado exitosamente");
        } catch (Exception e) {
            logger.error("Error al eliminar producto", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    private Producto obtenerProductoDeRequest(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return responseBuilder.getGson().fromJson(sb.toString(), Producto.class);
    }
}
