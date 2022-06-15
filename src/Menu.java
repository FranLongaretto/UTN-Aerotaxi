import Flota.*;
import Ticket.Ciudad;
import Ticket.Ticket;
import Usuario.Usuario;

import java.time.LocalDate;
import java.util.*;

public class Menu {
    private List<Usuario> miLista_Usuarios = new ArrayList<>();
    private List<Flota> listaAviones = new ArrayList<>();
    private List<Ticket> listaTicket = new ArrayList<>();

    //region ----- Menu -----
    public void inicio_menu() {
        //ejecucion del programa
        Scanner scanner = new Scanner(System.in);
        int respuesta = 5;

        while (respuesta != 0) {
            opciones_menu();
            try {//si ingresa el dato correcto sigue funcinando segun lo debido
                respuesta = (int) scanner.nextInt();
                switch (respuesta) {
                    case 1:
                        registrarse();
                        break;
                    case 2:
                        Usuario user = login();
                        if (user != null) {
                            //devuelve el usuario y pregunta si el usuario no es null para mostrar el menu
                            menu_AeroTaxi(user);
                        }
                        break;
                    case 3:
                        admin();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                }
            } catch (InputMismatchException e) {//capturo el error sobre dato ingresado
                System.out.println("Error, debe ingresar un numero!");

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }

    // Imprime opciones del menu para el usuario.
    public void opciones_menu() {
        System.out.println("\t¡Bienveido a AeroTaxi!\n\n");
        System.out.println("1: Registrarse\n");
        System.out.println("2: Loguearse\n");
        System.out.println("3: Admin\n");
        System.out.println("0: Salir del sistema\n");
    }
    //endregion

    //region ----- Registro de Usuario -----
    public void registrarse() {
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

        if (validacion) {
            //Guardar usuario en archivo
            nuevoUsuario.agregarEnArchivo();
            System.out.printf("Registro Completado \n");
            nuevoUsuario.mostrarArchivo();
        } else {
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
                dni = scanner.nextLine();
                auxNum = Integer.parseInt(dni);
                if (dni.length() == 0)
                    throw new ExeptionUsuario("Debe ingresar un DNI");
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
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
        } while (!aux);
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
        if (miLista_Usuarios != null) {
            for (Usuario elemento : miLista_Usuarios) {//Recorro lista y comparo usuarios.
                if (elemento.get_id() == usuario.get_id()) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }
    //endregion

    //region ----- Login -----
    public Usuario login() {
        Scanner scanner = new Scanner(System.in);
        Usuario logUsuario = new Usuario();
        String _dni, _password;

        try {
            System.out.println("Ingrese su DNI: ");
            _dni = scanner.nextLine();

            System.out.println("Ingrese su contraseña: ");
            _password = scanner.nextLine();

            logUsuario = logUsuario.userLogin(_dni, _password);//chequeo que el usuario exista
            if (logUsuario != null) {
                System.out.println("Bienvenido, " + logUsuario.getNombre() + " " + logUsuario.getApellido());
            } else {
                System.out.println("El usuario y/o contraseña ingresado es incorrecto, intenta nuevamente.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error, por favor ingrese un texto." + e);
        }
        return logUsuario;
    }

    //endregion

    //region ----- Menu Usuario -----

    //Menu opciones usuario
    public void menuOpciones() {
        System.out.println("1: Solicitar un vuelo.\n");
        System.out.println("2: Cancelar un vuelo.\n");
        System.out.println("3: Ver reservas.\n");
        System.out.println("0: Salir.\n");
    }
    public void menu_AeroTaxi(Usuario user) {
        Scanner scanner = new Scanner(System.in);
        Ticket tk;
        int aux = 4;
        //todas las funciones para comprar un pasaje de avion
        do {
            menuOpciones();
            aux = scanner.nextInt();
            switch (aux) {
                case 1:
                    tk = generarTicket(user);
                    if (tk != null) {
                        agregarTkALista(tk);
                        tk.agregarEnArchivo();
                        //falta agregar fecha de vuelo al avion
                        //Se podria corroborar la mejor categoria del usuario
                        //Se tiene que guardar el precio del tk.= (funcion que lo devuelva)
                    }else{
                        //el usuario deberia ingresar otra fecha o salir al menu principal
                    }
                    //guardar datos al usuario (total gastado)
                    break;
                case 2:
                    //funcion cancelar vuelo
                    break;
                case 3:
                    //mostrar reservas
                    break;
                case 0:
                    System.out.println("Volviendo al menu pricipal.");
                    break;
                default:
                    System.out.println("Ingrese un numero valido.");
            }
        } while (aux != 0);
    }
    public Ticket menuOrigen(Ticket tk) {
        int opcion = 0;
        int destino = 0;
        Scanner scanerOigen = new Scanner(System.in);
        Scanner scanerDesino = new Scanner(System.in);
        System.out.println("Ingrese la ciudad de origen:\n");
        System.out.println("1: Buenos Aires.\n");
        System.out.println("2: Cordoba.\n");
        System.out.println("3: Montevideo.\n");
        System.out.println("0: Salir.\n");
        while (opcion != 0) {
            opcion = scanerOigen.nextInt();
            switch (opcion) {
                case 1:
                    tk.setOrigen(Ciudad.BUENOSAIRES);
                    System.out.println("Ingrese destino");
                    System.out.println("1: Montevideo");
                    System.out.println("2: Cordoba");
                    System.out.println("3: Santiago");
                    destino = scanerDesino.nextInt();
                    if (destino == 1) {
                        tk.setDestino(Ciudad.MONTEVIDEO);
                    } else if (destino == 2) {
                        tk.setDestino(Ciudad.CORDOBA);
                    } else if (destino == 3) {
                        tk.setDestino(Ciudad.SANTIAGO);
                    } else {
                        System.out.println("Ingrese un numero valido!");
                    }
                    break;
                case 2:
                    tk.setOrigen(Ciudad.CORDOBA);
                    System.out.println("Ingrese destino");
                    System.out.println("1: Montevideo");
                    System.out.println("2: Santiago");
                    destino = scanerDesino.nextInt();
                    if (destino == 1) {
                        tk.setDestino(Ciudad.MONTEVIDEO);
                    } else if (destino == 2) {
                        tk.setDestino(Ciudad.SANTIAGO);
                    } else {
                        System.out.println("Ingrese un numero valido!");
                    }
                    break;
                case 3:
                    tk.setOrigen(Ciudad.MONTEVIDEO);
                    tk.setDestino(Ciudad.SANTIAGO);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Ingrese un numero valido!");
                    break;
            }
        }
        return tk;
    }
    public Ticket generarTicket(Usuario user) {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        Ticket ticket = new Ticket();
        Flota avionAux;
        ticket.setUsuarioDni(user.getDni());
        ticket.setFecha(fechaDeVuelo()); //generar funcion que me diga si esta disponible la ffecha sino que elija otra
        ticket = menuOrigen(ticket);
        ticket.setPasajeros(acompañantes());
        avionAux = seleccionarAvion(ticket);
        if (avionAux != null){
            ticket.setNumeroDeAvion(seleccionarAvion(ticket).getNumeroAvion());
        }else {
            System.out.println("No hay aviones disponibles");
        }
        System.out.println(ticket.toString());
        System.out.println("Los datos son correctos? 1 por si / 0 por no");
        opcion = sc.nextInt();
        if (opcion != 1) ticket = null;
        return ticket;
    }
    public LocalDate fechaDeVuelo() {
        LocalDate fecha;
        System.out.println("Ingrese el año en el que quiere viajar.\n");
        int año = ingreseUnNumero();
        System.out.println("Ingrese el mes.\n");
        int mes = ingreseUnNumero();
        System.out.println("Ingrese el dia.\n");
        int dia = ingreseUnNumero();
        fecha = LocalDate.of(año, mes, dia);
        return fecha;
    }
    private int ingreseUnNumero() {
        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();
        return numero;
    }
    private int acompañantes() {
        Scanner scanner = new Scanner(System.in);
        int cantidad = 1;
        System.out.println("Ingrese la cantidad de acompañantes");
        cantidad += scanner.nextInt();
        //validar si entran en el avion
        return cantidad;
    }
    private Flota seleccionarAvion(Ticket tk) {
        Scanner scanner = new Scanner(System.in);
        Scanner scVuelo = new Scanner(System.in);
        Flota avion;
        int opcionVuelo;
        HashSet<Integer> numerosVuelos = null;
        Boolean validado = false;

        int avionSeleccionado = 0;
        System.out.println("Ingrese categoria de Avion");
        System.out.println("1: Gold");
        System.out.println("2: Silver");
        System.out.println("3: Bronze");
        avionSeleccionado = scanner.nextInt();
        if (avionSeleccionado == 3) {
            Bronze avionBronze = new Bronze();
            numerosVuelos = avionBronze.mostrarAvionesDisponibles(tk);
        } else if (avionSeleccionado == 2) {
            Silver avionSilver = new Silver();
            numerosVuelos = avionSilver.mostrarAvionesDisponibles(tk);
        } else if (avionSeleccionado == 1) {
            Gold avionGold = new Gold();
            numerosVuelos = avionGold.mostrarAvionesDisponibles(tk);
        } else {
            System.out.println("Ingrese una opcion correcta");
        }
        System.out.println("\nSeleccione el numero de vuelo");
        if(!numerosVuelos.isEmpty()){
            do{
                opcionVuelo = scVuelo.nextInt();
                if(!numerosVuelos.contains(opcionVuelo)){
                    System.out.println("Ingrese un valor valido");
                }
            }while (!numerosVuelos.contains(opcionVuelo));
            listaAviones.get(opcionVuelo).addDate(tk.getFecha());
            avion = listaAviones.get(opcionVuelo);
        }else{
            avion = null;
        }

        return avion;
    }
    private void agregarTkALista(Ticket tk){
        this.listaTicket.add(tk);
    }
    //endregion

    //region ----- ADMIN -----
    public void admin() {
        Boolean loginValido = loginAdmin(); //Devuelvo valor true si entro correctamente el admin
        if (loginValido) {
            adminMenu(); //Si puso correcto el admin Abro menu de opciones
        }
    }

    public void adminMenu() {
        int respuesta = 5;
        while (respuesta != 0) {
            Scanner scanner = new Scanner(System.in);
            OpcionesMenu();
            try { //si ingresa el dato correcto sigue funcinando segun lo debido
                respuesta = (int) scanner.nextInt();
                switch (respuesta) {
                    case 1: //Ver Listado de vuelos por fecha especifica
                        mostrarVuelosXFecha();
                        break;
                    case 2: //Ver Listado de clientes
                        Usuario usuarios = new Usuario();
                        usuarios.mostrarArchivo();
                        break;
                    case 3: //Ver Listado de aviones
                        verListaAviones();
                        break;
                    case 4: //Agregar aviones
                        agregarAviones();
                        break;
                    case 5: //Agregar nuevo admin
                        agregarAdmin();
                        break;
                    case 0: //Salir al menu principal
                        inicio_menu();
                        break;
                }
            } catch (InputMismatchException e) {//capturo el error sobre dato ingresado
                System.out.println("Error, debe ingresar un numero!");

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println("Error");//volver a pedirle que ingrese bien el dato
            }
        }
    }

    private void verListaAviones() {
        int opcion = 5;
        Bronze bronze = new Bronze();
        Silver silver = new Silver();
        Gold gold = new Gold();
        while (opcion != 0) {
            Scanner scanner = new Scanner(System.in);
            OpcionesAviones();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1: //Agregar avion BRONZE
                    bronze.mostrarArchivo();
                    break;
                case 2: //Agregar avion SILVER
                    silver.mostrarArchivo();
                    break;
                case 3: //Agregar avion GOLD
                    gold.mostrarArchivo();
                    break;
                case 0: //Salir menu admin
                    adminMenu();
                default:
                    System.out.println("Ingrese una opcion valida.");
            }
        }
    }

    private void OpcionesMenu() {
        System.out.println("1: Listado de Vuelos en Fecha Especifica");
        System.out.println("2: Listado de Clientes");
        System.out.println("3: Listado de Aviones");
        System.out.println("4: Agregar Aviones");
        System.out.println("5: Agregar Admin");
        System.out.println("0: Salir");
    }

    public Boolean loginAdmin() {
        Scanner scanner = new Scanner(System.in);
        Boolean valido = false;
        String _dni, _password;
        Usuario logUsuario = new Usuario();

        try {
            System.out.println("Ingrese su DNI: ");
            _dni = scanner.nextLine();

            System.out.println("Ingrese su contraseña: ");
            _password = scanner.nextLine();

            logUsuario = logUsuario.userLogin(_dni, _password);
            if (logUsuario != null && logUsuario.isAdmin()) {
                valido = true;
            } else {
                System.out.println("El usuario y/o contraseña ingresado es incorrecto o no sos Administrador.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error, por favor ingrese un texto." + e);
        }

        return valido;
    }

    // Funcion para agregar nuevos admins
    private void agregarAdmin(){
        Scanner sc = new Scanner(System.in);
        Usuario user = new Usuario();
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>(user.leerArchivo());

        System.out.println("Ingresa el DNI del usuario a añadir como administrador");
        String _dni = sc.nextLine();

        for (Usuario usuario: listaUsuarios) {
            if (usuario.getDni().equals(_dni)){
                usuario.setAdmin(true);
                System.out.println(usuario);
            }
        }

        user.sobreEscribirArchivo(listaUsuarios);
    }

    private void agregarAviones() {
        int opcion = 5;
        while (opcion != 0) {
            Scanner scanner = new Scanner(System.in);
            OpcionesAviones();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1: //Agregar avion BRONZE
                    agregarBronce();
                    break;
                case 2: //Agregar avion SILVER
                    agregarSilver();
                    break;
                case 3: //Agregar avion GOLD
                    agregarGold();
                    break;
                case 0: //Salir menu admin
                    adminMenu();
                default:
                    System.out.println("Ingrese una opcion valida.");
            }
        }
    }

    public void agregarBronce(){
        Bronze bronze = new Bronze();

        bronze.agregarEnArchivo();
    }

    public void agregarSilver(){
        Silver silver = new Silver();
        silver.agregarEnArchivo();
    }

    public void agregarGold(){
        Gold gold = new Gold();
        gold.agregarEnArchivo();
    }

    private void OpcionesAviones(){
        System.out.println("\tTipo de avion");
        System.out.println("1: Avion BRONZE");
        System.out.println("2: Avion SILVER");
        System.out.println("3: Avion GOLD");
        System.out.println("0: Salir al menu admin");
    }
    public void mostrarVuelosXFecha(){
        LocalDate fecha;
        System.out.println("Ingrese el año.\n");
        int año = ingreseUnNumero();
        System.out.println("Ingrese el mes.\n");
        int mes = ingreseUnNumero();
        System.out.println("Ingrese el dia.\n");
        int dia = ingreseUnNumero();
        fecha = LocalDate.of(año, mes, dia);

        if(listaTicket!= null) {
            System.out.println("---"+fecha+"---\n");
            for (Ticket ticket : listaTicket) {
                if (ticket.getFecha().equals(fecha)) {
                    System.out.println(ticket.toString());
                }
            }
        }else {
            System.out.println("Actualmente no hay vuelos");
        }
    }
    //endregion
}
