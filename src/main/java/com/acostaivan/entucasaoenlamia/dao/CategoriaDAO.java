package com.acostaivan.entucasaoenlamia.dao;

import com.acostaivan.entucasaoenlamia.model.Categoria;
import com.acostaivan.entucasaoenlamia.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria WHERE activa = true ORDER BY prioridad";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setActiva(rs.getBoolean("activa"));
                c.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                c.setPrioridad(rs.getInt("prioridad"));
                c.setTarifaBase(rs.getBigDecimal("tarifa_base"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                c.setDescripcion(rs.getString("descripcion"));
                c.setActiva(rs.getBoolean("activa"));
                c.setFechaCreacion(rs.getDate("fecha_creacion").toLocalDate());
                c.setPrioridad(rs.getInt("prioridad"));
                c.setTarifaBase(rs.getBigDecimal("tarifa_base"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Categoria c) {
        String sql = """
                INSERT INTO categoria
                (nombre, descripcion, activa, fecha_creacion, prioridad, tarifa_base)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setBoolean(3, c.isActiva());
            ps.setDate(4, Date.valueOf(c.getFechaCreacion()));
            ps.setInt(5, c.getPrioridad());
            ps.setBigDecimal(6, c.getTarifaBase());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}