import Flota.TipoAvion;
import Usuario.Usuario;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    List<Usuario> miLista_Usuarios = new ArrayList<>();

    public void inicio_menu(){
        //ejecucion del programa
        Scanner scanner = new Scanner(System.in);
        int respuesta = 5;

        while(respuesta != 0) {
            opciones_menu();
            try {//si ingresa el dato correcto sigue funcinando segun lo debido
                respuesta = (int) scanner.nextInt();
                switch (respuesta){
                    case 1:
                        registrarse();
                        break;
                    case 2:
                        login();
                        menu_AeroTaxi();
                        break;
                    case 3:
                        admin();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema");
                        break;
                }
            } catch (InputMismatchException e){//capturo el error sobre dato ingresado
                System.out.println("Error! Debe ingresar un numero!");
            } catch (Exception e) {//sino capturo el error y lo gestiono
                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }

    // Imprime opciones del menu para el usuario.
    public void opciones_menu(){

        System.out.println("\t\tBienveido a AeroTaxi!!\n\n");
        System.out.println("1: registrarse\n");
        System.out.println("2: loguearse\n");
        System.out.println("3: admin\n");
        System.out.println("0: salir del sistema\n");
    }

    //registrar el usuario
    public void registrarse(){
        Scanner scanner = new Scanner(System.in);
        Usuario nuevoUsuario = new Usuario();

        try{
            System.out.println("Ingrese su nombre: ");
            nuevoUsuario.setNombre(scanner.nextLine());

            System.out.println("Ingrese su apellido: ");
            nuevoUsuario.setApellido(scanner.nextLine());

            System.out.println("Ingrese su DNI: ");
            nuevoUsuario.setDni(scanner.nextLine());

            System.out.println("Ingrese su edad: ");
            nuevoUsuario.setEdad(scanner.nextInt());

            nuevoUsuario.set_id();
            nuevoUsuario.setGastadoHistorico(0);
            nuevoUsuario.setMejorCategoria(TipoAvion.NINGUNA);
        }catch (Exception e){
            //mostrar exception
        }
        //comprobar usuario con los ya registrados
        Boolean validacion = validacionUsuario(nuevoUsuario);

        if (validacion){
            //Guardar usuario en archivo
            nuevoUsuario.agregarEnArchivo();
            System.out.printf("Registro Completado \n");
            //nuevoUsuario.mostrarArchivo();
        }else{
            //Aviso de usuario existente
            System.out.println("El usuario ingresado ya se encuentra en el sistema");
        }
    }

    private String checkName(String nombre) throws Exception{
        if(nombre.length() == 0){
            throw new Exception("El nombre no puede estar vacio");
        }
        return nombre;
    }
    private Boolean validacionUsuario(Usuario usuario) {
        //Arroja True si no encuentra al usuario.
        //Arroja False si encuentra usuario

        Boolean flag = true;
        miLista_Usuarios = usuario.leerArchivo(); //leerArchivo() trae lista de usuarios del json y se guarda en miLista_Usuarios
        if (miLista_Usuarios != null){
            for (Usuario elemento: miLista_Usuarios) {//Recorro lista y comparo usuarios.
                if(elemento.get_id() == usuario.get_id()){
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public void login(){

        //cargar datos usuario

        //chequear que datos ingresados coincidan con un usuario registrado
        //manejar las excepciones

    }

    public void menu_AeroTaxi(){

        //todas las funciones para comprar un pasaje de avion


    }

    public void admin(){

        //gestionar usuarios

        //gestionar vuelos (mostrar fechas de vuelos, crear vuelos)

        //gestionar aviones (agregar los tipos de aviones)
    }
}
