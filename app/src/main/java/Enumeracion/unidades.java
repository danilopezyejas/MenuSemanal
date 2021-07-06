package Enumeracion;

public enum unidades {
    GR("gr"),
    Kg("kg"),
    Ml("ml"),
    L("l"),
    CD("Cdas."),
    CDTA("Cditas."),
    Unidad("Un.");

    private final String unidad;

    unidades(String unidad){
        this.unidad = unidad;
    }

    @Override public String toString(){
        return unidad;
    }
}