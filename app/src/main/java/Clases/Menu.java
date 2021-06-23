package Clases;

import java.util.Date;

public class Menu {
    private long fecha;
    private String receta;
    private String comida;

    public Menu() {
    }

    public Menu(long fecha, String receta, String comida) {
        this.fecha = fecha;
        this.receta = receta;
        this.comida = comida;
    }

    public long getFecha() {
        return fecha;
    }

    public String getReceta() {
        return receta;
    }

    public String getComida() {
        return comida;
    }

}
