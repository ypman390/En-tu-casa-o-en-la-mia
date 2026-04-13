package com.acostaivan.entucasaoenlamia.dao;

import com.acostaivan.entucasaoenlamia.model.Espacio;
import com.acostaivan.entucasaoenlamia.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAO {

    // ─── LISTAR TODOS ─────────────────────────────────────────
    public List<Espacio> listar() {
        List<Espacio> lista = new ArrayList<>();
        String sql = "SELECT * FROM espacio ORDER BY fecha_publicacion DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ─── LISTAR SOLO DISPONIBLES ──────────────────────────────
    public List<Espacio> listarDisponibles() {
        List<Espacio> lista = new ArrayList<>();
        String sql = "SELECT * FROM espacio WHERE disponible = true ORDER BY fecha_publicacion DESC";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ─── BUSCAR CON FILTROS (precio + categoría) ──────────────
    public List<Espacio> buscar(Double precioMax, Integer categoriaId) {
        List<Espacio> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM espacio WHERE disponible = true"
        );

        if (precioMax != null)    sql.append(" AND precio <= ?");
        if (categoriaId != null)  sql.append(" AND categoria_id = ?");
        sql.append(" ORDER BY precio ASC");

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int i = 1;
            if (precioMax != null)   ps.setDouble(i++, precioMax);
            if (categoriaId != null) ps.setInt(i++, categoriaId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // ─── BUSCAR POR ID ────────────────────────────────────────
    public Espacio buscarPorId(int id) {
        String sql = "SELECT * FROM espacio WHERE id = ?";
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

    // ─── INSERTAR ─────────────────────────────────────────────
    public boolean insertar(Espacio e) {
        String sql = """
                INSERT INTO espacio
                (titulo, descripcion, precio, fecha_publicacion,
                 disponible, imagen, capacidad, valoracion, categoria_id)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getTitulo());
            ps.setString(2, e.getDescripcion());
            ps.setBigDecimal(3, e.getPrecio());
            ps.setDate(4, Date.valueOf(e.getFechaPublicacion()));
            ps.setBoolean(5, e.isDisponible());
            ps.setString(6, e.getImagen());
            ps.setInt(7, e.getCapacidad());
            ps.setBigDecimal(8, e.getValoracion());
            ps.setInt(9, e.getCategoriaId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // ─── ACTUALIZAR ───────────────────────────────────────────
    public boolean actualizar(Espacio e) {
        String sql = """
                UPDATE espacio
                SET titulo=?, descripcion=?, precio=?, disponible=?,
                    imagen=?, capacidad=?, valoracion=?, categoria_id=?
                WHERE id=?
                """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getTitulo());
            ps.setString(2, e.getDescripcion());
            ps.setBigDecimal(3, e.getPrecio());
            ps.setBoolean(4, e.isDisponible());
            ps.setString(5, e.getImagen());
            ps.setInt(6, e.getCapacidad());
            ps.setBigDecimal(7, e.getValoracion());
            ps.setInt(8, e.getCategoriaId());
            ps.setInt(9, e.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // ─── ELIMINAR ─────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM espacio WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ─── MAPEAR ResultSet → Espacio ───────────────────────────
    private Espacio mapear(ResultSet rs) throws SQLException {
        Espacio e = new Espacio();
        e.setId(rs.getInt("id"));
        e.setTitulo(rs.getString("titulo"));
        e.setDescripcion(rs.getString("descripcion"));
        e.setPrecio(rs.getBigDecimal("precio"));
        e.setFechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate());
        e.setDisponible(rs.getBoolean("disponible"));
        e.setImagen(rs.getString("imagen"));
        e.setCapacidad(rs.getInt("capacidad"));
        e.setValoracion(rs.getBigDecimal("valoracion"));
        e.setCategoriaId(rs.getInt("categoria_id"));
        return e;
    }
}