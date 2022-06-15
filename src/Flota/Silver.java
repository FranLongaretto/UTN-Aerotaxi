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

public class Silver extends Flota implements Archivos<Silver> {
    private boolean catering;

    public Silver() {
    }

    public Silver(String capCombustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion,int pasajerosAbordo){
        super(capCombustible, costoServicio, cantMaxPasajeros, velMax, propulsion,pasajerosAbordo);
        setTarifaFija(4000);
        catering = true;
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
                "\nCatering = " + catering +
                "\n";
    }

    @Override
    public List<Silver> leerArchivo() {
        List<Silver> listaAvionesSilver = null;
        File fileSilver = new File("AvionesSilver.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileSilver.exists() && fileSilver.canRead()) {
            try {
                listaAvionesSilver = Arrays.asList(mapper.readValue(fileSilver, Silver[].class)); //Convierto Json array a list de Aviones Silver
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.printf("El archivo no existe\n");
        }
        return listaAvionesSilver;
    }
    @Override
    public void agregarEnArchivo() {
        File fileSilver = new File("AvionesSilver.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //Pregunto si el archivo existe
            if (fileSilver.createNewFile()) {
                ArrayList<Silver> SilverArrayList = new ArrayList<>();
                SilverArrayList.add(this);
                mapper.writeValue(fileSilver, SilverArrayList);
            } else {
                ArrayList<Silver> SilverArrayList = new ArrayList<Silver>(leerArchivo());
                SilverArrayList.add(this);
                mapper.writeValue(fileSilver, SilverArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void mostrarArchivo() {
        File fileSilver = new File("AvionesSilver.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileSilver.exists()) {
            try{
                List<Silver> listaAvionesSilver = Arrays.asList(mapper.readValue(fileSilver, Silver[].class)); //Convierto Json array a list de objetos
                listaAvionesSilver.stream().forEach(obj -> System.out.println(obj)); // Mostrar lista de Aviones Silver
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        }else{
            System.out.println("El archivo esta vacio");
        }
    }
    public boolean mostrarAvionesDisponibles(Ticket tk) {
        File fileSilver = new File("AvionesSilver.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        HashSet<Integer> numerosVuelos = new HashSet<>();
        boolean validacion = false;
        int i = 0;
        LocalDate fechaTk = tk.getFecha();
        if(fileSilver.exists()) {
            try{
                List<Silver> listaAvionesSilver = Arrays.asList(mapper.readValue(fileSilver, Silver[].class)); //Convierto Json array a list de objetos
                for (Silver avion: listaAvionesSilver) {
                    if (avion.getFechas().equals(fechaTk) && avion.getCantMaxPasajeros() >= (tk.getPasajeros() + avion.getPasajerosAbordo())){
                        System.out.println(i + ":" + avion);
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
        File fileUsuarios = new File("AvionesSilver.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            mapper.writeValue(fileUsuarios, listaArch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
