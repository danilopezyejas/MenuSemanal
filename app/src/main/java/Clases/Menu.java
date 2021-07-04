package Clases;

import java.util.Date;

public class Menu {
    private long fecha;
    private String receta;
    private String comida;
    private String id;

    public Menu() {
    }

    public Menu(long fecha, String receta, String comida, String id) {
        this.fecha = fecha;
        this.receta = receta;
        this.comida = comida;
        this.id = id;

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

    public String getId() { return  id;}

}
