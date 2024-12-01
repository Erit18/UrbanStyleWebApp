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
        <span class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</span>
        <span class="mx-2">
          <img src="../Intranet/imagenes/peru.png" alt="Avión" width="24">
        </span>
      </div>
    <div class="container-fluid">
        <div class="row">
            <% 
                try {
                    ProductoDAO productoDAO = new ProductoDAO();
                    List<Producto> poleras = productoDAO.obtenerProductosPorTipoYCategoria("polera", "hombre");
                    
                    for (Producto polera : poleras) {
                        String imagePath = productoDAO.obtenerRutaImagen(polera);
            %>
            <div class="col-md-3">
                <div class="card full-width">
                    <a href="DetalleProducto.jsp?id=<%= polera.getId_ropa() %>">
                        <img src="${pageContext.request.contextPath}/<%= imagePath %>" 
                             class="card-img-top" 
                             alt="<%= polera.getNombre() %>"
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
                        <h5 class="card-title"><%= polera.getNombre() %></h5>
                        <p class="card-text">S/ <%= String.format("%.2f", polera.getPrecio()) %></p>
                    </div>
                </div>
            </div>
            <% 
                    }
                } catch (SQLException e) {
                    // Manejar el error apropiadamente
                    e.printStackTrace();
            %>
                    <div class="alert alert-danger" role="alert">
                        Error al cargar los productos. Por favor, intente más tarde.
                    </div>
            <%
                }
            %>

            <div class="col-md-3"> 
                <div class="card full-width">
                    <a href="DetalleProducto.html">
                    <img src="imagenes/CatPoleras/1.JPG" class="card-img-top" alt="Producto 1">
                </a>
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                   <center><span>5/5</span>
                    <span class="star">&#9733;</span>
                    <span class="star">&#9733;</span>
                    <span class="star">&#9733;</span>
                    <span class="star">&#9733;</span>
                    <span class="star">&#9733;</span></center> 
                  </div>
                    <div class="card-body">
                        <h5 class="card-title">POLERA CROP</h5>
                        <p class="card-text">S/ 109.00</p>
                    </div>
                </div>
            </div>
           
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/2.JPG" class="card-img-top" alt="Producto 2">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE CROP PRELAVADA 🔥
                        </h5>
                        <p class="card-text">S/ 129.00
                        </p>
                    </div>
                </div>
            </div>
          
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/3.JPG" class="card-img-top" alt="Producto 3">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE CROP PRELAVADA 🔥</h5>
                        <p class="card-text">S/ 129.00</p>
                    </div>
                </div>
            </div>
          
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/4.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">POLERA CROP FREESTYLE</h5>
                        <p class="card-text">S/ 99.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/5.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE BOXY</h5>
                        <p class="card-text">S/ 89.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/6.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">POLERA COLECCIÓN CÁPSULA COLORS  </h5>
                        <p class="card-text">S/ 109.00 </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/7.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HARDCORE PLEASURE" 🕷 </h5>
                        <p class="card-text">S/ 90.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/8.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE BOXY </h5>
                        <p class="card-text">S/ 89.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/9.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">POLERA CROP FREESTYLE</h5>
                        <p class="card-text">S/ S/ 99.90</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/10.JPG" class="card-img-top" alt="Producto 1" data-hover="imagenes/1.avif">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE BOXY</h5>
                        <p class="card-text">S/ 89.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/11.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE BOXY</h5>
                        <p class="card-text">S/89.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/12.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">POLERA ARESDEUS </h5>
                        <p class="card-text">S/ 90.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/13.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE BOXY</h5>
                        <p class="card-text">S/ 89.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/14.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">HODDIE CROP </h5>
                        <p class="card-text">S/ 119.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/15.JPG" class="card-img-top" alt="Producto 4">
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>
                    <div class="card-body">
                        <h5 class="card-title">
                            HODDIE CROP
                            </h5>
                        <p class="card-text"> S/ 119.00</p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card full-width">
                    <img src="imagenes/CatPoleras/16.JPG" class="card-img-top" alt="Producto 4">  
                    <div class="rating" style="font-size: 1.5rem; text-align: left;">
                        <center><span>5/5</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span>
                         <span class="star">&#9733;</span></center> 
                       </div>                 
                    <div class="card-body">
                        <h5 class="card-title">
                            HODDIE OVERSIZE

                          </h5>
                        <p class="card-text">  S/ 119.00 </p>
                        
                    </div>
                </div>
            </div>
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

