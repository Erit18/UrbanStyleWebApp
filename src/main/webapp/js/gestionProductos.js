// Funciones de carga
async function cargarProductos() {
    console.log('Iniciando cargarProductos()');
    try {
        const response = await fetch(`${contextPath}/api/productos`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
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
                <td>S/ ${producto.precio.toFixed(2)}</td>
                <td>${producto.stock}</td>
                <td>
                    <button class="btn btn-sm btn-primary me-1" onclick="editarProducto(${producto.id_ropa})">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.id_ropa})">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
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
            console.log('Click en botón Imprimir');
            window.print();
        };
    }
});
