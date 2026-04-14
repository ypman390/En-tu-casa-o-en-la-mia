<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- Navbar -->
<nav class="navbar navbar-dark bg-dark px-4">
    <span class="navbar-brand fw-bold">🏠 Admin Panel</span>
    <div>
        <span class="text-white me-3">
            👤 ${sessionScope.usuarioLogueado.nombre}
        </span>
        <a href="${pageContext.request.contextPath}/espacios"
           class="btn btn-outline-light btn-sm me-2">Ver espacios</a>
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-danger btn-sm">Cerrar sesión</a>
    </div>
</nav>

<div class="container mt-4">

    <!-- Mensaje éxito -->
    <c:if test="${exito == 'espacio'}">
        <div class="alert alert-success">✅ Espacio creado correctamente.</div>
    </c:if>
    <c:if test="${exito == 'usuario'}">
        <div class="alert alert-success">✅ Usuario actualizado correctamente.</div>
    </c:if>
    <c:if test="${exito == 'categoria'}">
        <div class="alert alert-success">✅ Categoría creada correctamente.</div>
    </c:if>

    <h2 class="mb-4">📊 Panel de administración</h2>

    <!-- Tarjetas resumen -->
    <div class="row g-3 mb-5">
        <div class="col-md-4">
            <div class="card text-white bg-primary shadow">
                <div class="card-body text-center">
                    <h1>${usuarios.size()}</h1>
                    <p class="mb-0">👤 Usuarios registrados</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-success shadow">
                <div class="card-body text-center">
                    <h1>${espacios.size()}</h1>
                    <p class="mb-0">🏠 Espacios publicados</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white bg-warning shadow">
                <div class="card-body text-center">
                    <h1>${categorias.size()}</h1>
                    <p class="mb-0">📂 Categorías activas</p>
                </div>
            </div>
        </div>
    </div>

    <!-- ── GESTIÓN DE ESPACIOS ────────────────────────────── -->
    <div class="card shadow mb-5">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="mb-0">🏠 Gestión de Espacios</h5>
            <a href="${pageContext.request.contextPath}/admin/crearEspacio"
               class="btn btn-success btn-sm">➕ Nuevo espacio</a>
        </div>
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
                    <c:when test="${empty espacios}">
                        <tr>
                            <td colspan="6" class="text-center text-muted py-3">
                                No hay espacios todavía.
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="e" items="${espacios}">
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
                                    <a href="${pageContext.request.contextPath}/admin/editarEspacio?id=${e.id}"
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

    <!-- ── GESTIÓN DE USUARIOS ───────────────────────────── -->
    <div class="card shadow mb-5">
        <div class="card-header">
            <h5 class="mb-0">👤 Gestión de Usuarios</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Rol</th>
                    <th>Activo</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="u" items="${usuarios}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.nombre}</td>
                        <td>${u.username}</td>
                        <td>${u.email}</td>
                        <td>
                                <span class="badge ${u.rol == 'ADMIN' ? 'bg-danger' : 'bg-primary'}">
                                        ${u.rol}
                                </span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${u.activo}">
                                    <span class="badge bg-success">Activo</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">Inactivo</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/editarUsuario?id=${u.id}"
                               class="btn btn-warning btn-sm">Editar</a>
                            <a href="${pageContext.request.contextPath}/admin/desactivarUsuario?id=${u.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Desactivar este usuario?')">Desactivar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <!-- ── GESTIÓN DE CATEGORÍAS ─────────────────────────── -->
    <div class="card shadow mb-4">
        <div class="card-header d-flex justify-content-between align-items-center">
            <h5 class="mb-0">📂 Gestión de Categorías</h5>
            <a href="${pageContext.request.contextPath}/admin/crearCategoria"
               class="btn btn-success btn-sm">➕ Nueva categoría</a>
        </div>
        <div class="card-body p-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Tarifa base</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${categorias}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.nombre}</td>
                        <td>${c.descripcion}</td>
                        <td>${c.tarifaBase} €</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/eliminarCategoria?id=${c.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Eliminar esta categoría?')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>