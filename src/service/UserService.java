package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import model.User;

public interface UserService {
    String crearUser(User user, List<User> usuarios, Connection conexion) throws SQLException;

    User buscaUserPorId(int id, List<User> usuarios);

    List<User> obtenerTodo(List<User> usuarios);

    String editaUser(User user, List<User> usuarios, Connection conexion) throws SQLException;

    String eliminarUserPorId(int id, List<User> usuarios, Connection conexion) throws SQLException;

    String eliminarTodo(List<User> usuarios, Connection conexion) throws SQLException;
}
