<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Producto" %>
<%@ page import="com.mycompany.aplicativowebintegrador.dao.ProductoDAO" %>
<%@ page import="java.util.List" %>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css?family=Inknut+Antiqua&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleindex.css">
    <title>Inicio</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand d-flex align-items-center" href="index.jsp">
                <img src="${pageContext.request.contextPath}/views/Intranet/imagenes/Logo.jpeg" alt="Logo" class="logo" >
            </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item dropdown mx-4">
                <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                CATÁLOGO    
                </a>
                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li class="nav-item dropdown mx-4">
                  <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    Hombre    
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <li><a class="dropdown-item" href="views/catalogo/CatPolera.jsp">Poleras</a></li>
                <li><a class="dropdown-item" href="views/catalogo/CatPolos.jsp">Polos</a></li>
                <li><a class="dropdown-item" href="views/catalogo/CatPantalones.jsp">Pantalones</a></li>
                </ul>
                </li>
                <li class="nav-item dropdown mx-4">
                    <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                      Mujer   
                      </a>
                      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <li><a class="dropdown-item" href="views/catalogo/CatPolerasM.jsp">Poleras</a></li>
                  <li><a class="dropdown-item" href="views/catalogo/CatPolosM.jsp">Polos</a></li>
                  <li><a class="dropdown-item" href="views/catalogo/CatPantalonesM.jsp">Pantalones</a></li>
                  </ul>
                  </li>
                  <li class="nav-item dropdown mx-4">
                    <li><a class="dropdown-item" href="views/catalogo/CatAccesorios.html">Accesorios<a></li>
                  </li>
             
                </ul>
            </li>
            <li class="nav-item mx-4" >
                <a class="nav-link active" aria-current="page" href="nosotros/Nosotros.html">NOSOTROS</a>
            </li>
            
            <li class="nav-item mx-4">
                <a class="nav-link active" href="SCliente/Servicio-Cliente.html">SERVICIO AL CLIENTE</a>
            </li>
            </ul>
            <form class="d-flex" style="width: 35%;">
                <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Search" style="height: 45px; font-size: 1.1rem;">
                <button class="btn d-flex align-items-center justify-content-center" type="submit" style="height: 45px; width: 45px;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                    </svg>
                </button>
            </form>
            
            
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <% if (session.getAttribute("usuario") != null) { %>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Bienvenido, <%= ((Usuario)session.getAttribute("usuario")).getNombre() %>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Cerrar sesión</a></li>
                        </ul>
                    </li>
                <% } else { %>
                    <li class="nav-item">
                        <a class="nav-link active" href="views/Intranet/Intranet.jsp">Log In</a>
                    </li>
                <% } %>
                <li class="nav-item">
                    <a class="nav-link" href="views/catalogo/CarritoCompras.jsp">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                            <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                        </svg>
                    </a>
                </li>
            </ul>
    </div>
        </div>
    </nav>
    <div class="envio-bar d-flex justify-content-center align-items-center">
        <span class="mx-2">
          <img src="views/Intranet/imagenes/peru.png" alt="Perú" width="24">
        </span>
        <span class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</span>
        <span class="mx-2">
          <img src="views/Intranet/imagenes/peru.png" alt="Avión" width="24">
        </span>
      </div>
  <main>
    <div class="banner">
        <img src="views/Intranet/imagenes/Banner.png" class="img-fluid w-100" alt="Banner de promoción">
      </div>
      <div class="container-fluid text-center ">
        <div class="row justify-content-center">

          <div class="col-6 col-md-3 category-item">
            <a href="views/catalogo/CatPolos.jsp" style="color: black; text-decoration: none;" >
              <img src="views/Intranet/imagenes/Polo.png" class="img-fluid" alt="Polos" >
              <h5 >POLOS</h5>
            </a>
          </div>

          <div class="col-6 col-md-3 category-item ">
            <a href="views/catalogo/CatPantalones.jsp" style="color: black; text-decoration: none;">
              <img src="views/Intranet/imagenes/Jean.png" class="img-fluid" alt="Pantalones">
              <h5>PANTALONES</h5>
            </a>
          </div>

          <div class="col-6 col-md-3 category-item">
            <a href="views/catalogo/CatPolerasM.jsp" style="color: black; text-decoration: none;">
              <img src="views/Intranet/imagenes/Polera.png" class="img-fluid" alt="Poleras">
              <h5>POLERAS</h5>
            </a>
          </div>

          <div class="col-6 col-md-3 category-item">
            <a href="views/catalogo/CatAccesorios.html" style="color: black; text-decoration: none;" >
              <img src="views/Intranet/imagenes/Accesorios.png" class="img-fluid" alt="Accesorios">
              <h5>ACCESORIOS</h5>
            </a>
          </div>
        </div>
      </div>
      </div>

    <div class="container-fluid best-sellers">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="text-left bold-text">PRODUCTOS DESTACADOS</h2>
            <h2 class="text-right bold-text">
                <a href="${pageContext.request.contextPath}/views/catalogo/TodosProductos.jsp" 
                   style="text-decoration: none; color: black;">Ver Todo</a>
            </h2>
        </div>
        <div class="product-container">
            <% 
                ProductoDAO productoDAO = new ProductoDAO();
                List<Producto> productos = productoDAO.obtenerProductosDestacados(5);
                for (Producto producto : productos) {
                    String imagePath = productoDAO.obtenerRutaImagen(producto);
            %>
            <div class="product-item">
                <a href="views/catalogo/DetalleProducto.jsp?id=<%= producto.getId_ropa() %>" 
                   style="text-decoration: none; color: #000;">
                    <img src="${pageContext.request.contextPath}/<%= imagePath %>" 
                         class="img-fluid" 
                         alt="<%= producto.getNombre() %>"
                         onerror="this.src='${pageContext.request.contextPath}/views/Intranet/imagenes/default-product.jpg'">
                    <h5><%= producto.getNombre() %></h5>
                    <h6>S/<%= String.format("%.2f", producto.getPrecio()) %></h6>
                </a>
            </div>
            <% } %>
        </div>
    </div>

 
      
</main>
<footer class="footer-distributed">
    <div class="footer-left">
        <img src="views/Intranet/imagenes/Logo.jpeg" alt="Logo" class="logo" >
        <p class="footer-company-name">Copyright © 2024 <strong>DC STYLE</strong> Reservados todos los derechos</p>
    </div>
    <div class="footer-center">
        <div>
            <i class="fa fa-map-marker"></i>
            <p><span>Ubicación</span> Los Olivos, UTP 2024</p>
        </div>
        <div>
            <i class="fa fa-phone"></i>
            <p>+51 456 789 067</p>
        </div>
        <div>
            <i class="fa fa-envelope"></i>
            <p><a href="mailto:sagar00001.co@gmail.com">DcStyle@empresa.com</a></p>
        </div>
    </div>
    <div class="footer-right">
        <p class="footer-company-about">
            <span>Sobre la empresa</span>
            <strong>DC Style</strong> Descubre las ropas en tendencia de este año 2024
        </p>
        <div class="footer-icons">
            <a href="#"><i class="fa fa-facebook"></i></a>
            <a href="#"><i class="fa fa-instagram"></i></a>
            <a href="#"><i class="fa fa-linkedin"></i></a>
            <a href="#"><i class="fa fa-twitter"></i></a>
            <a href="#"><i class="fa fa-youtube"></i></a>
        </div>
    </div>
</footer>
    

    <script src="js/script.js"></script>
    <!-- Añade esto justo antes del cierre del body -->
    <script>
        fetch('${pageContext.request.contextPath}/visita?pagina=index')
            .then(response => console.log('Visita registrada'))
            .catch(error => console.error('Error:', error));
    </script>
</body>



</html>
