package controller;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;

import database.DataBase;
import model.User;
import service.LoginService;
import service.LoginServiceImp;

public class LoginController {
    DataBase dataBase = new DataBase();
    LoginService loginService = new LoginServiceImp();
    Connection conexion = null;

    public boolean InicioSesion() {
        try {
            conexion = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsuario(), dataBase.getContrasena());
            System.out.println("Bienvenido!");
            Console console = System.console();

            if (console == null) {
                System.err.println("La consola no está disponible");
                System.exit(1);
            }

            String usuario = console.readLine("Ingrese su usuario (nombre): ");
            char[] passwordArray = console.readPassword("Ingrese su contraseña: ");
            String password = new String(passwordArray);

            List<User> inicioTrueOrFalse = loginService.iniciarSesion(usuario, password, conexion);
            DataBase.clearConsole();
            
            if (!inicioTrueOrFalse.isEmpty()) {
                UserController userController = new UserController();
                if (inicioTrueOrFalse.get(0).isEsAdmin()){
                    userController.UserControllerA();
                    return true;
                } else if (inicioTrueOrFalse.get(0).isEsSuperAdmin()){
                    userController.UserControllerT();
                    return true;
                } else if (inicioTrueOrFalse.get(0).isEsUsuario()){
                    userController.UserControllerU();
                    return true;
                } else {
                    System.out.println("No tiene acceso a este programa. Pida mas informacion al super administrador");
                    return false;
                }
            } else return false;
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            return false;
        }
    }
}
