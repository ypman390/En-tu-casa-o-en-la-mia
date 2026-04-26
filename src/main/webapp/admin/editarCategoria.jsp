<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Categoría</title>
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
    <h2 class="mb-4">✏️ Editar categoría</h2>

    <div class="card shadow p-4">
        <form action="${pageContext.request.contextPath}/admin/editarCategoria"
              method="post">

            <input type="hidden" name="id" value="${categoria.id}">

            <div class="mb-3">
                <label class="form-label">Nombre *</label>
                <input type="text" name="nombre" class="form-control"
                       value="${categoria.nombre}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcion" class="form-control"
                          rows="3">${categoria.descripcion}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Tarifa base (€)</label>
                    <input type="number" name="tarifaBase" class="form-control"
                           value="${categoria.tarifaBase}" min="0" step="0.01">
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Prioridad</label>
                    <input type="number" name="prioridad" class="form-control"
                           value="${categoria.prioridad}" min="1">
                </div>
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" name="activa" class="form-check-input"
                       id="activa" ${categoria.activa ? 'checked' : ''}>
                <label class="form-check-label" for="activa">Activa</label>
            </div>

            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-warning">Guardar cambios</button>
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="btn btn-secondary">Cancelar</a>
            </div>

        </form>
    </div>
</div>
<script>
    document.querySelector('form').addEventListener('submit', function(e) {
        if (!confirm('¿Estás seguro de que quieres guardar los cambios?')) {
            e.preventDefault();
        }
    });
</script>
</body>
</html>