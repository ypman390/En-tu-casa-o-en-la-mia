package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/eliminarEspacio")
public class EliminarEspacioServlet extends HttpServlet {

    private final EspacioDAO espacioDAO = new EspacioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        int id = Integer.parseInt(req.getParameter("id"));
        Espacio espacio = espacioDAO.buscarPorId(id);

        // Verificar que es el dueño o ADMIN
        if (!"ADMIN".equals(usuario.getRol()) && espacio.getUsuarioId() != usuario.getId()) {
            resp.sendRedirect(req.getContextPath() + "/espacios");
            return;
        }

        espacioDAO.eliminar(id);

        // Redirigir según rol
        if ("ADMIN".equals(usuario.getRol())) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } else {
            resp.sendRedirect(req.getContextPath() + "/usuario/misEspacios");
        }
    }
}