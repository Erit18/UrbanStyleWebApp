<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            background-color: #343a40;
            color: white;
            padding: 0.8rem;
            border-radius: 5px;
            margin-top: 1rem;
            text-align: center;
            font-size: 0.9rem;
            letter-spacing: 0.5px;
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
            margin: 1.5rem 0;
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

        .btn-carrito {
            padding: 1rem;
            font-size: 1.2rem;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
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
    </style>
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
                        <div class="tiempo-limite">
                            <i class="fas fa-clock"></i> ¡Oferta por tiempo limitado! 🔥
                        </div>
                    <% } else { %>
                        <div class="precio-normal">S/ <%= String.format("%.2f", precioOriginal) %></div>
                    <% } %>
                </div>
                
                <p class="producto-descripcion"><%= producto.getDescripcion() %></p>
                
                <div class="tallas-container">
                    <label for="tallas" class="form-label">Tallas disponibles</label>
                    <div id="tallas" class="btn-group" role="group">
                        <button type="button" class="btn btn-outline-dark">S</button>
                        <button type="button" class="btn btn-outline-dark">M</button>
                        <button type="button" class="btn btn-outline-dark">L</button>
                    </div>
                </div>
                
                <button class="btn btn-dark w-100 btn-carrito" 
                        onclick="añadirAlCarrito('<%= producto.getNombre() %>', <%= precioFinal %>, 'M', '<%= producto.getCategoria() %>')">
                    <i class="fas fa-shopping-cart"></i> Añadir al Carrito
                </button>
                
                <% if (producto.getStock() < 10) { %>
                    <div class="stock-alert">
                        <i class="fas fa-exclamation-triangle"></i>
                        ¡Últimas <%= producto.getStock() %> unidades disponibles!
                    </div>
                <% } %>
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