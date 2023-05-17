package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int id;
    private String nombres;
    private String apellidos;
    private String password;
    private String fechaDeCreacion;
    private String ultimaEdicion;
    private boolean esAdmin;
    private boolean esSuperAdmin;
    private boolean esUsuario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public User(int id, String nombres, String apellidos, String password, Timestamp fechaDeCreacion, Timestamp ultimaEdicion)
            throws ParseException {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.password = password;
        this.fechaDeCreacion = formatearFecha(fechaDeCreacion);
        this.ultimaEdicion = formatearFecha(ultimaEdicion);
    }

    public static String formatearFecha(Timestamp timestamp) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formato.format(timestamp);
        return fechaFormateada;

    }

    public static boolean esNumero(String str){
        return str.matches("\\d+");
    }

    public String getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(String fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public String getUltimaEdicion() {
        return ultimaEdicion;
    }

    public void setUltimaEdicion(String ultimaEdicion) {
        this.ultimaEdicion = ultimaEdicion;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public boolean isEsSuperAdmin() {
        return esSuperAdmin;
    }

    public void setEsSuperAdmin(boolean esSuperAdmin) {
        this.esSuperAdmin = esSuperAdmin;
    }

    public boolean isEsUsuario() {
        return esUsuario;
    }

    public void setEsUsuario(boolean esUsuario) {
        this.esUsuario = esUsuario;
    }


}
