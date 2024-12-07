async function cargarProductos() {
    console.log('Iniciando cargarProductos()');
    try {
        const [productosResponse, proveedoresResponse] = await Promise.all([
            fetch(`${contextPath}/api/productos`),
            fetch(`${contextPath}/api/proveedores`)
        ]);
        
        if (!productosResponse.ok || !proveedoresResponse.ok) {
            throw new Error('Error al cargar datos');
        }
        
        todosLosProductos = await productosResponse.json();
        const proveedores = await proveedoresResponse.json();
        
        const proveedoresMap = new Map(
            proveedores.map(p => [p.id_proveedor, p.nombre])
        );
        
        // Calcular productos para la página actual
        const inicio = (paginaActual - 1) * productosPorPagina;
        const fin = inicio + productosPorPagina;
        const productosPagina = todosLosProductos.slice(inicio, fin);
        
        const tbody = document.getElementById('tablaProductos');
        tbody.innerHTML = '';
        
        productosPagina.forEach(producto => {
            const tr = document.createElement('tr');
            
            // Crear todas las celdas con sus clases correspondientes
            tr.innerHTML = `
                <td>${producto.id_ropa}</td>
                <td>${producto.nombre}</td>
                <td>${producto.categoria} - ${producto.tipo_producto || 'No especificado'}</td>
                <td>S/ ${producto.precio.toFixed(2)}</td>
                <td>${producto.stock}</td>
                <td class="no-print">
                    <button class="btn btn-sm btn-primary me-1" onclick="editarProducto(${producto.id_ropa})">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.id_ropa})">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
                <td class="print-only" style="display: none;">${proveedoresMap.get(producto.id_proveedor) || 'No especificado'}</td>
                <td class="print-only" style="display: none;">${producto.fecha_caducidad ? new Date(producto.fecha_caducidad).toLocaleDateString() : '-'}</td>
            `;
            
            tbody.appendChild(tr);
        });

        // Actualizar controles de paginación
        actualizarPaginacion();
    } catch (error) {
        console.error('Error al cargar productos:', error);
    }
}

async function cargarProveedores() {
    console.log('Cargando proveedores...');
    try {
        const response = await fetch(`${contextPath}/api/proveedores`);
        console.log('Respuesta proveedores:', response);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const proveedores = await response.json();
        console.log('Proveedores recibidos:', proveedores);
        
        const select = document.getElementById('idProveedor');
        select.innerHTML = '<option value="">Seleccione un proveedor</option>';
        
        proveedores.forEach(proveedor => {
            const option = document.createElement('option');
            option.value = proveedor.id_proveedor;
            option.textContent = proveedor.nombre;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar proveedores:', error);
    }
}

// Variables globales para el modal
let productoModal;
let productoActual;

async function editarProducto(id) {
    console.log('Editando producto:', id);
    try {
        const response = await fetch(`${contextPath}/api/productos/${id}`);
        if (!response.ok) throw new Error('Error al obtener producto');
        
        productoActual = await response.json();
        
        // Rellenar el formulario con los datos del producto
        document.getElementById('productoId').value = productoActual.id_ropa;
        document.getElementById('nombre').value = productoActual.nombre;
        document.getElementById('descripcion').value = productoActual.descripcion || '';
        document.getElementById('precio').value = productoActual.precio;
        document.getElementById('categoria').value = productoActual.categoria;
        document.getElementById('tipoProducto').value = productoActual.tipo_producto;
        document.getElementById('stock').value = productoActual.stock;
        document.getElementById('fechaCaducidad').value = productoActual.fecha_caducidad ? 
            new Date(productoActual.fecha_caducidad).toISOString().split('T')[0] : '';
        document.getElementById('descuento').value = productoActual.descuento;
        document.getElementById('idProveedor').value = productoActual.id_proveedor;
        
        // Mostrar el modal
        productoModal.show();
        document.getElementById('productoModalLabel').textContent = 'Editar Producto';
    } catch (error) {
        console.error('Error al editar producto:', error);
        alert('Error al cargar el producto');
    }
}

async function eliminarProducto(id) {
    if (!confirm('¿Está seguro de que desea eliminar este producto?')) return;
    
    try {
        const response = await fetch(`${contextPath}/api/productos/${id}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Error al eliminar producto');
        
        await cargarProductos(); // Recargar la tabla
        alert('Producto eliminado exitosamente');
    } catch (error) {
        console.error('Error al eliminar producto:', error);
        alert('Error al eliminar el producto');
    }
}

async function guardarProducto(event) {
    event.preventDefault();
    
    const formData = {
        id_ropa: document.getElementById('productoId').value || null,
        nombre: document.getElementById('nombre').value,
        descripcion: document.getElementById('descripcion').value,
        precio: parseFloat(document.getElementById('precio').value),
        categoria: document.getElementById('categoria').value,
        tipo_producto: document.getElementById('tipoProducto').value,
        stock: parseInt(document.getElementById('stock').value),
        fecha_caducidad: document.getElementById('fechaCaducidad').value || null,
        descuento: parseFloat(document.getElementById('descuento').value) || 0,
        id_proveedor: parseInt(document.getElementById('idProveedor').value)
    };

    const url = `${contextPath}/api/productos${formData.id_ropa ? `/${formData.id_ropa}` : ''}`;
    const method = formData.id_ropa ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) throw new Error('Error al guardar producto');

        await cargarProductos(); // Recargar la tabla
        productoModal.hide();
        alert(formData.id_ropa ? 'Producto actualizado exitosamente' : 'Producto creado exitosamente');
    } catch (error) {
        console.error('Error al guardar producto:', error);
        alert('Error al guardar el producto');
    }
}

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', async function() {
    console.log('DOM Cargado - Inicializando gestionProductos.js');
    
    if (typeof contextPath === 'undefined') {
        console.error('Error: contextPath no está definido');
        return;
    }
    console.log('contextPath:', contextPath);

    // Cargar datos iniciales
    await cargarProductos();
    await cargarProveedores();

    // Configurar botones de exportación
    const btnExcel = document.getElementById('btnExportExcel');
    const btnPDF = document.getElementById('btnExportPDF');
    const btnPrint = document.getElementById('btnPrint');

    console.log('Botones encontrados:', {
        excel: btnExcel !== null,
        pdf: btnPDF !== null,
        print: btnPrint !== null
    });

    // Función para manejar exportaciones
    async function handleExport(tipo, url, mimeType, filename) {
        console.log(`Iniciando exportación a ${tipo}`);
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Accept': mimeType
                }
            });
            
            if (!response.ok) {
                const text = await response.text();
                throw new Error(`Error HTTP ${response.status}: ${text}`);
            }
            
            const blob = await response.blob();
            console.log(`Blob recibido para ${tipo}:`, blob.type, blob.size, 'bytes');
            
            const downloadUrl = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = downloadUrl;
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(downloadUrl);
            document.body.removeChild(a);
            console.log(`Descarga de ${tipo} completada`);
        } catch (error) {
            console.error(`Error al exportar ${tipo}:`, error);
            alert(`Error al exportar a ${tipo}: ${error.message}`);
        }
    }

    // Configurar eventos de los botones
    if (btnExcel) {
        btnExcel.onclick = () => {
            console.log('Click en botón Excel');
            handleExport(
                'Excel',
                `${contextPath}/api/productos/export/excel`,
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                'productos.xlsx'
            );
        };
    }

    if (btnPDF) {
        btnPDF.onclick = () => {
            console.log('Click en botón PDF');
            handleExport(
                'PDF',
                `${contextPath}/api/productos/export/pdf`,
                'application/pdf',
                'productos.pdf'
            );
        };
    }

    if (btnPrint) {
        btnPrint.onclick = () => {
            // Mostrar columnas adicionales antes de imprimir
            const printOnlyElements = document.querySelectorAll('.print-only');
            printOnlyElements.forEach(el => el.style.display = 'table-cell');
            
            // Ocultar elementos no imprimibles
            const noPrintElements = document.querySelectorAll('.no-print');
            noPrintElements.forEach(el => el.style.display = 'none');
            
            window.print();
            
            // Restaurar la vista normal después de imprimir
            printOnlyElements.forEach(el => el.style.display = 'none');
            noPrintElements.forEach(el => el.style.display = '');
        };
    }

    // Inicializar el modal
    productoModal = new bootstrap.Modal(document.getElementById('productoModal'));
    
    // Configurar el botón Nuevo Producto
    const btnNuevoProducto = document.getElementById('btnNuevoProducto');
    if (btnNuevoProducto) {
        btnNuevoProducto.onclick = () => {
            // Limpiar el formulario
            document.getElementById('productoForm').reset();
            document.getElementById('productoId').value = '';
            document.getElementById('productoModalLabel').textContent = 'Nuevo Producto';
            productoModal.show();
        };
    }

    // Configurar el botón Guardar del modal
    const btnGuardarProducto = document.getElementById('btnGuardarProducto');
    if (btnGuardarProducto) {
        btnGuardarProducto.onclick = guardarProducto;
    }
});

// Variables para paginación
let paginaActual = 1;
const productosPorPagina = 10;
let todosLosProductos = [];

function actualizarPaginacion() {
    const totalPaginas = Math.ceil(todosLosProductos.length / productosPorPagina);
    
    // Eliminar TODOS los contenedores de paginación existentes
    const paginacionesExistentes = document.querySelectorAll('.container.mt-3.no-print');
    paginacionesExistentes.forEach(elem => elem.remove());
    
    const paginacionContainer = document.createElement('div');
    paginacionContainer.className = 'container mt-3 no-print';
    paginacionContainer.innerHTML = `
        <nav aria-label="Navegación de productos">
            <ul class="pagination justify-content-center">
                <li class="page-item ${paginaActual === 1 ? 'disabled' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual - 1})">Anterior</a>
                </li>
                ${generarBotonesPagina(totalPaginas)}
                <li class="page-item ${paginaActual === totalPaginas ? 'disabled' : ''}">
                    <a class="page-link" href="#" onclick="cambiarPagina(${paginaActual + 1})">Siguiente</a>
                </li>
            </ul>
        </nav>
        <div class="text-center">
            Mostrando ${(paginaActual - 1) * productosPorPagina + 1} - 
            ${Math.min(paginaActual * productosPorPagina, todosLosProductos.length)} 
            de ${todosLosProductos.length} productos
        </div>
    `;
    
    // Agregar el nuevo contenedor después de la tabla
    document.querySelector('.table').after(paginacionContainer);
}

function generarBotonesPagina(totalPaginas) {
    let botones = '';
    const maxBotones = 5;
    let inicio = Math.max(1, paginaActual - Math.floor(maxBotones / 2));
    let fin = Math.min(totalPaginas, inicio + maxBotones - 1);
    
    if (inicio > 1) {
        botones += `
            <li class="page-item">
                <a class="page-link" href="#" onclick="cambiarPagina(1)">1</a>
            </li>
            ${inicio > 2 ? '<li class="page-item disabled"><span class="page-link">...</span></li>' : ''}
        `;
    }
    
    for (let i = inicio; i <= fin; i++) {
        botones += `
            <li class="page-item ${i === paginaActual ? 'active' : ''}">
                <a class="page-link" href="#" onclick="cambiarPagina(${i})">${i}</a>
            </li>
        `;
    }
    
    if (fin < totalPaginas) {
        botones += `
            ${fin < totalPaginas - 1 ? '<li class="page-item disabled"><span class="page-link">...</span></li>' : ''}
            <li class="page-item">
                <a class="page-link" href="#" onclick="cambiarPagina(${totalPaginas})">${totalPaginas}</a>
            </li>
        `;
    }
    
    return botones;
}

function cambiarPagina(nuevaPagina) {
    const totalPaginas = Math.ceil(todosLosProductos.length / productosPorPagina);
    if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas) {
        paginaActual = nuevaPagina;
        cargarProductos();
    }
}
