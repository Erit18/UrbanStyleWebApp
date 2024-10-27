// Al inicio del archivo
console.log('Inicializando script de gestión de proveedores');
console.log('contextPath:', contextPath);

// Asegurarse de que el DOM está cargado
document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM cargado');
    cargarProveedores();

    // Configurar eventos
    document.getElementById('btnNuevoProveedor').addEventListener('click', () => {
        console.log('Click en Nuevo Proveedor');
        document.getElementById('proveedorForm').reset();
        document.getElementById('proveedorId').value = '';
        document.getElementById('proveedorModalLabel').textContent = 'Nuevo Proveedor';
        new bootstrap.Modal(document.getElementById('proveedorModal')).show();
    });

    document.getElementById('guardarProveedor').addEventListener('click', guardarProveedor);
});

function cargarProveedores() {
    const url = `${contextPath}/api/proveedores`;
    console.log('Cargando proveedores desde:', url);
    
    fetch(url)
        .then(response => {
            console.log('Respuesta recibida:', response);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(proveedores => {
            console.log('Proveedores recibidos:', proveedores);
            actualizarTablaProveedores(proveedores);
        })
        .catch(error => {
            console.error('Error al cargar proveedores:', error);
            mostrarError(error.message);
        });
}

function actualizarTablaProveedores(proveedores) {
    const tbody = document.getElementById('tablaProveedores');
    if (!tbody) {
        console.error('No se encontró el elemento tablaProveedores');
        return;
    }
    
    tbody.innerHTML = '';
    
    if (!Array.isArray(proveedores) || proveedores.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center">
                    No hay proveedores registrados
                </td>
            </tr>
        `;
        return;
    }
    
    proveedores.forEach(proveedor => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${proveedor.id_proveedor}</td>
            <td>${proveedor.nombre || ''}</td>
            <td>${proveedor.contacto || ''}</td>
            <td>${proveedor.telefono || ''}</td>
            <td>${proveedor.email || ''}</td>
            <td>${proveedor.direccion || ''}</td>
            <td>
                <button class="btn btn-sm btn-primary" onclick="editarProveedor(${proveedor.id_proveedor})">
                    <i class="bi bi-pencil"></i> Editar
                </button>
                <button class="btn btn-sm btn-danger ms-1" onclick="eliminarProveedor(${proveedor.id_proveedor})">
                    <i class="bi bi-trash"></i> Eliminar
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function mostrarError(mensaje) {
    const tbody = document.getElementById('tablaProveedores');
    if (tbody) {
        tbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center text-danger">
                    Error al cargar los proveedores: ${mensaje}
                </td>
            </tr>
        `;
    }
}

function guardarProveedor() {
    const proveedor = {
        id_proveedor: document.getElementById('proveedorId').value || null,
        nombre: document.getElementById('nombre').value,
        contacto: document.getElementById('contacto').value,
        telefono: document.getElementById('telefono').value,
        email: document.getElementById('email').value,
        direccion: document.getElementById('direccion').value
    };

    const method = proveedor.id_proveedor ? 'PUT' : 'POST';
    const url = proveedor.id_proveedor ? 
        `${contextPath}/api/proveedores/${proveedor.id_proveedor}` : 
        `${contextPath}/api/proveedores`;

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(proveedor)
    })
    .then(response => response.json())
    .then(data => {
        bootstrap.Modal.getInstance(document.getElementById('proveedorModal')).hide();
        cargarProveedores();
        alert(proveedor.id_proveedor ? 'Proveedor actualizado con éxito' : 'Proveedor creado con éxito');
    })
    .catch(error => {
        console.error('Error al guardar proveedor:', error);
        alert('Error al guardar el proveedor: ' + error.message);
    });
}

function editarProveedor(id) {
    fetch(`${contextPath}/api/proveedores/${id}`)
        .then(response => response.json())
        .then(proveedor => {
            document.getElementById('proveedorId').value = proveedor.id_proveedor;
            document.getElementById('nombre').value = proveedor.nombre;
            document.getElementById('contacto').value = proveedor.contacto;
            document.getElementById('telefono').value = proveedor.telefono;
            document.getElementById('email').value = proveedor.email;
            document.getElementById('direccion').value = proveedor.direccion;

            document.getElementById('proveedorModalLabel').textContent = 'Editar Proveedor';
            new bootstrap.Modal(document.getElementById('proveedorModal')).show();
        })
        .catch(error => console.error('Error al cargar proveedor para editar:', error));
}

function eliminarProveedor(id) {
    if (confirm('¿Está seguro de que desea eliminar este proveedor?')) {
        fetch(`${contextPath}/api/proveedores/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            cargarProveedores();
            alert('Proveedor eliminado con éxito');
        })
        .catch(error => console.error('Error al eliminar proveedor:', error));
    }
}
