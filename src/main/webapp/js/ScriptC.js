function añadirAlCarrito(nombre, precio, talla, categoria, tipo_producto) {
    // Verificar talla solo si es necesario
    if (talla !== null && !talla) {
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
    const productoExistente = carrito.find(item => 
        item.name === nombre && 
        (talla === null || item.talla === talla)
    );

    if (productoExistente) {
        productoExistente.quantity += 1;
    } else {
        const nuevoProducto = {
            name: nombre,
            price: parseFloat(precio),
            talla: talla || 'No especificada',
            categoria: categoria || 'No especificada',
            tipo_producto: tipo_producto || 'No especificado',
            quantity: 1
        };
        carrito.push(nuevoProducto);
        console.log('Producto añadido:', nuevoProducto); // Debug
    }

    localStorage.setItem('cart', JSON.stringify(carrito));
    console.log('Carrito actualizado:', carrito); // Debug
    
    // Mostrar notificación
    Swal.fire({
        title: '¡Producto agregado!',
        text: `${nombre} se agregó al carrito`,
        icon: 'success',
        showConfirmButton: false,
        timer: 1500,
        position: 'top-end',
        toast: true,
        background: '#1a1a1a',
        color: '#fff',
        iconColor: '#28a745'
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
    // Eliminar el carrito del localStorage
    localStorage.removeItem('cart');
    
    // Actualizar la vista del carrito
    renderCarrito();
    
    // Actualizar los totales a 0
    const resumen = {
        subtotal: 0,
        descuento: 0,
        entrega: 0,
        total: 0
    };
    
    // Guardar el resumen actualizado en localStorage
    localStorage.setItem('resumenPedido', JSON.stringify(resumen));
    
    // Actualizar los elementos en la página
    document.querySelector('.resumen-detalles p:nth-child(1) span').textContent = 'S/0.00';
    document.querySelector('.resumen-detalles p:nth-child(2) span').textContent = '-S/0.00';
    document.querySelector('.resumen-detalles p:nth-child(3) span').textContent = 'S/0.00';
    document.querySelector('.resumen h3 span').textContent = 'S/0.00';
    
    // Mostrar mensaje visual
    mostrarMensaje('Carrito vaciado');
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
    const contenedorCarrito = document.getElementById('productos');
    if (!contenedorCarrito) return;

    contenedorCarrito.innerHTML = '';

    if (carrito.length === 0) {
        contenedorCarrito.innerHTML = '<p>El carrito está vacío.</p>';
        return;
    }

    carrito.forEach(producto => {
        const precioTotal = (producto.price * producto.quantity).toFixed(2);
        contenedorCarrito.innerHTML += `
            <div class="producto">
                <h3>${producto.name}</h3>
                <p><strong>Tipo:</strong> ${producto.tipo_producto || 'No especificado'}</p>
                <p><strong>Talla:</strong> ${producto.talla || 'No especificado'}</p>
                <p><strong>Categoría:</strong> ${producto.categoria || 'No especificado'}</p>
                <p><strong>Precio:</strong> S/ ${producto.price.toFixed(2)}</p>
                <p><strong>Cantidad:</strong> ${producto.quantity}</p>
                <p><strong>Total:</strong> S/ ${precioTotal}</p>
            </div>
        `;
    });

    actualizarResumen(carrito);
}

// Agregar función para finalizar compra
function finalizarCompra() {
    const carrito = JSON.parse(localStorage.getItem('cart')) || [];
    
    if (carrito.length === 0) {
        mostrarMensaje('El carrito está vacío');
        return;
    }
    
    window.location.href = '${pageContext.request.contextPath}/views/catalogo/FinalizarCompra.jsp';
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
    const resumenGuardado = JSON.parse(localStorage.getItem('resumenPedido'));
    if (!resumenGuardado) return;

    // Actualizar elementos en FinalizarCompra.html
    const subtotalElement = document.getElementById('subtotal');
    const descuentoElement = document.getElementById('descuento');
    const entregaElement = document.getElementById('entrega');
    const totalElement = document.getElementById('total');

    if (subtotalElement) subtotalElement.textContent = `S/${resumenGuardado.subtotal.toFixed(2)}`;
    if (descuentoElement) descuentoElement.textContent = `-S/${resumenGuardado.descuento.toFixed(2)}`;
    if (entregaElement) entregaElement.textContent = `S/${resumenGuardado.entrega.toFixed(2)}`;
    if (totalElement) totalElement.textContent = `S/${resumenGuardado.total.toFixed(2)}`;

    // Agregar event listener para el método de envío
    const shippingMethod = document.getElementById('shippingMethod');
    if (shippingMethod) {
        shippingMethod.addEventListener('change', actualizarCostoEnvio);
    }
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
async function confirmarPago() {
    // Verificar campos según el método de envío seleccionado
    const shippingMethod = document.getElementById('shippingMethod').value;
    if (!shippingMethod) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo requerido',
            text: 'Por favor seleccione un método de envío'
        });
        return;
    }

    // Verificar campos de envío
    if (shippingMethod === 'delivery') {
        const camposDelivery = ['department', 'province', 'district', 'address'];
        for (let campo of camposDelivery) {
            const elemento = document.getElementById(campo);
            if (!elemento || !elemento.value.trim()) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Campos incompletos',
                    text: 'Por favor complete todos los campos de envío'
                });
                return;
            }
        }
    } else if (shippingMethod === 'store-pickup') {
        const camposPickup = ['storeBranch', 'pickupDate', 'pickupTime'];
        for (let campo of camposPickup) {
            const elemento = document.getElementById(campo);
            if (!elemento || !elemento.value.trim()) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Campos incompletos',
                    text: 'Por favor complete todos los campos de recojo en tienda'
                });
                return;
            }
        }
    }

    // Obtener el tipo de documento una sola vez
    const tipoDocumento = document.getElementById('shippingOpcion').value;
    if (!tipoDocumento) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo requerido',
            text: 'Por favor seleccione tipo de documento (Boleta/RUC)'
        });
        return;
    }

    if (tipoDocumento === 'boleta') {
        if (!document.getElementById('dni').value.trim()) {
            Swal.fire({
                icon: 'warning',
                title: 'Campo requerido',
                text: 'Por favor ingrese su DNI'
            });
            return;
        }
    } else if (tipoDocumento === 'ruc') {
        const camposRuc = ['ruc', 'direccion', 'razonsocial', 'email'];
        for (let campo of camposRuc) {
            const elemento = document.getElementById(campo);
            if (!elemento || !elemento.value.trim()) {
                Swal.fire({
                    icon: 'warning',
                    title: 'Campos incompletos',
                    text: 'Por favor complete todos los campos de facturación'
                });
                return;
            }
        }
    }

    // Verificar método de pago
    const paymentMethod = document.getElementById('paymentMethod').value;
    if (!paymentMethod) {
        Swal.fire({
            icon: 'warning',
            title: 'Campo requerido',
            text: 'Por favor seleccione un método de pago'
        });
        return;
    }

    try {
        // Usar la variable tipoDocumento ya declarada
        if (tipoDocumento === 'boleta') {
            generarBoleta();
        } else if (tipoDocumento === 'ruc') {
            generarFactura();
        }

        // Continuar con el proceso de confirmación
        Swal.fire({
            title: 'Procesando pago',
            html: 'Por favor espere...',
            timer: 2000,
            timerProgressBar: true,
            didOpen: () => {
                Swal.showLoading();
            }
        }).then(() => {
            Swal.fire({
                icon: 'success',
                title: '¡Pago exitoso!',
                text: 'Se ha generado su comprobante de pago',
                showConfirmButton: true,
                confirmButtonText: 'Aceptar'
            }).then(() => {
                localStorage.removeItem('cart');
                window.location.href = '../../index.jsp';
            });
        });
    } catch (error) {
        console.error('Error al generar el PDF:', error);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Hubo un problema al generar el comprobante'
        });
    }
}

function generarBoleta() {
    try {
        // Crear nueva instancia de jsPDF
        const doc = new jsPDF();
        const carrito = JSON.parse(localStorage.getItem('cart')) || [];
        const resumen = JSON.parse(localStorage.getItem('resumenPedido')) || {};
        
        // Encabezado
        doc.setFontSize(22);
        doc.text('BOLETA DE VENTA', 105, 20, { align: 'center' });
        
        // Datos del cliente
        doc.setFontSize(12);
        const nombreCliente = document.getElementById('invoiceName')?.value || 'No especificado';
        const dniCliente = document.getElementById('dni')?.value || 'No especificado';
        
        doc.text(`Nombre: ${nombreCliente}`, 20, 40);
        doc.text(`DNI: ${dniCliente}`, 20, 50);
        doc.text(`Fecha: ${new Date().toLocaleDateString()}`, 20, 60);
        
        // Tabla de productos
        let y = 80;
        doc.setFontSize(10);
        doc.text('Producto', 20, y);
        doc.text('Cantidad', 100, y);
        doc.text('Precio Unit.', 130, y);
        doc.text('Subtotal', 160, y);
        
        y += 10;
        carrito.forEach(item => {
            if (y > 250) {
                doc.addPage();
                y = 20;
            }
            const nombre = item.name.length > 30 ? item.name.substring(0, 30) + '...' : item.name;
            doc.text(nombre, 20, y);
            doc.text(item.quantity.toString(), 100, y);
            doc.text(`S/ ${item.price.toFixed(2)}`, 130, y);
            doc.text(`S/ ${(item.price * item.quantity).toFixed(2)}`, 160, y);
            y += 10;
        });
        
        // Totales
        y += 10;
        doc.text(`Subtotal: S/ ${resumen.subtotal?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.text(`Descuento: S/ ${resumen.descuento?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.text(`Envío: S/ ${resumen.entrega?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.setFontSize(12);
        doc.text(`TOTAL: S/ ${resumen.total?.toFixed(2) || '0.00'}`, 130, y);
        
        // Guardar PDF
        doc.save('boleta.pdf');
    } catch (error) {
        console.error('Error generando boleta:', error);
        throw error;
    }
}

function generarFactura() {
    try {
        const doc = new jsPDF();
        const carrito = JSON.parse(localStorage.getItem('cart')) || [];
        const resumen = JSON.parse(localStorage.getItem('resumenPedido')) || {};
        
        // Encabezado
        doc.setFontSize(22);
        doc.text('FACTURA', 105, 20, { align: 'center' });
        
        // Datos de la empresa
        doc.setFontSize(12);
        const rucEmpresa = document.getElementById('ruc')?.value || 'No especificado';
        const razonSocial = document.getElementById('razonsocial')?.value || 'No especificado';
        const direccionEmpresa = document.getElementById('direccion')?.value || 'No especificado';
        const emailEmpresa = document.getElementById('email')?.value || 'No especificado';
        
        doc.text(`RUC: ${rucEmpresa}`, 20, 40);
        doc.text(`Razón Social: ${razonSocial}`, 20, 50);
        doc.text(`Dirección: ${direccionEmpresa}`, 20, 60);
        doc.text(`Email: ${emailEmpresa}`, 20, 70);
        doc.text(`Fecha: ${new Date().toLocaleDateString()}`, 20, 80);
        
        // Tabla de productos
        let y = 100;
        doc.setFontSize(10);
        doc.text('Producto', 20, y);
        doc.text('Cantidad', 100, y);
        doc.text('Precio Unit.', 130, y);
        doc.text('Subtotal', 160, y);
        
        y += 10;
        carrito.forEach(item => {
            if (y > 250) {
                doc.addPage();
                y = 20;
            }
            const nombre = item.name.length > 30 ? item.name.substring(0, 30) + '...' : item.name;
            doc.text(nombre, 20, y);
            doc.text(item.quantity.toString(), 100, y);
            doc.text(`S/ ${item.price.toFixed(2)}`, 130, y);
            doc.text(`S/ ${(item.price * item.quantity).toFixed(2)}`, 160, y);
            y += 10;
        });
        
        // Totales
        y += 10;
        doc.text(`Subtotal: S/ ${resumen.subtotal?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.text(`IGV (18%): S/ ${(resumen.subtotal * 0.18)?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.text(`Descuento: S/ ${resumen.descuento?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.text(`Envío: S/ ${resumen.entrega?.toFixed(2) || '0.00'}`, 130, y);
        y += 10;
        doc.setFontSize(12);
        doc.text(`TOTAL: S/ ${resumen.total?.toFixed(2) || '0.00'}`, 130, y);
        
        // Guardar PDF
        doc.save('factura.pdf');
    } catch (error) {
        console.error('Error generando factura:', error);
        throw error;
    }
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

function actualizarResumen(carrito) {
    // Calcular subtotal
    const subtotal = carrito.reduce((total, producto) => {
        return total + (producto.price * producto.quantity);
    }, 0);

    // Calcular descuento (20%)
    const descuento = subtotal * 0.20;

    // Guardar subtotal y descuento en localStorage
    const resumen = {
        subtotal: subtotal,
        descuento: descuento,
        entrega: 0, // Valor inicial, se actualizará según la selección
        total: subtotal - descuento // Se actualizará con el costo de envío
    };
    localStorage.setItem('resumenPedido', JSON.stringify(resumen));

    // Actualizar los valores en el HTML del carrito
    document.querySelector('.resumen-detalles p:nth-child(1) span').textContent = `S/${subtotal.toFixed(2)}`;
    document.querySelector('.resumen-detalles p:nth-child(2) span').textContent = `-S/${descuento.toFixed(2)}`;
    document.querySelector('.resumen-detalles p:nth-child(3) span').textContent = `S/0.00`;
    document.querySelector('.resumen h3 span').textContent = `S/${(subtotal - descuento).toFixed(2)}`;
}

function actualizarCostoEnvio() {
    const shippingMethod = document.getElementById('shippingMethod');
    if (!shippingMethod) return;

    const resumenGuardado = JSON.parse(localStorage.getItem('resumenPedido'));
    if (!resumenGuardado) return;

    // Actualizar costo de envío según método seleccionado
    const costoEnvio = shippingMethod.value === 'delivery' ? 10.00 : 0.00;
    
    // Actualizar resumen con nuevo costo de envío
    resumenGuardado.entrega = costoEnvio;
    resumenGuardado.total = resumenGuardado.subtotal - resumenGuardado.descuento + costoEnvio;
    
    // Guardar actualización
    localStorage.setItem('resumenPedido', JSON.stringify(resumenGuardado));

    // Actualizar elementos en la página
    const entregaElement = document.getElementById('entrega');
    const totalElement = document.getElementById('total');
    
    if (entregaElement) entregaElement.textContent = `S/${costoEnvio.toFixed(2)}`;
    if (totalElement) totalElement.textContent = `S/${resumenGuardado.total.toFixed(2)}`;
}

function cargarProductosSeleccionados() {
    // Debug para verificar que la función se está ejecutando
    console.log('Cargando productos seleccionados...');
    
    // Obtener el carrito del localStorage
    const carrito = JSON.parse(localStorage.getItem('cart')) || [];
    console.log('Contenido del carrito:', carrito); // Debug para ver el contenido
    
    // Obtener la referencia a la tabla
    const tbody = document.getElementById('productos-tabla');
    if (!tbody) {
        console.error('No se encontró el elemento productos-tabla');
        return;
    }
    
    // Limpiar la tabla antes de agregar nuevos datos
    tbody.innerHTML = '';
    
    // Verificar si hay productos
    if (carrito.length === 0) {
        tbody.innerHTML = `
            <tr>
                <td colspan="6" class="text-center">No hay productos en el carrito</td>
            </tr>
        `;
        return;
    }
    
    // Agregar cada producto a la tabla
    carrito.forEach(producto => {
        const subtotal = producto.price * producto.quantity;
        const row = `
            <tr>
                <td>${producto.name || 'Sin nombre'}</td>
                <td>${producto.tipo_producto || 'No especificado'}</td>
                <td>${producto.talla || 'No especificada'}</td>
                <td>${producto.quantity || 0}</td>
                <td>S/${(producto.price || 0).toFixed(2)}</td>
                <td>S/${subtotal.toFixed(2)}</td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

// Asegurarse de que la función se ejecute cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM Cargado'); // Debug
    
    // Verificar el contenido del carrito al cargar
    const carrito = JSON.parse(localStorage.getItem('cart')) || [];
    console.log('Contenido inicial del carrito:', carrito);
    
    cargarProductosSeleccionados();
    cargarResumenPedido();
    
    const shippingMethod = document.getElementById('shippingMethod');
    if (shippingMethod) {
        shippingMethod.addEventListener('change', actualizarCostoEnvio);
    }
});
