package aplicacion;

import minas.Tablero;
import minas.Mina;
import ficheros.LectorKRCC;
import ficheros.EscritorKRCC;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import static ficheros.Utilidades.*;
import static ficheros.Grafico.*;
import java.io.FileNotFoundException;


/**
 *
 * @author KRene
 */
public class ProyectoFinal {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, AWTException, InterruptedException, FileNotFoundException, ClassNotFoundException {
        //REGEX para que la cadena contenga al menos 4 digitos y maximo 12, que deba tener un numero y un caracter especial como minimo
        final String REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z0-9!@#$%^&*()_+]{4,12}$";
        String usuario, clave, usuarioNombre;
        String claveA, claveNew;
        int cont = 3;
        char nuevo, borrar;
        int opcInv = 0, opcCount = 0;
        boolean bandera = true, banderaInv = true, banderaUser = true;
        //Elementos buscaminas
        int opc, intentos = 0;
        int puntaje;
        //Elementos guardado
        String nombrePocion;
        LinkedList<Usuario> usuarios = new LinkedList<>();
        LinkedList<Producto> productos = new LinkedList<>();
        File fileU = new File("users.txt");
        File fileP = new File("prodc.txt");
        //Creo los objetos para acceder a los metodos de guardado uno para cada tipo de archivo que vaya a escribirKRCC
        LectorKRCC cargarUsuario = new LectorKRCC(fileU);
        EscritorKRCC guardarUsuario = new EscritorKRCC(fileU);
        LectorKRCC cargarProducto = new LectorKRCC(fileP);
        EscritorKRCC guardarProducto = new EscritorKRCC(fileP);
        usuarios = cargarUsuario.LeerKRCC();
        //Usuario y producto para acceder a cualquier metodo y la lista tenga algun dato aunque este vacio el fichero
        Usuario u = new Usuario("Admin", "ADM1234");
        Pocion p = new Pocion();
        if (usuarios.isEmpty()) {
            usuarios.add(u);
        }
        login();
        //Inicializamos un usuario para hacer la busqueda
        Usuario userB = new Usuario();
        do {
            usuario = pedirCadena(BROWN + "\n\t\t\t USUARIO: ");
            //Se busca el usuario, si este no es encontrado retorna null
            userB = u.buscar(usuarios, usuario);
            if (userB == null) {
                System.out.println(RED + "\t\t\t El usuario no se ha encontrado");
                nuevo = pedirLetra(GREEN + "\t\t\t Desea registrar un nuevo usuario? (S/N) ");
                if (nuevo == 'S') {
                    do {
                        System.out.println("\t\t La clave debe tener 4-12 caracteres contener al menos un numero y un caracter especial por ejemplo Usuario123!");
                        clave = pedirCadena("\t\t\t Escribe la clave para el usuario " + usuario + ": ");
                    } while (!clave.matches(REGEX));
                    //Iniciamos de nuevo el usuario con los nuevos datos del registro
                    userB = new Usuario(usuario, clave);
                    usuarios.add(userB);

                }
            }
            //La condicion se va cumplir cada vez que el usuario no se encuentre
        } while (userB == null);

        clave = pedirCadena(BLACK + "\t\t\t CLAVE: ");
        //Comprobamos si la clave es correcta llamando al atributo del objeto que fue buscado
        if (!clave.equals(userB.getClave())) {
            //Se muetra una pista para intentar recordar la clave 
            System.out.println(RED + "\t\t\t Clave erronea..." + PURPLE + " Pista caracter inicial: " + userB.getClave().charAt(0));
            do {
                System.out.println(RED + "\t\tIntentos restantes: " + cont);
                clave = pedirCadena(BLACK + "\t\t\t CLAVE: ");
                cont--;
            } while (!clave.equals(userB.getClave()) && cont > 0);
            if (cont < 1) {
                System.out.println(RED + "\tIntentos Excedidos, el usuario ha sido eliminado, vuelve a iniciar la aplicacion para intentarlo de nuevo...");
                usuarios.remove(userB);
                usuarios.remove(u.buscar(usuarios, usuario));
                u.eliminarUser(usuarios, usuario);
                if (usuarios.isEmpty()) {
                    usuarios.add(u);
                }
                guardarUsuario.escribirKRCC(usuarios);
                return;
            }
        }
        guardarUsuario.escribirKRCC(usuarios);
        cls();
        logo();
        Thread.sleep(1000);
        //Cargaremos los recursos asociados al usuario
        productos = cargarProducto.leeP();
        p.buscar(productos, p);
        userB.getMateriales();
        //Buscamos si algun producto esta asociado con el nombre de un usuario y los asignamos a la variable que usaremos
        do {
            cls();
            menu();
            opc = pedirNum(GREEN + "\t\t----->  ");
            switch (opc) {
                case 1 -> {
                    fabricaDibujo();
                    Thread.sleep(1000);
                    cls();
                    enunciadoPocion();
                    Pocion nuevaPocion = new Pocion();
                    nombrePocion = pedirCadena("\t\t------>  ");
                    //Le agregamos el nombre al objeto que escogio el usuario
                    nuevaPocion.setNombre(nombrePocion);
                    nuevaPocion.fabricar(userB.getMateriales());
                    nuevaPocion.setUsuario(usuario);
                    System.out.println("Los detalles de tu pocion son: ");
                    System.out.println(nuevaPocion.toString());
                    dibujoP(nuevaPocion.getRarez());
                    //Agregamos a la lista de productos
                    productos.add(nuevaPocion);
                    guardarProducto.guardoProducto(productos);
                    guardarUsuario.escribirKRCC(usuarios);
                    pedirCadena("Escribe cualquie tecla para continuar...");
                }
                case 2 -> {
                    System.out.println("Sobrevive y consigue puntos para ganar ingredientes para tus pociones!");
                    dibujoMina();
                    Thread.sleep(2000);
                    Tablero tablero = new Tablero(5, 5, 5);
                    tablero.generarMinas();
                    tablero.generarNumeros();
                    intentos = (tablero.getSizeX() * tablero.getSizeY()) - tablero.getMina();
                    puntaje=userB.getPuntaje();
                    do {
                        cls();
                        do {
                            System.out.print("Tu puntaje es: " + puntaje);
                            System.out.println("");
                            tablero.tableroJugador();
                            System.out.print("\nEscribe la opcion que quieras escoger...\n");
                            opc = pedirNum(GREEN + "\t\t\t----->  ");
                        } while (opc > (tablero.getSizeX() * tablero.getSizeY()) - 1 || opc < 0);
                        tablero.opcionJugador(opc);
                        tablero.mostrarCeros();
                        puntaje = puntaje + 10;
                    } while (!tablero.ganador() && tablero.acierto(opc));
                    if (tablero.casillas.get(opc).isEstado()) {
                        if (tablero.casillas.get(opc) instanceof Mina) {
                            System.out.println("Nada mal... Puedes intentarlo cuando quieras!");
                        } else {
                            System.out.println("Felicidades ganaste!");
                            System.out.println("Obtienes un objeto UNICO!");
                            Thread.sleep(3000);
                            userB.ingrediente(Rareza.UNICO);
                            puntaje = 0;
                        }
                    }
                    tablero.imprimirTablero();
                    if (puntaje >= 100) {
                        System.out.println("Obtuviste un objeto RARO");
                        Thread.sleep(2000);
                        userB.ingrediente(Rareza.RARO);
                        puntaje = puntaje - 100;
                    }
                    if (puntaje >= 50) {
                        System.out.println("Obtuviste un objeto COMUN");
                        Thread.sleep(1000);
                        userB.ingrediente(Rareza.COMUN);
                        puntaje = puntaje - 50;

                    }
                    userB.setPuntaje(puntaje);
                    guardarUsuario.escribirKRCC(usuarios);
                }
                case 3 -> {
                    do {
                        dibujoMenuInv();
                        opcInv = pedirNum(GREEN + "\t\t----->  ");
                        switch (opcInv) {
                            case 1:
                                if (productos.isEmpty()) {
                                    System.out.println("No tienes ningun elemento en tu inventario...");
                                } else {
                                    String nombreP;
                                    System.out.println("Escribe el nombre del producto que quieres eliminar");
                                    nombreP = pedirCadena(GREEN + "\t\t------>  ");

                                    for (Producto pdt : productos) {
                                        if (pdt.getNombre().equalsIgnoreCase(nombreP)) {
                                            System.out.println(pdt.toString());
                                            System.out.println("Desea eliminar esta pocion de tu inventario? (S/N)");
                                            borrar = pedirLetra(GREEN + "\t\t----->  ");
                                            if (borrar == 'S') {
                                                productos.remove(pdt);
                                                guardarProducto.guardoProducto(productos);
                                            }
                                        }
                                    }
                                }
                                break;
                            case 2:

                                if (productos.isEmpty()) {
                                    System.out.println("No tienes ningun elemento en tu inventario...");
                                }
                                for (Producto pdt : productos) {
                                    //Es necesario hacer un instanceof para acceder a la rarez del objeto
                                    if (pdt instanceof Pocion pocion) {
                                        //Con esta condicion filtramos los objetos que han sido creados por ese usuario
                                        if (pdt.getUsuario().equalsIgnoreCase(usuario)) {
                                            System.out.println(pdt.toString());
                                            //Enviamos la rareza para que se coloree segun la rarez de la pocion
                                            dibujoP(pocion.getRarez());
                                            Thread.sleep(500);
                                        }
                                    }
                                }
                                pedirCadena("Escribe cualquie tecla para continuar...");
                                break;
                            case 3:
                                userB.listaIngredientes();
                                pedirCadena("Escribe cualquie tecla para continuar...");
                                break;
                            case 4:
                                banderaInv = false;
                                break;
                            default:
                                System.out.println("Esa opcion no es valida");
                        }
                    } while (banderaInv);
                }
                case 4 -> {
                    do {
                        menuUser();
                        opcCount = pedirNum(GREEN + "\t\t----->  ");
                        switch (opcCount) {
                            case 1 -> {
                                u.eliminarUser(usuarios, usuario);
                                usuarios.remove(userB);
                                usuarios.remove(u.buscar(usuarios, usuario));
                                guardarUsuario.escribirKRCC(usuarios);
                                for (Producto pdt : productos) {
                                    if (pdt.getUsuario().equalsIgnoreCase(usuario)) {
                                        productos.remove(pdt);
                                    }
                                }
                                guardarProducto.guardoProducto(productos);
                                System.out.println("La cuenta a sido eliminada con exito!");
                                return;
                            }
                            case 2 -> {
                                //Revisamos que los datos coinciden con los de la sesion actual
                                System.out.println("Escribe tu clave anterior para cambiar a una nueva");
                                claveA = pedirCadena("\t\t----->  ");
                                if (!claveA.equals(userB.getClave())) {
                                    System.out.println(RED + "Clave incorrecta");
                                } else {
                                    do {
                                        System.out.println("Escribe una nueva clave que debe tener 4-12 digitos contener al menos un digito y un caracter especial");
                                        claveNew = pedirCadena("\t\t----->  ");
                                    } while (!claveNew.matches(REGEX));
                                    //Guardamos los cambios que se hicieron
                                    u.buscar(usuarios, usuario).setClave(claveNew);
                                    guardarUsuario.escribirKRCC(usuarios);
                                    System.out.println(GREEN + "Clave guardada con exito");
                                }
                            }
                            case 3 -> {
                                //Listado de usuarios
                                cargarUsuario.LeerUKRCC();
                            }
                            case 4 -> {
                                Usuario ub = new Usuario();
                                usuarioNombre = pedirCadena("Escribe el nombre del usuario que quieres buscar ");
                                //Si el usuario se encuentra se mostrara su informacion
                                ub = cargarUsuario.busca(usuarioNombre);
                                if (ub != null) {
                                    System.out.println(ub.toString());
                                }
                            }
                            case 5 -> {
                                banderaUser = false;
                                System.out.println("Volviendo al menu principal...");
                            }
                            default -> {
                                System.out.println("Escribe una opcion valida....");
                            }
                        }
                    } while (banderaUser);

                }
                case 5 -> {
                    System.out.println(GREEN + "\t\t\t BIENVENIDO A LA FABRICA DE POCIONES!\n\n" + BLACK);
                    System.out.println("\tPuedes fabricar tus pociones con agua, pero si quieres que sean fuerte tendras que usar ingredientes...");
                    System.out.println("\tPuedes conseguir ingredientes en el campo minado, minetras mas sobrevivas ahi sera mejor el premio!");
                    System.out.println("\tPara sobrevivir debes seleccionar la casilla que vas a pisar, las casillas mostraran unos numeros");
                    System.out.println("\tEstos numeros indican cuantas bombas hay alrededor tuyo. Deberias iniciar por las que tiene 0 para no morir al principio");
                    System.out.println("Si consigues mas de 50 puntos obtendras un material comun, si tienes mas de 100 tendras uno raro y si pasas el campo minado tendras uno UNICO!");
                    System.out.println("Tranquilo parece complicado al inicio, pero los puntos podras conservarlos e intentarlo de nuevo luego");
                    System.out.println("\tCuando cuentes con suficientes ingredientes podras fabricar pociones como loco, selecciona tu material, su tiempo de fabricacion y listo!");
                    System.out.println("\n\t\t COMUN: Suelen ser de color " + YELLOW + "Amarillo" + BLACK);
                    System.out.println("\n\t\t RARO: Suele ser de color " + GREEN + "Verde" + BLACK);
                    System.out.println("\n\t\t UNICO: Suelen ser de colo " + CYAN + "TURQUEZA" + BLACK);
                    System.out.println("\n\n\t\t\t Buena suerte en tu aventura!");
                    pedirCadena("Escribe cualquie tecla para continuar...");
                }
                case 6 -> {
                    bandera = false;
                }
                default ->
                    System.out.println(RED + "Escribe una opcion valida");
            }

        } while (bandera);
    }

}
