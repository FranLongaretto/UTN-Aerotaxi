package Usuario;

import Flota.TipoAvion;
import Interfaces.Archivos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Usuario implements Archivos<Usuario> {
    private String nombre = "";
    private String apellido = "";
    private int edad = 0;
    private String dni = "";
    private String password;
    private double gastadoHistorico = 0;
    private TipoAvion mejorCategoria = TipoAvion.BRONZE;
    private UUID _id;

    private boolean esAdmin = false;

    //Cada usuario va a tener que tener una lista de vuelos comprados y realizados.
    //Y despues con esos datos se utiliza metodo para calcular cuanto lleva gastado historicamente.

    public Usuario(){}

    public Usuario(String nombre, String apellido, int edad, String dni, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.password = password;
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

    public boolean isAdmin() {
        return esAdmin;
    }

    public void setAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Agregar Regex en caso de que sobre tiempo
        this.password = password;
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
        return "----------------------\n" +
                "\n" +
                "ID = " + _id +
                "\nNombre = " + nombre +
                "\nApellido = " + apellido +
                "\nEdad = " + edad +
                "\nDNI = " + dni +
                "\nGastado Historico = " + gastadoHistorico +
                "\nMejor Categoria = " + mejorCategoria +
                "\nAdmin = " + esAdmin +
                "\n";
    }

    //Se hace override de la interfaz para tratar Archivos (en este caso de Usuarios)
    @Override
    public List<Usuario> leerArchivo() {
        List<Usuario> listaUsuarios = null;
        File fileUsuarios = new File("Usuarios.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileUsuarios.exists() && fileUsuarios.canRead()) {
            try {
                listaUsuarios = Arrays.asList(mapper.readValue(fileUsuarios, Usuario[].class)); //Convierto Json array a list de usuarios
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.printf("El archivo no existe\n");
        }
        return listaUsuarios;
    }

    // Chequea si hay un usuario que coincida el dni y la password
    public Usuario userLogin(String _dni, String _password){
        List<Usuario> usuarioList = leerArchivo();

        for (Usuario user: usuarioList) {
            if (user.getDni().equals(_dni)){
                if (user.getPassword().equals(_password)){
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public void agregarEnArchivo() {
        File fileUsuarios = new File("Usuarios.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //Pregunto si el archivo existe
            if (fileUsuarios.createNewFile()) {
                ArrayList<Usuario> usuarioArrayList = new ArrayList<>();
                usuarioArrayList.add(this);
                mapper.writeValue(fileUsuarios, usuarioArrayList);//escribe la lista en el archivo
            } else {
                ArrayList<Usuario> usuarioArrayList = new ArrayList<Usuario>(leerArchivo());
                usuarioArrayList.add(this);
                mapper.writeValue(fileUsuarios, usuarioArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void mostrarArchivo() {
        File fileUsuarios = new File("Usuarios.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        if(fileUsuarios.exists()) {
            try{
                List<Usuario> users = Arrays.asList(mapper.readValue(fileUsuarios, Usuario[].class)); //Convierto Json array a list de objetos
                users.stream().forEach(obj -> System.out.println(obj)); // Mostrar lista de usuarios
            } catch (IOException e) {
                System.out.println("Error!!");
            }
        }else{
            System.out.println("El archivo esta vacio");
        }
    }

    @Override
    public void sobreEscribirArchivo(ArrayList listaArch) {
        File fileUsuarios = new File("Usuarios.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            mapper.writeValue(fileUsuarios, listaArch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
