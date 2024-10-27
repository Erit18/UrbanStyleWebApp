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
            <a href="#"><i class="bi bi-bar-chart-line"></i> Reportes de Ventas</a>
            <a href="#"><i class="bi bi-exclamation-triangle"></i> Alertas de Inventario</a>
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
        <div class="contentD">
            <h1>Dashboard de Ventas e Inventario</h1>
            <!-- Aquí va el contenido de los gráficos y estadísticas -->
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
