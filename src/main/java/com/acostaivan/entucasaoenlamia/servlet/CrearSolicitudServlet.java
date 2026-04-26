package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.SolicitudDAO;
import com.acostaivan.entucasaoenlamia.model.Solicitud;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@WebServlet("/solicitudes/crear")
public class CrearSolicitudServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Proteger: debe estar logueado
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        // Recoger datos del formulario
        int espacioId       = Integer.parseInt(req.getParameter("espacioId"));
        BigDecimal precio   = new BigDecimal(req.getParameter("precio"));
        String fechaIniStr  = req.getParameter("fechaInicio");
        String fechaFinStr  = req.getParameter("fechaFin");
        String comentario   = req.getParameter("comentario");
        int numeroPersonas  = Integer.parseInt(req.getParameter("numeroPersonas"));

        LocalDate fechaInicio = LocalDate.parse(fechaIniStr);
        LocalDate fechaFin    = LocalDate.parse(fechaFinStr);

        // Validar fechas
        if (fechaFin.isBefore(fechaInicio) || fechaFin.isEqual(fechaInicio)) {
            resp.sendRedirect(req.getContextPath() +
                    "/espacios/detalle?id=" + espacioId + "&error=fechas");
            return;
        }

        // Calcular precio total automáticamente
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        BigDecimal precioTotal = precio.multiply(BigDecimal.valueOf(dias));

        // Construir solicitud
        Solicitud s = new Solicitud();
        s.setUsuarioId(usuario.getId());
        s.setEspacioId(espacioId);
        s.setFechaInicio(fechaInicio);
        s.setFechaFin(fechaFin);
        s.setAceptada(false);
        s.setPrecioTotal(precioTotal);
        s.setComentario(comentario);
        s.setNumeroPersonas(numeroPersonas);

        boolean ok = solicitudDAO.insertar(s);

        if (ok) {
            resp.sendRedirect(req.getContextPath() +
                    "/espacios/detalle?id=" + espacioId + "&exito=solicitud");
        } else {
            resp.sendRedirect(req.getContextPath() +
                    "/espacios/detalle?id=" + espacioId + "&error=solicitud");
        }
    }
}