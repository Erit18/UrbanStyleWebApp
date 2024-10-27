document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM cargado, iniciando carga de productos...');
    cargarProductos();
});

function cargarProductos() {
    console.log('Iniciando cargarProductos()');
    
    fetch('/AplicativoWebIntegrador/api/productos-lista')
        .then(response => {
            console.log('Respuesta recibida:', response);
            return response.json();
        })
        .then(productos => {
            console.log('Productos recibidos:', productos);
            const tbody = document.getElementById('tablaProductos');
            tbody.innerHTML = '';

            productos.forEach(producto => {
                console.log('Procesando producto:', producto);
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${producto.id_ropa}</td>
                    <td>${producto.nombre}</td>
                    <td>${producto.categoria}</td>
                    <td>$${Number(producto.precio).toFixed(2)}</td>
                    <td>${producto.stock}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="editarProducto(${producto.id_ropa})">
                            <i class="bi bi-pencil"></i> Editar
                        </button>
                        <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.id_ropa})">
                            <i class="bi bi-trash"></i> Eliminar
                        </button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Error al cargar productos:', error);
            const tbody = document.getElementById('tablaProductos');
            tbody.innerHTML = `
                <tr>
                    <td colspan="6" class="text-center text-danger">
                        Error al cargar los productos: ${error.message}
                    </td>
                </tr>
            `;
        });
}

function cargarProveedores() {
    fetch('/AplicativoWebIntegrador/proveedor')
        .then(response => response.json())
        .then(proveedores => {
            const select = document.getElementById('idProveedor');
            select.innerHTML = '<option value="">Seleccione un proveedor</option>';
            proveedores.forEach(proveedor => {
                const option = document.createElement('option');
                option.value = proveedor.id_proveedor;  // Cambiar de id a id_proveedor
                option.textContent = proveedor.nombre;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error al cargar proveedores:', error));
}

function guardarProducto() {
    const fechaCaducidad = document.getElementById('fechaCaducidad').value;
    
    const producto = {
        id_ropa: document.getElementById('productoId').value || null,
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        precio: parseFloat(document.getElementById('precio').value),
        categoria: document.getElementById('categoria').value,
        stock: parseInt(document.getElementById('stock').value),
        fecha_caducidad: fechaCaducidad,
        descuento: parseFloat(document.getElementById('descuento').value) || 0,
        id_proveedor: parseInt(document.getElementById('idProveedor').value)
    };

    console.log('Datos a enviar:', producto); // Para debug

    const method = producto.id_ropa ? 'PUT' : 'POST';
    const url = producto.id_ropa ? 
        `/AplicativoWebIntegrador/producto/${producto.id_ropa}` : 
        '/AplicativoWebIntegrador/producto';

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
            document.getElementById('productoId').value = producto.id_ropa;
            document.getElementById('nombre').value = producto.nombre;
            document.getElementById('descripcion').value = producto.descripcion;
            document.getElementById('precio').value = producto.precio;
            document.getElementById('categoria').value = producto.categoria;
            document.getElementById('stock').value = producto.stock;
            document.getElementById('fechaCaducidad').value = producto.fecha_caducidad;
            document.getElementById('descuento').value = producto.descuento;
            document.getElementById('idProveedor').value = producto.id_proveedor;

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

// Función para depurar el objeto producto
function debugProducto(producto) {
    console.log('DEBUG - Producto completo:', producto);
    console.log('DEBUG - id_ropa:', producto.id_ropa, typeof producto.id_ropa);
    console.log('DEBUG - Propiedades:', Object.keys(producto));
    return producto;
}
