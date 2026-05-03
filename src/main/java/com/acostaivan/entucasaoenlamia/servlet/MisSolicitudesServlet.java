package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.SolicitudDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/usuario/misSolicitudes")
public class MisSolicitudesServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        req.setAttribute("misSolicitudes",
                solicitudDAO.listarPorUsuario(usuario.getId()));

        req.getRequestDispatcher("/usuario/misSolicitudes.jsp").forward(req, resp);
    }
}