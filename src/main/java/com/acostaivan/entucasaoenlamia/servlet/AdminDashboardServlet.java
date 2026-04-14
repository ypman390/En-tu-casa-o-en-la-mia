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

        // Cargar datos para el dashboard
        req.setAttribute("usuarios",   usuarioDAO.listar());
        req.setAttribute("espacios",   espacioDAO.listar());
        req.setAttribute("categorias", categoriaDAO.listar());
        req.setAttribute("solicitudes", solicitudDAO.listar());

        // Mensaje de éxito opcional
        req.setAttribute("exito", req.getParameter("exito"));

        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}