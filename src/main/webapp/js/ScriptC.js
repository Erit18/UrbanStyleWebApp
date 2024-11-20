function añadirAlCarrito(nombre, precio, talla, color) {
    // Verificar que se haya seleccionado una talla
    if (!talla) {
        return; // La función se detiene si no hay talla seleccionada
    }

    let carrito = JSON.parse(localStorage.getItem('cart')) || [];
    // Buscar producto con el mismo nombre Y talla
    const productoExistente = carrito.find(item => 
        item.name === nombre && item.talla === talla
    );

    if (productoExistente) {
        productoExistente.quantity += 1;
    } else {
        carrito.push({ 
            name: nombre, 
            price: precio, 
            quantity: 1, 
            talla: talla, 
            color: color 
        });
    }

    localStorage.setItem('cart', JSON.stringify(carrito));
    mostrarMensaje(`${nombre} (Talla: ${talla}) añadido al carrito`);
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
    if (!contenedorCarrito) return; // Agregar esta verificación

    contenedorCarrito.innerHTML = '';
    let subtotal = 0;

    if (carrito.length === 0) {
        contenedorCarrito.innerHTML = '<p>El carrito está vacío.</p>';
        const finalizarBtn = document.getElementById('btnFinalizarCompra');
        if (finalizarBtn) {
            finalizarBtn.disabled = true;
        }
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

// Agregar función para finalizar compra
function finalizarCompra() {
    const carrito = JSON.parse(localStorage.getItem('cart')) || [];
    console.log('Carrito:', carrito); // Debug
    
    if (carrito.length === 0) {
        mostrarMensaje('El carrito está vacío');
        return;
    }
    
    console.log('Redirigiendo...'); // Debug
    window.location.href = './FinalizarCompra.html';
}

// Simplificar el DOMContentLoaded
document.addEventListener('DOMContentLoaded', function() {
    if (document.querySelector('.productos')) {
        renderCarrito();
    }
    if (window.location.href.includes('FinalizarCompra.html')) {
        cargarResumenPedido();
    }
});

// Función para cargar el resumen en FinalizarCompra.html
function cargarResumenPedido() {
    console.log('Cargando resumen del pedido...'); // Debug
    const carrito = JSON.parse(localStorage.getItem('cart')) || [];
    console.log('Carrito cargado:', carrito); // Debug

    // Calcular subtotal
    let subtotal = 0;
    carrito.forEach(producto => {
        subtotal += producto.price * producto.quantity;
        console.log(`Producto: ${producto.name}, Precio: ${producto.price}, Cantidad: ${producto.quantity}`); // Debug
    });

    console.log('Subtotal calculado:', subtotal); // Debug

    // Obtener elementos del DOM
    const subtotalElement = document.getElementById('subtotal');
    const descuentoElement = document.getElementById('descuento');
    const envioElement = document.getElementById('entrega');
    const totalElement = document.getElementById('total');

    console.log('Elemento subtotal encontrado:', subtotalElement); // Debug

    // Actualizar valores
    if (subtotalElement) {
        subtotalElement.textContent = `S/${subtotal.toFixed(2)}`;
    } else {
        console.error('No se encontró el elemento subtotal');
    }

    // Calcular otros valores
    const descuento = subtotal * 0.20;
    const envio = 0;
    const total = subtotal - descuento + envio;

    // Actualizar otros elementos
    if (descuentoElement) descuentoElement.textContent = `-S/${descuento.toFixed(2)}`;
    if (envioElement) envioElement.textContent = `S/${envio.toFixed(2)}`;
    if (totalElement) totalElement.textContent = `S/${total.toFixed(2)}`;
}

// Asegurarse de que la función se ejecute cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM Cargado'); // Debug
    if (window.location.href.includes('FinalizarCompra.html')) {
        console.log('Página de finalizar compra detectada'); // Debug
        cargarResumenPedido();
    }
});

// Agregar función para confirmar pago
function confirmarPago() {
    // Mostrar mensaje de éxito
    alert('¡Pago confirmado! Gracias por tu compra.');
    
    // Limpiar el carrito
    localStorage.removeItem('cart');
    
    // Redirigir a la página principal o de confirmación
    window.location.href = '../catalogo/index.html';
}
