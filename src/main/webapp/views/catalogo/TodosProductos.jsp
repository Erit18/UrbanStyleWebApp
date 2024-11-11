<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.dao.ProductoDAO" %>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Producto" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todos los Productos - DC Style</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleindex.css">
</head>
<body>
    <!-- Navbar (puedes reutilizar el mismo del index.jsp) -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <!-- ... código del navbar ... -->
    </nav>

    <div class="container-fluid mt-5">
        <h2 class="text-center mb-4 bold-text">TODOS LOS PRODUCTOS</h2>
        
        <div class="row">
            <%
                ProductoDAO productoDAO = new ProductoDAO();
                List<Producto> productos = productoDAO.obtenerTodos();
                for (Producto producto : productos) {
                    String imagePath = productoDAO.obtenerRutaImagen(producto);
            %>
            <div class="col-md-4 col-lg-3 mb-4">
                <div class="product-item">
                    <a href="DetalleProducto.html" style="text-decoration: none; color: #000;">
                        <img src="${pageContext.request.contextPath}/<%= imagePath %>" 
                             class="img-fluid" 
                             alt="<%= producto.getNombre() %>"
                             onerror="this.src='${pageContext.request.contextPath}/views/Intranet/imagenes/default-product.jpg'">
                        <div class="rating">
                            <span>5/5</span>
                            <span class="star">&#9733;</span><span class="star">&#9733;</span>
                            <span class="star">&#9733;</span><span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                        </div>
                        <h5><%= producto.getNombre() %></h5>
                        <h6>S/<%= String.format("%.2f", producto.getPrecio()) %></h6>
                    </a>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>

    <!-- Footer (puedes reutilizar el mismo del index.jsp) -->
    <footer class="footer-distributed">
        <!-- ... código del footer ... -->
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 