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
    <title>Gestión de Productos - UrbanStyle</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    <style type="text/css" media="print">
        @media print {
            .sidebar, .no-print {
                display: none !important;
            }
            .main-content {
                margin-left: 0 !important;
                width: 100% !important;
            }
            .no-print {
                display: none !important;
            }
            .print-only {
                display: table-cell !important;
            }
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <!-- Copie aquí el contenido del sidebar del Dashboard.jsp -->
    </div>

    <div class="main-content">
        <div class="container mt-4">
            <div class="d-flex justify-content-between align-items-center mb-3 no-print">
                <h2>Gestión de Productos</h2>
                <div class="d-flex gap-2">
                    <button type="button" id="btnExportExcel" class="btn btn-success">
                        <i class="bi bi-file-earmark-excel"></i> Exportar Excel
                    </button>
                    <button type="button" id="btnExportPDF" class="btn btn-danger">
                        <i class="bi bi-file-earmark-pdf"></i> Exportar PDF
                    </button>
                    <button type="button" id="btnPrint" class="btn btn-primary">
                        <i class="bi bi-printer"></i> Imprimir
                    </button>
                    <button type="button" class="btn btn-primary" id="btnNuevoProducto">
                        <i class="bi bi-plus-circle"></i> Nuevo Producto
                    </button>
                </div>
            </div>
            <table class="table table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Categoría</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th class="no-print">Acciones</th>
                        <th class="print-only" style="display: none;">Proveedor</th>
                        <th class="print-only" style="display: none;">Fecha Cad.</th>
                    </tr>
                </thead>
                <tbody id="tablaProductos">
                    <!-- Los datos se cargarán dinámicamente -->
                </tbody>
            </table>
        </div>
    </div>

    <!-- Modal de Producto -->
    <div class="modal fade" id="productoModal" tabindex="-1" aria-labelledby="productoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="productoModalLabel">Nuevo Producto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="productoForm">
                        <input type="hidden" id="productoId">
                        
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="nombre" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion"></textarea>
                        </div>
                        
                        <div class="mb-3">
                            <label for="precio" class="form-label">Precio</label>
                            <input type="number" class="form-control" id="precio" step="0.01" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="categoria" class="form-label">Categoría</label>
                            <select class="form-control" id="categoria" required>
                                <option value="">Seleccione una categoría</option>
                                <option value="hombre">Hombre</option>
                                <option value="mujer">Mujer</option>
                                <option value="unisex">Unisex</option>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label for="stock" class="form-label">Stock</label>
                            <input type="number" class="form-control" id="stock" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="fechaCaducidad" class="form-label">Fecha de Caducidad</label>
                            <input type="date" class="form-control" id="fechaCaducidad">
                        </div>
                        
                        <div class="mb-3">
                            <label for="descuento" class="form-label">Descuento</label>
                            <input type="number" class="form-control" id="descuento" step="0.01" value="0">
                        </div>
                        
                        <div class="mb-3">
                            <label for="idProveedor" class="form-label">Proveedor</label>
                            <select class="form-control" id="idProveedor" required>
                                <option value="">Seleccione un proveedor</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" id="btnGuardarProducto">Guardar</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        const contextPath = '${pageContext.request.contextPath}';
        console.log('contextPath definido:', contextPath);
    </script>
    <script src="${pageContext.request.contextPath}/js/gestionProductos.js"></script>
</body>
</html>
