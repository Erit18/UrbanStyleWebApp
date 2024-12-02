<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Reclamos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    </head>
    <body>
        <jsp:include page="/views/templates/navbaradmin.jsp"/>
        
        <div class="container mt-5">
            <div class="row mb-4">
                <div class="col">
                    <h2>Gestión de Reclamos</h2>
                </div>
            </div>
            
            <div class="card">
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="tablaReclamos" class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fecha</th>
                                    <th>Cliente</th>
                                    <th>Tipo Doc.</th>
                                    <th>Nro. Doc.</th>
                                    <th>Tipo Reclamo</th>
                                    <th>Estado</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="reclamo" items="${reclamos}">
                                    <tr>
                                        <td>${reclamo.id}</td>
                                        <td>${reclamo.fechaCompra}</td>
                                        <td>${reclamo.nombre} ${reclamo.apellido}</td>
                                        <td>${reclamo.tipoDocumento}</td>
                                        <td>${reclamo.numeroDocumento}</td>
                                        <td>${reclamo.tipoReclamo}</td>
                                        <td>
                                            <span class="badge ${reclamo.estado eq 'Pendiente' ? 'bg-warning' : 
                                                                reclamo.estado eq 'En Proceso' ? 'bg-info' : 
                                                                reclamo.estado eq 'Resuelto' ? 'bg-success' : 'bg-danger'}">
                                                ${reclamo.estado}
                                            </span>
                                        </td>
                                        <td>
                                            <button class="btn btn-info btn-sm" onclick="verDetalleReclamo(${reclamo.id})">
                                                <i class="bi bi-eye"></i>
                                            </button>
                                            <button class="btn btn-primary btn-sm" onclick="actualizarEstado(${reclamo.id})">
                                                <i class="bi bi-pencil"></i>
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

        <!-- Modal para ver detalles del reclamo -->
        <div class="modal fade" id="modalDetalleReclamo" tabindex="-1">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Detalle del Reclamo</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div id="detalleReclamoContenido">
                            <!-- Aquí se cargará dinámicamente el detalle del reclamo -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Scripts necesarios -->
        <script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.13.5/js/dataTables.bootstrap5.min.js"></script>
        
        <script>
            $(document).ready(function() {
                $('#tablaReclamos').DataTable({
                    language: {
                        url: 'https://cdn.datatables.net/plug-ins/1.13.5/i18n/es-ES.json'
                    }
                });
            });

            function verDetalleReclamo(id) {
                // Hacer una petición AJAX para obtener los detalles del reclamo
                $.ajax({
                    url: '${pageContext.request.contextPath}/ReclamoServlet',
                    type: 'GET',
                    data: {
                        accion: 'obtenerDetalle',
                        id: id
                    },
                    success: function(response) {
                        $('#detalleReclamoContenido').html(response);
                        $('#modalDetalleReclamo').modal('show');
                    },
                    error: function() {
                        alert('Error al obtener los detalles del reclamo');
                    }
                });
            }

            function actualizarEstado(id) {
                const nuevoEstado = prompt('Ingrese el nuevo estado (Pendiente/En Proceso/Resuelto/Cancelado):');
                if (nuevoEstado) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/ReclamoServlet',
                        type: 'POST',
                        data: {
                            accion: 'actualizarEstado',
                            id: id,
                            estado: nuevoEstado
                        },
                        success: function(response) {
                            if (response === 'success') {
                                location.reload();
                            } else {
                                alert('Error al actualizar el estado');
                            }
                        },
                        error: function() {
                            alert('Error en la comunicación con el servidor');
                        }
                    });
                }
            }
        </script>
    </body>
</html>
