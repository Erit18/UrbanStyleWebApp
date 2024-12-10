<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Producto" %>
<%@ page import="com.mycompany.aplicativowebintegrador.dao.ProductoDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../css/catalogostyles.css">
    <title>Intranet</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
</head>
<body>
        <!--HEADER-->
        <div id="header-placeholder"></div>
        
    <div class="envio-bar d-flex justify-content-center align-items-center">
        <span class="mx-2">
          <img src="../Intranet/imagenes/peru.png" alt="Perú" width="24">
        </span>
        <marquee class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</marquee>
        <span class="mx-2">
          <img src="../Intranet/imagenes/peru.png" alt="Avión" width="24">
        </span>
      </div>
    <div class="container-fluid">
        <div class="row">
            <% 
                try {
                    ProductoDAO productoDAO = new ProductoDAO();
                    List<Producto> pantalones = productoDAO.obtenerProductosPorTipoYCategoria("pantalon", "mujer");
                    
                    for (Producto pantalon : pantalones) {
                        String imagePath = productoDAO.obtenerRutaImagen(pantalon);
            %>
            <div class="col-md-3">
                <div class="card full-width">
                    <a href="DetalleProducto.jsp?id=<%= pantalon.getId_ropa() %>">
                        <img src="${pageContext.request.contextPath}/<%= imagePath %>" 
                             class="card-img-top" 
                             alt="<%= pantalon.getNombre() %>"
                             onerror="this.src='${pageContext.request.contextPath}/views/Intranet/imagenes/default-product.jpg'">
                    </a>
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                            <span class="star">&#9733;</span>
                        </center>
                    </div>
                    <div class="card-body">
                        <h5 class="card-title"><%= pantalon.getNombre() %></h5>
                        <p class="card-text">S/ <%= String.format("%.2f", pantalon.getPrecio()) %></p>
                    </div>
                </div>
            </div>
            <% 
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
            %>
                    <div class="alert alert-danger" role="alert">
                        Error al cargar los productos. Por favor, intente más tarde.
                    </div>
            <%
                }
            %>

            </div>
        </div>

    <!--FOOTER (pie de página)-->
    <div id="footer-placeholder"></div>


<script>
    // Cargar el header
    fetch("../catalogo/fragments/header.html")
        .then(response => response.text())
        .then(data => document.getElementById("header-placeholder").innerHTML = data);

    // Cargar el footer
    fetch("../catalogo/fragments/footer.html")
        .then(response => response.text())
        .then(data => document.getElementById("footer-placeholder").innerHTML = data);
</script>
    

    <script src="../../js/script.js"></script>
</body>


