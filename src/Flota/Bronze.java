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

public class Bronze extends Flota implements Archivos<Bronze> {
    public Bronze() {
        super();
        setTarifaFija(3000);
    }
    public Bronze(String capCombustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion){
        super(capCombustible, costoServicio, cantMaxPasajeros, velMax, propulsion);
        setTarifaFija(3000);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n";
    }
    @Override
    public List<Bronze> leerArchivo() {
        List<Bronze> listaAvionesBronze = null;
        File fileBronze = new File("AvionesBronze.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileBronze.exists() && fileBronze.canRead()) {
            try {
                listaAvionesBronze = Arrays.asList(mapper.readValue(fileBronze, Bronze[].class)); //Convierto Json array a list de Aviones Bronze
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.printf("El archivo no existe\n");
        }
        return listaAvionesBronze;
    }
    @Override
    public void agregarEnArchivo() {
        File fileBronze = new File("AvionesBronze.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //Pregunto si el archivo existe
            if (fileBronze.createNewFile()) {
                ArrayList<Bronze> BronzeArrayList = new ArrayList<>();
                BronzeArrayList.add(this);
                mapper.writeValue(fileBronze, BronzeArrayList);
            } else {
                ArrayList<Bronze> BronzeArrayList = new ArrayList<Bronze>(leerArchivo());
                BronzeArrayList.add(this);
                mapper.writeValue(fileBronze, BronzeArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void mostrarArchivo() {
        File fileBronze = new File("AvionesBronze.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileBronze.exists()) {
            try{
                List<Bronze> listaAvionesBronze = Arrays.asList(mapper.readValue(fileBronze, Bronze[].class)); //Convierto Json array a list de objetos
                listaAvionesBronze.stream().forEach(obj -> System.out.println(obj)); // Mostrar lista de Aviones Bronze
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        }else{
            System.out.println("El archivo esta vacio");
        }
    }
    public boolean mostrarAvionesDisponibles(Ticket tk) {
        File fileBronze = new File("AvionesBronze.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        HashSet<Integer> numerosVuelos = new HashSet<>();
        boolean validacion = false;
        int i = 0;
        LocalDate fechaTk = tk.getFecha();
        if(fileBronze.exists()) {
            try{
                List<Bronze> listaAvionesBronze = Arrays.asList(mapper.readValue(fileBronze, Bronze[].class)); //Convierto Json array a list de objetos
                for (Bronze avion : listaAvionesBronze){
                    if (avion.getFechas().equals(fechaTk) && avion.getCantMaxPasajeros() >= (tk.getPasajeros() + avion.getPasajerosAbordo())){
                        System.out.println(avion.getNumeroAvion() + ":" + avion);//muestro cada avion con su numero de id
                        validacion = true;
                        //i++;
                    }else{
                        System.out.println("No hay aviones disponibles para la fecha o cantidad de pasajeros seleccionada.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        }else{
            System.out.println("El archivo esta vacio");
        }
        return validacion;
    }
    @Override
    public void sobreEscribirArchivo(ArrayList listaArch) {
        File fileUsuarios = new File("AvionesBronze.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            mapper.writeValue(fileUsuarios, listaArch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public double precioVuelo(Ticket tk){
        return (double) ((tk.getPasajeros() * 3500 ) + (this.getTarifaFija()) + (tk.getDistancia() * this.getCostoServicio() ));
    }
}
