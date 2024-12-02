<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
    <div class="row">
        <div class="col-md-6">
            <h5>Datos del Cliente</h5>
            <p><strong>Nombre:</strong> ${reclamo.nombre} ${reclamo.apellido}</p>
            <p><strong>Documento:</strong> ${reclamo.tipoDocumento} - ${reclamo.numeroDocumento}</p>
            <p><strong>Teléfono:</strong> ${reclamo.telefono}</p>
            <p><strong>Email:</strong> ${reclamo.correoElectronico}</p>
        </div>
        <div class="col-md-6">
            <h5>Ubicación</h5>
            <p><strong>Departamento:</strong> ${reclamo.departamento}</p>
            <p><strong>Provincia:</strong> ${reclamo.provincia}</p>
            <p><strong>Distrito:</strong> ${reclamo.distrito}</p>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-12">
            <h5>Detalles del Reclamo</h5>
            <p><strong>Tipo:</strong> ${reclamo.tipoReclamo}</p>
            <p><strong>Fecha de Compra:</strong> <fmt:formatDate value="${reclamo.fechaCompra}" pattern="dd/MM/yyyy"/></p>
            <p><strong>N° Boleta/Factura:</strong> ${reclamo.numeroBoleta}</p>
            <p><strong>Detalle:</strong></p>
            <div class="border p-3 bg-light">
                ${reclamo.detalleReclamo}
            </div>
        </div>
    </div>
</div> 