<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mis Solicitudes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 En tu casa o en la mía</span>
    <div>
        <a href="${pageContext.request.contextPath}/espacios"
           class="btn btn-outline-light btn-sm me-2">Ver espacios</a>
        <a href="${pageContext.request.contextPath}/usuario/misEspacios"
           class="btn btn-outline-light btn-sm me-2">Mis espacios</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4">
    <h2 class="mb-4">📋 Mis solicitudes enviadas</h2>

    <div class="card shadow">
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Espacio ID</th>
                    <th>Fecha inicio</th>
                    <th>Fecha fin</th>
                    <th>Personas</th>
                    <th>Precio total</th>
                    <th>Comentario</th>
                    <th>Estado</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${empty misSolicitudes}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-3">
                                Todavía no has enviado ninguna solicitud.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="s" items="${misSolicitudes}">
                            <tr>
                                <td>${s.id}</td>
                                <td>${s.espacioId}</td>
                                <td>${s.fechaInicio}</td>
                                <td>${s.fechaFin}</td>
                                <td>${s.numeroPersonas}</td>
                                <td>${s.precioTotal} €</td>
                                <td>${s.comentario}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.aceptada}">
                                            <span class="badge bg-success">Aceptada ✅</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-warning text-dark">Pendiente ⏳</span>
                                        </c:otherwise>
                                    </c:choose>
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