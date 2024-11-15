<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%
    // Verificación de seguridad
    Usuario currentUser = (Usuario) session.getAttribute("usuario");
    if (currentUser == null) {
        System.out.println("No hay usuario en sesión");
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        return;
    }
    
    String rolNormalizado = currentUser.getRol().trim().toLowerCase();
    System.out.println("Verificando acceso a Dashboard");
    System.out.println("Usuario: " + currentUser.getEmail());
    System.out.println("Rol: [" + currentUser.getRol() + "]");
    System.out.println("Rol normalizado: [" + rolNormalizado + "]");
    
    if (!"administrador".equals(rolNormalizado)) {
        System.out.println("Acceso denegado - no es administrador");
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        return;
    }
    
    System.out.println("Acceso permitido a Dashboard");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            <div class="user-info">
                <span class="user-name">UrbanStyle</span>
                <span class="user-role">Admin</span>
            </div>
        </div>
        <nav class="menu">
            <a href="#"><i class="bi bi-graph-up"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProductos.jsp"><i class="bi bi-box-seam"></i> Gestión de Productos</a>
            <a href="#"><i class="bi bi-cart-check"></i> Gestión de Pedidos</a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionUsuarios.jsp">
                <i class="bi bi-people"></i> Gestión de Usuarios
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProveedores.jsp">
                <i class="bi bi-truck"></i> Gestión de Proveedores
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionAlertas.jsp">
                <i class="bi bi-exclamation-triangle"></i> Alertas de Inventario
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/ReporteVentas.jsp">
                <i class="bi bi-bar-chart-line"></i> Reportes de Ventas
            </a>
        </nav>
        <div class="sidebar-footer">
            <div class="user-info">
                <span>Bienvenido, <%= currentUser.getNombre() %> (<%= currentUser.getRol() %>)</span>
            </div>
            <div class="logout">
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                </a>
            </div>
        </div>
    </div>

    <div class="main-content">
        <h1>Dashboard de Ventas e Inventario</h1>
        <div class="contentD">
            <!-- Gráficos arriba -->
            <div class="dashboard-right">
                <div class="chart-container">
                    <canvas id="ventasChart"></canvas>
                </div>
                <div class="chart-container">
                    <canvas id="productosChart"></canvas>
                </div>
            </div>
            
            <!-- Tarjetas abajo -->
            <div class="dashboard-left">
                <div class="dashboard-cards">
                    <!-- Tarjeta de Ventas Totales -->
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Ventas Totales</h5>
                            <p class="card-number">S/. 25,430</p>
                            <p class="card-trend positive">+15% vs mes anterior</p>
                        </div>
                    </div>
                    
                    <!-- Tarjeta de Pedidos Pendientes -->
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Pedidos Pendientes</h5>
                            <p class="card-number">28</p>
                            <p class="card-trend">Últimas 24 horas</p>
                        </div>
                    </div>
                    
                    <!-- Tarjeta de Productos en Stock -->
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Productos en Stock</h5>
                            <p class="card-number">1,245</p>
                            <p class="card-trend negative">-3% vs mes anterior</p>
                        </div>
                    </div>
                    
                    <!-- Tarjeta de Clientes Nuevos -->
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Clientes Nuevos</h5>
                            <p class="card-number">156</p>
                            <p class="card-trend positive">+25% vs mes anterior</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script>
        // Gráfico de Ventas
        const ventasCtx = document.getElementById('ventasChart').getContext('2d');
        new Chart(ventasCtx, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
                datasets: [{
                    label: 'Ventas Mensuales',
                    data: [12000, 19000, 15000, 25000, 22000, 30000],
                    borderColor: 'rgb(75, 192, 192)',
                    tension: 0.1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Tendencia de Ventas'
                    }
                }
            }
        });

        // Gráfico de Productos
        const productosCtx = document.getElementById('productosChart').getContext('2d');
        new Chart(productosCtx, {
            type: 'doughnut',
            data: {
                labels: ['Poleras', 'Pantalones', 'Polos', 'Accesorios'],
                datasets: [{
                    data: [300, 250, 200, 150],
                    backgroundColor: [
                        'rgb(255, 99, 132)',
                        'rgb(54, 162, 235)',
                        'rgb(255, 205, 86)',
                        'rgb(75, 192, 192)'
                    ]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Distribución de Inventario'
                    }
                }
            }
        });
    </script>
</body>
</html>
