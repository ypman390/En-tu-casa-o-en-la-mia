<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Espacios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 En tu casa o en la mía</span>
    <div>
        <a href="${pageContext.request.contextPath}/espacios"
           class="btn btn-outline-light btn-sm me-2">Ver espacios</a>
        <a href="${pageContext.request.contextPath}/usuario/misSolicitudes"
           class="btn btn-outline-light btn-sm me-2">Mis solicitudes</a>
        <a href="${pageContext.request.contextPath}/usuario/perfil"
           class="btn btn-outline-light btn-sm me-2">Mi perfil</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4">

    <!-- Mensajes -->
    <c:choose>
        <c:when test="${exito == 'aceptada'}">
            <div class="alert alert-success">✅ Solicitud aceptada correctamente.</div>
        </c:when>
        <c:when test="${exito == 'rechazada'}">
            <div class="alert alert-warning">❌ Solicitud rechazada.</div>
        </c:when>
        <c:when test="${exito == 'editado'}">
            <div class="alert alert-success">✅ Espacio actualizado correctamente.</div>
        </c:when>
    </c:choose>

    <!-- ── MIS ESPACIOS PUBLICADOS ───────────────────────── -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2>🏠 Mis espacios publicados</h2>
        <a href="${pageContext.request.contextPath}/admin/crearEspacio"
           class="btn btn-success">➕ Publicar nuevo espacio</a>
    </div>

    <div class="card shadow mb-5">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Título</th>
                    <th>Precio</th>
                    <th>Disponible</th>
                    <th>Capacidad</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty misEspacios}">
                        <tr>
                            <td colspan="6" class="text-center text-muted py-3">
                                Todavía no has publicado ningún espacio.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="e" items="${misEspacios}">
                            <tr>
                                <td>${e.id}</td>
                                <td>${e.titulo}</td>
                                <td>${e.precio} €</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${e.disponible}">
                                            <span class="badge bg-success">Sí</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">No</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${e.capacidad}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/espacios/detalle?id=${e.id}"
                                       class="btn btn-info btn-sm">Ver</a>
                                    <a href="${pageContext.request.contextPath}/editarEspacio?id=${e.id}"
                                       class="btn btn-warning btn-sm">Editar</a>
                                    <a href="${pageContext.request.contextPath}/admin/eliminarEspacio?id=${e.id}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('¿Eliminar este espacio?')">Eliminar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ── SOLICITUDES RECIBIDAS ─────────────────────────── -->
    <h2 class="mb-3">📋 Solicitudes recibidas en mis espacios</h2>

    <div class="card shadow mb-4">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Espacio ID</th>
                    <th>Usuario ID</th>
                    <th>Fecha inicio</th>
                    <th>Fecha fin</th>
                    <th>Personas</th>
                    <th>Precio total</th>
                    <th>Comentario</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty solicitudesRecibidas}">
                        <tr>
                            <td colspan="10" class="text-center text-muted py-3">
                                No has recibido solicitudes todavía.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="s" items="${solicitudesRecibidas}">
                            <tr>
                                <td>${s.id}</td>
                                <td>${s.espacioId}</td>
                                <td>${s.usuarioId}</td>
                                <td>${s.fechaInicio}</td>
                                <td>${s.fechaFin}</td>
                                <td>${s.numeroPersonas}</td>
                                <td>${s.precioTotal} €</td>
                                <td>${s.comentario}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.aceptada}">
                                            <span class="badge bg-success">Aceptada</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Pendiente</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${!s.aceptada}">
                                        <a href="${pageContext.request.contextPath}/usuario/aceptarSolicitud?id=${s.id}"
                                           class="btn btn-success btn-sm">Aceptar</a>
                                        <a href="${pageContext.request.contextPath}/usuario/rechazarSolicitud?id=${s.id}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('¿Rechazar esta solicitud?')">Rechazar</a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>