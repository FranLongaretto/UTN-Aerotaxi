package Interfaces;

import java.util.ArrayList;
import java.util.List;

public interface Archivos<X>{
    List<X> leerArchivo();
    void agregarEnArchivo();
    void mostrarArchivo();

    void sobreEscribirArchivo(ArrayList listaArch);
}
