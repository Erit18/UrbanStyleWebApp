document.addEventListener('DOMContentLoaded', function() {
    cargarProductos();
    cargarProveedores();

    document.getElementById('btnNuevoProducto').addEventListener('click', function() {
        document.getElementById('productoForm').reset();
        document.getElementById('productoId').value = '';
        document.getElementById('productoModalLabel').textContent = 'Nuevo Producto';
        new bootstrap.Modal(document.getElementById('productoModal')).show();
    });

    document.getElementById('guardarProducto').addEventListener('click', guardarProducto);
});

function cargarProductos() {
    fetch('/AplicativoWebIntegrador/producto')
        .then(response => response.json())
        .then(productos => {
            const tbody = document.getElementById('tablaProductos');
            tbody.innerHTML = '';

            productos.forEach(producto => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${producto.id}</td>
                    <td>${producto.nombre}</td>
                    <td>${producto.categoria}</td>
                    <td>$${producto.precio.toFixed(2)}</td>
                    <td>${producto.stock}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editarProducto(${producto.id})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => console.error('Error al cargar productos:', error));
}

function cargarProveedores() {
    fetch('/AplicativoWebIntegrador/proveedor')
        .then(response => response.json())
        .then(proveedores => {
            const select = document.getElementById('idProveedor');
            select.innerHTML = '<option value="">Seleccione un proveedor</option>';
            proveedores.forEach(proveedor => {
                const option = document.createElement('option');
                option.value = proveedor.id;
                option.textContent = proveedor.nombre;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar proveedores:', error));
}

function guardarProducto() {
    // Convertir la fecha al formato correcto (YYYY-MM-DD)
    const fechaCaducidad = document.getElementById('fechaCaducidad').value;
    
    const producto = {
        id: document.getElementById('productoId').value || null,
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        precio: parseFloat(document.getElementById('precio').value),
        categoria: document.getElementById('categoria').value,
        stock: parseInt(document.getElementById('stock').value),
        fechaCaducidad: fechaCaducidad, // Ya viene en formato YYYY-MM-DD del input date
        descuento: parseFloat(document.getElementById('descuento').value) || 0,
        idProveedor: parseInt(document.getElementById('idProveedor').value)
    };

    console.log('Datos a enviar:', producto); // Para debug

    const method = producto.id ? 'PUT' : 'POST';
    const url = producto.id ? `/AplicativoWebIntegrador/producto/${producto.id}` : '/AplicativoWebIntegrador/producto';

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(producto)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text || 'Error en la respuesta del servidor');
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del servidor:', data);
        bootstrap.Modal.getInstance(document.getElementById('productoModal')).hide();
        cargarProductos();
    })
    .catch(error => {
        console.error('Error detallado:', error);
        alert('Error al guardar el producto: ' + error.message);
    });
}

function editarProducto(id) {
    fetch(`/AplicativoWebIntegrador/producto/${id}`)
        .then(response => response.json())
        .then(producto => {
            document.getElementById('productoId').value = producto.id;
            document.getElementById('nombre').value = producto.nombre;
            document.getElementById('descripcion').value = producto.descripcion;
            document.getElementById('precio').value = producto.precio;
            document.getElementById('categoria').value = producto.categoria;
            document.getElementById('stock').value = producto.stock;

            document.getElementById('productoModalLabel').textContent = 'Editar Producto';
            new bootstrap.Modal(document.getElementById('productoModal')).show();
        })
        .catch(error => console.error('Error al cargar producto para editar:', error));
}

function eliminarProducto(id) {
    if (confirm('¿Está seguro de que desea eliminar este producto?')) {
        fetch(`/AplicativoWebIntegrador/producto/${id}`, {
            method: 'DELETE'
        })
        .then(response => response.json())
        .then(data => {
            console.log('Producto eliminado:', data);
            cargarProductos();
        })
        .catch(error => console.error('Error al eliminar producto:', error));
    }
}
