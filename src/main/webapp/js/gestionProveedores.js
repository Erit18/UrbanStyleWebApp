document.addEventListener('DOMContentLoaded', function() {
    cargarProveedores();

    document.getElementById('btnNuevoProveedor').addEventListener('click', function() {
        document.getElementById('proveedorForm').reset();
        document.getElementById('proveedorId').value = '';
        document.getElementById('proveedorModalLabel').textContent = 'Nuevo Proveedor';
        new bootstrap.Modal(document.getElementById('proveedorModal')).show();
    });

    document.getElementById('guardarProveedor').addEventListener('click', guardarProveedor);
});

function cargarProveedores() {
    fetch('/AplicativoWebIntegrador/proveedor')
        .then(response => response.json())
        .then(proveedores => {
            const tbody = document.getElementById('tablaProveedores');
            tbody.innerHTML = '';

            proveedores.forEach(proveedor => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${proveedor.id}</td>
                    <td>${proveedor.nombre}</td>
                    <td>${proveedor.contacto}</td>
                    <td>${proveedor.telefono}</td>
                    <td>${proveedor.email}</td>
                    <td>${proveedor.direccion}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editarProveedor(${proveedor.id})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarProveedor(${proveedor.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error('Error al cargar proveedores:', error));
}

function guardarProveedor() {
    const proveedor = {
        id: document.getElementById('proveedorId').value || null,
        nombre: document.getElementById('nombre').value,
        contacto: document.getElementById('contacto').value,
        telefono: document.getElementById('telefono').value,
        email: document.getElementById('email').value,
        direccion: document.getElementById('direccion').value
    };

    const method = proveedor.id ? 'PUT' : 'POST';
    const url = proveedor.id ? 
        `/AplicativoWebIntegrador/proveedor/${proveedor.id}` : 
        '/AplicativoWebIntegrador/proveedor';

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
    })
    .catch(error => console.error('Error al guardar proveedor:', error));
}

function editarProveedor(id) {
    fetch(`/AplicativoWebIntegrador/proveedor/${id}`)
        .then(response => response.json())
        .then(proveedor => {
            document.getElementById('proveedorId').value = proveedor.id;
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
        fetch(`/AplicativoWebIntegrador/proveedor/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Proveedor eliminado:', data);
            cargarProveedores();
        })
        .catch(error => console.error('Error al eliminar proveedor:', error));
    }
}

