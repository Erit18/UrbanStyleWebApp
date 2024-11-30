<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.aplicativowebintegrador.modelo.Usuario" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    // Verificación de seguridad
    Usuario currentUser = (Usuario) session.getAttribute("usuario");
    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        return;
    }
    
    String rolNormalizado = currentUser.getRol().trim().toLowerCase();
    if (!"administrador".equals(rolNormalizado)) {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Alertas</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <!-- Sidebar igual que en Dashboard.jsp -->
    <div class="sidebar">
        <div class="sidebar-header">
            <div class="user-info">
                <span class="user-name">UrbanStyle</span>
                <span class="user-role">Admin</span>
            </div>
        </div>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/views/intranex/Dashboard.jsp"><i class="bi bi-graph-up"></i> Dashboard</a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProductos.jsp"><i class="bi bi-box-seam"></i> Gestión de Productos</a>
            <a href="#"><i class="bi bi-cart-check"></i> Gestión de Pedidos</a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionUsuarios.jsp">
                <i class="bi bi-people"></i> Gestión de Usuarios
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionProveedores.jsp">
                <i class="bi bi-truck"></i> Gestión de Proveedores
            </a>
            <a href="${pageContext.request.contextPath}/views/intranex/GestionAlertas.jsp" class="active">
                <i class="bi bi-exclamation-triangle"></i> Alertas de Inventario
            <a href="${pageContext.request.contextPath}/views/intranex/ReporteVentas.jsp">
                <i class="bi bi-bar-chart-line"></i> Reportes de Ventas
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
        <div class="container mt-4">
            <h2><i class="bi bi-exclamation-triangle"></i> Gestión de Alertas de Inventario</h2>
            
            <!-- Filtros -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Buscar alertas...">
                        <button class="btn btn-primary" type="button">Buscar</button>
                    </div>
                </div>
                <div class="col-md-6 text-end">
                    <button class="btn btn-success" type="button" data-bs-toggle="modal" data-bs-target="#nuevaAlertaModal">
                        <i class="bi bi-plus-circle"></i> Nueva Alerta
                    </button>
                </div>
            </div>

            <!-- Tabla de Alertas -->
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Producto</th>
                            <th>Mensaje</th>
                            <th>Fecha</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="alertasTableBody">
                        <!-- Las filas se cargarán dinámicamente -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Modal para nueva/editar alerta -->
    <div class="modal fade" id="nuevaAlertaModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Nueva Alerta</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="nuevaAlertaForm">
                        <input type="hidden" name="id_alerta" value="">
                        <div class="mb-3">
                            <label class="form-label">Producto</label>
                            <select name="id_ropa" class="form-select" required>
                                <!-- Se llenará dinámicamente -->
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mensaje</label>
                            <textarea name="mensaje" class="form-control" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" onclick="guardarAlerta()">Guardar</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
    function cargarAlertas() {
        fetch('${pageContext.request.contextPath}/api/alertas')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(alertas => {
                console.log('Alertas recibidas:', alertas); // Para debug
                const tbody = document.getElementById('alertasTableBody');
                tbody.innerHTML = '';
                
                if (alertas && alertas.length > 0) {
                    alertas.forEach(alerta => {
                        // Crear la fila usando createElement para mejor control
                        const tr = document.createElement('tr');
                        
                        // Crear y añadir cada celda individualmente
                        const tdId = document.createElement('td');
                        tdId.textContent = alerta.id_alerta;
                        
                        const tdProducto = document.createElement('td');
                        tdProducto.textContent = alerta.nombre_producto;
                        
                        const tdMensaje = document.createElement('td');
                        tdMensaje.textContent = alerta.mensaje;
                        
                        const tdFecha = document.createElement('td');
                        const fecha = new Date(alerta.fecha_alerta);
                        tdFecha.textContent = fecha.toLocaleString('es-ES');
                        
                        const tdEstado = document.createElement('td');
                        const estadoClass = alerta.estado === 'activa' ? 'bg-warning' : 'bg-success';
                        tdEstado.innerHTML = `<span class="badge ${estadoClass}">${alerta.estado}</span>`;
                        
                        const tdAcciones = document.createElement('td');
                        tdAcciones.innerHTML = `
                            <button type="button" class="btn btn-sm btn-primary me-1" onclick="editarAlerta(${alerta.id_alerta})">
                                <i class="bi bi-pencil"></i> Editar
                            </button>
                            <button type="button" class="btn btn-sm btn-danger" onclick="eliminarAlerta(${alerta.id_alerta})">
                                <i class="bi bi-trash"></i> Eliminar
                            </button>
                        `;
                        
                        // Añadir todas las celdas a la fila
                        tr.appendChild(tdId);
                        tr.appendChild(tdProducto);
                        tr.appendChild(tdMensaje);
                        tr.appendChild(tdFecha);
                        tr.appendChild(tdEstado);
                        tr.appendChild(tdAcciones);
                        
                        // Añadir la fila a la tabla
                        tbody.appendChild(tr);
                    });
                } else {
                    tbody.innerHTML = `
                        <tr>
                            <td colspan="6" class="text-center">No hay alertas disponibles</td>
                        </tr>
                    `;
                }
            })
            .catch(error => {
                console.error('Error al cargar alertas:', error);
                mostrarError('Error al cargar las alertas: ' + error.message);
            });
    }

    // Asegúrate de que la función se ejecute cuando la página cargue
    document.addEventListener('DOMContentLoaded', () => {
        console.log('Página cargada, ejecutando cargarAlertas()');
        cargarAlertas();
    });

    // Función para formatear fechas (utilidad)
    function formatearFecha(fecha) {
        if (!fecha) return '';
        const d = new Date(fecha);
        return d.toLocaleString('es-ES', {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    // Funciones auxiliares
    function editarAlerta(id) {
        if (!id) {
            console.error('ID de alerta no proporcionado');
            return;
        }
        
        console.log('Editando alerta:', id);
        
        fetch(`${pageContext.request.contextPath}/api/alertas/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al obtener la alerta');
                }
                return response.json();
            })
            .then(alerta => {
                if (!alerta) {
                    throw new Error('No se encontró la alerta');
                }
                
                const form = document.getElementById('nuevaAlertaForm');
                if (form) {
                    const selectProducto = form.querySelector('select[name="id_ropa"]');
                    const textareaMensaje = form.querySelector('textarea[name="mensaje"]');
                    
                    if (selectProducto) selectProducto.value = alerta.id_ropa;
                    if (textareaMensaje) textareaMensaje.value = alerta.mensaje;
                    
                    // Guardar el ID de la alerta en un campo oculto
                    let idInput = form.querySelector('input[name="id_alerta"]');
                    if (!idInput) {
                        idInput = document.createElement('input');
                        idInput.type = 'hidden';
                        idInput.name = 'id_alerta';
                        form.appendChild(idInput);
                    }
                    idInput.value = id;
                    
                    // Modificar el modal para modo edición
                    const modalTitle = document.querySelector('#nuevaAlertaModal .modal-title');
                    if (modalTitle) modalTitle.textContent = 'Editar Alerta';
                    
                    const guardarBtn = document.querySelector('#nuevaAlertaModal .btn-primary');
                    if (guardarBtn) {
                        guardarBtn.textContent = 'Actualizar';
                        guardarBtn.onclick = () => actualizarAlerta(id);
                    }
                    
                    // Mostrar el modal
                    const modal = new bootstrap.Modal(document.getElementById('nuevaAlertaModal'));
                    modal.show();
                } else {
                    throw new Error('No se encontró el formulario');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al cargar los datos de la alerta: ' + error.message);
            });
    }

    function actualizarAlerta(id) {
        const form = document.getElementById('nuevaAlertaForm');
        const formData = new FormData(form);
        
        fetch(`${pageContext.request.contextPath}/api/alertas/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id_ropa: formData.get('id_ropa'),
                mensaje: formData.get('mensaje')
            })
        })
        .then(response => response.json())
        .then(result => {
            if (result) {
                alert('Alerta actualizada con éxito');
                bootstrap.Modal.getInstance(document.getElementById('nuevaAlertaModal')).hide();
                cargarAlertas();
            } else {
                throw new Error('No se pudo actualizar la alerta');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al actualizar la alerta: ' + error.message);
        });
    }

    function guardarAlerta() {
        const form = document.getElementById('nuevaAlertaForm');
        const formData = new FormData(form);
        
        try {
            validarFormulario(formData);
            
            fetch('${pageContext.request.contextPath}/api/alertas', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id_ropa: parseInt(formData.get('id_ropa')),
                    mensaje: formData.get('mensaje')
                })
            })
            .then(response => {
                if (!response.ok) throw new Error('Error en la respuesta del servidor');
                return response.json();
            })
            .then(result => {
                if (result) {
                    mostrarExito('Alerta creada con éxito');
                    form.reset();
                    bootstrap.Modal.getInstance(document.getElementById('nuevaAlertaModal')).hide();
                    cargarAlertas();
                } else {
                    throw new Error('No se pudo crear la alerta');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                mostrarError('Error al crear la alerta: ' + error.message);
            });
        } catch (error) {
            mostrarError(error.message);
        }
    }

    function eliminarAlerta(id) {
        if (confirm('¿Está seguro de que desea eliminar esta alerta?')) {
            fetch(`${pageContext.request.contextPath}/api/alertas?id=${id}`, {
                method: 'DELETE'
            })
            .then(response => response.json())
            .then(result => {
                if (result) {
                    alert('Alerta eliminada con éxito');
                    cargarAlertas();
                } else {
                    throw new Error('No se pudo eliminar la alerta');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al eliminar la alerta: ' + error.message);
            });
        }
    }

    function cargarProductos() {
        fetch('${pageContext.request.contextPath}/api/productos')
            .then(response => response.json())
            .then(productos => {
                const select = document.querySelector('select[name="id_ropa"]');
                select.innerHTML = '<option value="">Seleccione un producto...</option>';
                productos.forEach(producto => {
                    select.innerHTML += `<option value="${producto.id_ropa}">${producto.nombre}</option>`;
                });
            })
            .catch(error => {
                console.error('Error al cargar productos:', error);
            });
    }

    // Modificar el evento del botón "Nueva Alerta"
    document.querySelector('[data-bs-target="#nuevaAlertaModal"]').addEventListener('click', () => {
        const form = document.getElementById('nuevaAlertaForm');
        form.reset();
        document.querySelector('#nuevaAlertaModal .modal-title').textContent = 'Nueva Alerta';
        const guardarBtn = document.querySelector('#nuevaAlertaModal .btn-primary');
        guardarBtn.textContent = 'Guardar';
        guardarBtn.onclick = guardarAlerta;
        cargarProductos(); // Cargar productos cuando se abre el modal
    });

    function mostrarError(mensaje) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-danger alert-dismissible fade show';
        alertDiv.innerHTML = `
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        document.querySelector('.container').insertBefore(alertDiv, document.querySelector('.row'));
    }

    function validarFormulario(formData) {
        if (!formData.get('id_ropa')) {
            throw new Error('Debe seleccionar un producto');
        }
        if (!formData.get('mensaje')) {
            throw new Error('Debe ingresar un mensaje');
        }
        return true;
    }

    function mostrarExito(mensaje) {
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-success alert-dismissible fade show';
        alertDiv.innerHTML = `
            ${mensaje}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        document.querySelector('.container').insertBefore(alertDiv, document.querySelector('.row'));
    }
    </script>
</body>
</html>
