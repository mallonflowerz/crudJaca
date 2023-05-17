
import controller.LoginController;
import controller.UserController;

public class App {

    public static void main(String[] args) throws Exception {
        LoginController loginController = new LoginController();
        loginController.InicioSesion();
    }
}
