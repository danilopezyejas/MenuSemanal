package Clases;
//
//import Enumeracion.unidades;

import Enumeracion.unidades;

public class Ingrediente {
    String id;
    String nombre;
    float precio;
    int cantidad;
    String unidad;
    boolean sel;


    public Ingrediente() {}

    public Ingrediente(String id, String nombre, float precio, int cantidad, String unidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.unidad = unidad;
        this.sel = false;
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

    public boolean getSel (){return this.sel;}

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

    public void setSel (boolean s ){ this.sel=s;}
//
//    public boolean setCantidad(int cantidad, String unidad){
//        if (unidad.equals(this.unidad)){
//            this.cantidad= cantidad;
//            return true;
//        }else
//            if(this.unidad.equals(unidades.Ml.toString()) && unidad.equals(unidades.L.toString())){
//                this.cantidad += cantidad*1000;
//                return true;
//            }else
//                  if(this.unidad
//    }

    public void reasignaMenorUnidad(){
        if (unidad.equals(unidades.Kg.toString())){
            cantidad=cantidad*100;
            unidad = unidades.GR.toString();
        }else
            if(unidad.equals(unidades.L.toString())){
                cantidad=cantidad*1000;
                unidad=unidades.Ml.toString();
            }
    }


}