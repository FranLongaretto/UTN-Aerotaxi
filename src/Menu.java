import Usuario.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    List<Usuario> milista_Usuarios = new ArrayList<>();
    public void inicio_menu(){
        //ejecucion del programa
        Scanner scanner = new Scanner(System.in);
        int respuesta = 5;

        while(respuesta != 0) {

            opciones_menu();

            //generar el gestionamiento de las excepcciones!!!!!!!!!!!!!!

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

            } catch (Exception e) {//sino capturo el error y lo gestiono

                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }

    public void opciones_menu(){

        System.out.println("\t\tBienveido a AeroTaxi!!\n\n");
        System.out.println("1: registrarse\n");
        System.out.println("2: loguearse\n");
        System.out.println("3: admin\n");
        System.out.println("0: salir del sistema\n");
    }

    public void registrarse(){

        //registrar el usuario
        Scanner scanner = new Scanner(System.in);
        Usuario usuario = new Usuario();

        System.out.println("Ingrese su nombre: ");
        usuario.setNombre(scanner.nextLine());

        System.out.println("Ingrese su apellido: ");
        usuario.setApellido(scanner.nextLine());

        System.out.println("Ingrese su DNI: ");
        usuario.setDni(scanner.nextLine());

        System.out.println("Ingrese su edad: ");
        usuario.setEdad(scanner.nextInt());

        usuario.set_id();

        //comprobar usuario con los ya registrados mediante el dni
        //boolean resp = comprobar_Usuario(usuario.getDni());
        //guardar usuario en archivo
        //if()
        milista_Usuarios.add(usuario);






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
