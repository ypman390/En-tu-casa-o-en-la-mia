package com.acostaivan.entucasaoenlamia.servlet;


import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/editarCategoria")
public class EditarCategoriaServlet extends HttpServlet {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("categoria", categoriaDAO.buscarPorId(id));
        req.getRequestDispatcher("/admin/editarCategoria.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        Categoria c = categoriaDAO.buscarPorId(id);

        c.setNombre(req.getParameter("nombre"));
        c.setDescripcion(req.getParameter("descripcion"));
        c.setPrioridad(Integer.parseInt(req.getParameter("prioridad")));
        c.setTarifaBase(new BigDecimal(req.getParameter("tarifaBase")));
        c.setActiva(req.getParameter("activa") != null);

        categoriaDAO.actualizar(c);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard?exito=categoria");
    }
}