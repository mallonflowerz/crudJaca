package service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Productos;

public class ProductosServiceImpl implements ProductosService {

    @Override
    public String grabarProducto(Productos productos, Connection conexion) throws SQLException {
        String query = "INSERT INTO productos (id, nombreDelProducto, precioDeCompra, precioDeVenta, cantidad) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, productos.getId());
        ps.setString(2, productos.getNombreProducto());
        BigDecimal precioDeCompra = new BigDecimal(productos.getPrecioDeCompra());
        BigDecimal precioDeVenta = new BigDecimal(productos.getPrecioDeVenta());
        ps.setBigDecimal(3, precioDeCompra);
        ps.setBigDecimal(4, precioDeVenta);
        ps.setInt(5, productos.getCantidad());
        ps.executeUpdate();
        return "Se creo el producto: " + productos.getNombreProducto();
    }

    @Override
    public List<Productos> verProductos(Connection conexion) throws SQLException {
        List<Productos> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        PreparedStatement ps = conexion.prepareStatement(query);
        ResultSet resultados = ps.executeQuery(query);
        while (resultados.next()) {
            Productos productos2 = new Productos(resultados.getInt("id"), resultados.getString("nombreDelProducto"),
                    resultados.getBigDecimal("precioDeCompra"), resultados.getBigDecimal("precioDeVenta"),
                    resultados.getInt("cantidad"));
            productos2.setGanacias(resultados.getBigDecimal("ganancias").toString());
            productos2.setPerdidas(resultados.getBigDecimal("perdidas").toString());
            productos.add(productos2);
        }
        return productos;
    }

    @Override
    public Productos buscarProducto(int id, Connection conexion) throws SQLException {
        String query = "SELECT * FROM productos WHERE id = ?";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, id);
        ResultSet resultados = ps.executeQuery();
        if (resultados.next()) {
            Productos produ = new Productos(resultados.getInt("id"), resultados.getString("nombreDelProducto"),
                    resultados.getBigDecimal("precioDeCompra"),
                    resultados.getBigDecimal("precioDeVenta"), resultados.getInt("cantidad"));
            produ.setGanacias(resultados.getBigDecimal("ganancias").toString());
            produ.setPerdidas(resultados.getBigDecimal("perdidas").toString());
            return produ;
        } else {
            return null;
        }
    }

    @Override
    public boolean venderProducto(Productos productos, Connection connection) throws SQLException {
        Productos productoAntes = buscarProducto(productos.getId(), connection);
        String valorLimpio = productos.getPrecioDeVenta();
        valorLimpio = valorLimpio.replaceAll("[^\\d.]", "");
        BigDecimal valorVenta = new BigDecimal(valorLimpio);
        BigDecimal gananciaActual = new BigDecimal(productos.getGanacias());
        BigDecimal perdidaActual = new BigDecimal(productos.getPerdidas());
        Integer ganancia = (valorVenta.intValueExact() * productos.getCantidad()) + gananciaActual.intValueExact();
        if (ganancia < gananciaActual.intValueExact()) {
            Integer perdida = perdidaActual.intValueExact() - ganancia;
            productos.setPerdidas(perdida.toString());
        } else {
            productos.setGanacias(ganancia.toString());
        }
        Integer nuevaCant = productoAntes.getCantidad() - productos.getCantidad();
        String sql = "UPDATE productos SET cantidad = ?, ganancias = ?, perdidas = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, nuevaCant);
        BigDecimal perdidaNueva = new BigDecimal(productos.getPerdidas());
        BigDecimal gananciaNueva = new BigDecimal(productos.getGanacias());
        ps.setBigDecimal(2, gananciaNueva);
        ps.setBigDecimal(3, perdidaNueva);
        ps.setInt(4, productos.getId());
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            return true;
        } else {
            return false;
        }
    }

}
