<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Espacios disponibles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 En tu casa o en la mía</span>
    <div>
        <c:choose>
            <c:when test="${sessionScope.rol == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="btn btn-outline-warning btn-sm me-2">Admin Panel</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/usuario/misEspacios"
                   class="btn btn-outline-light btn-sm me-2">Mis espacios</a>
                <a href="${pageContext.request.contextPath}/usuario/misSolicitudes"
                   class="btn btn-outline-light btn-sm me-2">Mis solicitudes</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="mb-3">🔍 Espacios disponibles</h2>

    <!-- Filtros -->
    <div class="card p-3 mb-4 shadow-sm">
        <form action="${pageContext.request.contextPath}/espacios" method="get"
              class="row g-2 align-items-end">
            <div class="col-md-4">
                <label class="form-label">Precio máximo (€)</label>
                <input type="number" name="precioMax" class="form-control"
                       min="0" step="0.01" value="${precioMax}"
                       placeholder="Ej: 20">
            </div>
            <div class="col-md-4">
                <label class="form-label">Categoría</label>
                <select name="categoriaId" class="form-select">
                    <option value="">Todas</option>
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.id}"
                            ${categoriaSeleccionada == cat.id ? 'selected' : ''}>
                                ${cat.nombre}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">Filtrar</button>
            </div>
            <div class="col-md-2">
                <a href="${pageContext.request.contextPath}/espacios"
                   class="btn btn-outline-secondary w-100">Limpiar</a>
            </div>
        </form>
    </div>

    <!-- Listado -->
    <c:choose>
        <c:when test="${empty espacios}">
            <div class="alert alert-info">No hay espacios disponibles con esos filtros.</div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="e" items="${espacios}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">

                            <!-- Imagen -->
                            <c:choose>
                                <c:when test="${not empty e.imagen}">
                                    <img src="${pageContext.request.contextPath}/${e.imagen}"
                                         class="card-img-top"
                                         style="height:180px; object-fit:cover;"
                                         alt="${e.titulo}">
                                </c:when>
                                <c:otherwise>
                                    <div class="bg-secondary text-white d-flex align-items-center
                                                justify-content-center"
                                         style="height:180px; font-size:3rem;">🏠</div>
                                </c:otherwise>
                            </c:choose>

                            <div class="card-body">
                                <h5 class="card-title">${e.titulo}</h5>
                                <p class="card-text text-muted small">${e.descripcion}</p>
                                <p class="fw-bold text-success">${e.precio} €/noche</p>
                                <p class="small">👥 Capacidad: ${e.capacidad} persona(s)</p>
                                <p class="small">⭐ Valoración: ${e.valoracion}</p>
                            </div>

                            <div class="card-footer">
                                <a href="${pageContext.request.contextPath}/espacios/detalle?id=${e.id}"
                                   class="btn btn-primary btn-sm w-100">Ver detalle</a>
                            </div>

                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>