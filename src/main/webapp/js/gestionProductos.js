document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM cargado');
    
    // Cargar productos iniciales
    cargarProductos();
    
    // Cargar proveedores al inicio
    cargarProveedores();
    
    // Configurar botón Nuevo Producto
    document.getElementById('btnNuevoProducto').addEventListener('click', () => {
        console.log('Click en Nuevo Producto');
        document.getElementById('productoForm').reset();
        document.getElementById('productoId').value = '';
        document.getElementById('productoModalLabel').textContent = 'Nuevo Producto';
        const modal = new bootstrap.Modal(document.getElementById('productoModal'));
        modal.show();
    });
    
    // Configurar botón Guardar del modal
    document.getElementById('btnGuardarProducto').addEventListener('click', guardarProducto);
});

async function cargarProductos() {
    console.log('Iniciando cargarProductos()');
    
    try {
        const response = await fetch(`${contextPath}/api/productos-lista`);
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        
        const productos = await response.json();
        console.log('Productos recibidos:', productos);
        
        const tbody = document.getElementById('tablaProductos');
        tbody.innerHTML = '';
        
        productos.forEach(producto => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${producto.id_ropa}</td>
                <td>${producto.nombre}</td>
                <td>${producto.categoria}</td>
                <td>$${Number(producto.precio).toFixed(2)}</td>
                <td>${producto.stock}</td>
                <td>
                    <button onclick="editarProducto(${producto.id_ropa})" class="btn btn-primary btn-sm">
                        <i class="bi bi-pencil"></i> Editar
                    </button>
                    <button onclick="eliminarProducto(${producto.id_ropa})" class="btn btn-danger btn-sm ms-1">
                        <i class="bi bi-trash"></i> Eliminar
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error al cargar productos:', error);
        alert('Error al cargar los productos: ' + error.message);
    }
}

async function cargarProveedores() {
    console.log('Cargando proveedores...');
    try {
        const response = await fetch(`${contextPath}/api/proveedores`);
        console.log('Respuesta proveedores:', response);
        
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        
        const proveedores = await response.json();
        console.log('Proveedores recibidos:', proveedores);
        
        const select = document.getElementById('idProveedor');
        select.innerHTML = '<option value="">Seleccione un proveedor</option>';
        
        proveedores.forEach(proveedor => {
            const option = document.createElement('option');
            option.value = proveedor.id;  // Asegúrate de que coincida con el nombre del campo en el modelo
            option.textContent = proveedor.nombre;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar proveedores:', error);
        alert('Error al cargar la lista de proveedores');
    }
}

async function guardarProducto() {
    console.log('Iniciando guardarProducto');
    const form = document.getElementById('productoForm');
    
    if (!form.checkValidity()) {
        form.reportValidity();
        return;
    }
    
    const productoData = {
        id_ropa: document.getElementById('productoId').value || null,
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        precio: parseFloat(document.getElementById('precio').value),
        categoria: document.getElementById('categoria').value,
        stock: parseInt(document.getElementById('stock').value),
        fecha_caducidad: document.getElementById('fechaCaducidad').value || null,
        descuento: parseFloat(document.getElementById('descuento').value) || 0,
        id_proveedor: parseInt(document.getElementById('idProveedor').value)
    };
    
    try {
        const isEditing = productoData.id_ropa !== null;
        const url = isEditing ? 
            `${contextPath}/api/productos/${productoData.id_ropa}` : 
            `${contextPath}/api/productos`;
        
        const response = await fetch(url, {
            method: isEditing ? 'PUT' : 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productoData)
        });
        
        if (!response.ok) throw new Error('Error al guardar el producto');
        
        // Cerrar modal
        const modal = bootstrap.Modal.getInstance(document.getElementById('productoModal'));
        modal.hide();
        
        // Recargar productos
        await cargarProductos();
        
        alert(isEditing ? 'Producto actualizado con éxito' : 'Producto creado con éxito');
    } catch (error) {
        console.error('Error:', error);
        alert('Error al guardar el producto: ' + error.message);
    }
}

async function editarProducto(id) {
    console.log('Editando producto:', id);
    try {
        const response = await fetch(`${contextPath}/api/productos/${id}`);
        if (!response.ok) throw new Error('Error al obtener el producto');
        
        const producto = await response.json();
        console.log('Producto a editar:', producto);
        
        // Rellenar el formulario
        document.getElementById('productoId').value = producto.id_ropa;
        document.getElementById('nombre').value = producto.nombre;
        document.getElementById('descripcion').value = producto.descripcion || '';
        document.getElementById('precio').value = producto.precio;
        document.getElementById('categoria').value = producto.categoria;
        document.getElementById('stock').value = producto.stock;
        
        // Mostrar el modal
        const modal = new bootstrap.Modal(document.getElementById('productoModal'));
        modal.show();
    } catch (error) {
        console.error('Error:', error);
        alert('Error al cargar el producto');
    }
}

async function eliminarProducto(id) {
    console.log('Eliminando producto:', id);
    if (!confirm('¿Está seguro de eliminar este producto?')) return;
    
    try {
        const response = await fetch(`${contextPath}/api/productos/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Error al eliminar el producto');
        
        await cargarProductos(); // Recargar la tabla
        alert('Producto eliminado con éxito');
    } catch (error) {
        console.error('Error:', error);
        alert('Error al eliminar el producto');
    }
}

// Función para depurar el objeto producto
function debugProducto(producto) {
    console.log('DEBUG - Producto completo:', producto);
    console.log('DEBUG - id_ropa:', producto.id_ropa, typeof producto.id_ropa);
    console.log('DEBUG - Propiedades:', Object.keys(producto));
    return producto;
}
