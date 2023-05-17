package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.User;

public class UserServiceImpl implements UserService {

    @Override
    public String crearUser(User user, List<User> usuarios, Connection conexion) throws SQLException {
        String sql = "INSERT INTO usuarios (id, nombre, apellido, password, fechaDeCreacion, ultimaEdicion, esAdmin, esSuperAdmin, esUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, user.getId());
        ps.setString(2, user.getNombres());
        ps.setString(3, user.getApellidos());
        ps.setString(4, user.getPassword());
        java.util.Date fechaUtil = new java.util.Date();
        java.sql.Timestamp fechaTimestamp = new java.sql.Timestamp(fechaUtil.getTime());
        ps.setTimestamp(5, fechaTimestamp);
        ps.setTimestamp(6, fechaTimestamp);
        ps.setBoolean(7, user.isEsAdmin());
        ps.setBoolean(8, user.isEsSuperAdmin());
        ps.setBoolean(9, user.isEsUsuario());
        ps.executeUpdate();
        usuarios.add(user);
        return "Se creo el usuario: " + user.getNombres() + " " + user.getApellidos();
    }

    @Override
    public List<User> obtenerTodo(List<User> usuarios) {
        return usuarios;
    }

    @Override
    public String editaUser(User userNuevo, List<User> usuarios, Connection conexion) throws SQLException {
        User usuarioBuscado = null;

        for (User user : usuarios) {
            if (user.getId() == userNuevo.getId()) {
                usuarioBuscado = user;
                break;
            }
        }

        if (usuarioBuscado != null) {
            String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, password = ?, ultimaEdicion = ?, esAdmin = ?, esSuperAdmin = ?, esUsuario = ? WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, userNuevo.getNombres());
            ps.setString(2, userNuevo.getApellidos());
            ps.setString(3, userNuevo.getPassword());
            java.util.Date fechaUtil = new java.util.Date();
            java.sql.Timestamp fechaTimestamp = new java.sql.Timestamp(fechaUtil.getTime());
            ps.setTimestamp(4, fechaTimestamp);
            ps.setBoolean(5, userNuevo.isEsAdmin());
            ps.setBoolean(6, userNuevo.isEsSuperAdmin());
            ps.setBoolean(7, userNuevo.isEsUsuario());
            ps.setInt(8, userNuevo.getId());
            ps.executeUpdate();
            usuarios.remove(usuarioBuscado);
            usuarios.add(userNuevo);
            return "Se ha editado el usuario con id: " + userNuevo.getId();
        } else {
            return "El usuario buscado no existe";
        }
    }

    @Override
    public String eliminarUserPorId(int id, List<User> usuarios, Connection conexion) throws SQLException {
        User usuarioBuscado = null;

        for (User user : usuarios) {
            if (user.getId() == id) {
                usuarioBuscado = user;
                break;
            }
        }

        if (usuarioBuscado != null) {
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            usuarios.remove(usuarioBuscado);
            return "Usuario: " + usuarioBuscado.getNombres() + " " + usuarioBuscado.getApellidos() + ". Eliminado";
        } else {
            return "No se encontro ese usuario";
        }
    }

    @Override
    public String eliminarTodo(List<User> usuarios, Connection conexion) throws SQLException {
        String sql = "DELETE FROM usuarios";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.executeUpdate();
        usuarios.clear();
        return "se elimino todos los usuarios";
    }

    @Override
    public User buscaUserPorId(int id, List<User> usuarios) {
        User usuarioBuscado = null;

        for (User user : usuarios) {
            if (user.getId() == id) {
                usuarioBuscado = user;
                break;
            }
        }

        if (usuarioBuscado != null) {
            return usuarioBuscado;
        } else {
            return null;
        }
    }

}
