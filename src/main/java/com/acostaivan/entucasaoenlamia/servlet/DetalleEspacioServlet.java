package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/espacios/detalle")
public class DetalleEspacioServlet extends HttpServlet {

    private final EspacioDAO espacioDAO     = new EspacioDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Proteger: debe estar logueado
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/espacios");
            return;
        }

        Espacio espacio = espacioDAO.buscarPorId(Integer.parseInt(idStr));
        if (espacio == null) {
            resp.sendRedirect(req.getContextPath() + "/espacios");
            return;
        }

        req.setAttribute("espacio", espacio);
        req.setAttribute("categoria", categoriaDAO.buscarPorId(espacio.getCategoriaId()));
        req.getRequestDispatcher("/espacios/detalle.jsp").forward(req, resp);
    }
}