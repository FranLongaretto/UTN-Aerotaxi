package Flota;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

public abstract class Flota {
    private HashSet<LocalDate> fechas;
    private String capacidad_combustible = "10000";
    private float costoServicio = 150f;
    private int cantMaxPasajeros = 100;
    private static int pasajerosAbordo = 0;
    private float velMax = 300f;
    private TipoPropulsion propulsion = TipoPropulsion.HELICE;
    private int tarifaFija;
    private UUID numeroAvion = UUID.randomUUID();
    public Flota() {
    }

    public Flota(String capacidad_combustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion, int cantPasajeros) {
        this.capacidad_combustible = capacidad_combustible;
        this.costoServicio = costoServicio;
        this.cantMaxPasajeros = cantMaxPasajeros;
        this.velMax = velMax;
        this.propulsion = propulsion;
        this.numeroAvion = UUID.randomUUID();
        this.pasajerosAbordo = getPasajerosAbordo() + cantPasajeros;
    }
    public int getPasajerosAbordo() {
        return pasajerosAbordo;
    }
    public void setPasajerosAbordo(int pasajerosAbordo) {
        this.pasajerosAbordo = pasajerosAbordo;
    }

    public UUID getNumeroAvion() {
        return numeroAvion;
    }

    public void setNumeroAvion(UUID numeroAvion) {
        this.numeroAvion = numeroAvion;
    }

    public String getCapacidad_combustible() {
        return capacidad_combustible;
    }

    public void setCapacidad_combustible(String capacidad_combustible) {
        this.capacidad_combustible = capacidad_combustible;
    }

    public HashSet<LocalDate> getFechas() {
        return fechas;
    }
    public void setFechas(HashSet<LocalDate> fechas) {
        this.fechas = fechas;
    }
    public void addDate(LocalDate date){
        this.fechas.add(date);
    }
    public void removeDate(LocalDate date){
        this.fechas.remove(date);
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

    public void setTarifaFija(int tarifaFija) {
        this.tarifaFija = tarifaFija;
    }

    public int getTarifaFija() {
        return tarifaFija;
    }

    @Override
    public String toString() {
        return "----------------------\n" +
                "\n\t---Numero de Avion--- = " + "'"+numeroAvion+"'" +
                "\nCapacidad de combustible = " + capacidad_combustible +
                "\nCosto de servicio = " + costoServicio +
                "\nCantidad maxima de pasajeros = " + cantMaxPasajeros +
                "\nCantidad de pasajeros a bordo = " + pasajerosAbordo +
                "\nVelocidad maxima = " + velMax +
                "\nPropulsion = " + propulsion +
                "\nTarifa Fija = " + tarifaFija;
    }
}