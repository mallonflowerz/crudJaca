package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import model.Login;
import model.User;

public interface LoginService {
    
    List<User> iniciarSesion(String usuario, String password, Connection conn) throws SQLException, ParseException;
}
