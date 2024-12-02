<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Reclamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.datatables.net/1.13.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
</head>
<body>
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
                                        <button class="btn btn-info btn-sm" onclick="verDetalle(${reclamo.id})">
                                            <i class="fas fa-eye"></i> Ver
                                        </button>
                                        <button class="btn btn-primary btn-sm" onclick="cambiarEstado(${reclamo.id})">
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
            $.get('ReclamoServlet?accion=obtenerDetalle&id=' + id, function(data) {
                $('#detalleContenido').html(data);
                $('#detalleModal').modal('show');
            });
        }

        function cambiarEstado(id) {
            const nuevoEstado = prompt('Ingrese el nuevo estado (Pendiente/En Proceso/Resuelto/Cancelado):');
            if (nuevoEstado) {
                $.post('ReclamoServlet', {
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
</body>
</html>