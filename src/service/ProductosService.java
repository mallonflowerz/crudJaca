package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.Productos;

public interface ProductosService {

    boolean venderProducto(Productos productos, Connection connection) throws SQLException;

    Productos buscarProducto(int id, Connection conexion) throws SQLException;
    
    List<Productos> verProductos(Connection conexion) throws SQLException;

    String grabarProducto(Productos productos, Connection conexion) throws SQLException;
}
