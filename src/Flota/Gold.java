package Flota;

import Interfaces.Archivos;
import Ticket.Ticket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Gold extends Flota implements Archivos<Gold> {
    private boolean wifi;
    private boolean catering;
    public Gold() {
    }

    public Gold(String capCombustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion) {
        super(capCombustible, costoServicio, cantMaxPasajeros, velMax, propulsion);
        setTarifaFija(6000);
        wifi = true;
        catering = true;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isCatering() {
        return catering;
    }

    public void setCatering(boolean catering) {
        this.catering = catering;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nWifi = " + wifi +
                "\nCatering = " + catering +
                "\n";
    }

    @Override
    public List<Gold> leerArchivo() {
        List<Gold> listaAvionesGold = null;
        File fileGold = new File("AvionesGold.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if (fileGold.exists() && fileGold.canRead()) {
            try {
                listaAvionesGold = Arrays.asList(mapper.readValue(fileGold, Gold[].class)); //Convierto Json array a list de Aviones Gold
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.printf("El archivo no existe\n");
        }
        return listaAvionesGold;
    }

    @Override
    public void agregarEnArchivo() {
        File fileGold = new File("AvionesGold.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //Pregunto si el archivo existe
            if (fileGold.createNewFile()) {
                ArrayList<Gold> GoldArrayList = new ArrayList<>();
                GoldArrayList.add(this);
                mapper.writeValue(fileGold, GoldArrayList);
            } else {
                ArrayList<Gold> GoldArrayList = new ArrayList<Gold>(leerArchivo());
                GoldArrayList.add(this);
                mapper.writeValue(fileGold, GoldArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mostrarArchivo() {
        File fileGold = new File("AvionesGold.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if (fileGold.exists()) {
            try {
                List<Gold> listaAvionesGold = Arrays.asList(mapper.readValue(fileGold, Gold[].class)); //Convierto Json array a list de objetos
                listaAvionesGold.stream().forEach(obj -> System.out.println(obj)); // Mostrar lista de Aviones Gold
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        } else {
            System.out.println("El archivo esta vacio");
        }
    }
    public HashSet mostrarAvionesDisponibles(Ticket tk) {
        File fileGold = new File("AvionesGold.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        HashSet<Integer> numerosVuelos = new HashSet<>();
        int i = 0;
        LocalDate fechaTk = tk.getFecha();
        if(fileGold.exists()) {
            try{
                List<Gold> listaAvionesGold = Arrays.asList(mapper.readValue(fileGold, Gold[].class)); //Convierto Json array a list de objetos
                for (Gold avion: listaAvionesGold) {
                    if (avion.getFechas().contains(fechaTk) && avion.getCantMaxPasajeros() >= tk.getPasajeros()){
                        System.out.println(i + ":" + avion);
                        i++;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        }else{
            System.out.println("El archivo esta vacio");
        }
        return numerosVuelos;
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