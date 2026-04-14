<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Espacio</title>
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

<div class="container mt-4" style="max-width: 700px;">
    <h2 class="mb-4">✏️ Editar espacio</h2>

    <div class="card shadow p-4">
        <form action="${pageContext.request.contextPath}/admin/editarEspacio"
              method="post" enctype="multipart/form-data">

            <input type="hidden" name="id" value="${espacio.id}">

            <div class="mb-3">
                <label class="form-label">Título *</label>
                <input type="text" name="titulo" class="form-control"
                       value="${espacio.titulo}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcion" class="form-control"
                          rows="3">${espacio.descripcion}</textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Precio (€) *</label>
                    <input type="number" name="precio" class="form-control"
                           value="${espacio.precio}" min="0.01" step="0.01" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Capacidad</label>
                    <input type="number" name="capacidad" class="form-control"
                           value="${espacio.capacidad}" min="1">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Categoría</label>
                <select name="categoriaId" class="form-select">
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.id}"
                            ${cat.id == espacio.categoriaId ? 'selected' : ''}>
                                ${cat.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3 form-check">
                <input type="checkbox" name="disponible" class="form-check-input"
                       id="disponible" ${espacio.disponible ? 'checked' : ''}>
                <label class="form-check-label" for="disponible">Disponible</label>
            </div>

            <div class="mb-3">
                <label class="form-label">Nueva imagen (opcional)</label>
                <input type="file" name="imagen" class="form-control" accept="image/*">
                <c:if test="${not empty espacio.imagen}">
                    <div class="mt-2">
                        <small class="text-muted">Imagen actual:</small><br>
                        <img src="${pageContext.request.contextPath}/${espacio.imagen}"
                             style="height:80px; border-radius:4px;" alt="imagen actual">
                    </div>
                </c:if>
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