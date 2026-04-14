package com.acostaivan.entucasaoenlamia.dao;

import com.acostaivan.entucasaoenlamia.model.Solicitud;
import com.acostaivan.entucasaoenlamia.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudDAO {

    // ─── INSERTAR ─────────────────────────────────────────────
    public boolean insertar(Solicitud s) {
        String sql = """
                INSERT INTO solicitud
                (usuario_id, espacio_id, fecha_inicio, fecha_fin,
                 aceptada, precio_total, comentario, numero_personas)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, s.getUsuarioId());
            ps.setInt(2, s.getEspacioId());
            ps.setDate(3, Date.valueOf(s.getFechaInicio()));
            ps.setDate(4, Date.valueOf(s.getFechaFin()));
            ps.setBoolean(5, false);
            ps.setBigDecimal(6, s.getPrecioTotal());
            ps.setString(7, s.getComentario());
            ps.setInt(8, s.getNumeroPersonas());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─── LISTAR TODAS (admin) ─────────────────────────────────
    public List<Solicitud> listar() {
        List<Solicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud ORDER BY id DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ─── LISTAR POR USUARIO ───────────────────────────────────
    public List<Solicitud> listarPorUsuario(int usuarioId) {
        List<Solicitud> lista = new ArrayList<>();
        String sql = "SELECT * FROM solicitud WHERE usuario_id = ? ORDER BY id DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ─── BUSCAR POR ID ────────────────────────────────────────
    public Solicitud buscarPorId(int id) {
        String sql = "SELECT * FROM solicitud WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ─── ACEPTAR solicitud ────────────────────────────────────
    public boolean aceptar(int id) {
        String sql = "UPDATE solicitud SET aceptada = true WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─── ELIMINAR ─────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM solicitud WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─── MAPEAR ResultSet → Solicitud ─────────────────────────
    private Solicitud mapear(ResultSet rs) throws SQLException {
        Solicitud s = new Solicitud();
        s.setId(rs.getInt("id"));
        s.setUsuarioId(rs.getInt("usuario_id"));
        s.setEspacioId(rs.getInt("espacio_id"));
        s.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
        s.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
        s.setAceptada(rs.getBoolean("aceptada"));
        s.setPrecioTotal(rs.getBigDecimal("precio_total"));
        s.setComentario(rs.getString("comentario"));
        s.setNumeroPersonas(rs.getInt("numero_personas"));
        return s;
    }
}