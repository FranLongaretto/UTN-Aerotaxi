import Flota.TipoAvion;
import Usuario.Usuario;

import java.util.*;

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
                        System.out.println("Saliendo del sistema...");
                        break;
                }
            } catch (InputMismatchException e){//capturo el error sobre dato ingresado
                System.out.println("Error, debe ingresar un numero!");

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }

    // Imprime opciones del menu para el usuario.
    public void opciones_menu(){
        System.out.println("\t¡Bienveido a AeroTaxi!\n\n");
        System.out.println("1: Registrarse\n");
        System.out.println("2: Loguearse\n");
        System.out.println("3: Admin\n");
        System.out.println("0: Salir del sistema\n");
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

            System.out.println("Ingrese su contraseña: ");
            nuevoUsuario.setPassword(scanner.nextLine());

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
            nuevoUsuario.mostrarArchivo();
        }else{
            //Aviso de usuario existente
            System.out.println("El usuario ingresado ya se encuentra en el sistema");
        }
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
        Scanner scanner = new Scanner(System.in);
        Usuario logUsuario = new Usuario();
        String _dni, _password;

        try{
            System.out.println("Ingrese su DNI: ");
            _dni = scanner.nextLine();

            System.out.println("Ingrese su contraseña: ");
            _password = scanner.nextLine();

            logUsuario = logUsuario.userLogin(_dni, _password);
            if (logUsuario!=null){
                System.out.println("Bienvenido, " + logUsuario.getNombre() + " " + logUsuario.getApellido());
            } else {
                System.out.println("El usuario y/o contraseña ingresado es incorrecto, intenta nuevamente.");
            }
        }catch (NoSuchElementException e){
            System.out.println("Error, por favor ingrese un texto." + e);
        }
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
