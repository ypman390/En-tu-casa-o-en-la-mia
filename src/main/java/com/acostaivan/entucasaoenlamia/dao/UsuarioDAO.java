package com.acostaivan.entucasaoenlamia.dao;

import com.acostaivan.entucasaoenlamia.model.Usuario;
import com.acostaivan.entucasaoenlamia.util.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    public Usuario login(String username, String password) {
        String sql = "SELECT * FROM usuario WHERE username = ? AND password = ? AND activo = true";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean insertar(Usuario u) {
        String sql = """
                INSERT INTO usuario
                (nombre, username, email, password, dni, rol, activo, fecha_registro, puntos, credito)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getDni());
            ps.setString(6, u.getRol());
            ps.setBoolean(7, u.isActivo());
            ps.setDate(8, Date.valueOf(u.getFechaRegistro()));
            ps.setInt(9, u.getPuntos());
            ps.setBigDecimal(10, u.getCredito());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY id";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public List<Usuario> buscar(String nombre, String rol) {
        List<Usuario> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM usuario WHERE 1=1");

        if (nombre != null && !nombre.isBlank()) sql.append(" AND nombre LIKE ?");
        if (rol != null && !rol.isBlank())       sql.append(" AND rol = ?");
        sql.append(" ORDER BY id");



        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int i = 1;
            if (nombre != null && !nombre.isBlank()) ps.setString(i++, "%" + nombre + "%");
            if (rol != null && !rol.isBlank())       ps.setString(i++, rol);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
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


    public boolean actualizar(Usuario u) {
        String sql = """
            UPDATE usuario
            SET nombre=?, username=?, email=?, password=?, dni=?, rol=?, activo=?, puntos=?, credito=?
            WHERE id=?
            """;
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());  // ← NUEVO
            ps.setString(5, u.getDni());
            ps.setString(6, u.getRol());
            ps.setBoolean(7, u.isActivo());
            ps.setInt(8, u.getPuntos());
            ps.setBigDecimal(9, u.getCredito());
            ps.setInt(10, u.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean desactivar(int id) {
        String sql = "UPDATE usuario SET activo = false WHERE id = ?";
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNombre(rs.getString("nombre"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setDni(rs.getString("dni"));
        u.setRol(rs.getString("rol"));
        u.setActivo(rs.getBoolean("activo"));
        u.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
        u.setPuntos(rs.getInt("puntos"));
        u.setCredito(rs.getBigDecimal("credito"));
        return u;
    }
}