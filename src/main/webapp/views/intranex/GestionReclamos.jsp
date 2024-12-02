<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Reclamos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <jsp:include page="menu.jsp" />
            
            <div class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <h2 class="mt-4">Gestión de Reclamos</h2>
                
                <div class="table-responsive mt-4">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Fecha</th>
                                <th>Cliente</th>
                                <th>Tipo</th>
                                <th>Estado</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${reclamos}" var="reclamo">
                                <tr>
                                    <td>${reclamo.id}</td>
                                    <td>${reclamo.fechaRegistro}</td>
                                    <td>${reclamo.nombre} ${reclamo.apellido}</td>
                                    <td>${reclamo.tipoReclamo}</td>
                                    <td>${reclamo.estado}</td>
                                    <td>
                                        <button class="btn btn-info btn-sm" onclick="verDetalle(${reclamo.id})">
                                            Ver Detalle
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>