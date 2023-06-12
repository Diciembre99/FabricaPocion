package aplicacion;

import static ficheros.Utilidades.*;
import java.util.ArrayList;

/**
 *
 * @author KRene
 */
public class Pocion extends Producto {

    private String ingrediente;
    private Rareza rarez;
    private String descripcion;

    /**
     * Constructor completo de una pocion
     * @param descripcion
     * @param nombre
     * @param stock 
     */
    public Pocion(String descripcion, String nombre, int stock) {
        super(nombre, stock);
        this.descripcion = descripcion;
    }

    /**
     * Constructor donde se pide solo nombre
     * @param nombre
     * @param disponible 
     */
    public Pocion(String nombre, boolean disponible) {
        super(nombre, disponible);
    }

    /**
     * Metodo vacio para alcanzar sus metodos
     */
    public Pocion() {
        this.setDisponible(true);
    }

    //GETTERS AND SETTERS
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Rareza getRarez() {
        return rarez;
    }

    public void setRarez(Rareza rarez) {
        this.rarez = rarez;
    }
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return """
                                    +------------+
                                    |   Poción   |
                                    +------------+
               """ + super.toString() + "\n\tingrediente: " + ingrediente + "\n\tRareza: " + this.rarez;
    }

    /**
     * Genera la pocion apartir del ingrediente y la preparacion
     *
     * @param porcentaje
     */
    public void generarPocion(int porcentaje) {
        int aleatorio;
        //Si no se utilizo ningun material se le asigna agua como material que aporta cero a la fuerza
        if (this.rarez == null) {
            this.setIngrediente("Agua");
            this.setRarez(rarez.VACIO);
        }
        //Segun la reza se le asigna mas porcentaje de fuerza a la pocion
        switch (this.rarez) {
            case COMUN:
                porcentaje = porcentaje + 10;
                break;
            case RARO:
                porcentaje = porcentaje + 20;
                break;
            case UNICO:
                porcentaje = porcentaje + 30;
                break;
            default:
                porcentaje = +0;
        }
        //Se agrega otro valor aleatorio que puede dar juego a aumentarle la fuerza a una pocion
        aleatorio = (int) (Math.random() * 40+1);
        porcentaje = porcentaje + aleatorio;
        //Segun su porcentaje de fuerza se le asigna un tipo de rareza
        if (porcentaje <= 40) {
            System.out.println("Su pocion es de tipo" + YELLOW + " COMUN");
            this.setRarez(rarez.COMUN);
        }
        if (porcentaje > 40 && porcentaje <= 70) {
            System.out.println("Su pocion es de tipo" + GREEN + " RARO");
             this.setRarez(rarez.RARO);
        }
        if (porcentaje > 70 && porcentaje <= 100) {
            System.out.println("HAZ OBTENIDO UNA POCON " + CYAN + " LEGENDARIA!!");
             this.setRarez(rarez.UNICO);
        }
        System.out.println("El porcentaje de fuerza fue de: " + porcentaje);

    }

    /**
     * Metodo que genera una pocion, segun que ingrediente y segun que tipo
     * genera una pocion
     * @param lista
     * @throws java.lang.InterruptedException
     */
    public void fabricar(ArrayList<ArrayList<String>> lista) throws InterruptedException {
        int opc;
        int porcentaje = 0;
        usarIngrediente(lista);
        System.out.println("Vamos a fabricar la pocion depende de su fabricacion sabremos su fuerza");

        do {
            System.out.println("Cuanto tiempo quieres preparar tu mezcla magica?");
            System.out.println("""
                                       [1]          [2]             [3]
                                  5 Segundos    10 Segundos     15 Segundos
                           """);
            opc = pedirNum(GREEN + "----->  ");
            switch (opc) {
                case 1:
                    System.out.println(BLUE + "********Fabricadon pocion...***********");
                    Thread.sleep(5000);
                    porcentaje = 10;

                    break;
                case 2:
                    System.out.println(BLUE + "********Fabricadon pocion...***********");
                    Thread.sleep(10000);
                    porcentaje = 20;
                    break;
                case 3:
                    System.out.println(BLUE + "********Fabricadon pocion...***********");
                    Thread.sleep(15000);
                    porcentaje = 30;
                    break;
                default:
                    System.out.println("Escribe una opcion valida");
            }
        } while (opc < 1 || opc > 3);
        generarPocion(porcentaje);

    }

    /**
     * Metodo para fabricar pocion, muestra 9 opciones de ingredientes segun el
     * tipo de pocion que sea
     *
     * @param materiales
     */
    public void usarIngrediente(ArrayList<ArrayList<String>> materiales) {
        System.out.println("Para fabricar la pocion, debes escoger alguno de los ingredientes con los que cuentas");
        System.out.println("La lista de los ingredientes con los que cuentas es la siguiente:");
        if (materiales.isEmpty()) {
            System.out.println("No tienes ningun material en tu inventario");
        } else {
            for (ArrayList<String> material : materiales) {
                System.out.println(material);
            }
            seleccion(materiales);
        }

    }

    /**
     * Metodo para que el usuario seleccion el material que va utilizar en su mezcla
     * @param materiales
     * @return 
     */
    public String seleccion(ArrayList<ArrayList<String>> materiales) {
        boolean encontrado = false;
        String nombre = "";
        char opc = 0;
        do {
            System.out.println("Selecciona un material para crear tu pocion");
            nombre = pedirCadena(GREEN + "\t\t------>  ");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < materiales.get(i).size(); j++) {
                    if (materiales.get(i).get(j).equalsIgnoreCase(nombre)) {
                        System.out.println("Haz utilizado el material: " + nombre);
                        materiales.get(i).remove(j);
                        encontrado = true;
                        opc='0';
                        this.setIngrediente(ingrediente);
                        if (i == 0) {
                            setRarez(rarez.COMUN);
                            setIngrediente(nombre);
                        } else if (i == 1) {
                            setRarez(rarez.RARO);
                            setIngrediente(nombre);
                        } else {
                            setRarez(rarez.UNICO);
                            setIngrediente(nombre);
                        }
                    }
                }
            }
            if (!encontrado) {
                opc = pedirLetra("El material no se ha encontrado, desea intentarlo de nuevo? (S/N) ");
            }
        } while (opc == 'S' );

        return nombre;
    }
}
