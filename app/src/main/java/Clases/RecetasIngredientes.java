package Clases;

public class RecetasIngredientes {
    String nombreReceta;
    String nombreIngrediente;
    int cantidad;
    String unidad;

    public RecetasIngredientes() {
    }

    public RecetasIngredientes(String receta, String ingrediente, int cantidad, String unidad) {

        this.nombreReceta = receta;
        this.nombreIngrediente = ingrediente;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    public String getNombreReceta() {
        return this.nombreReceta;
    }

    public String getNombreIngrediente() {
        return this.nombreIngrediente;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public String getUnidad() {
        return this.unidad;
    }

    public void setNombreReceta(String nombre) {
        this.nombreReceta = nombre;
    }

    public void setNombreIngrediente(String nombre) {
        this.nombreIngrediente = nombre;
    }
}
