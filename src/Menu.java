import Utilidades.Utilidades;
import Flota.*;
import Ticket.*;
import Usuario.Usuario;
import jdk.jshell.execution.Util;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class Menu {
    private List<Usuario> miLista_Usuarios = new ArrayList<>();
    private List<Flota> listaAviones = new ArrayList<>();//guardar aviones que van a viajar con sus respectivas fechas
    private List<Ticket> listaTicket = new ArrayList<Ticket>();

    //region ----- Menu -----
    public void inicio_menu() {
        //ejecucion del programa
        Scanner scanner = new Scanner(System.in);
        int respuesta = 0;

        do {
            opciones_menu();
            try {
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
                        System.out.println(Utilidades.ANSI_BLUE + "Saliendo del sistema..." + Utilidades.ANSI_RESET);
                        break;
                }
            } catch (InputMismatchException e) {//capturo el error sobre dato ingresado
                System.out.println(Utilidades.ANSI_RED + "Error, debe ingresar un numero!" + Utilidades.ANSI_RESET);

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println(Utilidades.ANSI_RED + "Error" + Utilidades.ANSI_RESET);//volver a pedirle que ingrese bien el dato
            }
        } while (respuesta != 0);
    }

    // Imprime opciones del menu para el usuario.
    public void opciones_menu() {
        System.out.println(Utilidades.ANSI_YELLOW + "\n\t¡Bienveido a AEROTAXI!\n" + Utilidades.ANSI_RESET);
        System.out.println("1 - Registrarse");
        System.out.println("2 - Loguearse");
        System.out.println("3 - Admin");
        System.out.println("0 - Salir del sistema");
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
            System.out.println(nuevoUsuario.toString());
            System.out.println(Utilidades.ANSI_BLUE + "---------------------------------" + Utilidades.ANSI_RESET);
            System.out.printf(Utilidades.ANSI_BLUE + "El usuario a sido registrado con exito.\n" + Utilidades.ANSI_RESET);
        } else {
            //Aviso de usuario existente
            System.out.println(Utilidades.ANSI_RED + "El usuario ingresado ya se encuentra en el sistema!" + Utilidades.ANSI_RESET);
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
                    throw new ExeptionUsuario(Utilidades.ANSI_RED + "Debe ingresar un nombre" + Utilidades.ANSI_RESET);
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
                    throw new ExeptionUsuario(Utilidades.ANSI_RED + "Debe ingresar un apellido" + Utilidades.ANSI_RESET);
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
                    throw new ExeptionUsuario(Utilidades.ANSI_RED + "Debe ingresar un DNI" + Utilidades.ANSI_RESET);
                else
                    aux = true;
            } catch (ExeptionUsuario e) {
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(Utilidades.ANSI_RED + "Debe ingresar un numero" + Utilidades.ANSI_RESET);
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
                    throw new ExeptionUsuario(Utilidades.ANSI_RED + "La edad ingresada no es valida" + Utilidades.ANSI_RESET);
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
                    throw new ExeptionUsuario(Utilidades.ANSI_RED + "Por favor ingrese una contraseña dentro de los parametros" + Utilidades.ANSI_RESET);
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
                System.out.println(Utilidades.ANSI_YELLOW + "\n\nBienvenido, " + logUsuario.getNombre() + " " + logUsuario.getApellido() + "\n" + Utilidades.ANSI_RESET);
            } else {
                System.out.println(Utilidades.ANSI_RED + "El usuario y/o contraseña ingresado es incorrecto, intenta nuevamente.\n" + Utilidades.ANSI_RESET);
            }
        } catch (NoSuchElementException e) {
            System.out.println(Utilidades.ANSI_RED + "Error, por favor ingrese un texto.\n" + e + Utilidades.ANSI_RESET);
        }
        return logUsuario;
    }

    //endregion

    //region ----- Menu Usuario -----

    //Menu opciones usuario
    public void menuOpciones() {
        System.out.println("1 - Solicitar un vuelo.");
        System.out.println("2 - Cancelar un vuelo.");
        System.out.println("3 - Ver reservas.");
        System.out.println("0 - Salir.");
    }

    public void menu_AeroTaxi(Usuario user) {
        Scanner scanner = new Scanner(System.in);
        Ticket tk = null;
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
                    } else {
                        System.out.println("Error!!");
                    }
                    break;
                case 2:
                    cancelarVuelo(user);
                    break;
                case 3:
                    mostrarReservasUsuario(user);
                    break;
                case 0:
                    System.out.println(Utilidades.ANSI_BLUE + "Volviendo al menu pricipal." + Utilidades.ANSI_RESET);
                    break;
                default:
                    System.out.println(Utilidades.ANSI_RED + "Ingrese un numero valido.\n" + Utilidades.ANSI_RESET);
            }
        } while (aux != 0);
    }

    public Ticket generarTicket(Usuario user) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;
        Ticket ticket = new Ticket();
        Flota avionAux;

        ticket.setUsuarioDni(user.getDni());
        ticket = menuOrigen(ticket);
        do {
            mostrarFechas();
            ticket.setFecha(fechaDeVuelo());
            avionAux = seleccionarAvion(ticket);

            if (avionAux == null) {
                System.out.println("Fecha o capacidad del avion invalida, Por favor vuelva a ingresar los datos");
                System.out.println("\nIngrese 1 para volver a solicitar un vuelo, 0 volver al menu pricipal.");
                opcion = sc.nextInt();
            } else {
                //termino de generar ticket y genero el pago
                System.out.println(Utilidades.ANSI_GREEN + "Ingrese 1 si desea cambiar los datos, 0 para continuar." + Utilidades.ANSI_RESET);
                opcion = sc.nextInt();
            }
        } while (opcion != 0);
        ticket.setNroTicket();

        return ticket;
    }

    public Ticket menuOrigen(Ticket tk) {
        int opcion = -1;

        Scanner scanerOrigen = new Scanner(System.in);
        Scanner scanerDestino = new Scanner(System.in);
        System.out.println("Ingrese la ciudad de origen:\n");
        System.out.println("1 - Buenos Aires.");
        System.out.println("2 - Cordoba.");
        System.out.println("3 - Montevideo.");
        System.out.println("0 - Salir.");

        opcion = scanerOrigen.nextInt();

        switch (opcion) {
            case 1:
                tk.setOrigen(Ciudad.BUENOSAIRES);
                tk = destinoBsAs(tk);
                break;
            case 2:
                tk.setOrigen(Ciudad.CORDOBA);
                tk = destinoCordoba(tk);
                break;
            case 3:
                tk.setOrigen(Ciudad.MONTEVIDEO);
                tk.setDestino(Ciudad.SANTIAGO);
                tk.setDistancia(2100);
                break;
            case 0:
                break;
            default:
                System.out.println(Utilidades.ANSI_RED + "Ingrese un numero valido!" + Utilidades.ANSI_RESET);
                break;
        }

        return tk;
    }//selecciona origen y destino, devuelvo tk con esos datos

    public Ticket destinoBsAs(Ticket tk) {
        Scanner sc = new Scanner(System.in);
        int destino;

        System.out.println("Ingrese destino:");
        System.out.println("1 - Montevideo");
        System.out.println("2 - Cordoba");
        System.out.println("3 - Santiago");

        destino = sc.nextInt();
        if (destino == 1) {
            tk.setDestino(Ciudad.MONTEVIDEO);
            tk.setDistancia(950);
        } else if (destino == 2) {
            tk.setDestino(Ciudad.CORDOBA);
            tk.setDistancia(695);
        } else if (destino == 3) {
            tk.setDestino(Ciudad.SANTIAGO);
            tk.setDistancia(1400);
        } else {
            System.out.println(Utilidades.ANSI_RED + "Ingrese un numero valido!" + Utilidades.ANSI_RESET);
        }
        return tk;
    }

    public Ticket destinoCordoba(Ticket tk) {
        Scanner sc = new Scanner(System.in);
        int destino;

        System.out.println("Ingrese destino:");
        System.out.println("1 - Montevideo");
        System.out.println("2 - Santiago");
        destino = sc.nextInt();
        if (destino == 1) {
            tk.setDestino(Ciudad.MONTEVIDEO);
            tk.setDistancia(1190);
        } else if (destino == 2) {
            tk.setDestino(Ciudad.SANTIAGO);
            tk.setDistancia(1050);
        } else {
            System.out.println(Utilidades.ANSI_RED + "Ingrese un numero valido!" + Utilidades.ANSI_RESET);
        }
        return tk;
    }

    public void mostrarFechas() {
        Gold gold = new Gold();
        ArrayList<Gold> goldArrayList = new ArrayList<Gold>(gold.leerArchivo());
        Silver silver = new Silver();
        ArrayList<Silver> silverArrayList = new ArrayList<Silver>(silver.leerArchivo());
        Bronze bronze = new Bronze();
        ArrayList<Bronze> bronzesArrayList = new ArrayList<Bronze>(bronze.leerArchivo());

        if (goldArrayList != null) {
            System.out.println("-----------------------------");
            System.out.println(Utilidades.ANSI_YELLOW_BACKGROUND + Utilidades.ANSI_BLACK + "Fechas aviones GOLD" + Utilidades.ANSI_RESET);
            for (Gold avion : goldArrayList) {
                System.out.println(Utilidades.ANSI_BOLD + "Numero de vuelo: " + Utilidades.ANSI_RESET + avion.getNumeroAvion()
                        + Utilidades.ANSI_BOLD + " - Fecha: " + Utilidades.ANSI_RESET + avion.getFechas() + Utilidades.ANSI_RESET
                        + Utilidades.ANSI_BOLD + " - Pasajeros: " + Utilidades.ANSI_RESET + avion.getPasajerosAbordo() + "/" + avion.getCantMaxPasajeros() + Utilidades.ANSI_RESET);
            }
        }
        if (silverArrayList != null) {
            System.out.println("-----------------------------");
            System.out.println(Utilidades.ANSI_WHITE_BACKGROUND + Utilidades.ANSI_BLACK + "Fechas aviones SILVER" + Utilidades.ANSI_RESET);
            for (Silver avion : silverArrayList) {
                System.out.println(Utilidades.ANSI_BOLD + "Numero de vuelo: " + Utilidades.ANSI_RESET + avion.getNumeroAvion()
                        + Utilidades.ANSI_BOLD + " - Fecha: " + Utilidades.ANSI_RESET + avion.getFechas() + Utilidades.ANSI_RESET
                        + Utilidades.ANSI_BOLD + " - Pasajeros: " + Utilidades.ANSI_RESET + avion.getPasajerosAbordo() + "/" + avion.getCantMaxPasajeros() + Utilidades.ANSI_RESET);
            }
        }
        if (bronzesArrayList != null) {
            System.out.println("-----------------------------");
            System.out.println(Utilidades.ANSI_BLACK_BACKGROUND + "Fechas aviones BRONZE" + Utilidades.ANSI_RESET);
            for (Bronze avion : bronzesArrayList) {
                System.out.println(Utilidades.ANSI_BOLD + "Numero de vuelo: " + Utilidades.ANSI_RESET + avion.getNumeroAvion()
                        + Utilidades.ANSI_BOLD + " - Fecha: " + Utilidades.ANSI_RESET + avion.getFechas() + Utilidades.ANSI_RESET
                        + Utilidades.ANSI_BOLD + " - Pasajeros: " + Utilidades.ANSI_RESET + avion.getPasajerosAbordo() + "/" + avion.getCantMaxPasajeros() + Utilidades.ANSI_RESET);
            }
            System.out.println("-----------------------------\n");
        }
    }

    public LocalDate fechaDeVuelo() {

        LocalDate fecha;
        System.out.println("Ingrese el año.");
        int año = ingreseUnNumero();
        System.out.println("Ingrese el mes.");
        int mes = ingreseUnNumero();
        System.out.println("Ingrese el dia.");
        int dia = ingreseUnNumero();
        fecha = LocalDate.of(año, mes, dia);

        return fecha;
    }

    private int ingreseUnNumero(){

        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();
        return numero;
    }

    private boolean acompañantes(Flota avion, Ticket tk) {
        Scanner scanner = new Scanner(System.in);
        int cantidad = 1;
        boolean aux = false;

        System.out.println("Ingrese la cantidad de acompañantes");
        cantidad += scanner.nextInt();
        if (avion.getCantMaxPasajeros() >= (avion.getPasajerosAbordo() + cantidad)) {
            avion.setPasajerosAbordo(avion.getPasajerosAbordo() + cantidad);
            tk.setPasajeros(cantidad);
            aux = true;
        } else {
            System.out.println(Utilidades.ANSI_RED + "Ya no queda espacio en el avion." + Utilidades.ANSI_RESET);
        }
        return aux;
    }//devuelve false si no quedan espacios en el avion

    private Flota seleccionarAvion(Ticket tk) {
        Scanner scanner = new Scanner(System.in);
        Scanner scVuelo = new Scanner(System.in);
        Flota avion = null;
        int opcionVuelo;
        int numerosVuelos = -1;
        Boolean validado = false;
        int avionSeleccionado = -1;

        System.out.println("Ingrese categoria de Avion");
        System.out.println("1 - Gold.");
        System.out.println("2 - Silver.");
        System.out.println("3 - Bronze.");
        System.out.println("0 - Salir del sistema.");

        avionSeleccionado = scanner.nextInt();
        if (avionSeleccionado == 1) {
            Gold avionGold = new Gold();
            List<Gold> listaDisponibles = avionGold.leerArchivo();
            for (Flota _avion : listaDisponibles) {
                if (_avion.getFechas().equals(tk.getFecha())) {
                    boolean capacidad = acompañantes(_avion, tk);
                    if (capacidad == true) {
                        tk.setNumeroDeAvion(_avion.getNumeroAvion());
                        tk.setPrecio(avionGold.precioVuelo(tk));
                        avion = _avion;
                    }
                }
            }
        } else if (avionSeleccionado == 2) {
            Silver avionSilver = new Silver();
            List<Silver> listaDisponibles = avionSilver.leerArchivo();
            for (Flota _avion : listaDisponibles) {
                if (_avion.getFechas().equals(tk.getFecha())) {
                    boolean capacidad = acompañantes(_avion, tk);
                    if (capacidad == true) {
                        tk.setNumeroDeAvion(_avion.getNumeroAvion());
                        tk.setPrecio(avionSilver.precioVuelo(tk));
                        avion = _avion;
                    }
                }
            }
        } else if (avionSeleccionado == 3) {
            Bronze avionBronze = new Bronze();
            List<Bronze> listaDisponibles = avionBronze.leerArchivo();
            for (Flota _avion : listaDisponibles) {
                if (_avion.getFechas().equals(tk.getFecha())) {
                    boolean capacidad = acompañantes(_avion, tk);
                    if (capacidad == true) {
                        tk.setNumeroDeAvion(_avion.getNumeroAvion());
                        tk.setPrecio(avionBronze.precioVuelo(tk));
                        avion = _avion;
                    }
                }
            }
        } else if (avionSeleccionado == 0) {

        } else {
            System.out.println(Utilidades.ANSI_RED + "¡Opcion incorrecta, volviendo al menu!" + Utilidades.ANSI_RESET);
        }
        return avion;
    }

    private void agregarTkALista(Ticket tk) {
        this.listaTicket.add(tk);
    }

    public void mostrarReservasUsuario(Usuario user) {
        Ticket tk = new Ticket();
        boolean aux = false;

        ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>(tk.leerArchivo());
        for (Ticket ticket : ticketArrayList) {
            if (user.getDni().equals(ticket.getUsuarioDni())) {
                System.out.println(ticket);
                aux = true;
            }
        }
        if (!aux)
            System.out.println(Utilidades.ANSI_RED + "No tienes ninguna reserva.\n" + Utilidades.ANSI_RESET);
    }

    public void cancelarVuelo(Usuario user) {
        Scanner scanner = new Scanner(System.in);
        Ticket tk = new Ticket();
        ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>(tk.leerArchivo());
        boolean flag = false;
        int i = 1;
        LocalDate hoy = LocalDate.now();

        if (ticketArrayList != null) {
            for (Ticket ticket : ticketArrayList) {
                if (ticket.getUsuarioDni().equals(user.getDni()) && !hoy.equals(ticket.getFecha().minusDays(1))) {
                    System.out.println(Utilidades.ANSI_PURPLE + i + Utilidades.ANSI_RESET +
                            Utilidades.ANSI_BOLD + " - Numero de Ticket: " + Utilidades.ANSI_RESET + ticket.getNroTicket() +
                            Utilidades.ANSI_BOLD + " - Origen: " + Utilidades.ANSI_RESET + ticket.getOrigen() +
                            Utilidades.ANSI_BOLD + " - Destino: " + Utilidades.ANSI_RESET + ticket.getDestino() +
                            Utilidades.ANSI_BOLD + " - Fecha: " + Utilidades.ANSI_RESET + ticket.getFecha() +
                            Utilidades.ANSI_BOLD + " - Pasajeros: " + Utilidades.ANSI_RESET + ticket.getPasajeros() +
                            Utilidades.ANSI_BOLD + " - Precio: " + Utilidades.ANSI_RESET + ticket.getPrecio());
                    flag = true;
                    i++;
                }
            }
        }
        if (flag != false) {
            System.out.println("Ingrese el numero de ticket a cancelar.\n");
            String aux = scanner.nextLine();

            for (Ticket ticket : ticketArrayList) {
                if (ticket.getNroTicket().equals(aux)) {
                    tk = ticket;
                }
            }
            ticketArrayList.remove(tk);
            tk.sobreEscribirArchivo(ticketArrayList);
            System.out.println(Utilidades.ANSI_BLUE + "El ticket ha sido cancelado con exito.\n" + Utilidades.ANSI_RESET);
        } else {
            System.out.println(Utilidades.ANSI_RED + "No tienes reservas para cancelar!" + Utilidades.ANSI_RESET);
        }
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
                        mostrarVuelosXFecha(fechaDeVuelo());
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
                System.out.println(Utilidades.ANSI_RED + "Error, debe ingresar un numero!" + Utilidades.ANSI_RESET);

            } catch (NoSuchElementException e) {//sino capturo el error y lo gestiono
                System.out.println(Utilidades.ANSI_RED + "Error" + Utilidades.ANSI_RESET);//volver a pedirle que ingrese bien el dato
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
                System.out.println(Utilidades.ANSI_RED + "El usuario y/o contraseña ingresado es incorrecto o no eres un Administrador." + Utilidades.ANSI_RESET);
            }
        } catch (NoSuchElementException e) {
            System.out.println(Utilidades.ANSI_RED + "Error, por favor ingrese un texto." + Utilidades.ANSI_RESET + e);
        }

        return valido;
    }

    // Funcion para agregar nuevos admins
    private void agregarAdmin() {
        Scanner sc = new Scanner(System.in);
        Usuario user = new Usuario();
        ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>(user.leerArchivo());

        System.out.println("Ingresa el DNI del usuario a añadir como administrador");
        String _dni = sc.nextLine();

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getDni().equals(_dni)) {
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
                    System.out.println(Utilidades.ANSI_RED + "Ingrese una opcion valida." + Utilidades.ANSI_RESET);
            }
        }
    }

    public void agregarBronce() {
        Bronze bronze = new Bronze();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de pasajeros");
        int cantPasajeros = sc.nextInt();

        bronze.setCantMaxPasajeros(cantPasajeros);
        bronze.setFechas(fechaDeVuelo());
        bronze.agregarEnArchivo();
    }

    public void agregarSilver() {
        Silver silver = new Silver();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de pasajeros");
        int cantPasajeros = sc.nextInt();

        silver.setCantMaxPasajeros(cantPasajeros);
        silver.setFechas(fechaDeVuelo());
        silver.agregarEnArchivo();
    }

    public void agregarGold() {
        Gold gold = new Gold();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de pasajeros");
        int cantPasajeros = sc.nextInt();

        gold.setCantMaxPasajeros(cantPasajeros);
        gold.setFechas(fechaDeVuelo());
        gold.agregarEnArchivo();
    }

    private void OpcionesAviones() {
        System.out.println("\tTipo de avion");
        System.out.println("1: Avion BRONZE");
        System.out.println("2: Avion SILVER");
        System.out.println("3: Avion GOLD");
        System.out.println("0: Salir al menu admin");
    }

    public void mostrarVuelosXFecha(LocalDate fecha) {
        Gold gold = new Gold();
        ArrayList<Gold> goldArrayList = new ArrayList<Gold>(gold.leerArchivo());
        Silver silver = new Silver();
        ArrayList<Silver> silverArrayList = new ArrayList<Silver>(silver.leerArchivo());
        Bronze bronze = new Bronze();
        ArrayList<Bronze> bronzesArrayList = new ArrayList<Bronze>(bronze.leerArchivo());
        boolean aux1 = false, aux2 = false, aux3 = false;

        if (goldArrayList != null) {
            for (Gold avion : goldArrayList) {
                if (avion.getFechas().equals(fecha)) {
                    System.out.println("Fechas aviones GOLD");
                    System.out.println(avion);
                }
            }
            if (!aux1) {
                System.out.println(Utilidades.ANSI_RED + "No hay vuelos Gold para la fecha solicitada." + Utilidades.ANSI_RESET);
            }
        }

        if (silverArrayList != null) {
            for (Silver avion : silverArrayList) {
                if (avion.getFechas().equals(fecha)) {
                    System.out.println("Fechas aviones SILVER");
                    System.out.println(avion);
                }
            }
            if (!aux2) {
                System.out.println(Utilidades.ANSI_RED + "No hay vuelos Silver para la fecha solicitada." + Utilidades.ANSI_RESET);
            }
        }

        if (bronzesArrayList != null) {
            for (Bronze avion : bronzesArrayList) {
                if (avion.getFechas().equals(fecha)) {
                    System.out.println("Fechas aviones BRONZE");
                    System.out.println(avion);
                    aux3 = true;
                }
            }
            if (!aux1) {
                System.out.println(Utilidades.ANSI_RED + "No hay vuelos Bronze para la fecha solicitada\n." + Utilidades.ANSI_RESET);
            }
        }
        //endregion
    }
}
