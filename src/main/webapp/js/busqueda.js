document.addEventListener('DOMContentLoaded', function() {
    // Usar el buscador de la parte superior
    const searchBox = document.querySelector('input[placeholder="Buscar"]');
    const productos = document.getElementsByClassName('producto-item');
    const noResults = document.getElementById('noResults');

    if (searchBox) {
        searchBox.addEventListener('keyup', function() {
            const searchTerm = this.value.toLowerCase().trim();
            let encontrado = false;

            Array.from(productos).forEach(producto => {
                const titulo = producto.querySelector('.card-title').textContent.toLowerCase();
                const precio = producto.querySelector('.card-text').textContent.toLowerCase();
                const descripcion = producto.querySelector('.card-description')?.textContent.toLowerCase() || '';
                
                if (titulo.includes(searchTerm) || 
                    precio.includes(searchTerm) || 
                    descripcion.includes(searchTerm)) {
                    producto.style.display = '';
                    encontrado = true;
                } else {
                    producto.style.display = 'none';
                }
            });

            if (noResults) {
                noResults.style.display = encontrado ? 'none' : 'block';
            }
        });

        searchBox.addEventListener('input', function() {
            if (this.value === '') {
                Array.from(productos).forEach(producto => {
                    producto.style.display = '';
                });
                if (noResults) {
                    noResults.style.display = 'none';
                }
            }
        });
    }
});