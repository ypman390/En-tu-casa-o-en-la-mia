package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
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
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        espacioDAO.eliminar(id);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}