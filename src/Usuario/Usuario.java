package Usuario;

import Flota.TipoAvion;
import Interfaces.Archivos;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario implements Archivos<Usuario> {
    private String nombre = "";
    private String apellido = "";
    private int edad = 0;
    private String dni = "";
    private double gastadoHistorico = 0;
    private TipoAvion mejorCategoria = TipoAvion.BRONZE;
    private UUID _id;

    //Cada usuario va a tener que tener una lista de vuelos comprados y realizados.
    //Y despues con esos datos se utiliza metodo para calcular cuanto lleva gastado historicamente.

    public Usuario(){}

    public Usuario(String nombre, String apellido, int edad, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this._id = UUID.randomUUID();   //Acordarse de pasar el id a String para guardarlo,en json o archivo, o mostrarlo -> Ejemplo: _id.toString().
    }

    public void set_id(){
        this._id = UUID.randomUUID();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public UUID get_id() {
        return _id;
    }

    public double getGastadoHistorico() {
        return gastadoHistorico;
    }

    public void setGastadoHistorico(double gastadoHistorico) {
        this.gastadoHistorico += gastadoHistorico;
    }

    public TipoAvion getMejorCategoria() {
        return mejorCategoria;
    }

    public void setMejorCategoria(TipoAvion mejorCategoria) {
        this.mejorCategoria = mejorCategoria;
    }

    @Override
    public String toString() {
        return "Usuario{\n" +
                "_id=" + _id +
                "\n,nombre='" + nombre + '\'' +
                "\n, apellido='" + apellido + '\'' +
                "\n, edad=" + edad +
                "\n, dni='" + dni + '\'' +
                "\n, gastadoHistorico=" + gastadoHistorico +
                "\n, mejorCategoria=" + mejorCategoria +
                "\n}";
    }

    //Se hace override de la interfaz para tratar Archivos (en este caso de Usuarios)
    @Override
    public List<Usuario> leerArchivo() {
        File fileUsuarios = new File("Usuarios.json");
        Type listArchUsuarios = new TypeToken<ArrayList<Usuario>>() {}.getType();
        ArrayList<Usuario> userArray = null;
        if (fileUsuarios.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileUsuarios));
                Gson gson = new Gson();
                userArray = gson.fromJson(bufferedReader, listArchUsuarios);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("El archivo no existe\n");
        }

        return userArray;
    }
    @Override
    public void agregarEnArchivo() {
        File fileUsuarios = new File("Usuarios.json");
        Type listArchUsuarios = new TypeToken<ArrayList<Usuario>>() {}.getType();
        ArrayList<Usuario> userArray = null;
        Gson gson = new Gson();
        //Pregunto si el archivo existe
        if (fileUsuarios.exists()){
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileUsuarios));
                userArray = new ArrayList<Usuario>(leerArchivo()); //Bajo el los datos del json con leerArchivo() y lo guardo en userArray
                userArray.add(this); //Guardo en la lista de usuarios totales el nuevo usuario a registrar
                gson.toJson(userArray, listArchUsuarios, bufferedWriter); //vuelvo a escribir el archivo JSON de Usuarios
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //Si no existe hago un array list vacio, guardo mi primer usuario y lo escribo en el archivo JSON.
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileUsuarios));
                userArray = new ArrayList<>();
                userArray.add(this);
                gson.toJson(userArray, listArchUsuarios, bufferedWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void mostrarArchivo() {
        File fileUsuarios = new File("Usuarios.json");
        Type listArchUsuarios = new TypeToken<ArrayList<Usuario>>() {}.getType();
        ArrayList<Usuario> userArray = null;
        if (fileUsuarios.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fileUsuarios));
                Gson gson = new Gson();
                userArray = gson.fromJson(bufferedReader, listArchUsuarios);
                for (Usuario elemento: userArray) {
                    elemento.toString();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
