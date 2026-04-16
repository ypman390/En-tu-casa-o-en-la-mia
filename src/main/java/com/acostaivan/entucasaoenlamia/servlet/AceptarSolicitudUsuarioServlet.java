package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.EspacioDAO;
import com.acostaivan.entucasaoenlamia.dao.SolicitudDAO;
import com.acostaivan.entucasaoenlamia.model.Espacio;
import com.acostaivan.entucasaoenlamia.model.Solicitud;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/usuario/aceptarSolicitud")
public class AceptarSolicitudUsuarioServlet extends HttpServlet {

    private final SolicitudDAO solicitudDAO = new SolicitudDAO();
    private final EspacioDAO espacioDAO     = new EspacioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario usuario  = (Usuario) session.getAttribute("usuarioLogueado");
        int solicitudId  = Integer.parseInt(req.getParameter("id"));
        Solicitud s      = solicitudDAO.buscarPorId(solicitudId);
        Espacio espacio  = espacioDAO.buscarPorId(s.getEspacioId());

        // Verificar que el espacio pertenece al usuario
        if (espacio.getUsuarioId() != usuario.getId()) {
            resp.sendRedirect(req.getContextPath() + "/usuario/misEspacios");
            return;
        }

        solicitudDAO.aceptar(solicitudId);
        resp.sendRedirect(req.getContextPath() + "/usuario/misEspacios?exito=aceptada");
    }
}