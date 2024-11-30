function añadirAlCarrito(nombre, precio, talla, color) {
    // Verificar que se haya seleccionado una talla
    if (!talla) {
        Swal.fire({
            title: '¡Atención!',
            text: 'Por favor, selecciona una talla',
            icon: 'warning',
            showConfirmButton: false,
            timer: 1500
        });
        return;
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

    // Mostrar notificación animada
    Swal.fire({
        title: '¡Añadido al carrito!',
        text: `${nombre} (Talla: ${talla})`,
        icon: 'success',
        showConfirmButton: false,
        timer: 1500,
        position: 'top-end',
        toast: true,
        background: '#1a1a1a',
        color: '#fff',
        iconColor: '#28a745',
        customClass: {
            popup: 'animated slideInRight'
        },
        showClass: {
            popup: 'animate__animated animate__fadeInRight'
        },
        hideClass: {
            popup: 'animate__animated animate__fadeOutRight'
        }
    });
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

function actualizarTotales() {
    let carrito = JSON.parse(localStorage.getItem('cart')) || [];
    let subtotal = 0;
    
    carrito.forEach(producto => {
        subtotal += producto.price * producto.quantity;
    });

    // Calcular descuento y total
    const descuento = subtotal * 0.20;
    const envio = 0;
    const total = subtotal - descuento + envio;

    // Formatear números a 2 decimales
    const subtotalFormateado = subtotal.toFixed(2);
    const descuentoFormateado = descuento.toFixed(2);
    const envioFormateado = envio.toFixed(2);
    const totalFormateado = total.toFixed(2);

    // Actualizar el DOM con los valores formateados
    document.querySelector('.resumen-detalles p:nth-child(1) span').textContent = `S/${subtotalFormateado}`;
    document.querySelector('.resumen-detalles p:nth-child(2) span').textContent = `-S/${descuentoFormateado}`;
    document.querySelector('.resumen-detalles p:nth-child(3) span').textContent = `S/${envioFormateado}`;
    document.querySelector('h3 span').textContent = `S/${totalFormateado}`;
}

function renderCarrito() {
    let carrito = JSON.parse(localStorage.getItem('cart')) || [];
    const contenedorCarrito = document.querySelector('.productos');
    if (!contenedorCarrito) return;

    contenedorCarrito.innerHTML = '';

    if (carrito.length === 0) {
        contenedorCarrito.innerHTML = '<p>El carrito está vacío.</p>';
        actualizarTotales(); // Actualizar totales incluso si está vacío
        return;
    }

    carrito.forEach(producto => {
        const precioTotal = (producto.price * producto.quantity).toFixed(2); // Formatear precio total por producto
        contenedorCarrito.innerHTML += `
            <div class="producto">
                <h3>${producto.name}</h3>
                <p>Talla: ${producto.talla}</p>
                <p>Categoría: ${producto.color || 'N/A'}</p>
                <p>Precio: S/${producto.price.toFixed(2)}</p>
                <p>Cantidad: ${producto.quantity}</p>
                <p>Total: S/${precioTotal}</p>
            </div>
        `;
    });

    actualizarTotales();
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

// Asegurarse de que los estilos de animate.css estén disponibles
document.addEventListener('DOMContentLoaded', function() {
    if (!document.querySelector('link[href*="animate.css"]')) {
        const animateCss = document.createElement('link');
        animateCss.rel = 'stylesheet';
        animateCss.href = 'https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css';
        document.head.appendChild(animateCss);
    }
});
