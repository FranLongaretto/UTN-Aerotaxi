package Flota;

import Interfaces.Archivos;
import Usuario.Usuario;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bronze extends Flota implements Archivos<Bronze> {
    public Bronze() {
    }

    public Bronze(String capCombustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion){
        super(capCombustible, costoServicio, cantMaxPasajeros, velMax, propulsion);
        setTarifaFija(3000);
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

}
