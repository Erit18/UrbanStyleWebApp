<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleindex.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/stylicarrito.css">
    <title>Carrito de Compras</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
   
<!--HEADER-->

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
                    <div class="dropdown-menu">
                        <div class="dropdown-item" id="hombreDropdown">
                            Hombre >
                            <div class="dropdown-menu submenu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolera.jsp">Poleras</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolos.jsp">Polos</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPantalones.jsp">Pantalones</a>
                            </div>
                        </div>
                        <div class="dropdown-item" id="mujerDropdown">
                            Mujer >
                            <div class="dropdown-menu submenu">
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolerasM.jsp">Poleras</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPolosM.jsp">Polos</a>
                                <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatPantalonesM.jsp">Pantalones</a>
                            </div>
                        </div>
                        <a class="dropdown-item" href="${pageContext.request.contextPath}/views/catalogo/CatAccesorios.html">Accesorios</a>
                    </div>
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

<!-- Agregar estos estilos específicos -->
<style>
.dropdown-menu {
    display: none;
    position: absolute;
}

.nav-item:hover > .dropdown-menu {
    display: block;
}

.dropdown-item {
    position: relative;
}

.dropdown-item:hover > .dropdown-menu.submenu {
    display: block;
    position: absolute;
    left: 100%;
    top: 0;
    margin-top: 0;
}

/* Estilos para el menú desplegable */
.navbar .dropdown:hover > .dropdown-menu {
    display: block;
    margin-top: 0;
}

.navbar .dropdown-menu {
    margin-top: 0;
}

.submenu {
    display: none;
    position: absolute;
    left: 100%;
    top: 0;
    margin-top: 0;
    background-color: white;
    border: 1px solid rgba(0,0,0,.15);
    border-radius: 0.25rem;
}

.dropdown-item:hover .submenu {
    display: block;
}
</style>

<!-- Banner de envíos -->
<div class="envio-bar text-center">
    <span class="envio-text">🇵🇪 ENVÍOS GRATIS A TODO EL PERÚ 🇵🇪</span>
</div>




      <main>
        <section class="carrito">
            <h1><b>CARRITO DE COMPRAS</b></h1>
            <div class="productos" id="productos"></div>
            <div class="resumen">
                <h2><b>RESUMEN DEL PEDIDO</b></h2>
                <div class="resumen-detalles">
                    <p>SUBTOTAL: <span>S/0</span></p>
                    <p>DESCUENTO (20%): <span class="descuento">-S/0</span></p>
                    <p>ENTREGA: <span>S/0</span></p>
                </div>
                <h3>TOTAL: <span>S/0</span></h3>
                <!-- Cambiamos el onclick por un id -->
                <button id="btnFinalizarCompra" class="finalizar btn btn-primary">FINALIZAR COMPRA</button>
                <button class="btn btn-danger" onclick="vaciarCarrito()">Vaciar Carrito</button>
            </div>
        </section>
    </main>


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
    
    
</body>
<script src="../../js/script.js"></script>
<script src="../../js/ScriptC.js"></script>

<script>
    // Asegurarnos de que el DOM esté cargado
    document.addEventListener('DOMContentLoaded', function() {
        // Inicializar el carrito
        renderCarrito();
        
        // Agregar el event listener al botón
        const btnFinalizarCompra = document.getElementById('btnFinalizarCompra');
        if (btnFinalizarCompra) {
            btnFinalizarCompra.addEventListener('click', function() {
                const carrito = JSON.parse(localStorage.getItem('cart')) || [];
                console.log('Carrito:', carrito); // Debug
                
                if (carrito.length === 0) {
                    mostrarMensaje('El carrito está vacío');
                    return;
                }
                
                console.log('Redirigiendo...'); // Debug
                window.location.href = 'FinalizarCompra.jsp';
            });
        }
    });
</script>

