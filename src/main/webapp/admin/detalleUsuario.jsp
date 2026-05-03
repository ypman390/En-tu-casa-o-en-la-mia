<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand"> Admin Panel</span>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="btn btn-outline-light btn-sm me-2">← Volver</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 600px;">
    <h2 class="mb-4"> Detalle del usuario</h2>

    <div class="card shadow p-4">
        <table class="table">
            <tr>
                <th>ID</th>
                <td>${usuario.id}</td>
            </tr>
            <tr>
                <th>Nombre</th>
                <td>${usuario.nombre}</td>
            </tr>
            <tr>
                <th>Username</th>
                <td>${usuario.username}</td>
            </tr>
            <tr>
                <th>Email</th>
                <td>${usuario.email}</td>
            </tr>
            <tr>
                <th>DNI</th>
                <td>${usuario.dni}</td>
            </tr>
            <tr>
                <th>Rol</th>
                <td>
                    <span class="badge ${usuario.rol == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                        ${usuario.rol}
                    </span>
                </td>
            </tr>
            <tr>
                <th>Activo</th>
                <td>
                    <c:choose>
                        <c:when test="${usuario.activo}">
                            <span class="badge bg-success">Activo</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge bg-secondary">Inactivo</span>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>Fecha registro</th>
                <td>${usuario.fechaRegistro}</td>
            </tr>
            <tr>
                <th>Puntos</th>
                <td>${usuario.puntos}</td>
            </tr>
            <tr>
                <th>Crédito</th>
                <td>${usuario.credito} €</td>
            </tr>
        </table>

        <div class="d-flex gap-2 mt-2">
            <a href="${pageContext.request.contextPath}/admin/editarUsuario?id=${usuario.id}"
               class="btn btn-warning"> Editar</a>
            <a href="${pageContext.request.contextPath}/admin/desactivarUsuario?id=${usuario.id}"
               class="btn btn-danger"
               onclick="return confirm('¿Desactivar este usuario?')"> Desactivar</a>
        </div>
    </div>
</div>

</body>
</html>