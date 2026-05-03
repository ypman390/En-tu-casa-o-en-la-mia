<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Crear Espacio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand"> En tu casa o en la mía</span>
    <div>
        <%-- Navbar dinámica según rol --%>
        <c:choose>
            <c:when test="${sessionScope.rol == 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard"
                   class="btn btn-outline-light btn-sm me-2">Dashboard</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/usuario/misEspacios"
                   class="btn btn-outline-light btn-sm me-2">Mis espacios</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4" style="max-width: 700px;">
    <h2 class="mb-4"> Crear nuevo espacio</h2>

    <!-- Error -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <div class="card shadow p-4">
        <form action="${pageContext.request.contextPath}/admin/crearEspacio"
              method="post" enctype="multipart/form-data">

            <div class="mb-3">
                <label class="form-label">Título *</label>
                <input type="text" name="titulo" class="form-control"
                       placeholder="Ej: Sofá cómodo en el centro" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcion" class="form-control" rows="3"
                          placeholder="Describe el espacio..."></textarea>
            </div>

            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Precio por noche (€) *</label>
                    <input type="number" name="precio" class="form-control"
                           min="0.01" step="0.01" placeholder="Ej: 15.00" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Capacidad (personas)</label>
                    <input type="number" name="capacidad" class="form-control"
                           min="1" value="1">
                </div>
            </div>

            <div class="mb-3">
                <label class="form-label">Categoría</label>
                <select name="categoriaId" class="form-select">
                    <c:forEach var="cat" items="${categorias}">
                        <option value="${cat.id}">${cat.nombre}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Imagen del espacio</label>
                <input type="file" name="imagen" class="form-control"
                       accept="image/*">
                <div class="form-text">Formatos: JPG, PNG, WEBP. Máximo 5MB.</div>
            </div>

            <div class="d-flex gap-2">
                <button type="submit" class="btn btn-success">Guardar espacio</button>
                <c:choose>
                    <c:when test="${sessionScope.rol == 'ADMIN'}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard"
                           class="btn btn-secondary">Cancelar</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/usuario/misEspacios"
                           class="btn btn-secondary">Cancelar</a>
                    </c:otherwise>
                </c:choose>
            </div>

        </form>
    </div>
</div>

</body>
</html>