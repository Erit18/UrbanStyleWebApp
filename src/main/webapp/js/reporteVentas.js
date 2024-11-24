document.addEventListener('DOMContentLoaded', function() {
    // Inicializar fechas por defecto
    const hoy = new Date();
    const inicioMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);
    
    document.getElementById('fechaFin').value = hoy.toISOString().split('T')[0];
    document.getElementById('fechaInicio').value = inicioMes.toISOString().split('T')[0];

    // Cargar datos iniciales
    cargarDatos();

    // Event listeners
    document.getElementById('btnFiltrar').addEventListener('click', cargarDatos);
    document.getElementById('btnExportExcel').addEventListener('click', exportarExcel);
    document.getElementById('btnExportPDF').addEventListener('click', exportarPDF);
    document.getElementById('btnPrint').addEventListener('click', () => window.print());
});

async function cargarDatos() {
    try {
        const fechaInicio = document.getElementById('fechaInicio').value;
        const fechaFin = document.getElementById('fechaFin').value;
        const categoria = document.getElementById('categoria').value;

        const params = new URLSearchParams({
            fechaInicio: fechaInicio,
            fechaFin: fechaFin,
            categoria: categoria
        });

        const response = await fetch(`${contextPath}/api/ventas?${params}`);
        if (!response.ok) {
            throw new Error('Error en la respuesta del servidor');
        }
        
        const ventas = await response.json();
        if (ventas.error) {
            throw new Error(ventas.error);
        }

        console.log('Datos de ventas recibidos:', ventas);
        
        actualizarTabla(ventas);
        const resumen = calcularResumen(ventas);
        actualizarResumen(resumen);
        const datosGraficos = prepararDatosGraficos(ventas);
        actualizarGraficos(datosGraficos);
        
    } catch (error) {
        console.error('Error al cargar datos:', error);
        mostrarError(`Error al cargar los datos: ${error.message}`);
    }
}

function calcularResumen(ventas) {
    const ventasTotales = ventas.reduce((sum, venta) => sum + venta.total, 0);
    const productosVendidos = ventas.reduce((sum, venta) => {
        const productos = venta.productos.split(', ');
        return sum + productos.reduce((prodSum, prod) => {
            const cantidad = prod.includes('x') ? 
                parseInt(prod.split('x')[1]) : 1;
            return prodSum + cantidad;
        }, 0);
    }, 0);
    
    const ticketPromedio = ventas.length > 0 ? ventasTotales / ventas.length : 0;

    return {
        ventasTotales,
        productosVendidos,
        ticketPromedio,
        ventasTotalesPorcentaje: 0,
        productosVendidosPorcentaje: 0,
        ticketPromedioPorcentaje: 0
    };
}

function prepararDatosGraficos(ventas) {
    // Agrupar ventas por día
    const ventasPorDia = {};
    ventas.forEach(venta => {
        const fecha = formatearFecha(venta.fecha);
        ventasPorDia[fecha] = (ventasPorDia[fecha] || 0) + venta.total;
    });

    // Agrupar ventas por categoría (usando la categoría del primer producto)
    const ventasPorCategoria = {
        'hombre': 0,
        'mujer': 0,
        'unisex': 0
    };
    
    ventas.forEach(venta => {
        // Asumimos que todos los productos de una venta son de la misma categoría
        const total = venta.total;
        // Por ahora asignamos a 'unisex' si no podemos determinar la categoría
        ventasPorCategoria['unisex'] += total;
    });

    return {
        ventasPorDia: {
            labels: Object.keys(ventasPorDia),
            datos: Object.values(ventasPorDia)
        },
        ventasPorCategoria: {
            labels: Object.keys(ventasPorCategoria),
            datos: Object.values(ventasPorCategoria)
        }
    };
}

function actualizarResumen(resumen) {
    document.getElementById('ventasTotales').textContent = `S/. ${resumen.ventasTotales.toFixed(2)}`;
    document.getElementById('productosVendidos').textContent = resumen.productosVendidos;
    document.getElementById('ticketPromedio').textContent = `S/. ${resumen.ticketPromedio.toFixed(2)}`;
    
    // Actualizar porcentajes
    actualizarPorcentaje('ventasTotalesPorcentaje', resumen.ventasTotalesPorcentaje);
    actualizarPorcentaje('productosVendidosPorcentaje', resumen.productosVendidosPorcentaje);
    actualizarPorcentaje('ticketPromedioPorcentaje', resumen.ticketPromedioPorcentaje);
}

function actualizarPorcentaje(elementId, valor) {
    const elemento = document.getElementById(elementId);
    elemento.textContent = `${valor >= 0 ? '+' : ''}${valor}% vs periodo anterior`;
    elemento.className = valor >= 0 ? 'text-success' : 'text-danger';
}

// Variables globales para los gráficos
let ventasPorDiaChart = null;
let ventasPorCategoriaChart = null;

function actualizarGraficos(datos) {
    // Destruir gráficos existentes si existen
    if (ventasPorDiaChart) {
        ventasPorDiaChart.destroy();
    }
    if (ventasPorCategoriaChart) {
        ventasPorCategoriaChart.destroy();
    }

    // Gráfico de ventas por día
    const ventasPorDiaCtx = document.getElementById('ventasPorDiaChart').getContext('2d');
    ventasPorDiaChart = new Chart(ventasPorDiaCtx, {
        type: 'line',
        data: {
            labels: datos.ventasPorDia.labels,
            datasets: [{
                label: 'Ventas Diarias',
                data: datos.ventasPorDia.datos,
                borderColor: 'rgb(75, 192, 192)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Ventas Diarias'
                }
            }
        }
    });

    // Gráfico de ventas por categoría
    const ventasPorCategoriaCtx = document.getElementById('ventasPorCategoriaChart').getContext('2d');
    ventasPorCategoriaChart = new Chart(ventasPorCategoriaCtx, {
        type: 'doughnut',
        data: {
            labels: datos.ventasPorCategoria.labels,
            datasets: [{
                data: datos.ventasPorCategoria.datos,
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)'
                ]
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Ventas por Categoría'
                }
            }
        }
    });
}

function actualizarTabla(ventas) {
    const tbody = document.getElementById('tablaVentas');
    tbody.innerHTML = '';
    
    if (!ventas || ventas.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td colspan="6" class="text-center">No se encontraron ventas en el período seleccionado</td>
        `;
        tbody.appendChild(tr);
        return;
    }

    ventas.forEach(venta => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${venta.id || '-'}</td>
            <td>${formatearFecha(venta.fecha)}</td>
            <td>${venta.nombreCliente || 'Cliente no especificado'}</td>
            <td>${venta.productos || 'No hay productos'}</td>
            <td>S/. ${venta.total.toFixed(2)}</td>
            <td><span class="badge bg-${obtenerColorEstado(venta.estado)}">${venta.estado}</span></td>
        `;
        tbody.appendChild(tr);
    });
}

function formatearFecha(fecha) {
    if (!fecha) return '-';
    return new Date(fecha).toLocaleDateString('es-PE', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

function formatearProductos(productos) {
    if (!productos) return 'No hay productos';
    return productos;
}

function obtenerColorEstado(estado) {
    const colores = {
        'pagado': 'warning',
        'completado': 'success',
        'pendiente': 'info',
        'cancelado': 'danger'
    };
    return colores[estado] || 'secondary';
}

// Funciones de exportación
function exportarExcel() {
    console.log('Exportando a Excel...');
    // Implementar lógica de exportación a Excel
}

function exportarPDF() {
    console.log('Exportando a PDF...');
    // Implementar lógica de exportación a PDF
}

function mostrarError(mensaje) {
    // Agregar al HTML un div para mostrar alertas
    const alertaDiv = document.createElement('div');
    alertaDiv.className = 'alert alert-danger alert-dismissible fade show';
    alertaDiv.innerHTML = `
        ${mensaje}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.querySelector('.container').insertBefore(alertaDiv, document.querySelector('.filtros-container'));
}
 