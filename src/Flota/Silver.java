package Flota;

import Interfaces.Archivos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Silver extends Flota implements Archivos<Silver> {
    private boolean catering;

    public Silver() {
    }

    public Silver(String capCombustible, float costoServicio, int cantMaxPasajeros, float velMax, TipoPropulsion propulsion){
        super(capCombustible, costoServicio, cantMaxPasajeros, velMax, propulsion);
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
