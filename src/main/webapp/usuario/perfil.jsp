<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mi Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand">🏠 En tu casa o en la mía</span>
    <div>
        <c:choose>
            <c:when test="${sessionScope.rol == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="btn btn-outline-warning btn-sm me-2">Dashboard</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/espacios"
                   class="btn btn-outline-light btn-sm me-2">Ver espacios</a>
                <a href="${pageContext.request.contextPath}/usuario/misEspacios"
                   class="btn btn-outline-light btn-sm me-2">Mis espacios</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 600px;">
    <h2 class="mb-4">👤 Mi perfil</h2>

    <!-- Mensajes -->
    <c:if test="${not empty exito}">
        <div class="alert alert-success">✅ ${exito}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">❌ ${error}</div>
    </c:if>

    <div class="card shadow p-4">

        <!-- Info no editable -->
        <div class="mb-4">
            <p class="mb-1"><strong>Username:</strong> ${usuario.username}</p>
            <p class="mb-1"><strong>Rol:</strong>
                <span class="badge ${usuario.rol == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                    ${usuario.rol}
                </span>
            </p>
            <p class="mb-1"><strong>Fecha de registro:</strong> ${usuario.fechaRegistro}</p>
            <p class="mb-1"><strong>Puntos:</strong> ${usuario.puntos}</p>
            <p class="mb-0"><strong>Crédito:</strong> ${usuario.credito} €</p>
        </div>

        <hr>

        <!-- Formulario editable -->
        <form action="${pageContext.request.contextPath}/usuario/perfil" method="post">

            <div class="mb-3">
                <label class="form-label">Nombre completo *</label>
                <input type="text" name="nombre" class="form-control"
                       value="${usuario.nombre}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email *</label>
                <input type="email" name="email" class="form-control"
                       value="${usuario.email}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">DNI *</label>
                <input type="text" name="dni" class="form-control"
                       value="${usuario.dni}" required>
            </div>

            <hr>
            <h6 class="mb-3">🔒 Cambiar contraseña</h6>

            <div class="mb-3">
                <label class="form-label">Contraseña actual *</label>
                <input type="password" name="passwordActual" class="form-control"
                       placeholder="Introduce tu contraseña actual" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Nueva contraseña (opcional)</label>
                <input type="password" name="passwordNueva" class="form-control"
                       placeholder="Déjalo vacío si no quieres cambiarla">
            </div>

            <button type="submit" class="btn btn-primary w-100">
                Guardar cambios
            </button>

        </form>
    </div>
</div>

</body>
</html>