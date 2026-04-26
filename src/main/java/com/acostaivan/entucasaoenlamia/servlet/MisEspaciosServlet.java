package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.dao.SolicitudDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/usuario/misEspacios")
public class MisEspaciosServlet extends HttpServlet {

    private final EspacioDAO espacioDAO     = new EspacioDAO();
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

        // Espacios publicados por este usuario
        req.setAttribute("misEspacios", espacioDAO.listarPorUsuario(usuario.getId()));

        // Solicitudes recibidas en sus espacios
        req.setAttribute("solicitudesRecibidas",
                solicitudDAO.listarPorEspaciosDeUsuario(usuario.getId()));

        req.setAttribute("exito", req.getParameter("exito"));
        req.getRequestDispatcher("/usuario/misEspacios.jsp").forward(req, resp);
    }
}