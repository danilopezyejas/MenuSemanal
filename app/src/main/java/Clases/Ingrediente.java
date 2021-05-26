package Clases;
//
//import Enumeracion.unidades;

public class Ingrediente {
    String id;
    String nombre;
    float precio;
    int cantidad;
    String unidad;


    public Ingrediente() {}

    public Ingrediente(String id, String nombre, float precio, int cantidad, String unidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getId() {
        return id;
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

    public String getUnidad() {
        return unidad;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}