package Clases;

import Enumeracion.unidades;

public class Recetas {
    String nombre;
    String descripcion;


    private Recetas() {}

    private Recetas(String nombre,String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion(){ return this.descripcion;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion=descripcion;
    }

}