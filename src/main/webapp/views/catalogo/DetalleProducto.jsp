<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.dao.ProductoDAO" %>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Producto" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogostyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylicarrito.css">
    <title>Detalle del Producto</title>
</head>
<body>
    <div id="header-placeholder"></div>
    
    <div class="envio-bar d-flex justify-content-center align-items-center">
        <span class="mx-2">
            <img src="${pageContext.request.contextPath}/views/Intranet/imagenes/peru.png" alt="Perú" width="24">
        </span>
        <span class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</span>
        <span class="mx-2">
            <img src="${pageContext.request.contextPath}/views/Intranet/imagenes/peru.png" alt="Avión" width="24">
        </span>
    </div>

    <%
        int productoId = Integer.parseInt(request.getParameter("id"));
        ProductoDAO productoDAO = new ProductoDAO();
        Producto producto = productoDAO.obtenerPorId(productoId);
        String imagePath = productoDAO.obtenerRutaImagen(producto);
    %>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <img src="${pageContext.request.contextPath}/<%= imagePath %>" class="img-fluid" alt="<%= producto.getNombre() %>" />
                </div>
            </div>

            <div class="col-md-6">
                <h1><%= producto.getNombre() %></h1>
                <h2 class="text-muted">S/ <%= String.format("%.2f", producto.getPrecio()) %></h2>
                <p><%= producto.getDescripcion() %></p>
                
                <div class="mb-3">
                    <label for="tallas" class="form-label">Tallas</label>
                    <div id="tallas" class="btn-group" role="group">
                        <button type="button" class="btn btn-outline-dark">S</button>
                        <button type="button" class="btn btn-outline-dark">M</button>
                        <button type="button" class="btn btn-outline-dark">L</button>
                    </div>
                </div>
                
                <div class="mb-4">
                    <button class="btn btn-dark w-100" onclick="añadirAlCarrito('<%= producto.getNombre() %>', <%= producto.getPrecio() %>, 'M', '<%= producto.getCategoria() %>')">
                        Añadir al Carrito
                    </button>
                </div>
            </div>
        </div>
    </div>

    <div id="footer-placeholder"></div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/ScriptC.js"></script>
    
    <script>
        fetch("fragments/header.html")
            .then(response => response.text())
            .then(data => document.getElementById("header-placeholder").innerHTML = data);

        fetch("fragments/footer.html")
            .then(response => response.text())
            .then(data => document.getElementById("footer-placeholder").innerHTML = data);
    </script>
</body>
</html> 