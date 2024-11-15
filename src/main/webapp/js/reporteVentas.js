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
    const fechaInicio = document.getElementById('fechaInicio').value;
    const fechaFin = document.getElementById('fechaFin').value;
    const categoria = document.getElementById('categoria').value;

    try {
        const params = new URLSearchParams({
            fechaInicio: fechaInicio,
            fechaFin: fechaFin,
            categoria: categoria
        });

        console.log('Cargando datos con parámetros:', {
            fechaInicio,
            fechaFin,
            categoria
        });

        const response = await fetch(`${contextPath}/api/ventas?${params}`);
        
        if (!response.ok) {
            throw new Error('Error al cargar datos');
        }
        
        const ventas = await response.json();
        console.log('Datos recibidos:', ventas);
        
        // Actualizar la UI
        actualizarTabla(ventas);
        actualizarResumen(calcularResumen(ventas));
        actualizarGraficos(prepararDatosGraficos(ventas));
        
    } catch (error) {
        console.error('Error al cargar datos:', error);
        alert('Error al cargar los datos del reporte');
    }
}

function calcularResumen(ventas) {
    const ventasTotales = ventas.reduce((sum, venta) => sum + venta.total, 0);
    const productosVendidos = ventas.length;
    const ticketPromedio = ventasTotales / (productosVendidos || 1);

    return {
        ventasTotales,
        productosVendidos,
        ticketPromedio,
        // Por ahora, porcentajes estáticos
        ventasTotalesPorcentaje: 15,
        productosVendidosPorcentaje: 8,
        ticketPromedioPorcentaje: 5
    };
}

function prepararDatosGraficos(ventas) {
    // Agrupar ventas por día
    const ventasPorDia = {};
    ventas.forEach(venta => {
        const fecha = venta.fecha;
        ventasPorDia[fecha] = (ventasPorDia[fecha] || 0) + venta.total;
    });

    // Obtener últimos 7 días
    const labels = Object.keys(ventasPorDia).sort().slice(-7);
    const datos = labels.map(fecha => ventasPorDia[fecha]);

    return {
        ventasPorDia: {
            labels,
            datos
        },
        ventasPorCategoria: {
            labels: ['Hombre', 'Mujer', 'Unisex'],
            datos: [45, 35, 20] // Por ahora, datos estáticos
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

function actualizarGraficos(datos) {
    // Gráfico de ventas por día
    const ventasPorDiaCtx = document.getElementById('ventasPorDiaChart').getContext('2d');
    new Chart(ventasPorDiaCtx, {
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
    new Chart(ventasPorCategoriaCtx, {
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
    
    // Limpiar completamente la tabla
    tbody.innerHTML = '';
    
    // Verificar si hay ventas
    if (!ventas || ventas.length === 0) {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td colspan="6" class="text-center">No se encontraron ventas en el período seleccionado</td>
        `;
        tbody.appendChild(tr);
        return;
    }

    // Actualizar con los nuevos datos
    ventas.forEach(venta => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${venta.id_venta}</td>
            <td>${venta.fecha}</td>
            <td>${venta.cliente}</td>
            <td>${venta.productos}</td>
            <td>S/. ${venta.total.toFixed(2)}</td>
            <td><span class="badge bg-${venta.estado === 'completado' ? 'success' : 'warning'}">${venta.estado}</span></td>
        `;
        tbody.appendChild(tr);
    });
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
 