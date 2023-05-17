package model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Productos {
    private int id;
    private String nombreProducto;
    private String precioDeCompra;
    private String precioDeVenta;
    private int cantidad;
    private String ganacias;
    private String perdidas;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
    public String getPrecioDeCompra() {
        return precioDeCompra;
    }
    public void setPrecioDeCompra(String precioDeCompra) {
        this.precioDeCompra = precioDeCompra;
    }
    public String getPrecioDeVenta() {
        return precioDeVenta;
    }
    public void setPrecioDeVenta(String precioDeVenta) {
        this.precioDeVenta = precioDeVenta;
    }

    public Productos(int id, String nombreProducto, BigDecimal precioDeCompra, BigDecimal precioDeVenta, int cantidad) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.precioDeCompra = convertirAPesosColombianos(precioDeCompra);
        this.precioDeVenta = convertirAPesosColombianos(precioDeVenta);
        this.cantidad = cantidad;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public static String convertirAPesosColombianos(BigDecimal valor) {
        // Crear un DecimalFormat para formatear el valor
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setGroupingSeparator(',');
        simbolos.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        // Formatear el valor y retornarlo como una cadena
        return formato.format(valor);
    }
    public String getGanacias() {
        return ganacias;
    }
    public void setGanacias(String ganacias) {
        this.ganacias = ganacias;
    }
    public String getPerdidas() {
        return perdidas;
    }
    public void setPerdidas(String perdidas) {
        this.perdidas = perdidas;
    }
    
}
