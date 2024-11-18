
async function cargarPedidos() {
    console.log('Iniciando cargarPedidos()');
    try {
        const response = await fetch(`${contextPath}/api/pedidos`);
        
        if (!response.ok) {
            throw new Error('Error al cargar pedidos');
        }
        
        const pedidos = await response.json();
        const tbody = document.getElementById('tablaPedidos');
        tbody.innerHTML = '';
        
        pedidos.forEach(pedido => {
            const tr = document.createElement('tr');
            
            tr.innerHTML = `
                <td>PED-${pedido.id_pedido.toString().padStart(3, '0')}</td>
                <td>${new Date(pedido.fecha_pedido).toLocaleDateString()}</td>
                <td>${pedido.nombre_usuario}</td>
                <td>S/ ${pedido.total.toFixed(2)}</td>
                <td>
                    <span class="badge ${getEstadoClass(pedido.estado)}">
                        ${pedido.estado}
                    </span>
                </td>
                <td class="no-print">
                    <button class="btn btn-sm btn-primary me-1" onclick="verDetallesPedido(${pedido.id_pedido})">
                        <i class="bi bi-eye"></i>
                    </button>
                    <button class="btn btn-sm btn-warning me-1" onclick="cambiarEstado(${pedido.id_pedido})">
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

async function verDetallesPedido(id) {
    try {
        const response = await fetch(`${contextPath}/api/pedidos/${id}/detalles`);
        if (!response.ok) throw new Error('Error al obtener detalles');
        
        const detalles = await response.json();
        const tbody = document.getElementById('detallesPedidoBody');
        tbody.innerHTML = '';
        
        detalles.forEach(detalle => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${detalle.nombre_producto}</td>
                <td>${detalle.cantidad}</td>
                <td>S/ ${detalle.precio_unitario.toFixed(2)}</td>
                <td>S/ ${(detalle.cantidad * detalle.precio_unitario).toFixed(2)}</td>
            `;
            tbody.appendChild(tr);
        });
        
        const modal = new bootstrap.Modal(document.getElementById('detallesPedidoModal'));
        modal.show();
    } catch (error) {
        console.error('Error al ver detalles:', error);
        alert('Error al cargar los detalles del pedido');
    }
}

async function cambiarEstado(id) {
    try {
        const response = await fetch(`${contextPath}/api/pedidos/${id}/estado`);
        if (!response.ok) throw new Error('Error al obtener estados');
        
        const estados = await response.json();
        const estadoActual = estados.estadoActual;
        const estadosDisponibles = estados.estadosDisponibles;
        
        const selectHTML = estadosDisponibles
            .map(estado => `<option value="${estado}">${estado}</option>`)
            .join('');
            
        Swal.fire({
            title: 'Cambiar Estado del Pedido',
            html: `
                <select id="nuevoEstado" class="form-select">
                    ${selectHTML}
                </select>
            `,
            showCancelButton: true,
            confirmButtonText: 'Actualizar',
            cancelButtonText: 'Cancelar'
        }).then(async (result) => {
            if (result.isConfirmed) {
                const nuevoEstado = document.getElementById('nuevoEstado').value;
                await actualizarEstadoPedido(id, nuevoEstado);
            }
        });
    } catch (error) {
        console.error('Error al cambiar estado:', error);
        alert('Error al actualizar el estado del pedido');
    }
}

async function actualizarEstadoPedido(id, nuevoEstado) {
    try {
        const response = await fetch(`${contextPath}/api/pedidos/${id}/estado`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ estado: nuevoEstado })
        });
        
        if (!response.ok) throw new Error('Error al actualizar estado');
        
        await cargarPedidos();
        Swal.fire('¡Éxito!', 'Estado actualizado correctamente', 'success');
    } catch (error) {
        console.error('Error al actualizar estado:', error);
        Swal.fire('Error', 'No se pudo actualizar el estado', 'error');
    }
}

// Inicialización cuando el DOM está listo
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

    // Configurar eventos de exportación similar al código anterior
    // pero adaptado para pedidos en lugar de productos
    if (btnExcel) {
        btnExcel.onclick = () => handleExport(
            'Excel',
            `${contextPath}/api/pedidos/export/excel`,
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
            'pedidos.xlsx'
        );
    }

    if (btnPDF) {
        btnPDF.onclick = () => handleExport(
            'PDF',
            `${contextPath}/api/pedidos/export/pdf`,
            'application/pdf',
            'pedidos.pdf'
        );
    }

    if (btnPrint) {
        btnPrint.onclick = () => {
            const printOnlyElements = document.querySelectorAll('.print-only');
            const noPrintElements = document.querySelectorAll('.no-print');
            
            printOnlyElements.forEach(el => el.style.display = 'table-cell');
            noPrintElements.forEach(el => el.style.display = 'none');
            
            window.print();
            
            printOnlyElements.forEach(el => el.style.display = 'none');
            noPrintElements.forEach(el => el.style.display = '');
        };
    }
});