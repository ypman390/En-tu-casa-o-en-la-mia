package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import com.acostaivan.entucasaoenlamia.util.SubidaImagenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/admin/editarEspacio")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class EditarEspacioServlet extends HttpServlet {

    private final EspacioDAO espacioDAO     = new EspacioDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Espacio espacio = espacioDAO.buscarPorId(Integer.parseInt(req.getParameter("id")));
        req.setAttribute("espacio", espacio);
        req.setAttribute("categorias", categoriaDAO.listar());
        req.getRequestDispatcher("/admin/editarEspacio.jsp").forward(req, resp);
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
        Espacio espacio = espacioDAO.buscarPorId(id);

        espacio.setTitulo(req.getParameter("titulo"));
        espacio.setDescripcion(req.getParameter("descripcion"));
        espacio.setPrecio(new BigDecimal(req.getParameter("precio")));
        espacio.setCapacidad(Integer.parseInt(req.getParameter("capacidad")));
        espacio.setCategoriaId(Integer.parseInt(req.getParameter("categoriaId")));
        espacio.setDisponible(req.getParameter("disponible") != null);

        // Si se sube nueva imagen la reemplazamos, si no conservamos la anterior
        Part imagenPart = req.getPart("imagen");
        if (imagenPart != null && imagenPart.getSize() > 0) {
            String rutaBase = getServletContext().getRealPath("");
            String rutaImagen = SubidaImagenUtil.guardar(imagenPart, rutaBase);
            espacio.setImagen(rutaImagen);
        }

        espacioDAO.actualizar(espacio);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard?exito=espacio");
    }
}