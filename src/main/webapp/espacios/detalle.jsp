<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${espacio.titulo}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand"> En tu casa o en la mía</span>
    <div>
        <a href="${pageContext.request.contextPath}/espacios"
           class="btn btn-outline-light btn-sm me-2">← Volver</a>
        <c:if test="${sessionScope.rol == 'ADMIN'}">
            <a href="${pageContext.request.contextPath}/admin/dashboard"
               class="btn btn-outline-warning btn-sm me-2">Admin Panel</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 800px;">

    <!-- Mensaje éxito -->
    <c:if test="${param.exito == 'solicitud'}">
        <div class="alert alert-success">Solicitud enviada correctamente.</div>
    </c:if>

    <div class="card shadow">

        <!-- Imagen -->
        <c:choose>
            <c:when test="${not empty espacio.imagen}">
                <img src="${pageContext.request.contextPath}/${espacio.imagen}"
                     class="card-img-top"
                     style="height: 350px; object-fit: cover;"
                     alt="${espacio.titulo}">
            </c:when>
            <c:otherwise>
                <div class="bg-secondary text-white d-flex align-items-center
                            justify-content-center"
                     style="height: 350px; font-size: 5rem;">🏠</div>
            </c:otherwise>
        </c:choose>

        <div class="card-body p-4">

            <div class="d-flex justify-content-between align-items-start">
                <h2>${espacio.titulo}</h2>
                <span class="badge ${espacio.disponible ? 'bg-success' : 'bg-danger'} fs-6">
                    ${espacio.disponible ? 'Disponible' : 'No disponible'}
                </span>
            </div>

            <p class="text-muted">${espacio.descripcion}</p>

            <hr>

            <div class="row g-3">
                <div class="col-md-3 text-center">
                    <h4 class="text-success">${espacio.precio} €</h4>
                    <small class="text-muted">por noche</small>
                </div>
                <div class="col-md-3 text-center">
                    <h4> ${espacio.capacidad}</h4>
                    <small class="text-muted">personas</small>
                </div>
                <div class="col-md-3 text-center">
                    <h4> ${espacio.valoracion}</h4>
                    <small class="text-muted">valoración</small>
                </div>
                <div class="col-md-3 text-center">
                    <h4> ${categoria.nombre}</h4>
                    <small class="text-muted">categoría</small>
                </div>
            </div>

            <hr>

            <!-- Botones según rol -->
            <c:choose>
                <c:when test="${sessionScope.rol == 'ADMIN'}">
                    <div class="d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/editarEspacio?id=${espacio.id}"
                           class="btn btn-warning"> Editar</a>
                        <a href="${pageContext.request.contextPath}/admin/eliminarEspacio?id=${espacio.id}"
                           class="btn btn-danger"
                           onclick="return confirm('¿Estás seguro de que quieres eliminar este espacio?')">
                             Eliminar</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <%--  Solo muestra el formulario si NO es el dueño del espacio --%>
                    <c:choose>
                        <c:when test="${espacio.usuarioId == sessionScope.usuarioLogueado.id}">
                            <div class="alert alert-info mt-3">
                                 Este es tu espacio. Puedes
                                <a href="${pageContext.request.contextPath}/editarEspacio?id=${espacio.id}">editarlo</a>
                                o
                                <a href="${pageContext.request.contextPath}/admin/eliminarEspacio?id=${espacio.id}"
                                   onclick="return confirm('¿Estás seguro de que quieres eliminar este espacio?')">eliminarlo</a>.
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${espacio.disponible}">
                                <h5 class="mt-3"> Solicitar este espacio</h5>
                                <form action="${pageContext.request.contextPath}/solicitudes/crear"
                                      method="post">
                                    <input type="hidden" name="espacioId" value="${espacio.id}">
                                    <input type="hidden" name="precio" value="${espacio.precio}">

                                    <div class="row g-2">
                                        <div class="col-md-4">
                                            <label class="form-label">Fecha inicio</label>
                                            <input type="date" name="fechaInicio"
                                                   class="form-control" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label class="form-label">Fecha fin</label>
                                            <input type="date" name="fechaFin"
                                                   class="form-control" required>
                                        </div>
                                        <div class="col-md-4">
                                            <label class="form-label">Nº personas</label>
                                            <input type="number" name="numeroPersonas"
                                                   class="form-control" min="1"
                                                   max="${espacio.capacidad}" value="1">
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label">Comentario (opcional)</label>
                                            <textarea name="comentario" class="form-control"
                                                      rows="2"></textarea>
                                        </div>
                                        <div class="col-12">
                                            <button type="submit" class="btn btn-primary">
                                                Enviar solicitud
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>

</body>
</html>