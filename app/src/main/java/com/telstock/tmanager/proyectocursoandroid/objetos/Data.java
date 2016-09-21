package com.telstock.tmanager.proyectocursoandroid.objetos;

import java.util.List;

/**
 * Created by usr_micro9 on 1/08/16.
 */
public class Data {
    @com.google.gson.annotations.SerializedName("data")
    private List<Cancion> cancion;
    @com.google.gson.annotations.SerializedName("total")
    private int total;
    @com.google.gson.annotations.SerializedName("next")
    private String next;

    public List<Cancion> getCancion() {
        return cancion;
    }

    public void setCancion(List<Cancion> cancion) {
        this.cancion = cancion;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
