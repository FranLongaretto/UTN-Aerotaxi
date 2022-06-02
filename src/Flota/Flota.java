package Flota;

public class Flota {
    private String capacidad_combustible = "10000";
    private float costoServicio = 150f;
    private int cantMaxPasajeros = 100;
    private float velMax = 300f;
    private TipoPropulsion propulsion = TipoPropulsion.HELICE;

    public Flota() {
    }

    public String getCapacidad_combustible() {
        return capacidad_combustible;
    }

    public void setCapacidad_combustible(String capacidad_combustible) {
        this.capacidad_combustible = capacidad_combustible;
    }

    public float getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(float costoServicio) {
        this.costoServicio = costoServicio;
    }

    public int getCantMaxPasajeros() {
        return cantMaxPasajeros;
    }

    public void setCantMaxPasajeros(int cantMaxPasajeros) {
        this.cantMaxPasajeros = cantMaxPasajeros;
    }

    public float getVelMax() {
        return velMax;
    }

    public void setVelMax(float velMax) {
        this.velMax = velMax;
    }

    public TipoPropulsion getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(TipoPropulsion propulsion) {
        this.propulsion = propulsion;
    }
}