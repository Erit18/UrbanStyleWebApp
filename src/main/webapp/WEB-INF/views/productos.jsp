<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Productos</title>
</head>
<body>
    <h1>Lista de Productos</h1>
    <ul>
        <c:forEach items="${productos}" var="producto">
            <li>${producto.nombre} - $${producto.precio}</li>
        </c:forEach>
    </ul>

    <h2>Agregar Nuevo Producto</h2>
    <form action="${pageContext.request.contextPath}/productos" method="post">
        Nombre: <input type="text" name="nombre" required><br>
        Precio: <input type="number" step="0.01" name="precio" required><br>
        <input type="submit" value="Agregar Producto">
    </form>
</body>
</html>
