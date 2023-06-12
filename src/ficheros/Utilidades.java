package ficheros;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author KRene
 */
public class Utilidades {

    // Colores que se usan en toda la aplicacion
    public static final String BROWN = "\033[33m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    /**
     * Metodo que pide al usuario una cadenad de texto
     *
     * @param mensaje
     * @return cadena de texto
     */
    public static String pedirCadena(String mensaje) {
        String cadena = "";
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        try {
            do {
                System.out.print(GREEN + mensaje);
                cadena = bfr.readLine().trim();
            } while (cadena.isEmpty());

        } catch (IOException ioe) {
            System.out.println("Hay un problema con la lectura de los datos " + ioe);
        }
        return cadena;
    }

    /**
     * Pide un valor tipo int al usuario
     *
     * @param mensaje que se desaa mostrar
     * @return numero esperado
     */
    public static int pedirNum(String mensaje) {
        int num = 0;
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(mensaje);
        try {
            do {
                num = Integer.parseInt(bfr.readLine());
            } while (num < 0);

        } catch (NumberFormatException nfe) {
            System.out.println(RED + "Tipo de dato equivocado... " + nfe);
        } catch (IOException ioe) {
            System.out.println(RED + "Hay un problema con la lectura de los datos " + ioe);
        }
        return num;
    }

    /**
     * Metodo que controla los posibles erores y pide un caracter al usuario
     *
     * @param mensaje
     * @return Letra esperada
     */
    public static char pedirLetra(String mensaje) {
        char c = '$';
        BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(mensaje);
        try {

            c = bfr.readLine().trim().toUpperCase().charAt(0);;
        } catch (IOException ioe) {
            System.out.println(RED + "Hay un problema con la lectura de los datos " + ioe);
        } catch (StringIndexOutOfBoundsException sibe) {
            System.out.println("No fue el dato esperado..." + sibe);
        }
        return c;
    }

    /**
     * Metodo que limpia la pantalla
     *
     * @throws AWTException
     * @throws InterruptedException
     */
    public static void cls() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        Thread.sleep(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_L);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_L);
        Thread.sleep(1000);
    }

    public static boolean validar() {
        String clave;
        boolean valido = false;
        final String REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+])[A-Za-z0-9!@#$%^&*()_+]{4,12}$";
        clave = pedirCadena("Escribe el nombre la clave del usuario");
        if (clave.matches(REGEX)) {
            valido=true;
        }else{
            valido=false;
        }
        return valido;

    }
}
