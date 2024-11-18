async function cargarPedidos() {
    console.log('Iniciando cargarPedidos()');
    try {
        const response = await fetch(`${contextPath}/PedidosServlet`);
        
        if (!response.ok) {
            throw new Error('Error al cargar pedidos');
        }
        
        const pedidos = await response.json();
        const tbody = document.getElementById('tablaPedidos');
        tbody.innerHTML = '';
        
        pedidos.forEach(pedido => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${pedido.nombreCliente}</td>
                <td>${new Date(pedido.fechaPedido).toLocaleDateString()}</td>
                <td>
                    <span class="badge ${getEstadoClass(pedido.estado)}">
                        ${pedido.estado}
                    </span>
                </td>
                <td>S/ ${pedido.total.toFixed(2)}</td>
                <td class="no-print">
                    <button class="btn btn-sm btn-primary me-1" onclick="verDetallesPedido(${pedido.idPedido})">
                        <i class="bi bi-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-warning me-1" onclick="cambiarEstado(${pedido.idPedido})">
                        <i class="bi bi-arrow-repeat"></i>
                    </button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error al cargar pedidos:', error);
    }
}

function getEstadoClass(estado) {
    const clases = {
        'pendiente': 'bg-warning',
        'pagado': 'bg-info',
        'enviado': 'bg-primary',
        'completado': 'bg-success',
        'cancelado': 'bg-danger'
    };
    return clases[estado] || 'bg-secondary';
}

async function handleExport(tipo, url, contentType, fileName) {
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Error al exportar a ${tipo}`);
        }

        const blob = await response.blob();
        const link = document.createElement('a');
        link.href = window.URL.createObjectURL(blob);
        link.download = fileName;
        link.click();
        console.log(`${tipo} exportado correctamente`);
    } catch (error) {
        console.error(`Error al exportar a ${tipo}:`, error);
    }
}

document.addEventListener('DOMContentLoaded', async function() {
    console.log('DOM Cargado - Inicializando gestionPedidos.js');
    
    if (typeof contextPath === 'undefined') {
        console.error('Error: contextPath no está definido');
        return;
    }
    
    await cargarPedidos();

    // Configurar botones de exportación
    const btnExcel = document.getElementById('btnExportExcel');
    const btnPDF = document.getElementById('btnExportPDF');
    const btnPrint = document.getElementById('btnPrint');

    if (btnExcel) {
        btnExcel.onclick = () => {
            handleExport(
                'Excel',
                `${contextPath}/api/pedidos/export/excel`, // Cambiar si usas otra ruta
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                'pedidos.xlsx'
            );
        };
    }

    if (btnPDF) {
        btnPDF.onclick = () => {
            handleExport(
                'PDF',
                `${contextPath}/api/pedidos/export/pdf`, // Cambiar si usas otra ruta
                'application/pdf',
                'pedidos.pdf'
            );
        };
    }

    if (btnPrint) {
        btnPrint.onclick = () => window.print();
    }
});
