package Enumeracion;

public enum unidades {
    GR("gr"),
    Kg("kg"),
    Ml("ml"),
    L("l"),
    CD("Cdas."),
    CDTA("Cditas.");

    private String unidad;

    private unidades(String unidad){
        this.unidad = unidad;
    }

    @Override public String toString(){
        return unidad;
    }
}