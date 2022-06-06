import java.util.List;

public interface Archivos<X> {
    List<X> leerArchivo();
    void agregarEnArchivo();
    void mostrarArchivo();
}
