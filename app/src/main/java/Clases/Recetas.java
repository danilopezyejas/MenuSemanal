package Clases;

import Enumeracion.unidades;

public class Recetas {
    String nombre;
    String descripcion;
    boolean Sel;

    public Recetas(){
        super();
    }

    public Recetas(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.Sel= false;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion(){ return this.descripcion;}
    public boolean getSel(){return this.Sel;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion=descripcion;
    }
    public void setSel (boolean sel){this.Sel = sel;}
}