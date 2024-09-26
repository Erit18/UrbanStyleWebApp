document.addEventListener("DOMContentLoaded", function() {
    fetch('/productos')
        .then(response => response.json())
        .then(data => {
            let productosDiv = document.getElementById('productos');
            data.forEach(producto => {
                let div = document.createElement('div');
                div.className = 'producto';
                div.innerHTML = `<h2>${producto.nombre}</h2><p>Precio: $${producto.precio}</p>`;
                productosDiv.appendChild(div);
            });
        });
});
