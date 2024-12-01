<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../css/catalogostyles.css">
    <title>Intranet</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    
</head>
<body>
    <!--HEADER-->
    <div id="header-placeholder"></div>

    <div class="envio-bar d-flex justify-content-center align-items-center">
        <span class="mx-2">
          <img src="../Intranet/imagenes/peru.png" alt="Perú" width="24">
        </span>
        <span class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</span>
        <span class="mx-2">
          <img src="../Intranet/imagenes/peru.png" alt="Avión" width="24">
        </span>
      </div>

    <style>
        .card {
            margin-bottom: 1.5rem;
        }
        .payment-method-icon {
            font-size: 24px;
            margin-right: 10px;
        }
        .payment-card-img {
            font-size: 2rem;
            margin-right: 10px;
            color: #666;
        }
        .qr-img {
            width: 150px;
            margin: 10px 0;
        }
        .d-none {
            display: none;
        }
    </style>
</head>
<body>
    <div class="container my-5">
        <h2 class="mb-4">Pagar</h2>
        
        <!-- Nueva sección de productos seleccionados -->
        <div class="card p-4 mb-4">
            <h4 class="mb-3">PRODUCTOS SELECCIONADOS</h4>
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Tipo</th>
                            <th>Talla</th>
                            <th>Cantidad</th>
                            <th>Precio Unit.</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody id="productos-tabla">
                        <!-- Los productos se cargarán dinámicamente aquí -->
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <!-- Información de envío -->
            <div class="col-md-6">
                <div class="card p-4">
                    <h4 class="mb-3">Información de Envío</h4>
                    <form>
                        <!-- Opciones de Recogo o Delivery -->
                        <div class="mb-3">
                            <label class="form-label">Método de Envío</label>
                            <select class="form-select" id="shippingMethod" required>
                                <option value="" disabled selected>Selecciona un método de envío</option>
                                <option value="store-pickup">Recogo en tienda</option>
                                <option value="delivery">Delivery</option>
                            </select>
                        </div>

                        <!-- Campos de dirección y envío, solo para Delivery -->
                        <div id="deliveryFields" class="d-none">
                            <div class="mb-3">
                                <label for="country" class="form-label">País</label>
                                <select class="form-select" id="country" required>
                                    <option value="peru" selected>Perú</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="department" class="form-label">Departamento</label>
                                <select class="form-select" id="department" required>
                                    <option value="" disabled selected>Selecciona tu departamento</option>
                                    <option value="lima">Lima</option>
                                    <option value="arequipa">Arequipa</option>
                                    <option value="cusco">Cusco</option>
                                    <!-- Añade más opciones según sea necesario -->
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="province" class="form-label">Provincia</label>
                                <input type="text" class="form-control" id="province" required>
                            </div>
                            <div class="mb-3">
                                <label for="district" class="form-label">Distrito</label>
                                <input type="text" class="form-control" id="district" required>
                            </div>
                            <div class="mb-3">
                                <label for="address" class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="address" required>
                            </div>
                        </div>

                        <!-- Campos adicionales para Recogo en Tienda -->
                        <div id="storePickupFields" class="d-none">
                            <div class="mb-3">
                                <label for="storeBranch" class="form-label">Escoge la Sucursal</label>
                                <select class="form-select" id="storeBranch" required>
                                    <option value="" disabled selected>Selecciona la sucursal</option>
                                    <option value="sucursal1">Sucursal 1</option>
                                    <option value="sucursal2">Sucursal 2</option>
                                    <option value="sucursal3">Sucursal 3</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="pickupDate" class="form-label">Escoge el Día y Mes</label>
                                <input type="date" class="form-control" id="pickupDate" required>
                            </div>
                            <div class="mb-3">
                                <label for="pickupTime" class="form-label">Escoge la Hora (entre 8:00 am y 8:00 pm)</label>
                                <input type="time" class="form-control" id="pickupTime" min="08:00" max="20:00" required>
                            </div>
                        </div>
                    </form>
                </div>
            </div>




            <!-- Detalles de Pago -->
            <div class="col-md-6">
                <div class="card p-4">
                    <h4 class="mb-3">Detalles de Pago</h4>
                    <form>
                        <!-- Opciones de bota/factura -->
                        <div class="mb-3">
                            <label for="invoiceName" class="form-label">Nombres y Apellidos</label>
                            <input type="text" class="form-control" id="invoiceName" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Desea boleta o factura</label>
                            <select class="form-select" id="shippingOpcion" required>
                                <option value="" disabled selected>Escoge una opción</option>
                                <option value="ruc">RUC</option>
                                <option value="boleta">Boleta</option>
                            </select>
                        </div>

                        <!-- Boleta -->
                        <div id="boletaFields" class="d-none">
                            <div class="mb-3">
                                <label for="dni" class="form-label">Número de DNI</label>
                                <input type="text" class="form-control" id="dni" required>
                            </div>
                        </div>

                        <!-- Factura -->
                        <div id="rucFields" class="d-none">
                            <div class="mb-3">
                                <label for="ruc" class="form-label">RUC</label>
                                <input type="text" class="form-control" id="ruc" required>
                            </div>
                            <div class="mb-3">
                                <label for="direccion" class="form-label">Dirección</label>
                                <input type="text" class="form-control" id="direccion" required>
                            </div>
                            <div class="mb-3">
                                <label for="razonsocial" class="form-label">Razón Social</label>
                                <input type="text" class="form-control" id="razonsocial" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="email" required>
                            </div>
                        </div>
                    </form>
                </div>
            </div>





            
            <!-- Información de pago -->
            <div class="col-md-12">
                <div class="card p-4">
                    <h4 class="mb-3">Información de Pago</h4>
                    <form>
                        <div class="mb-3">
                            <label for="paymentMethod" class="form-label">Método de Pago</label>
                            <select class="form-select" id="paymentMethod" required>
                                <option value="" disabled selected>Selecciona un método de pago</option>
                                <option value="credit-card">Tarjeta de Crédito/Débito</option>
                                <option value="transferencia-deposito">Transferencia/Depósito</option>
                                <option value="yape-plin">Yape/Plin</option>
                            </select>
                        </div>
                        
                        <!-- Detalles de Tarjeta -->
                        <div id="creditCardDetails" class="d-none">
                            <div class="mb-3">
                                <label class="form-label">Tarjetas Aceptadas</label>
                                <div class="payment-icons">
                                    <i class="fab fa-cc-visa"></i>
                                    <i class="fab fa-cc-mastercard"></i>
                                    <i class="fab fa-cc-paypal"></i>
                                    <i class="fab fa-cc-discover"></i>
                                </div>
                            </div>
                            <div class="mb-3">
                                <label for="cardholderName" class="form-label">Nombre del Titular</label>
                                <input type="text" class="form-control" id="cardholderName" required>
                            </div>
                            <div class="mb-3">
                                <label for="cardNumber" class="form-label">Número de Tarjeta</label>
                                <input type="text" class="form-control" id="cardNumber" placeholder="0000 0000 0000 0000" required>
                            </div>
                            <div class="mb-3">
                                <label for="expiryDate" class="form-label">Fecha de Expiración</label>
                                <input type="text" class="form-control" id="expiryDate" placeholder="MM/AA" required>
                            </div>
                            <div class="mb-3">
                                <label for="cvv" class="form-label">CVV</label>
                                <input type="text" class="form-control" id="cvv" placeholder="123" required>
                            </div>
                        </div>

                        <!-- Detalles de Yape/Plin -->
                        <div id="yapePlinDetails" class="d-none">
                            <label class="form-label">Paga escaneando el QR:</label>
                            <div>
                                <img src="/src/main/resources/static/img/qr/yape.png" alt="QR Yape" class="qr-img">
                                <img src="/src/main/resources/static/img/qr/plin.png" alt="QR Plin" class="qr-img">
                            </div>
                        </div>

                        <!-- Detalles de Transferencia/Depósito -->
                        <div id="transferenciaDepositoDetails" class="d-none">
                            <div class="mb-3">
                                <label for="paymentType" class="form-label">Tipo de Pago</label>
                                <select class="form-select" id="paymentType" required>
                                    <option value="" disabled selected>Selecciona una opción</option>
                                    <option value="transferencia">Transferencia Bancaria</option>
                                    <option value="deposito">Depósito en Efectivo</option>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Código de Pago Efectivo</label>
                                <input type="text" class="form-control" id="codigoPagoEfectivo" value="COD12345" readonly>
                                <small class="form-text text-muted">Utiliza este código para depositar en un agente autorizado.</small>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

                 <!-- Resumen del pedido -->
                 <div class="card p-4 mt-4">
                    <h4 class="mb-3">RESUMEN DEL PEDIDO</h4>
                    <div class="resumen-detalles">
                        <div class="d-flex justify-content-between">
                            <p>SUBTOTAL:</p>
                            <p id="subtotal">S/0.00</p>
                        </div>
                        <div class="d-flex justify-content-between">
                            <p>DESCUENTO (20%):</p>
                            <p id="descuento">-S/0.00</p>
                        </div>
                        <div class="d-flex justify-content-between">
                            <p>ENTREGA:</p>
                            <p id="entrega">S/0.00</p>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between">
                            <h3>TOTAL:</h3>
                            <h3 id="total">S/0.00</h3>
                        </div>
                    </div>
                    <button onclick="confirmarPago()" class="btn btn-dark w-100 mt-3">Confirmar Pago</button>
                </div>
            </div>
        </div>
    
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    
        <script>

            document.getElementById('shippingMethod').addEventListener('change', function () {
            var shippingMethod = this.value;
            var deliveryFields = document.getElementById('deliveryFields');
            var storePickupFields = document.getElementById('storePickupFields');
            var shippingCostElement = document.getElementById('shippingCost');
            var totalCostElement = document.getElementById('totalCost');
            var subtotalElement = document.getElementById('subtotal');
            var subtotal = parseFloat(subtotalElement.textContent);
            var shippingCost = 0;

            if (shippingMethod === 'delivery') {
                deliveryFields.classList.remove('d-none');
                storePickupFields.classList.add('d-none');
                shippingCost = 10.00; // Costo de envío para delivery
            } else if (shippingMethod === 'store-pickup') {
                storePickupFields.classList.remove('d-none');
                deliveryFields.classList.add('d-none');
                shippingCost = 0.00; // Sin costo de envío para recogida en tienda
            } else {
                deliveryFields.classList.add('d-none');
                storePickupFields.classList.add('d-none');
            }

            shippingCostElement.textContent = shippingCost.toFixed(2);
            totalCostElement.textContent = (subtotal + shippingCost).toFixed(2);
        });
    
            document.getElementById('paymentMethod').addEventListener('change', function () {
                var paymentMethod = this.value;
                document.getElementById('creditCardDetails').classList.add('d-none');
                document.getElementById('yapePlinDetails').classList.add('d-none');
                document.getElementById('transferenciaDepositoDetails').classList.add('d-none');
    
                if (paymentMethod === 'credit-card') {
                    document.getElementById('creditCardDetails').classList.remove('d-none');
                } else if (paymentMethod === 'yape-plin') {
                    document.getElementById('yapePlinDetails').classList.remove('d-none');
                } else if (paymentMethod === 'transferencia-deposito') {
                    document.getElementById('transferenciaDepositoDetails').classList.remove('d-none');
                }
            });
        </script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Variables para los campos
    var boletaFields = document.getElementById('boletaFields');
    var rucFields = document.getElementById('rucFields');
    var shippingOpcion = document.getElementById('shippingOpcion');

    shippingOpcion.addEventListener('change', function() {
        // Ocultar todos los campos
        boletaFields.classList.add('d-none');
        rucFields.classList.add('d-none');

        // Mostrar los campos según la opción seleccionada
        if (this.value === 'boleta') {
            boletaFields.classList.remove('d-none');
        } else if (this.value === 'ruc') {
            rucFields.classList.remove('d-none');
        }
    });
</script>

<!--FOOTER (pie de página)-->
<div id="footer-placeholder"></div>
<script>
    // Cargar el header
    fetch("../catalogo/fragments/header.html")
        .then(response => response.text())
        .then(data => document.getElementById("header-placeholder").innerHTML = data);

    // Cargar el footer
    fetch("../catalogo/fragments/footer.html")
        .then(response => response.text())
        .then(data => document.getElementById("footer-placeholder").innerHTML = data);
</script>

<script src="../../js/script.js"></script>
<script src="../../js/ScriptC.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        console.log('DOM Cargado');
        cargarProductosSeleccionados(); // Cargar los productos
        cargarResumenPedido();
        
        // Event listener para el método de envío
        const shippingMethod = document.getElementById('shippingMethod');
        if (shippingMethod) {
            shippingMethod.addEventListener('change', actualizarCostoEnvio);
        }
    });
</script>
</body>









