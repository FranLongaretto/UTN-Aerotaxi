import Flota.TipoAvion;
import Usuario.Usuario;

import java.util.*;

public class Menu {
    List<Usuario> miLista_Usuarios = new ArrayList<>();

    //region ----- Menu -----
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
    //endregion

    //region ----- Registro de Usuario -----
    public void registrarse(){
        Scanner scanner = new Scanner(System.in);
        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombre(chequearNombre());
        nuevoUsuario.setApellido(chequearApellido());
        nuevoUsuario.setDni(chequearDNI());
        nuevoUsuario.setPassword(chequearContraseña());
        nuevoUsuario.setEdad(chequearEdad());

        nuevoUsuario.set_id();
        nuevoUsuario.setGastadoHistorico(0);
        nuevoUsuario.setMejorCategoria(TipoAvion.NINGUNA);

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
    public String chequearNombre() {

        boolean aux = false;
        String nombre;
        System.out.println("Ingrese su nombre: ");
        do {
            Scanner scanner = new Scanner(System.in);
            nombre = scanner.nextLine();
            try {

                if (nombre.length() == 0)
                    throw new ExeptionUsuario("Debe ingresar un nombre");
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        } while (!aux);

        return nombre;
    }
    public String chequearApellido() {

        boolean aux = false;
        String apellido;
        System.out.println("Ingrese su apellido: ");
        do {

            Scanner scanner = new Scanner(System.in);
            apellido = scanner.nextLine();
            try {

                if (apellido.length() == 0)
                    throw new ExeptionUsuario("Debe ingresar un apellido");
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            }
        } while (!aux);

        return apellido;
    }
    public String chequearDNI() {
        boolean aux = false;
        Integer auxNum;
        String dni = null;
        System.out.println("Ingrese su DNI: ");
        do {
            Scanner scanner = new Scanner(System.in);
            //Pattern patron = Pattern.compile("[0-9]");
            try {
//________________________________________________________________________________________//
                dni = scanner.nextLine();
                auxNum = Integer.parseInt(dni);
                if (dni.length() == 0)
                    throw new ExeptionUsuario("Debe ingresar un DNI");
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            }catch (NumberFormatException e){
                System.out.println("Debe ingresar un numero");
            }
        } while (!aux);
        return dni;
    }
    public int chequearEdad() {

        int EDAD_MAXIMA = 100;
        boolean aux = false;
        int edad;
        System.out.println("Ingrese su edad: ");
        do {
            Scanner scanner = new Scanner(System.in);
            edad = scanner.nextInt();
            try {
                if (edad <= 0 || edad >= 100)
                    throw new ExeptionUsuario("La edad ingresada no es valida");
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            }
        }while (!aux) ;
            return edad;
    }
    public String chequearContraseña() {

        boolean aux = false;
        String contraseña;
        System.out.println("Ingrese su contraseña: ");
        do {
            Scanner scanner = new Scanner(System.in);
            contraseña = scanner.nextLine();
            try {
                if (contraseña.length() < 3 || contraseña.length() > 12) {
                    throw new ExeptionUsuario("Por favor ingrese una contraseña dentro de los parametros");
                } else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            }
        } while (!aux);
        return contraseña;
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
    //endregion

    //region ----- Login -----
    public void login(){
        Scanner scanner = new Scanner(System.in);
        Usuario logUsuario = new Usuario();
        String _dni, _password;

        try{
            System.out.println("Ingrese su DNI: ");
            _dni = scanner.nextLine();

            System.out.println("Ingrese su contraseña: ");
            _password = scanner.nextLine();

            logUsuario = logUsuario.userLogin(_dni, _password);//chequeo que el usuario exista
            if (logUsuario!=null){
                System.out.println("Bienvenido, " + logUsuario.getNombre() + " " + logUsuario.getApellido());
            } else {
                System.out.println("El usuario y/o contraseña ingresado es incorrecto, intenta nuevamente.");
            }
        }catch (NoSuchElementException e){
            System.out.println("Error, por favor ingrese un texto." + e);
        }
    }
    //endregion
    public void menu_AeroTaxi(){

        //todas las funciones para comprar un pasaje de avion
        fechaDeVuelo();
        destinoDeVuelo();

    }

    public void fechaDeVuelo(){

        Scanner scanner = new Scanner(System.in);
        int aux = 0;

        System.out.println("Inicie su viaje con AeroTaxi, elija a donde seran sus proximas vacaciones");
        System.out.println("\nDestinos: \n" +
                "1- Buenos Aires - Cordoba.\n" +
                "2- Buenos Aires - Santiago del Estero.\n" +
                "3- Buenos Aires - Montevideo.\n" +
                "4- Cordoba - Montevideo.\n" +
                "5- Cordoba - Santiago del Estero.\n" +
                "6- Montevideo - Santiago del Estero.\n");
        System.out.println("Ingrese su proximo destino.");

        try{
            aux = scanner.nextInt();
            if (aux <=6 || aux >= 1)
                throw new ExeptionUsuario("Debe ingresar una opcion vaida.");
        }catch (ExeptionUsuario e){
            System.out.println(e.getMessage());
        }
        // debera ingresar la fecha en que quiere viajar


    }

    public void destinoDeVuelo(){


        System.out.println("Ingrese el destino al que desea viajar");
    }

    //region ----- ADMIN -----
    public void admin(){
        Boolean loginValido = loginAdmin(); //Devuelvo valor true si entro correctamente el admin
        if (loginValido){
            adminMenu(); //Si puso correcto el admin Abro menu de opciones
        }
    }
    public void adminMenu(){
        int respuesta = 5;
        while (respuesta != 0){
            Scanner scanner = new Scanner(System.in);
            OpcionesMenu();
            try { //si ingresa el dato correcto sigue funcinando segun lo debido
                respuesta = (int) scanner.nextInt();
                switch (respuesta){
                    case 1: //Ver Listado de vuelos por fecha especifica
                        break;
                    case 2: //Ver Listado de clientes
                        Usuario usuarios = new Usuario();
                        usuarios.mostrarArchivo();
                        break;
                    case 3: //Ver Listado de aviones
                        break;
                    case 4: //Agregar aviones
                        agregarAviones();
                        break;
                    case 0: //Salir al menu principal
                        inicio_menu();
                        break;
                }
            } catch (InputMismatchException e){//capturo el error sobre dato ingresado
                System.out.println("Error, debe ingresar un numero!");

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }
    private void OpcionesMenu(){
        System.out.println("1: Listado de Vuelos en Fecha Especifica");
        System.out.println("2: Listado de Clientes");
        System.out.println("3: Listado de Aviones");
        System.out.println("4: Agregar Aviones");
        System.out.println("0: Salir");
    }
    public Boolean loginAdmin(){
        Scanner scanner = new Scanner(System.in);
        Boolean valido = false;
        String usuario, password;
        System.out.println("Ingrese Usuario Administrador:");
        usuario = scanner.nextLine();
        if (usuario.equals("admin")){
            System.out.println("Ingrese contraseña:");
            password = scanner.nextLine();
            if (password.equals("admin123")) {
                valido = true;
            }else{
                System.out.println("Contraseña incorrecta");
            }
        }else{
            System.out.println("Usuario incorrecta");
        }
        return valido;
    }
    private void agregarAviones(){
        int opcion = 5;
        while (opcion != 0) {
            Scanner scanner = new Scanner(System.in);
            OpcionesAviones();
            opcion = scanner.nextInt();
            switch (opcion){
                case 1: //Agregar avion BRONZE
                    break;
                case 2: //Agregar avion SILVER
                    break;
                case 3: //Agregar avion GOLD
                    break;
                case 0: //Salir menu admin
                    adminMenu();
                default:
                    System.out.println("Ingrese una opcion valida.");
            }
        }
    }
    private void OpcionesAviones(){
        System.out.println("\tAgregar Tipo de Gold");
        System.out.println("1: Gold BRONZE");
        System.out.println("2: Gold SILVER");
        System.out.println("3: Gold GOLD");
        System.out.println("0: Salir al menu admin");
    }
    //endregion
}
