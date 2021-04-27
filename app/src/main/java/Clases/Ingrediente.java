package Clases;

import Enumeracion.unidades;

public class Ingrediente {
    String nombre;
    float precio;
    int cantidad;
    unidades unidad;


    private Ingrediente() {}

    private Ingrediente(String nombre, float precio, int cantidad, unidades unidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public unidades getUnidad() {
        return unidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setUnidad(unidades unidad) {
        this.unidad = unidad;
    }
}