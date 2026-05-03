<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login — En tu casa o en la mía</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow p-4" style="width: 100%; max-width: 420px;">

        <h3 class="text-center mb-4"> En tu casa o en la mía</h3>

        <!-- Mensaje de éxito tras registro -->
        <% if ("1".equals(request.getParameter("exito"))) { %>
        <div class="alert alert-success">
             Cuenta creada correctamente. Ya puedes iniciar sesión.
        </div>
        <% } %>

        <!-- Mensaje de error -->
        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post">

            <div class="mb-3">
                <label class="form-label">Usuario</label>
                <input type="text" name="username" class="form-control"
                       placeholder="Tu username" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" name="password" class="form-control"
                       placeholder="Tu contraseña" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">Entrar</button>

        </form>

        <hr>
        <p class="text-center mb-0">
            ¿No tienes cuenta?
            <a href="<%= request.getContextPath() %>/registro">Regístrate</a>
        </p>

    </div>
</div>

</body>
</html>