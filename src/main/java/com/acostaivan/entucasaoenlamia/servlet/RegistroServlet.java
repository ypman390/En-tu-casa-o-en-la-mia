package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.UsuarioDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Muestra el formulario
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/registro.jsp").forward(req, resp);
    }

    // Procesa el formulario
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nombre   = req.getParameter("nombre");
        String username = req.getParameter("username");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String dni      = req.getParameter("dni");

        // ── Validaciones ──────────────────────────────────────
        if (nombre == null || nombre.isBlank() ||
                username == null || username.isBlank() ||
                email == null || email.isBlank() ||
                password == null || password.isBlank() ||
                dni == null || dni.isBlank()) {

            req.setAttribute("error", "Todos los campos son obligatorios.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
            return;
        }

        if (!email.contains("@")) {
            req.setAttribute("error", "El email no es válido.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
            return;
        }

        if (password.length() < 4) {
            req.setAttribute("error", "La contraseña debe tener al menos 4 caracteres.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
            return;
        }

        // ── Construir el objeto Usuario ────────────────────────
        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(password);
        u.setDni(dni);
        u.setRol("USER");                        // siempre USER al registrarse
        u.setActivo(true);
        u.setFechaRegistro(LocalDate.now());
        u.setPuntos(0);
        u.setCredito(BigDecimal.ZERO);

        // ── Insertar en BD ─────────────────────────────────────
        boolean ok = usuarioDAO.insertar(u);

        if (ok) {
            // Redirige al login con mensaje de éxito
            resp.sendRedirect(req.getContextPath() + "/login?exito=1");
        } else {
            req.setAttribute("error", "No se pudo registrar. Username, email o DNI ya están en uso.");
            req.getRequestDispatcher("/registro.jsp").forward(req, resp);
        }
    }
}