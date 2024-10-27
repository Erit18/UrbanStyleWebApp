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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <div class="sidebar">
        <!-- Copie aquí el contenido del sidebar del Dashboard.jsp -->
    </div>

    <div class="main-content">
        <h1>Gestión de Productos</h1>
        <div class="mb-3">
            <button class="btn btn-primary" id="btnNuevoProducto">Nuevo Producto</button>
        </div>
        <table class="table table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Categoría</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody id="tablaProductos">
                <!-- Los productos se cargarán dinámicamente aquí -->
            </tbody>
        </table>
    </div>

    <!-- Modal para agregar/editar producto -->
    <div class="modal fade" id="productoModal" tabindex="-1" aria-labelledby="productoModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="productoModalLabel">Nuevo Producto</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form id="productoForm">
                        <input type="hidden" id="productoId" name="id_ropa">
                        <div class="mb-3">
                            <label for="nombre" class="form-label">Nombre</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <div class="mb-3">
                            <label for="descripcion" class="form-label">Descripción</label>
                            <textarea class="form-control" id="descripcion" rows="3"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="precio" class="form-label">Precio</label>
                            <input type="number" class="form-control" id="precio" step="0.01" required>
                        </div>
                        <div class="mb-3">
                            <label for="categoria" class="form-label">Categoría</label>
                            <select class="form-select" id="categoria" required>
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
                            <input type="number" class="form-control" id="descuento" step="0.01">
                        </div>
                        <div class="mb-3">
                            <label for="idProveedor" class="form-label">Proveedor</label>
                            <select class="form-control" id="idProveedor" name="id_proveedor">
                                <option value="">Seleccione un proveedor</option>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                    <button type="button" class="btn btn-primary" id="guardarProducto">Guardar</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/gestionProductos.js"></script>
</body>
</html>
