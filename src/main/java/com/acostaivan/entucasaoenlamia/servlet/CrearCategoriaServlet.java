package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.model.Categoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/admin/crearCategoria")
public class CrearCategoriaServlet extends HttpServlet {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/admin/crearCategoria.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String nombre      = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String tarifaStr   = req.getParameter("tarifaBase");
        String prioridadStr = req.getParameter("prioridad");

        if (nombre == null || nombre.isBlank()) {
            req.setAttribute("error", "El nombre es obligatorio.");
            req.getRequestDispatcher("/admin/crearCategoria.jsp").forward(req, resp);
            return;
        }

        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        c.setActiva(true);
        c.setFechaCreacion(LocalDate.now());
        c.setPrioridad(prioridadStr != null && !prioridadStr.isBlank()
                ? Integer.parseInt(prioridadStr) : 1);
        c.setTarifaBase(tarifaStr != null && !tarifaStr.isBlank()
                ? new BigDecimal(tarifaStr) : BigDecimal.ZERO);

        boolean ok = categoriaDAO.insertar(c);

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard?exito=categoria");
        } else {
            req.setAttribute("error", "Error al crear la categoría.");
            req.getRequestDispatcher("/admin/crearCategoria.jsp").forward(req, resp);
        }
    }
}