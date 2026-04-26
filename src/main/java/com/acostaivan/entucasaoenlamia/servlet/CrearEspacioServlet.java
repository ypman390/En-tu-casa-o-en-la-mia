package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.CategoriaDAO;
import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import com.acostaivan.entucasaoenlamia.util.SubidaImagenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/admin/crearEspacio")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class CrearEspacioServlet extends HttpServlet {

    private final EspacioDAO espacioDAO     = new EspacioDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Cualquier usuario logueado puede acceder
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("categorias", categoriaDAO.listar());
        req.getRequestDispatcher("/admin/crearEspacio.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario    = (Usuario) session.getAttribute("usuarioLogueado");
        String titulo      = req.getParameter("titulo");
        String descripcion = req.getParameter("descripcion");
        String precioStr   = req.getParameter("precio");
        String capacidadStr = req.getParameter("capacidad");
        String categoriaStr = req.getParameter("categoriaId");
        Part imagenPart     = req.getPart("imagen");

        // ── Validaciones ──────────────────────────────────────
        if (titulo == null || titulo.isBlank() ||
                precioStr == null || precioStr.isBlank()) {
            req.setAttribute("error", "Título y precio son obligatorios.");
            req.setAttribute("categorias", categoriaDAO.listar());
            req.getRequestDispatcher("/admin/crearEspacio.jsp").forward(req, resp);
            return;
        }

        BigDecimal precio = new BigDecimal(precioStr);
        if (precio.compareTo(BigDecimal.ZERO) <= 0) {
            req.setAttribute("error", "El precio debe ser mayor que 0.");
            req.setAttribute("categorias", categoriaDAO.listar());
            req.getRequestDispatcher("/admin/crearEspacio.jsp").forward(req, resp);
            return;
        }

        // ── Subir imagen ──────────────────────────────────────
        String rutaBase   = getServletContext().getRealPath("");
        String rutaImagen = SubidaImagenUtil.guardar(imagenPart, rutaBase);

        // ── Construir Espacio ─────────────────────────────────
        Espacio espacio = new Espacio();
        espacio.setTitulo(titulo);
        espacio.setDescripcion(descripcion);
        espacio.setPrecio(precio);
        espacio.setFechaPublicacion(LocalDate.now());
        espacio.setDisponible(true);
        espacio.setImagen(rutaImagen);
        espacio.setCapacidad(capacidadStr != null ? Integer.parseInt(capacidadStr) : 1);
        espacio.setValoracion(BigDecimal.ZERO);
        espacio.setCategoriaId(categoriaStr != null ? Integer.parseInt(categoriaStr) : 0);
        espacio.setUsuarioId(usuario.getId()); // dueño del espacio

        boolean ok = espacioDAO.insertar(espacio);

        if (ok) {
            // Redirigir según rol
            if ("ADMIN".equals(usuario.getRol())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard?exito=espacio");
            } else {
                resp.sendRedirect(req.getContextPath() + "/usuario/misEspacios?exito=creado");
            }
        } else {
            req.setAttribute("error", "Error al guardar el espacio.");
            req.setAttribute("categorias", categoriaDAO.listar());
            req.getRequestDispatcher("/admin/crearEspacio.jsp").forward(req, resp);
        }
    }
}