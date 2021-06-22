package Clases;

import java.util.Date;

public class Menu {
    private Date fecha;
    private Recetas receta;
    private boolean cena;

    public Menu() {
    }

    public Menu(Date fecha, Recetas receta, boolean cena) {
        this.fecha = fecha;
        this.receta = receta;
        this.cena = cena;
    }

    public Date getFecha() {
        return fecha;
    }

    public Recetas getReceta() {
        return receta;
    }

    public boolean isCena() {
        return cena;
    }

}
