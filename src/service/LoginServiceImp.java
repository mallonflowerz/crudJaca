package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import model.Login;
import model.User;

public class LoginServiceImp implements LoginService{

    @Override
    public List<User> iniciarSesion(String usuario, String password, Connection conn) throws SQLException, ParseException {
        List<User> usuarios = new ArrayList<>();
        String consulta = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(consulta);
        ps.setString(1, usuario);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt("id"), rs.getString("nombre"),
                            rs.getString("apellido"), rs.getString("password"),
                            rs.getTimestamp("fechaDeCreacion"), rs.getTimestamp("ultimaEdicion"));
            user.setEsUsuario(rs.getBoolean("esUsuario")); 
            user.setEsSuperAdmin(rs.getBoolean("esSuperAdmin")); 
            user.setEsAdmin(rs.getBoolean("esAdmin"));
            usuarios.add(user);
            if (!(usuarios.size() > 1) && usuarios.get(0) != null){
                return usuarios;
            }
        }

        usuarios.clear();
        return usuarios;
    }
    
}
