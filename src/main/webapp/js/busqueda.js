document.addEventListener('DOMContentLoaded', function() {
    const searchBox = document.getElementById('searchBox');
    const productos = document.getElementsByClassName('producto-item');
    const noResults = document.getElementById('noResults');

    searchBox.addEventListener('keyup', function() {
        const searchTerm = this.value.toLowerCase().trim();
        let encontrado = false;

        Array.from(productos).forEach(producto => {
            const titulo = producto.querySelector('.card-title').textContent.toLowerCase();
            const precio = producto.querySelector('.card-text').textContent.toLowerCase();
            
            // Buscar en título y precio
            if (titulo.includes(searchTerm) || precio.includes(searchTerm)) {
                producto.style.display = '';
                encontrado = true;
            } else {
                producto.style.display = 'none';
            }
        });

        // Mostrar/ocultar mensaje de no resultados
        noResults.style.display = encontrado ? 'none' : 'block';
    });

    // Limpiar búsqueda cuando el campo está vacío
    searchBox.addEventListener('input', function() {
        if (this.value === '') {
            Array.from(productos).forEach(producto => {
                producto.style.display = '';
            });
            noResults.style.display = 'none';
        }
    });
});