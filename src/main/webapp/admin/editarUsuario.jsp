<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 Admin Panel</span>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="btn btn-outline-light btn-sm me-2">Dashboard</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 600px;">
    <h2 class="mb-4">✏️ Editar usuario</h2>

    <div class="card shadow p-4">
        <form action="${pageContext.request.contextPath}/admin/editarUsuario"
              method="post">

            <input type="hidden" name="id" value="${usuario.id}">

            <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control"
                       value="${usuario.nombre}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control"
                       value="${usuario.username}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control"
                       value="${usuario.email}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">DNI</label>
                <input type="text" name="dni" class="form-control"
                       value="${usuario.dni}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Rol</label>
                <select name="rol" class="form-select">
                    <option value="USER" ${usuario.rol == 'USER' ? 'selected' : ''}>USER</option>
                    <option value="ADMIN" ${usuario.rol == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Puntos</label>
                <input type="number" name="puntos" class="form-control"
                       value="${usuario.puntos}" min="0">
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" name="activo" class="form-check-input"
                       id="activo" ${usuario.activo ? 'checked' : ''}>
                <label class="form-check-label" for="activo">Activo</label>
            </div>

            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-warning">Guardar cambios</button>
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="btn btn-secondary">Cancelar</a>
            </div>

        </form>
    </div>
</div>

</body>
</html>