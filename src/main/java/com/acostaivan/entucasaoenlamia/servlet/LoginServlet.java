package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.UsuarioDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Muestra el formulario de login
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    // Procesa el formulario
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validación básica
        if (username == null || username.isBlank() ||
                password == null || password.isBlank()) {
            req.setAttribute("error", "Rellena todos los campos.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        Usuario usuario = usuarioDAO.login(username, password);

        if (usuario != null) {
            // Guardamos el usuario en sesión
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            session.setAttribute("rol", usuario.getRol());

            // Redirigir según rol
            if ("ADMIN".equals(usuario.getRol())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/espacios");
            }

        } else {
            req.setAttribute("error", "Usuario o contraseña incorrectos.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}