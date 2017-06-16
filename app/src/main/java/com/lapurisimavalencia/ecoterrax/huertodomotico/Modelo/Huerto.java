package com.lapurisimavalencia.ecoterrax.huertodomotico.Modelo;

/**
 * Created by administrador on 16/06/17.
 */

public class Huerto {

    private int id;
    private String nombre;
    private String descripcion;
    private String localizacion;
    private int tempAmb;
    private int humAmb;
    private int humTierra;
    private int totalRiegos;

    public Huerto(){

    }

    public Huerto(int id, String nombre, String descripcion, String localizacion, int tempAmb, int humAmb, int humTierra, int totalRiegos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.tempAmb = tempAmb;
        this.humAmb = humAmb;
        this.humTierra = humTierra;
        this.totalRiegos = totalRiegos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public int getTempAmb() {
        return tempAmb;
    }

    public void setTempAmb(int tempAmb) {
        this.tempAmb = tempAmb;
    }

    public int getHumAmb() {
        return humAmb;
    }

    public void setHumAmb(int humAmb) {
        this.humAmb = humAmb;
    }

    public int getHumTierra() {
        return humTierra;
    }

    public void setHumTierra(int humTierra) {
        this.humTierra = humTierra;
    }

    public int getTotalRiegos() {
        return totalRiegos;
    }

    public void setTotalRiegos(int totalRiegos) {
        this.totalRiegos = totalRiegos;
    }
}
