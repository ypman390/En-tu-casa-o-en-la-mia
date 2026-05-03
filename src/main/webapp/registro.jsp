<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro — En tu casa o en la mía</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow p-4" style="width: 100%; max-width: 480px;">

        <h3 class="text-center mb-4"> Crear cuenta</h3>

        <!-- Mensaje de error -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/registro" method="post">

            <div class="mb-3">
                <label class="form-label">Nombre completo</label>
                <input type="text" name="nombre" class="form-control"
                       placeholder="Tu nombre" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Username</label>
                <input type="text" name="username" class="form-control"
                       placeholder="Elige un username" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control"
                       placeholder="tu@email.com" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" name="password" class="form-control"
                       placeholder="Mínimo 4 caracteres" required>
            </div>

            <div class="mb-3">
                <label class="form-label">DNI</label>
                <input type="text" name="dni" class="form-control"
                       placeholder="12345678A" required>
            </div>

            <button type="submit" class="btn btn-success w-100">Registrarse</button>

        </form>

        <hr>
        <p class="text-center mb-0">
            ¿Ya tienes cuenta?
            <a href="<%= request.getContextPath() %>/login">Inicia sesión</a>
        </p>

    </div>
</div>

</body>
</html>