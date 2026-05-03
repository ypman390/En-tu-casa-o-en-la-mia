package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/espacios")
public class EspacioServlet extends HttpServlet {

    private final EspacioDAO espacioDAO = new EspacioDAO();
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

        // Recoger filtros (pueden ser null si no se filtra)
        String precioStr    = req.getParameter("precioMax");
        String categoriaStr = req.getParameter("categoriaId");

        Double precioMax    = null;
        Integer categoriaId = null;

        if (precioStr != null && !precioStr.isBlank()) {
            precioMax = Double.parseDouble(precioStr);
        }
        if (categoriaStr != null && !categoriaStr.isBlank()) {
            categoriaId = Integer.parseInt(categoriaStr);
        }

        // Si hay filtros → buscar, si no → listar disponibles
        List<Espacio> espacios;
        if (precioMax != null || categoriaId != null) {
            espacios = espacioDAO.buscar(precioMax, categoriaId);
        } else {
            espacios = espacioDAO.listarDisponibles();
        }

        req.setAttribute("espacios", espacios);
        req.setAttribute("categorias", categoriaDAO.listar());
        req.setAttribute("precioMax", precioStr);
        req.setAttribute("categoriaSeleccionada", categoriaStr);

        req.getRequestDispatcher("/espacios/listado.jsp").forward(req, resp);
    }
}