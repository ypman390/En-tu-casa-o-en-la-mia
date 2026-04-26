package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.dao.SolicitudDAO;
import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.dao.UsuarioDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO       = new UsuarioDAO();
    private final EspacioDAO espacioDAO       = new EspacioDAO();
    private final CategoriaDAO categoriaDAO   = new CategoriaDAO();
    private final SolicitudDAO solicitudDAO = new SolicitudDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Solo ADMIN
        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

// ── Filtros usuarios ──────────────────────────────────
        String nombreFiltro = req.getParameter("nombreUsuario");
        String rolFiltro    = req.getParameter("rolUsuario");

        if ((nombreFiltro != null && !nombreFiltro.isBlank()) ||
                (rolFiltro != null && !rolFiltro.isBlank())) {
            req.setAttribute("usuarios", usuarioDAO.buscar(nombreFiltro, rolFiltro));
        } else {
            req.setAttribute("usuarios", usuarioDAO.listar());
        }

        req.setAttribute("nombreFiltro", nombreFiltro);
        req.setAttribute("rolFiltro", rolFiltro);

// ── Filtros categorías ────────────────────────────────
        String nombreCategoriaFiltro = req.getParameter("nombreCategoria");
        String tarifaMaxStr          = req.getParameter("tarifaMax");
        Double tarifaMax             = null;

        if (tarifaMaxStr != null && !tarifaMaxStr.isBlank()) {
            tarifaMax = Double.parseDouble(tarifaMaxStr);
        }

        if ((nombreCategoriaFiltro != null && !nombreCategoriaFiltro.isBlank()) || tarifaMax != null) {
            req.setAttribute("categorias", categoriaDAO.buscar(nombreCategoriaFiltro, tarifaMax));
        } else {
            req.setAttribute("categorias", categoriaDAO.listar());
        }

        req.setAttribute("nombreCategoriaFiltro", nombreCategoriaFiltro);
        req.setAttribute("tarifaMaxFiltro", tarifaMaxStr);

// ── Solo espacios y solicitudes sin filtro ────────────
        req.setAttribute("espacios",    espacioDAO.listar());
        req.setAttribute("solicitudes", solicitudDAO.listar());

        req.setAttribute("exito", req.getParameter("exito"));

        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}