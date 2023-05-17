package controller;

import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.net.Socket;
import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import database.DataBase;
import model.Login;
import model.Productos;
import model.User;
import service.ProductosService;
import service.ProductosServiceImpl;
import service.UserService;
import service.UserServiceImpl;

public class UserController {
    DataBase dataBase = new DataBase();

    public void UserControllerT() throws ParseException {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsuario(), dataBase.getContrasena());
            System.out.println("Bienvenido!. Perfil: SUPER_ADMIN");

            List<User> usuarios = new ArrayList<>();
            UserService userService = new UserServiceImpl();
            Scanner scanner = new Scanner(System.in);
            try {
                Statement statement = conexion.createStatement();
                ResultSet resultados = statement.executeQuery("SELECT * FROM usuarios");
                while (resultados.next()) {
                    User user = new User(resultados.getInt("id"), resultados.getString("nombre"),
                            resultados.getString("apellido"), resultados.getString("password"),
                            resultados.getTimestamp("fechaDeCreacion"), resultados.getTimestamp("ultimaEdicion"));
                    usuarios.add(user);
                    // System.out.println(resultados.getString("id") + ", " +
                    // resultados.getString("nombre"));
                }
            } catch (SQLException e) {
                System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            }

            while (true) {
                try {
                    System.out.println("""

                            *************************************************
                            ********** Bienvenido a TerraProgram ************
                            *************************************************
                                           ¿Que desea hacer?
                            ???????????????????????¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿

                            (Digite el numero de la opcion la cual desea hacer)

                            0. Ver todos los usuarios.
                            1. Registrar un nuevo usuario.
                            2. Editar un usuario.
                            3. Eliminar un usuario.
                            4. Eliminar ¡¡TODOS!! los usuarios (Verificacion antes)
                            999. Salir.

                            """);
                    if (scanner.hasNextInt()) {
                        int peticion = scanner.nextInt();

                        if (peticion == 0) {
                            DataBase.clearConsole();
                            if (usuarios.isEmpty())
                                System.out.println("No hay usuarios para mostrar");
                            else {
                                System.out.println("Estos son todos los usuarios registrados: ");
                                userService.obtenerTodo(usuarios).forEach(dato -> {
                                    System.out
                                            .println("Id: " + dato.getId() + ". Nombres y apellidos: "
                                                    + dato.getNombres()
                                                    + " "
                                                    + dato.getApellidos()
                                                    + ". Password: " + dato.getPassword() + ". Fecha de creacion: "
                                                    + dato.getFechaDeCreacion() + ". Ultima edicion: "
                                                    + dato.getUltimaEdicion());
                                });
                            }

                        } else if (peticion == 1) {
                            DataBase.clearConsole();
                            scanner.nextLine();
                            System.out.println("Ingrese el id: ");
                            String id = scanner.nextLine();
                            // scanner.nextLine();
                            if (!(id.length() >= 1) || !User.esNumero(id)) {
                                while (true) {
                                    System.out.println("El id es incorrecto. Por favor ingrese un id valido");
                                    System.out.println("Ingrese el id:");
                                    id = scanner.nextLine();
                                    if (id.length() >= 1 && User.esNumero(id))
                                        break;
                                }
                            }
                            if (userService.buscaUserPorId(Integer.parseInt(id), usuarios) == null) {
                                System.out.println("Ingrese los nombres: ");
                                String nombre = scanner.nextLine().toUpperCase();
                                if (!(nombre.length() >= 1)) {
                                    while (true) {
                                        System.out.println("El nombre esta vacio. Por favor ingrese un nombre valido");
                                        System.out.println("Ingrese los nombres: ");
                                        nombre = scanner.nextLine().toUpperCase();
                                        if (nombre.length() >= 1)
                                            break;
                                    }
                                }
                                System.out.println("Ingrese la contraseña (min 8 caracteres):");
                                String password = scanner.nextLine().toUpperCase();
                                if (!(password.length() >= 8)) {
                                    while (true) {
                                        System.out.println(
                                                "La contraseña es incorrecta. Por favor ingrese una contraseña valida");
                                        System.out.println("Ingrese la contraseña (min 8 caracteres):");
                                        password = scanner.nextLine().toUpperCase();
                                        if (password.length() >= 8)
                                            break;
                                    }
                                }
                                System.out.println("Ingrese los apellidos:");
                                String apellido = scanner.nextLine().toUpperCase();
                                if (!(apellido.length() >= 1)) {
                                    while (true) {
                                        System.out.println(
                                                "El apellido esta vacio. Por favor ingrese un apellido valido");
                                        System.out.println("Ingrese los apellidos:");
                                        apellido = scanner.nextLine().toUpperCase();
                                        if (apellido.length() >= 1)
                                            break;
                                    }
                                }
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                User user = new User(Integer.parseInt(id), nombre, apellido, password,
                                        timestamp, timestamp);
                                System.out.println("Seleccione el rol del usuario: 1. ADMIN 2. USUARIO 3. SUPER_ADMIN");
                                String rolRes = scanner.nextLine();
                                if (!(rolRes.length() >= 1)) {
                                    while (true) {
                                        System.out.println("El rol no puede estar vacio");
                                        System.out.println(
                                                "Seleccione el rol del usuario: 1. ADMIN 2. USUARIO 3. SUPER_ADMIN");
                                        rolRes = scanner.nextLine();
                                        if (rolRes.length() >= 1)
                                            break;
                                    }
                                } else {
                                    if (rolRes.equals("1")) {
                                        user.setEsAdmin(true);
                                    } else if (rolRes.equals("2")) {
                                        user.setEsUsuario(true);
                                    } else if (rolRes.equals("3")) {
                                        user.setEsSuperAdmin(true);
                                    } else {
                                        System.out.println("Ese rol no existe");
                                    }
                                }
                                // scanner.close();
                                System.out.println(userService.crearUser(user, usuarios, conexion));

                            } else {
                                System.out.println("Ese id ya existe");
                                continue;
                            }

                        } else if (peticion == 2) {
                            DataBase.clearConsole();
                            if (usuarios.isEmpty()) {
                                System.out.println("No usuarios para editar");
                            } else {
                                System.out.println("Ingrese el id del usuario que desea editar (listado disponible):");
                                userService.obtenerTodo(usuarios).forEach(dato -> {
                                    System.out
                                            .println("Id: " + dato.getId() + ". Nombres y apellidos: "
                                                    + dato.getNombres()
                                                    + " "
                                                    + dato.getApellidos()
                                                    + ". Password: " + dato.getPassword() + ". Fecha de creacion: "
                                                    + dato.getFechaDeCreacion() + ". Ultima edicion: "
                                                    + dato.getUltimaEdicion());
                                });
                                int id = scanner.nextInt();
                                User usuarioEncontrado = userService.buscaUserPorId(id, usuarios);
                                if (usuarioEncontrado != null) {
                                    scanner.nextLine();
                                    System.out.println("Ingrese los nombres: ");
                                    String nombre = scanner.nextLine().toUpperCase();
                                    if (!(nombre.length() >= 1)) {
                                        while (true) {
                                            System.out.println(
                                                    "El nombre esta vacio. Por favor ingrese un nombre valido");
                                            System.out.println("Ingrese los nombres: ");
                                            nombre = scanner.nextLine().toUpperCase();
                                            if (nombre.length() >= 1)
                                                break;
                                        }
                                    }
                                    System.out.println("Ingrese la contraseña (min 8 caracteres):");
                                    String password = scanner.nextLine().toUpperCase();
                                    if (!(password.length() >= 8)) {
                                        while (true) {
                                            System.out.println(
                                                    "La contraseña es incorrecta. Por favor ingrese una contraseña valida");
                                            System.out.println("Ingrese la contraseña (min 8 caracteres):");
                                            password = scanner.nextLine().toUpperCase();
                                            if (password.length() >= 8)
                                                break;
                                        }
                                    }
                                    System.out.println("Ingrese los apellidos:");
                                    String apellido = scanner.nextLine().toUpperCase();
                                    if (!(apellido.length() >= 1)) {
                                        while (true) {
                                            System.out.println(
                                                    "El apellido esta vacio. Por favor ingrese un apellido valido");
                                            System.out.println("Ingrese los apellidos:");
                                            apellido = scanner.nextLine().toUpperCase();
                                            if (apellido.length() >= 1)
                                                break;
                                        }
                                    }
                                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                    // scanner.close();

                                    User user = new User(id, nombre, apellido, password, timestamp,
                                            timestamp);
                                    System.out.println(
                                            "Seleccione el rol del usuario: 1. ADMIN 2. USUARIO 3. SUPER_ADMIN");
                                    String rolRes = scanner.nextLine();
                                    if (!(rolRes.length() >= 1)) {
                                        while (true) {
                                            System.out.println("El rol no puede estar vacio");
                                            System.out.println(
                                                    "Seleccione el rol del usuario: 1. ADMIN 2. USUARIO 3. SUPER_ADMIN");
                                            rolRes = scanner.nextLine();
                                            if (rolRes.length() >= 1)
                                                break;
                                        }
                                    } else {
                                        if (rolRes.equals("1")) {
                                            user.setEsAdmin(true);
                                        } else if (rolRes.equals("2")) {
                                            user.setEsUsuario(true);
                                        } else if (rolRes.equals("3")) {
                                            user.setEsSuperAdmin(true);
                                        } else {
                                            System.out.println("Ese rol no existe");
                                        }
                                    }
                                    System.out.println(userService.editaUser(user, usuarios, conexion));
                                } else {
                                    System.out.println("El id ingresado es incorrecto");
                                }
                            }

                        } else if (peticion == 3) {
                            DataBase.clearConsole();
                            if (usuarios.isEmpty()) {
                                System.out.println("No hay usuarios para eliminar");
                                continue;
                            } else {
                                System.out
                                        .println("Ingrese el id del usuario que desea eliminar (listado disponible):");
                                userService.obtenerTodo(usuarios).forEach(dato -> {
                                    System.out
                                            .println("Id: " + dato.getId() + ". Nombres y apellidos: "
                                                    + dato.getNombres()
                                                    + " "
                                                    + dato.getApellidos()
                                                    + ". Password: " + dato.getPassword() + ". Fecha de creacion: "
                                                    + dato.getFechaDeCreacion() + ". Ultima edicion: "
                                                    + dato.getUltimaEdicion());
                                });
                            }
                            int id = scanner.nextInt();
                            User usuarioEncontrado = userService.buscaUserPorId(id, usuarios);
                            if (usuarioEncontrado != null) {
                                System.out.println(userService.eliminarUserPorId(id, usuarios, conexion));
                            } else {
                                System.out.println("El id ingresado es incorrecto");
                            }
                        } else if (peticion == 4) {
                            DataBase.clearConsole();
                            if (usuarios.isEmpty()) {
                                System.out.println("No hay usuarios para eliminar");
                                continue;
                            } else {
                                System.out.println(
                                        "Esta a punto de eliminar ¡TODOS LOS USUARIOS!. ¿Desea seguir? <(1. SI, Cualquier otro numero: NO)>");
                                int response = scanner.nextInt();
                                if (response == 1) {
                                    System.out.println(userService.eliminarTodo(usuarios, conexion));
                                } else {
                                    continue;
                                }
                            }
                        } else if (peticion == 999) {
                            DataBase.clearConsole();
                            System.out.println("¡¡Hasta pronto...!!");
                            scanner.close();
                            conexion.close();
                            break;
                        } else {
                            System.out.println("Esa opcion ¡¡No existe!!");
                            continue;
                        }
                    } else {
                        System.err.println("Entrada no válida.");
                        scanner.nextLine();
                    }

                } catch (Exception e) {
                    System.err.println("Fatal error: " + e);
                    break;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }

    public void UserControllerU() throws ParseException {
        Connection conexion = null;
        ProductosService productosService = new ProductosServiceImpl();
        try {
            conexion = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsuario(), dataBase.getContrasena());
            System.out.println("Bienvenido!. Perfil: USUARIO");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("""

                        *************************************************
                        ********** Bienvenido a TerraProgram ************
                        *************************************************
                                       ¿Que desea hacer?
                        ???????????????????????¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿

                        (Digite el numero de la opcion la cual desea hacer)

                        0. Ver productos.
                        1. Vender producto(s).
                        999. Salir

                        """);
                String peticion = scanner.nextLine();
                if (peticion.equals("0")) {
                    List<Productos> productos = productosService.verProductos(conexion);
                    if (productos.isEmpty()) {
                        System.out.println("No hay productos para ver");
                    } else {
                        System.out.println("Productos disponibles:");
                        productos.forEach(dato -> {
                            System.out.println(
                                    "Id: " + dato.getId() + ". Nombre: " + dato.getNombreProducto()
                                            + ". Cantidad Restantes: "
                                            + dato.getCantidad()
                                            + ". Precio de venta: "
                                            + dato.getPrecioDeVenta());
                        });
                    }
                } else if (peticion.equals("1")) {
                    try {
                        while (true) {
                            List<Productos> productos = productosService.verProductos(conexion);
                            if (productos.isEmpty()) {
                                System.out.println("No hay productos para vender");
                                break;
                            } else {
                                DataBase.clearConsole();
                                System.out.println("Productos disponibles:");
                                productos.forEach(dato -> {
                                    System.out.println(
                                            "Id: " + dato.getId() + ". Nombre: " + dato.getNombreProducto()
                                                    + ". Cantidad Restantes: "
                                                    + dato.getCantidad()
                                                    + ". Precio de venta: "
                                                    + dato.getPrecioDeVenta());
                                });
                                System.out.println("Ingrese el id del producto a vender: ");
                                String id = scanner.nextLine();
                                if (!(id.length() >= 1 || !User.esNumero(id))) {
                                    while (true) {
                                        System.out.println("El id es invalido. Por favor ingrese un numero valido");
                                        System.out.println("Ingrese el id: ");
                                        id = scanner.nextLine();
                                        if (id.length() >= 1 && User.esNumero(id))
                                            break;
                                    }
                                }
                                Productos productos1 = productosService.buscarProducto(Integer.parseInt(id), conexion);
                                if (productos1 != null) {
                                    System.out.println("Escoja la cantidad a vender (Existencias: "
                                            + productos1.getCantidad() + "): ");
                                    String cant = scanner.nextLine();
                                    if (!(cant.length() >= 1) || !User.esNumero(cant)) {
                                        while (true) {
                                            System.out.println(
                                                    "La cantidad es invalida. Por favor ingrese un numero valido");
                                            System.out.println("Escoja la cantidad a vender: ");
                                            cant = scanner.nextLine();
                                            if (cant.length() >= 1 && User.esNumero(cant))
                                                break;
                                        }
                                    }
                                    Integer cantidad = Integer.parseInt(cant);
                                    if (cantidad <= productos1.getCantidad()) {
                                        productos1.setCantidad(cantidad);
                                        Boolean exito = productosService.venderProducto(productos1, conexion);
                                        if (exito) {
                                            System.out.println("Venta exitosa");
                                        } else {
                                            System.out.println(
                                                    "Algo salio mal: la venta no se realizo por un error interno");
                                        }
                                    } else {
                                        System.out.println("ERROR: La existencia es: " + productos1.getCantidad()
                                                + " y la digitada es: " + cantidad);
                                    }
                                    System.out.println("¿Seguir vendiendo?: CUALQUIER CARACTER: SI, 0. NO");
                                    String peti = scanner.nextLine();
                                    if (peti.equals("0"))
                                        break;
                                } else {
                                    System.out.println("Ese producto no existe");
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                } else if (peticion.equals("999")) {
                    DataBase.clearConsole();
                    System.out.println("Hasta pronto...");
                    scanner.close();
                    break;
                } else {
                    DataBase.clearConsole();
                    System.out.println("Esa opcion no existe");
                    continue;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }

    }

    public void UserControllerA() throws ParseException {
        ProductosService productosService = new ProductosServiceImpl();
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(dataBase.getUrl(), dataBase.getUsuario(), dataBase.getContrasena());
            System.out.println("Bienvenido!. Perfil: ADMIN");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("""

                        *************************************************
                        ********** Bienvenido a TerraProgram ************
                        *************************************************
                                       ¿Que desea hacer?
                        ???????????????????????¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿¿

                        (Digite el numero de la opcion la cual desea hacer)

                        0. Ver productos.
                        1. Registrar producto(s).
                        999. Salir.

                        """);
                String peticion = scanner.nextLine();
                if (peticion.equals("0")) {
                    List<Productos> productos = productosService.verProductos(conexion);
                    if (productos.isEmpty()) {
                        System.out.println("No hay ningun producto");
                    } else {
                        System.out.println("Productos registrados: ");
                        productos.forEach(dato -> {
                            System.out.println(
                                    "Id: " + dato.getId() + ". Nombre: " + dato.getNombreProducto() + ". Cantidad: "
                                            + dato.getCantidad()
                                            + ". Precio de compra: " + dato.getPrecioDeCompra()
                                            + ". Precio de venta (unidad): "
                                            + dato.getPrecioDeVenta() + ". Ganacias: " + dato.getGanacias()
                                            + ". Perdidas: " + dato.getPerdidas());
                        });
                    }
                } else if (peticion.equals("1")) {
                    while (true) {
                        System.out.println("Ingrese el id: ");
                        String id = scanner.nextLine();
                        if (!(id.length() >= 1)) {
                            while (true) {
                                System.out.println("El id esta vacio. Por favor ingrese un id valido");
                                System.out.println("Ingrese el id: ");
                                id = scanner.nextLine();
                                if (id.length() >= 1)
                                    break;
                            }
                        }
                        Productos producto = productosService.buscarProducto(Integer.parseInt(id), conexion);
                        if (producto != null) {
                            System.out.println("El id indicado ya existe");
                            break;
                        }
                        System.out.println("Ingrese el nombre del producto: ");
                        String nombre = scanner.nextLine().toUpperCase();
                        if (!(nombre.length() >= 1)) {
                            while (true) {
                                System.out.println("El nombre esta vacio. Por favor ingrese un nombre valido");
                                System.out.println("Ingrese el id: ");
                                nombre = scanner.nextLine();
                                if (nombre.length() >= 1)
                                    break;
                            }
                        }
                        System.out.println("Ingrese la cantidad: ");
                        String cantidad = scanner.nextLine();
                        if (!(cantidad.length() >= 1 || !User.esNumero(cantidad))) {
                            while (true) {
                                System.out
                                        .println(
                                                "La cantidad es invalida. Por favor ingrese una cantidad valida");
                                System.out.println("Ingrese la cantidad: ");
                                cantidad = scanner.nextLine();
                                if (cantidad.length() >= 1 && User.esNumero(cantidad))
                                    break;
                            }
                        }
                        try {
                            System.out.println("Ingrese el precio de compra del producto: ");
                            String precioDeCompra = scanner.nextLine();
                            if (!(precioDeCompra.length() >= 1 || !User.esNumero(precioDeCompra))) {
                                while (true) {
                                    System.out
                                            .println(
                                                    "El precio de compra es invalido. Por favor ingrese un precio valido");
                                    System.out.println("Ingrese el precio de compra del producto: ");
                                    precioDeCompra = scanner.nextLine();
                                    if (precioDeCompra.length() >= 1 && User.esNumero(precioDeCompra))
                                        break;
                                }
                            }
                            BigDecimal precioDeCompraB = new BigDecimal(precioDeCompra);
                            System.out.println("Ingrese el precio de venta del producto (unidad): ");
                            String precioDeVenta = scanner.nextLine();
                            if (!(precioDeVenta.length() >= 1 || !User.esNumero(precioDeVenta))) {
                                while (true) {
                                    System.out
                                            .println(
                                                    "El precio de venta es invalido. Por favor ingrese un precio valido");
                                    System.out.println("Ingrese el precio de venta del producto (unidad): ");
                                    precioDeVenta = scanner.nextLine();
                                    if (precioDeVenta.length() >= 1 && User.esNumero(precioDeVenta))
                                        break;
                                }
                            }
                            BigDecimal precioDeVentaB = new BigDecimal(precioDeVenta);
                            Productos productos = new Productos(Integer.parseInt(id), nombre, precioDeCompraB,
                                    precioDeVentaB, Integer.parseInt(cantidad));
                            System.out.println(productosService.grabarProducto(productos, conexion));
                            System.out.println("¿Desea registrar otro producto?: 1. SI, CUALQUIER CARACTER: NO");
                            String peti = scanner.nextLine();
                            if (peti.equals("1")) {
                                continue;
                            } else {
                                break;
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }

                } else if (peticion.equals("999")) {
                    DataBase.clearConsole();
                    System.out.println("Hasta pronto...");
                    scanner.close();
                    break;
                } else {
                    DataBase.clearConsole();
                    System.out.println("Esa opcion no existe");
                    continue;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
        }
    }
}
