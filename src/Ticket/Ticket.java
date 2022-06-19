package Ticket;

import Interfaces.Archivos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Ticket implements Archivos<Ticket> {
    private LocalDate fecha;
    private Ciudad origen;
    private Ciudad destino;
    private int pasajeros;
    private double precio;
    private int distancia;
    private String usuarioDni;
    private boolean cancelarTicket;
    private UUID numeroDeAvion;

    public Ticket() {
    }

    public Ticket(LocalDate fecha, Ciudad origen, Ciudad destino, int pasajeros, String usuarioDni, String numeroDeAvion) throws IOException {
        this.fecha = fecha;
        this.origen = origen;
        this.destino = destino;
        this.pasajeros = pasajeros;
        this.numeroDeAvion = UUID.randomUUID();
        this.usuarioDni = usuarioDni;
        this.setDistnacia();
        this.setPrecio();
        this.cancelarTicket = false;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }

    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public void setUsuarioDni(String usuarioDni) {
        this.usuarioDni = usuarioDni;
    }

    public void setPasajeros(int pasajeros) {
        this.pasajeros = pasajeros;
    }

    public boolean isCancelarTicket() {
        return cancelarTicket;
    }

    public String getUsuarioDni() {
        return usuarioDni;
    }

    public boolean getCancelarTicket() {
        return this.cancelarTicket;
    }

    public void setCancelarTicket(boolean cancelarTicket) {
        this.cancelarTicket = cancelarTicket;
    }

    public UUID getNumeroDeAvion() {
        return numeroDeAvion;
    }

    public void setNumeroDeAvion(UUID numeroDeAvion) {
        this.numeroDeAvion = numeroDeAvion;
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

    @Override
    public String toString() {
        return "----------------------\n" +
                "\nFecha = " + fecha +
                "\nOrigen = " + origen +
                "\nDestino = " + destino +
                "\nPasajeros = " + pasajeros +
                "\nPrecio = " + precio +
                "\nDistancia = " + distancia +
                "\nDNI del pasajero = " + usuarioDni +
                "\nCancelar Ticket = " + cancelarTicket +
                "\nNumero de Avion = " + numeroDeAvion +
                "----------------------\n";
    }

    @Override
    public List<Ticket> leerArchivo() {
        List<Ticket> listaTicket = null;
        File fileTickets = new File("Tickets.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if (fileTickets.exists() && fileTickets.canRead()) {
            try {
                listaTicket = Arrays.asList(mapper.readValue(fileTickets, Ticket[].class)); //Convierto Json array a list de Tickets
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("El archivo no existe\n");
        }
        return listaTicket;
    }
    @Override
    public void agregarEnArchivo() {
        File fileTicket = new File("Tickets.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //Pregunto si el archivo existe
            if (fileTicket.createNewFile()) {
                ArrayList<Ticket> TicketArrayList = new ArrayList<>();
                TicketArrayList.add(this);
                mapper.writeValue(fileTicket, TicketArrayList);
            } else {
                ArrayList<Ticket> TicketArrayList = new ArrayList<Ticket>(leerArchivo());
                TicketArrayList.add(this);
                mapper.writeValue(fileTicket, TicketArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void mostrarArchivo() {
        File fileTicket = new File("Tickets.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if (fileTicket.exists()) {
            try {
                List<Ticket> listaTicket = Arrays.asList(mapper.readValue(fileTicket, Ticket[].class)); //Convierto Json array a list de objetos
                listaTicket.stream().forEach(obj -> System.out.println(obj)); // Mostrar lista de Ticket
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        } else {
            System.out.println("El archivo esta vacio");
        }
    }
    @Override
    public void sobreEscribirArchivo(ArrayList listaArch) {
        File fileUsuarios = new File("AvionesGold.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            mapper.writeValue(fileUsuarios, listaArch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
