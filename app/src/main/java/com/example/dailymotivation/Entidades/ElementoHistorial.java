package com.example.dailymotivation.Entidades;

public class ElementoHistorial {

    private String fecha;
    private String distancia;
    private String tiempo;
    private String tipo;
    public ElementoHistorial(String fecha, String distancia, String tiempo, String tipo) {
        this.fecha = fecha;
        this.distancia = distancia;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }




}
