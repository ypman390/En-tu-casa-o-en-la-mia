package com.acostaivan.entucasaoenlamia.servlet;

import com.acostaivan.entucasaoenlamia.dao.UsuarioDAO;
import com.acostaivan.entucasaoenlamia.model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/admin/editarUsuario")
public class EditarUsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("rol"))) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("usuario", usuarioDAO.buscarPorId(id));
        req.getRequestDispatcher("/admin/editarUsuario.jsp").forward(req, resp);
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
        Usuario u = usuarioDAO.buscarPorId(id);

        u.setNombre(req.getParameter("nombre"));
        u.setUsername(req.getParameter("username"));
        u.setEmail(req.getParameter("email"));
        u.setDni(req.getParameter("dni"));
        u.setRol(req.getParameter("rol"));
        u.setActivo(req.getParameter("activo") != null);
        u.setPuntos(Integer.parseInt(req.getParameter("puntos")));

        usuarioDAO.actualizar(u);
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard?exito=usuario");
    }
}