package Usuario;

import java.util.UUID;

public class Usuario {
    private String nombre = "";
    private String apellido = "";
    private int edad = 0;
    private String dni = "";
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

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", dni=" + dni +
                ", _id=" + _id.toString() +
                '}';
    }
}
