<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%@ page import="com.mycompany.aplicativowebintegrador.dao.ProductoDAO" %>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Producto" %>
<%@ page import="java.math.BigDecimal" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/catalogostyles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylicarrito.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <style>
        .precio-original {
            text-decoration: line-through;
            color: #666;
            font-size: 1.1rem;
            margin-bottom: 0.5rem;
        }
        
        .precio-normal {
            color: #333;
            font-size: 2rem;
            font-weight: bold;
        }
        
        .precio-descuento {
            color: #ff0000;
            font-size: 2rem;
            font-weight: bold;
        }
        
        .badge-descuento {
            background: linear-gradient(45deg, #ff0000, #ff4444);
            color: white;
            padding: 0.5rem 1rem;
            border-radius: 25px;
            font-size: 1.1rem;
            position: absolute;
            top: 20px;
            right: 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
            animation: pulse 2s infinite;
            z-index: 1;
        }
        
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.05); }
            100% { transform: scale(1); }
        }
        
        .precio-container {
            background-color: #f8f9fa;
            padding: 1.5rem;
            border-radius: 10px;
            margin: 1.5rem 0;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }
        
        .ahorro-texto {
            color: #28a745;
            font-size: 1.1rem;
            margin-top: 0.8rem;
            padding: 0.5rem;
            background-color: #e8f5e9;
            border-radius: 5px;
            display: inline-block;
        }
        
        .tiempo-limite {
            background-color: #212529;
            color: white;
            padding: 1rem;
            border-radius: 5px;
            margin: 1rem 0;
            text-align: center;
            font-size: 1.2rem;
            letter-spacing: 1px;
            text-transform: uppercase;
            width: 100%;
            display: block;
        }

        .producto-titulo {
            font-size: 2.5rem;
            color: #333;
            margin-bottom: 1.5rem;
        }

        .producto-descripcion {
            font-size: 1.1rem;
            color: #666;
            line-height: 1.6;
            padding: 1.5rem;
            background-color: #f8f9fa;
            border-radius: 10px;
            margin: 1.5rem 0;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
        }

        .producto-descripcion-titulo {
            font-size: 1.3rem;
            color: #333;
            font-weight: 600;
            margin-bottom: 1rem;
        }

        .tallas-container {
            margin: 2rem 0;
        }

        .tallas-container .btn-group {
            width: 100%;
            gap: 10px;
        }

        .tallas-container .btn {
            flex: 1;
            padding: 0.8rem;
            font-size: 1.1rem;
        }

        .btn-carrito, .tiempo-limite {
            padding: 1rem;
            font-size: 1.2rem;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            width: 100%;
            margin: 0.5rem 0;
            border-radius: 5px;
        }

        .btn-carrito:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .stock-alert {
            background-color: #fff3cd;
            border: 1px solid #ffeeba;
            color: #856404;
            padding: 1rem;
            border-radius: 5px;
            margin-top: 1rem;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
        }

        .dropdown:hover > .dropdown-menu {
            display: block;
        }

        .dropdown-submenu {
            position: absolute;
            left: 100%;
            top: 0;
        }

        .dropdown-item.dropdown-toggle {
            position: relative;
        }

        .dropdown-item.dropdown-toggle::after {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
        }

        .nav-item.dropdown:hover > .dropdown-menu,
        .dropdown-item:hover > .dropdown-submenu {
            display: block;
        }

        /* Ajustes específicos para submenús */
        .dropdown-submenu {
            top: 0;
            left: 100%;
            margin-top: -1px;
        }

        /* Estilos para el hover */
        .dropdown-item:hover {
            background-color: #f8f9fa;
        }

        /* Asegurar que los submenús permanezcan visibles durante el hover */
        .dropdown-menu li:hover > .dropdown-submenu {
            display: block;
        }
    </style>
    <title>Detalle del Producto</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/views/Intranet/imagenes/Logo.jpeg" alt="Logo" class="logo">
            </a>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" id="catalogoDropdown">
                            CATÁLOGO ▼
                        </a>
                        <ul class="dropdown-menu">
                            <li class="nav-item dropdown">
                                <a class="dropdown-item dropdown-toggle" href="#">
                                    Hombre
                                </a>
                                <ul class="dropdown-menu dropdown-submenu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolera.jsp">Poleras</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolos.jsp">Polos</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPantalones.jsp">Pantalones</a></li>
                                </ul>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="dropdown-item dropdown-toggle" href="#">
                                    Mujer
                                </a>
                                <ul class="dropdown-menu dropdown-submenu">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolerasM.jsp">Poleras</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolosM.jsp">Polos</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPantalonesM.jsp">Pantalones</a></li>
                                </ul>
                            </li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatAccesorios.html">Accesorios</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/nosotros/Nosotros.html">NOSOTROS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/SCliente/Servicio-Cliente.html">SERVICIO AL CLIENTE</a>
                    </li>
                </ul>
                
                <form class="d-flex mx-auto" style="width: 35%;">
                    <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Search">
                    <button class="btn" type="submit">
                        <i class="bi bi-search"></i>
                    </button>
                </form>

                <ul class="navbar-nav ms-auto">
                    <% if (session.getAttribute("usuario") != null) { %>
                        <li class="nav-item dropdown">
                            <a class="nav-link" href="#" id="userDropdown">
                                Bienvenido, <%= ((Usuario)session.getAttribute("usuario")).getNombre() %>
                            </a>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Cerrar sesión</a>
                            </div>
                        </li>
                    <% } else { %>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/views/Intranet/Intranet.jsp">Log In</a>
                        </li>
                    <% } %>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/views/catalogo/CarritoCompras.jsp">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-cart4" viewBox="0 0 16 16">
                                <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5M3.14 5l.5 2H5V5zM6 5v2h2V5zm3 0v2h2V5zm3 0v2h1.36l.5-2zm1.11 3H12v2h.61zM11 8H9v2h2zM8 8H6v2h2zM5 8H3.89l.5 2H5zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0m9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0"/>
                            </svg>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    
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
        
        // Calcular precio con descuento si existe
        BigDecimal precioOriginal = producto.getPrecio();
        BigDecimal descuento = producto.getDescuento();
        BigDecimal precioFinal = precioOriginal;
        if (descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal descuentoAmount = precioOriginal.multiply(descuento.divide(new BigDecimal("100")));
            precioFinal = precioOriginal.subtract(descuentoAmount);
        }
    %>

    <div class="container mt-5">
        <div class="row">
            <div class="col-md-6 position-relative">
                <% if (descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) { %>
                    <span class="badge-descuento">-<%= descuento %>% OFF</span>
                <% } %>
                <div class="mb-3">
                    <img src="${pageContext.request.contextPath}/<%= imagePath %>" class="img-fluid" alt="<%= producto.getNombre() %>" />
                </div>
            </div>

            <div class="col-md-6">
                <h1 class="producto-titulo"><%= producto.getNombre() %></h1>
                
                <div class="precio-container">
                    <% if (descuento != null && descuento.compareTo(BigDecimal.ZERO) > 0) { %>
                        <div class="precio-original">Precio normal: S/ <%= String.format("%.2f", precioOriginal) %></div>
                        <div class="precio-descuento">S/ <%= String.format("%.2f", precioFinal) %></div>
                        <div class="ahorro-texto">
                            <i class="fas fa-tags"></i> ¡Ahorras S/ <%= String.format("%.2f", precioOriginal.subtract(precioFinal)) %>!
                        </div>
                    <% } else { %>
                        <div class="precio-normal">S/ <%= String.format("%.2f", precioOriginal) %></div>
                    <% } %>
                </div>
                
                <% 
                // Determinar si mostrar tallas y qué tipo de tallas mostrar
                String tipoProducto = producto.getTipo_producto();
                boolean mostrarTallas = tipoProducto != null && !tipoProducto.equals("accesorio");
                String[] tallas = null;
                String labelTallas = "Tallas disponibles";
                
                if (mostrarTallas) {
                    switch(tipoProducto.toLowerCase()) {
                        case "calzado":
                            tallas = new String[]{"35", "36", "37", "38", "39", "40", "41", "42", "43"};
                            labelTallas = "Tallas disponibles (EUR)";
                            break;
                        case "pantalon":
                            tallas = new String[]{"28", "30", "32", "34", "36", "38", "40"};
                            labelTallas = "Tallas disponibles (Cintura)";
                            break;
                        case "polo":
                        case "polera":
                            tallas = new String[]{"XS", "S", "M", "L", "XL", "XXL"};
                            break;
                    }
                %>
                    <div class="tallas-container">
                        <label for="tallas" class="form-label"><%= labelTallas %></label>
                        <div id="tallas" class="btn-group" role="group">
                            <% for (String talla : tallas) { %>
                                <button type="button" class="btn btn-outline-dark talla-btn" data-talla="<%= talla %>"><%= talla %></button>
                            <% } %>
                        </div>
                    </div>
                <% } %>
                
                <button class="btn btn-dark btn-carrito" 
                        onclick="añadirAlCarrito(
                            '<%= producto.getNombre() %>', 
                            <%= precioFinal %>, 
                            <%= mostrarTallas ? "obtenerTallaSeleccionada()" : "null" %>, 
                            '<%= producto.getCategoria() %>', 
                            '<%= producto.getTipo_producto() != null ? producto.getTipo_producto() : "No especificado" %>'
                        )">
                    <i class="fas fa-shopping-cart"></i> Añadir al Carrito
                </button>
                
                <% if (producto.getStock() < 10) { %>
                    <div class="stock-alert">
                        <i class="fas fa-exclamation-triangle"></i>
                        ¡Últimas <%= producto.getStock() %> unidades disponibles!
                    </div>
                <% } %>

                <div class="producto-descripcion">
                    <div class="producto-descripcion-titulo">Descripción del Producto</div>
                    <%= producto.getDescripcion() %>
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

        // Agregar manejo de selección de tallas
        let tallaSeleccionada = null;
        const tallaBtns = document.querySelectorAll('.talla-btn');
        
        tallaBtns.forEach(btn => {
            btn.addEventListener('click', function() {
                // Remover clase activa de todos los botones
                tallaBtns.forEach(b => b.classList.remove('active'));
                // Agregar clase activa al botón seleccionado
                this.classList.add('active');
                tallaSeleccionada = this.getAttribute('data-talla');
            });
        });

        function obtenerTallaSeleccionada() {
            if (!tallaSeleccionada) {
                alert('Por favor, selecciona una talla');
                return false;
            }
            return tallaSeleccionada;
        }
    </script>
</body>
</html> 