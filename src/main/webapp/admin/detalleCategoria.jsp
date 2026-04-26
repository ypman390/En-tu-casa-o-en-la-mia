<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Categoría</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 Admin Panel</span>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="btn btn-outline-light btn-sm me-2">← Volver</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 600px;">
    <h2 class="mb-4">📂 Detalle de categoría</h2>

    <div class="card shadow p-4">
        <table class="table">
            <tr>
                <th>ID</th>
                <td>${categoria.id}</td>
            </tr>
            <tr>
                <th>Nombre</th>
                <td>${categoria.nombre}</td>
            </tr>
            <tr>
                <th>Descripción</th>
                <td>${categoria.descripcion}</td>
            </tr>
            <tr>
                <th>Activa</th>
                <td>
                    <c:choose>
                        <c:when test="${categoria.activa}">
                            <span class="badge bg-success">Sí</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-danger">No</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>Fecha creación</th>
                <td>${categoria.fechaCreacion}</td>
            </tr>
            <tr>
                <th>Prioridad</th>
                <td>${categoria.prioridad}</td>
            </tr>
            <tr>
                <th>Tarifa base</th>
                <td>${categoria.tarifaBase} €</td>
            </tr>
        </table>

        <div class="d-flex gap-2 mt-2">
            <a href="${pageContext.request.contextPath}/admin/editarCategoria?id=${categoria.id}"
               class="btn btn-warning">✏️ Editar</a>
            <a href="${pageContext.request.contextPath}/admin/eliminarCategoria?id=${categoria.id}"
               class="btn btn-danger"
               onclick="return confirm('¿Eliminar esta categoría?')">🗑️ Eliminar</a>
        </div>
    </div>
</div>

</body>
</html>