package Ticket;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;

public class Ticket {

    private LocalDate fecha;
    private Ciudad origen;
    private Ciudad destino;
    private int pasajeros;
    private double precio;
    private int distancia;
    private int usuarioDni;
    private boolean cancelarTicket;
    private String numeroDeAvion;


    public Ticket(LocalDate fecha, Ciudad origen, Ciudad destino, int pasajeros, int usuarioDni, String numeroDeAvion) throws IOException {
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.numeroDeAvion = numeroDeAvion;
        this.usuarioDni = usuarioDni;
        this.setDistnacia();
        this.setPrecio();
        this.cancelarTicket = false;
    }

    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }

    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Ciudad getOrigen() {
        return this.origen;
    }

    public Ciudad getDestino() {
        return this.destino;
    }

    public int getPasajeros() {
        return this.pasajeros;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(){
                                                        //CHEQUEAR

    }

    public int getUsuarioDni() {
        return usuarioDni;
    }

    public boolean getCancelarTicket() {
        return this.cancelarTicket;
    }

    public void setCancelarTicket(boolean cancelarTicket) {
        this.cancelarTicket = cancelarTicket;
    }

    public String getNumeroDeAvion() {
        return numeroDeAvion;
    }

    public int getDistancia(){return this.distancia;}

    private void setDistnacia() {

        HashSet<Ciudad> origenDestino = new HashSet<>();
        origenDestino.add(this.destino);
        origenDestino.add(this.origen);

        if (origenDestino.contains(Ciudad.BUENOSAIRES)) {
            if (origenDestino.contains(Ciudad.MONTEVIDEO)) {
                this.distancia = 950;
            } else if (origenDestino.contains(Ciudad.CORDOBA)) {
                this.distancia = 695;
            } else {
                this.distancia = 1400;
            }
        } else if (origenDestino.contains(Ciudad.MONTEVIDEO)) {
            if (origenDestino.contains(Ciudad.CORDOBA)) {
                this.distancia = 1190;
            } else {
                this.distancia = 2100;
            }
        } else {
            this.distancia = 1050;
        }


    }


}
