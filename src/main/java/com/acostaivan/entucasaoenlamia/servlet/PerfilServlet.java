package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.UsuarioDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/usuario/perfil")
public class PerfilServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        req.setAttribute("usuario", usuarioDAO.buscarPorId(usuario.getId()));
        req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuarioSesion = (Usuario) session.getAttribute("usuarioLogueado");
        Usuario usuario = usuarioDAO.buscarPorId(usuarioSesion.getId());

        String nombre      = req.getParameter("nombre");
        String email       = req.getParameter("email");
        String dni         = req.getParameter("dni");
        String passwordNueva    = req.getParameter("passwordNueva");
        String passwordActual   = req.getParameter("passwordActual");

        // ── Validar password actual ───────────────────────────
        if (!usuario.getPassword().equals(passwordActual)) {
            req.setAttribute("error", "La contraseña actual no es correcta.");
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
            return;
        }

        // ── Validar campos obligatorios ───────────────────────
        if (nombre.isBlank() || email.isBlank() || dni.isBlank()) {
            req.setAttribute("error", "Nombre, email y DNI son obligatorios.");
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
            return;
        }

        if (!email.contains("@")) {
            req.setAttribute("error", "El email no es válido.");
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
            return;
        }

        // ── Actualizar datos ──────────────────────────────────
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setDni(dni);

        // Solo actualiza contraseña si se ha introducido una nueva
        if (passwordNueva != null && !passwordNueva.isBlank()) {
            if (passwordNueva.length() < 4) {
                req.setAttribute("error", "La nueva contraseña debe tener al menos 4 caracteres.");
                req.setAttribute("usuario", usuario);
                req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
                return;
            }
            usuario.setPassword(passwordNueva);
        }

        boolean ok = usuarioDAO.actualizar(usuario);

        if (ok) {
            // Actualizar la sesión con los nuevos datos
            session.setAttribute("usuarioLogueado", usuario);
            req.setAttribute("exito", "Perfil actualizado correctamente.");
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Error al actualizar el perfil.");
            req.setAttribute("usuario", usuario);
            req.getRequestDispatcher("/usuario/perfil.jsp").forward(req, resp);
        }
    }
}
