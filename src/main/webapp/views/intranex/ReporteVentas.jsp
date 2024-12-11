<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if(usuario == null || !"administrador".equals(usuario.getRol())) {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte de Ventas - UrbanStyle</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style>
        .filtros-container {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .resumen-ventas {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .card-resumen {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .grafico-container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        
        @media print {
            .no-print {
                display: none !important;
            }
            .print-only {
                display: block !important;
            }
        }
    </style>
</head>
<body>
    <div class="sidebar no-print">
        <!-- Mismo sidebar que en Dashboard.jsp -->
        <div class="sidebar-header">
            <div class="user-info">
                <span class="user-name">UrbanStyle</span>
                <span class="user-role">Admin</span>
            </div>
        </div>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/views/intranex/Dashboard.jsp">
                <i class="bi bi-graph-up"></i> Dashboard
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProductos.jsp">
                <i class="bi bi-box-seam"></i> Gestión de Productos
            </a>
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionUsuarios.jsp">
                <i class="bi bi-people"></i> Gestión de Usuarios
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProveedores.jsp">
                <i class="bi bi-truck"></i> Gestión de Proveedores
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionAlertas.jsp">
                <i class="bi bi-exclamation-triangle"></i> Alertas de Inventario
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/ReporteVentas.jsp" class="active">
                <i class="bi bi-bar-chart-line"></i> Reportes de Ventas
            </a>
        </nav>
        <div class="sidebar-footer">
            <div class="user-info">
                <span>Bienvenido, <%= usuario.getNombre() %> (<%= usuario.getRol() %>)</span>
            </div>
            <div class="logout">
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">
                    <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                </a>
            </div>
        </div>
    </div>

    <div class="main-content">
        <div class="container mt-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2>Reporte de Ventas</h2>
                <div class="btn-group no-print">
                    <button type="button" id="btnExportExcel" class="btn btn-success">
                        <i class="bi bi-file-earmark-excel"></i> Excel
                    </button>
                    <button type="button" id="btnExportPDF" class="btn btn-danger">
                        <i class="bi bi-file-earmark-pdf"></i> PDF
                    </button>
                    <button type="button" id="btnPrint" class="btn btn-primary">
                        <i class="bi bi-printer"></i> Imprimir
                    </button>
                </div>
            </div>

            <div class="filtros-container no-print">
                <div class="row">
                    <div class="col-md-3">
                        <label for="fechaInicio" class="form-label">Fecha Inicio</label>
                        <input type="date" class="form-control" id="fechaInicio">
                    </div>
                    <div class="col-md-3">
                        <label for="fechaFin" class="form-label">Fecha Fin</label>
                        <input type="date" class="form-control" id="fechaFin">
                    </div>
                    <div class="col-md-3">
                        <label for="categoria" class="form-label">Categoría</label>
                        <select class="form-control" id="categoria">
                            <option value="">Todas</option>
                            <option value="hombre">Hombre</option>
                            <option value="mujer">Mujer</option>
                            <option value="unisex">Unisex</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">&nbsp;</label>
                        <button class="btn btn-primary w-100" id="btnFiltrar">
                            <i class="bi bi-search"></i> Filtrar
                        </button>
                    </div>
                </div>
            </div>

            <div class="resumen-ventas">
                <div class="card-resumen">
                    <h5>Ventas Totales</h5>
                    <h3 id="ventasTotales">S/. 0.00</h3>
                    <p id="ventasTotalesPorcentaje" class="text-success">+0% vs periodo anterior</p>
                </div>
                <div class="card-resumen">
                    <h5>Productos Vendidos</h5>
                    <h3 id="productosVendidos">0</h3>
                    <p id="productosVendidosPorcentaje" class="text-success">+0% vs periodo anterior</p>
                </div>
                <div class="card-resumen">
                    <h5>Ticket Promedio</h5>
                    <h3 id="ticketPromedio">S/. 0.00</h3>
                    <p id="ticketPromedioPorcentaje" class="text-success">+0% vs periodo anterior</p>
                </div>
            </div>

            <div class="row">
                <div class="col-md-8">
                    <div class="grafico-container">
                        <canvas id="ventasPorDiaChart" width="400" height="200"></canvas>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="grafico-container">
                        <canvas id="ventasPorCategoriaChart" width="400" height="200"></canvas>
                    </div>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID Venta</th>
                            <th>Fecha</th>
                            <th>Cliente</th>
                            <th>Productos</th>
                            <th>Total</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody id="tablaVentas">
                        <!-- Los datos se cargarán dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/reporteVentas.js"></script>
</body>
</html> 