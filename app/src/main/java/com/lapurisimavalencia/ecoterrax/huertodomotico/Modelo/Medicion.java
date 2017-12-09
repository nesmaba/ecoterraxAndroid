package com.lapurisimavalencia.ecoterrax.huertodomotico.Modelo;

/**
 * Created by administrador on 16/06/17.
 */

public class Medicion {

    private int idHuerto;
    private int tempAmb;
    private int humAmb;
    private int humTierra;
    private String fecha;
    private String hora;
    private String esRegado;

    public Medicion(){

    }

    public Medicion(int idHuerto, String descripcion, String localizacion, int tempAmb, int humAmb, int humTierra, String fecha, String hora, String esRegado) {
        this.idHuerto = idHuerto;
        this.tempAmb = tempAmb;
        this.humAmb = humAmb;
        this.humTierra = humTierra;
        this.fecha = fecha;
        this.hora = hora;
        this.esRegado = esRegado;
    }

    public int getIdHuerto() {
        return idHuerto;
    }

    public void setIdHuerto(int idHuerto) {
        this.idHuerto = idHuerto;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEsRegado() {
        return esRegado;
    }

    public void setEsRegado(String esRegado) {
        this.esRegado = esRegado;
    }
}
