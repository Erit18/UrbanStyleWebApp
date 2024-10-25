   // Datos para el gráfico de ventas
   const salesData = {
    labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio'],
    datasets: [{
        label: 'Ventas ($)',
        data: [5000, 7000, 8000, 6000, 9000, 10000],
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
    }]
};

// Configuración del gráfico de ventas
const salesConfig = {
    type: 'bar',
    data: salesData,
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};

// Renderizar gráfico de ventas
const salesChart = new Chart(
    document.getElementById('salesChart'),
    salesConfig
);

// Datos para el gráfico de inventario
const inventoryData = {
    labels: ['POLERAS', 'POLOS', 'ACCESORIOS',  'PANTALONES'],
    datasets: [{
        label: 'Cantidad en Inventario',
        data: [150, 80, 60, 120, 30],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
    }]
};

// Configuración del gráfico de inventario
const inventoryConfig = {
    type: 'bar',
    data: inventoryData,
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
};

// Renderizar gráfico de inventario
const inventoryChart = new Chart(
    document.getElementById('inventoryChart'),
    inventoryConfig
);

// Datos para el gráfico circular de ventas por categoría
const salesPieData = {
    labels: ['Polos Mujer', 'Polos Hombre', 'Poleras Hombre', 'Poleras Mujer', 'Pantalones Hombre', 'Pantalones Mujer'],
    datasets: [{
        label: 'Ventas por Categoría (S/)',
        data: [30000, 15000, 10000, 20000, 12000, 20000],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(134, 112, 255, 0.2)'
        ],
        borderColor: [
            'rgba(255, 99, 132, 1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(143, 112, 155, 1)'
        ],
        borderWidth: 1
    }]
};

// Configuración del gráfico circular
const salesPieConfig = {
    type: 'pie',
    data: salesPieData,
    options: {
        responsive: true
    }
};

// Renderizar gráfico circular
const salesPieChart = new Chart(
    document.getElementById('salesPieChart'),
    salesPieConfig
);