<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute("usuario") == null) {
        response.sendRedirect(request.getContextPath() + "/views/Intranet/Intranet.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.5/font/bootstrap-icons.min.css">
    <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="../../css/estilos.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script src="../../js/app.js" defer></script>
</head>
<body>

   <!--HEADER-->
   <div id="sidebar-placeholder"></div>

   <script>
       // Cargar el header
       fetch("../intranex/fragments1/sidebar.html")
           .then(response => response.text())
           .then(data => document.getElementById("sidebar-placeholder").innerHTML = data);
   </script>

    <!-- Contenido principal -->
    <div class="contentD">
        <h1>Dashboard de Ventas e Inventario</h1> 

        <!-- Ejemplo de secciones adicionales -->
        <div class="container-fluid">
            <div class="row">
                <!-- Sección 1 -->
                <div class="col-md-6">
                <!-- Gráfico de Ventas -->
                <section>
                    <h2>Ventas por Categoría y genero</h2>
                    <canvas id="salesPieChart" width="400" height="200"></canvas>
                </section>
                
                </div>
                <!-- Sección 2 -->
                <div class="col-md-6">
                    <section>
                        <h2>Ventas (Últimos 6 Meses)</h2>
                        <canvas id="salesChart" width="400" height="200"></canvas>
                    </section>
                    <section>
                        <h2>Inventario de Prendas</h2>
                        <canvas id="inventoryChart" width="400" height="200"></canvas>
                    </section>
                
                </div>
                 <div class="col-md-12">
                    
                 </div>

            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
     <script src="../MenuDashboard-main/js/app.js"></script>

     <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>
