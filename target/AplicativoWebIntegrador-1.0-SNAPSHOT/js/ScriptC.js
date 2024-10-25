function añadirAlCarrito(nombre, precio, talla, color) {
    let carrito = JSON.parse(localStorage.getItem('cart')) || [];
    const productoExistente = carrito.find(item => item.name === nombre);

    if (productoExistente) {
        productoExistente.quantity += 1;
    } else {
        carrito.push({ name: nombre, price: precio, quantity: 1, talla: talla, color: color });
    }

    localStorage.setItem('cart', JSON.stringify(carrito));
    mostrarMensaje(`${nombre} añadido al carrito`); // Mensaje visual
}

function mostrarMensaje(mensaje) {
    const mensajeDiv = document.createElement('div');
    mensajeDiv.className = 'mensaje';
    mensajeDiv.textContent = mensaje;
    document.body.appendChild(mensajeDiv);

    // Eliminar el mensaje después de 2 segundos
    setTimeout(() => {
        mensajeDiv.remove();
    }, 2000); // El mensaje se mostrará durante 2 segundos
}

function vaciarCarrito() {
    localStorage.removeItem('cart'); // Elimina el carrito del localStorage
    renderCarrito(); // Actualiza la vista del carrito
    mostrarMensaje('Carrito vaciado'); // Mensaje visual
}

function renderCarrito() {
    let carrito = JSON.parse(localStorage.getItem('cart')) || [];
    const contenedorCarrito = document.querySelector('.productos');
    contenedorCarrito.innerHTML = '';

    let subtotal = 0;

    if (carrito.length === 0) {
        contenedorCarrito.innerHTML = '<p>El carrito está vacío.</p>';
    } else {
        carrito.forEach(producto => {
            const productoHTML = `
                <div class="producto">
                    <h3>${producto.name}</h3>
                    <p>Talla: ${producto.talla}</p>
                    <p>Color: ${producto.color}</p>
                    <p>Precio: S/${producto.price}</p>
                    <p>Cantidad: ${producto.quantity}</p>
                </div>
            `;
            contenedorCarrito.innerHTML += productoHTML;
            subtotal += producto.price * producto.quantity;
        });
    }

    document.querySelector('.resumen-detalles span').textContent = `S/${subtotal}`;
    document.querySelector('.resumen h3 span').textContent = `S/${subtotal}`;
}

// Llama a renderCarrito al cargar la página
document.addEventListener('DOMContentLoaded', renderCarrito);
