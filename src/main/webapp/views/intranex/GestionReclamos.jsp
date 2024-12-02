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
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Proveedores - UrbanStyle</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
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
            <a href="${pageContext.request.contextPath}/views/intranex/GestionReclamos.jsp">
                <i class="bi bi-bar-chart-line"></i> GestionReclamos
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

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="menu.jsp" />
            
            <div class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <h2 class="mt-4">Gestión de Reclamos</h2>
                
                <div class="table-responsive mt-4">
                    <table id="tablaReclamos" class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Fecha Registro</th>
                                <th>Cliente</th>
                                <th>Documento</th>
                                <th>Tipo Reclamo</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${reclamos}" var="reclamo">
                                <tr>
                                    <td>${reclamo.id}</td>
                                    <td><fmt:formatDate value="${reclamo.fechaRegistro}" pattern="dd/MM/yyyy HH:mm"/></td>
                                    <td>${reclamo.nombre} ${reclamo.apellido}</td>
                                    <td>${reclamo.tipoDocumento} - ${reclamo.numeroDocumento}</td>
                                    <td>${reclamo.tipoReclamo}</td>
                                    <td>
                                        <span class="badge bg-${reclamo.estado eq 'Pendiente' ? 'warning' : 
                                                                reclamo.estado eq 'En Proceso' ? 'info' : 
                                                                reclamo.estado eq 'Resuelto' ? 'success' : 'danger'}">
                                            ${reclamo.estado}
                                        </span>
                                    </td>
                                    <td>
                                        <button class="btn btn-info btn-sm" onclick="verDetalle('${reclamo.id}')">
                                            <i class="fas fa-eye"></i> Ver
                                        </button>
                                        <button class="btn btn-primary btn-sm" onclick="cambiarEstado('${reclamo.id}')">
                                            <i class="fas fa-edit"></i> Estado
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal para ver detalles -->
    <div class="modal fade" id="detalleModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Detalle del Reclamo</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="detalleContenido">
                    <!-- El contenido se cargará dinámicamente -->
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts necesarios -->
    <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.5/js/dataTables.bootstrap5.min.js"></script>
    <script src="https://kit.fontawesome.com/your-code.js"></script>
    
    <script>
        $(document).ready(function() {
            $('#tablaReclamos').DataTable({
                language: {
                    url: 'https://cdn.datatables.net/plug-ins/1.13.5/i18n/es-ES.json'
                }
            });
        });

        function verDetalle(id) {
            $.get('${pageContext.request.contextPath}/ReclamoServlet?accion=obtenerDetalle&id=' + id, function(data) {
                $('#detalleContenido').html(data);
                $('#detalleModal').modal('show');
            });
        }

        function cambiarEstado(id) {
            const nuevoEstado = prompt('Ingrese el nuevo estado (Pendiente/En Proceso/Resuelto/Cancelado):');
            if (nuevoEstado) {
                $.post('${pageContext.request.contextPath}/ReclamoServlet', {
                    accion: 'actualizarEstado',
                    id: id,
                    estado: nuevoEstado
                }, function(response) {
                    if (response === 'success') {
                        location.reload();
                    } else {
                        alert('Error al actualizar el estado');
                    }
                });
            }
        }
    </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/gestionProveedores.js"></script>

</body>
</html>