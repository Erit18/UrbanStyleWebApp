    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="../../css/intranetstyles.css">
        <title>Intranet</title>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        
    </head>

  <body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand d-flex align-items-center" href="../../index.jsp">
                <img src="imagenes/Logo.jpeg" alt="Logo" class="logo" >
            </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown mx-4">
                    <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownCatalogo" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        CATÁLOGO
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdownCatalogo">
                        <li class="nav-item dropdown mx-4">
                            <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownHombre" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Hombre
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownHombre">
                                <li><a class="dropdown-item" href="../catalogo/CatPolera.html">Poleras</a></li>
                                <li><a class="dropdown-item" href="../catalogo/CatPolos.html">Polos</a></li>
                                <li><a class="dropdown-item" href="../catalogo/CatPantalones.html">Pantalones</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown mx-4">
                            <a class="nav-link active dropdown-toggle" href="#" id="navbarDropdownMujer" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Mujer
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdownMujer">
                                <li><a class="dropdown-item" href="../catalogo/CatPolerasM.html">Poleras</a></li>
                                <li><a class="dropdown-item" href="../catalogo/CatPolosM.html">Polos</a></li>
                                <li><a class="dropdown-item" href="../catalogo/CatPantalonesM.html">Pantalones</a></li>
                            </ul>
                        </li>
                        <li class="nav-item mx-4">
                            <a class="dropdown-item" href="../catalogo/CatAccesorios.html">Accesorios</a>
                        </li>
                    </ul>
                </li>
                
            <li class="nav-item mx-4" >
                <a class="nav-link active" aria-current="page" href="nosotros/Nosotros.html">NOSOTROS</a>
            </li>
            
            <li class="nav-item mx-4">
                <a class="nav-link active" href="../SCliente/Servicio-Cliente.html">LIBRO DE RECLAMACIONES</a>
            </li>
            </ul>
            <form class="d-flex" style="width: 35%;">
                <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Search" style="height: 45px; font-size: 1.1rem;">
                <button class="btn d-flex align-items-center justify-content-center" type="submit" style="height: 45px; width: 45px;">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                    </svg>
                </button>
            </form>
            
            
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item ">
                    <a class="nav-link active" href="Intranet.jsp">Log In</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="../catalogo/CarritoCompras.html">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
                            <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                          </svg>
                    </a>
                </li>
            </ul>
    </div>
        </div>
    </nav>
    <div class="envio-bar d-flex justify-content-center align-items-center">
        <span class="mx-2">
          <img src="imagenes/peru.png" alt="Perú" width="24">
        </span>
        <span class="envio-text">🛩️ENVÍOS GRATIS A TODO EL PERÚ🛩️</span>
        <span class="mx-2">
          <img src="imagenes/peru.png" alt="Avión" width="24">
        </span>
      </div>
      <main>
         <div class="container" id="container">
            <div class="form-container sign-up">
                <form action="${pageContext.request.contextPath}/registro" method="post" id="registroForm" onsubmit="console.log('Formulario enviado')">
                    <h1>Registrarse</h1>
                    <div class="social-icons">
                        <a href="#" class="icon"><i class="fa-brands fa-google-plus-g"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-facebook-f"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-github"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-linkedin-in"></i></a>
                    </div>
                    <input type="text" name="nombre" placeholder="Nombre" required>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="password" placeholder="Password" required>
                    <button type="submit" onclick="console.log('Botón clickeado')">Registrar</button>
                </form>
            </div>
            <div class="form-container sign-in">
                <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
                    <h1>Iniciar Sesión</h1>
                    <div class="social-icons">
                        <a href="#" class="icon"><i class="fa-brands fa-google-plus-g"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-facebook-f"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-github"></i></a>
                        <a href="#" class="icon"><i class="fa-brands fa-linkedin-in"></i></a>
                    </div>
                    <input type="email" name="email" placeholder="Email" required>
                    <input type="password" name="password" placeholder="Password" required>
                    <% 
                    String error = (String) session.getAttribute("error");
                    String mensaje = (String) session.getAttribute("mensaje");
                    session.removeAttribute("error");
                    session.removeAttribute("mensaje");
                    
                    if (error != null) { 
                    %>
                        <div class="alert alert-danger" id="errorAlert">
                            <%= error %>
                        </div>
                    <% } %>
                    <% if (mensaje != null) { %>
                        <div class="alert alert-success" id="mensajeAlert">
                            <%= mensaje %>
                        </div>
                    <% } %>
                    <button type="submit">Iniciar Sesión</button>
                </form>
            </div>
            <div class="toggle-container">
                <div class="toggle">
                    <div class="toggle-panel toggle-left">
                        <h1>Bienvenido!</h1>
                        <p>Por favor, Iniciar Sesión</p>
                        <button class="hidden" id="login">Iniciar</button>
                    </div>
                    <div class="toggle-panel toggle-right">
                        <h1>Hola y Bienvenido!</h1>
                        <p>Por favor, regístrese con su información personal para disfrutar de todas las características disponibles en nuestro sitio </p>
                        <button class="hidden" id="register">Únete</button>
                    </div>
                </div>
            </div>
          </div>
    </main>
 <footer class="footer-distributed">
        <div class="footer-left">
            <img src="imagenes/Logo.jpeg" alt="Logo" class="logo">
            <h3>Urban<span>Style</span></h3>
            <p class="footer-company-name">Copyright © 2024 <strong>UrbanStyle</strong> Reservados todos los derechos</p>
        </div>
        <div class="footer-center">
            <div>
                <i class="fa fa-map-marker"></i>
                <p><span>Ubicación</span> Los Olivos, UTP 2024</p>
            </div>
            <div>
                <i class="fa fa-phone"></i>
                <p>+51 957 789 067</p>
            </div>
            <div>
                <i class="fa fa-envelope"></i>
                <p><a href="mailto:sagar00001.co@gmail.com">UrbanStyle@empresa.com</a></p>
            </div>
        </div>
        <div class="footer-right">
            <p class="footer-company-about">
                <span>Sobre la empresa</span>
                <strong>UrbanStyle</strong> Descubre las ropas en tendencia de este año 2024
            </p>
            <div class="footer-icons">
                <a href="#"><i class="fa fa-facebook"></i></a>
                <a href="#"><i class="fa fa-instagram"></i></a>
                <a href="#"><i class="fa fa-linkedin"></i></a>
                <a href="#"><i class="fa fa-twitter"></i></a>
                <a href="#"><i class="fa fa-youtube"></i></a>
            </div>
        </div>
    </footer>
        

        <script src="../../js/script.js"></script>
        <script>
            // Debug para verificar que los elementos existen
            console.log('Container:', document.getElementById('container'));
            console.log('Register button:', document.getElementById('register'));
            console.log('Login button:', document.getElementById('login'));
            console.log('Registro form:', document.getElementById('registroForm'));
        </script>
        <script>
            // Función para ocultar alertas automáticamente
            function ocultarAlertas() {
                // Ocultar mensaje de éxito después de 3 segundos
                const mensajeAlert = document.getElementById('mensajeAlert');
                if (mensajeAlert) {
                    setTimeout(() => {
                        mensajeAlert.style.transition = 'opacity 0.5s ease';
                        mensajeAlert.style.opacity = '0';
                        setTimeout(() => {
                            mensajeAlert.remove();
                        }, 500);
                    }, 3000); // 3000 milisegundos = 3 segundos
                }

                // Ocultar mensaje de error después de 5 segundos
                const errorAlert = document.getElementById('errorAlert');
                if (errorAlert) {
                    setTimeout(() => {
                        errorAlert.style.transition = 'opacity 0.5s ease';
                        errorAlert.style.opacity = '0';
                        setTimeout(() => {
                            errorAlert.remove();
                        }, 500);
                    }, 5000); // 5000 milisegundos = 5 segundos
                }
            }

            // Ejecutar cuando el DOM esté cargado
            document.addEventListener('DOMContentLoaded', ocultarAlertas);
        </script>
        <style>
            .alert {
                padding: 10px;
                margin: 10px 0;
                border-radius: 4px;
                transition: opacity 0.5s ease;
            }
            
            .alert-success {
                background-color: #d4edda;
                border-color: #c3e6cb;
                color: #155724;
            }
            
            .alert-danger {
                background-color: #f8d7da;
                border-color: #f5c6cb;
                color: #721c24;
            }
        </style>
    </body>
  
    

    </html>


