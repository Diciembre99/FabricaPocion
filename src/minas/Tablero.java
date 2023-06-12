package minas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author KRene
 */
public class Tablero {

    private int sizeX, mina, sizeY;
    public ArrayList<Casillas> casillas = new ArrayList<Casillas>();
    public ArrayList<Numero> numeros = new ArrayList<Numero>();
    public ArrayList<Mina> minas = new ArrayList<Mina>();

    /**
     * Metodo obligatorio para la creacion de un tablero de juego
     * @param sizeX
     * @param mina
     * @param sizeY 
     */
    public Tablero(int sizeX, int mina, int sizeY) {
        this.sizeX = sizeX;
        this.mina = mina;
        this.sizeY = sizeY;
    }

    /**
     * Asigna las posiciones a las casillas dentro del tablero
     * @return 
     */
    public int posicion() {
        int area = sizeX;
        return (int) ((Math.random() * area) + 1);
    }

    /**
     * Metodo para generar las minas que estaran dentro del tablero
     */
    public void generarMinas() {
        for (int i = 0; i < this.mina;) {
            Mina bomb = new Mina();
            bomb.setPosicionX(this.posicion());
            bomb.setPosicionY(this.posicion());
            if (revisarLista(bomb)) {
                casillas.add(bomb);
                minas.add(bomb);
                i++;
            }
        }
    }

    /**
     * Metodo para mostrar el tablero al jugador segun su estado, si la casilla esta usada o no
     */
    public void tableroJugador() {
        int cont = 0;
        //Se debe ordenar para que visualmente las casillas tengan sentido
        Collections.sort(casillas);
        //Al estar ordenado van en orden y se pueden colocar en filas y columnas respectivamente como una matriz
        for (int i = 0; i < this.getSizeY(); i++) {
            if (casillas.get(cont).isEstado()) {
                casillas.get(cont).dibujar();
            } else {
                System.out.print("\t[  ] " + cont);
            }

            //Hay un contador distinto para que las filas se puedan ir colocando de forma correcta
            cont++;
            for (int j = 1; j < this.getSizeX(); j++) {
                if (casillas.get(cont).isEstado()) {
                    casillas.get(cont).dibujar();
                } else {
                    System.out.print("\t[  ] " + cont);
                }

                cont++;
            }
            System.out.println("");
        }
    }

    /**
     * Determina si la casilla ya fue usada o si no para cambiarle el estado a usada
     * @param opc 
     */
    public void opcionJugador(int opc) {
        Collections.sort(casillas);
        if (casillas.get(opc).isEstado()) {
            System.out.println("Esa casilla ya la usaste");
        } else {

            casillas.get(opc).setEstado(true);
        }

    }

    /**
     * Metodo para mostrar las casillas que no tienen valor, para aumentar la velocidad del juego
     */
    public void mostrarCeros() {
        boolean cambiar = false;
        for (Casillas casilla : casillas) {
            if (casilla instanceof Numero numero) {
                //Si la casilla esta en cero cambia su estado a revelada
                if (numero.getValor() == 0 && !numero.isEstado()) {
                    cambiar = true;
                }
            }
            if (cambiar) {
                casilla.setEstado(true);
            }
            cambiar = false;
        }
    }

    /**
     * Metodo para ocultar el tablero entero
     */
    public void ocultarTablero() {
        int cont = 0;
        for (int i = 0; i < sizeY; i++) {
            System.out.print("\t[   ] " + cont);
            cont++;
            for (int j = 1; j < sizeX; j++) {
                System.out.print("\t[   ] " + cont);
                cont++;
            }
            System.out.println("");
        }
    }

    /**
     * Muestra el tablero por completo
     */
    public void imprimirTablero() {
        int cont = 0;
        Collections.sort(casillas);
        for (int i = 0; i < this.getSizeY(); i++) {
            casillas.get(cont).dibujar();
            cont++;
            for (int j = 1; j < this.getSizeX(); j++) {
                casillas.get(cont).dibujar();
                cont++;
            }
            System.out.println("");
        }
    }

    /**
     * Metodo que le asigna el valor a la casilla de numero, determinando si tiene minas a su alredeodr
     */
    public void generarNumeros() {
        int total, num, x = 0, y = 0;
        int cont = 0;
        //Necesitamos saber el total de casillas de las cuales no hay minas para que no se repitan
        total = (sizeX * sizeY) - mina;
        for (int i = 0; i < total;) {
            Numero numero = new Numero();
            //Les asigna una ubicacion con sus posiciones X y Y
            numero.setPosicionX(this.posicion());
            numero.setPosicionY(this.posicion());
            if (revisarLista(numero)) {
                //Si la casilla paso la condicion de noser una bomba se agrega a la lista
                numeros.add(numero);
                casillas.add(numero);
                i++;
            }
        }

        //Aca llamamos al metodo de asignarle un numero hasta que revise que todas las casillas alredodr si son minas o no y el contador vaya aumentando
        for (Numero numero : numeros) {
            cont = 0;
            for (Mina mina1 : minas) {
                num = numero.generarNum(mina1.getPosicionX(), mina1.getPosicionY());
                cont = cont + num;
            }
            numero.setValor(cont);
        }
    }

    /**
     * Metodo que determina si la opcion del jugador no fue una mina, si es una mina pierde
     * @param opc
     * @return 
     */
    public boolean acierto(int opc) {
        if (casillas.get(opc) instanceof Mina) {
            return false;
        }
        return true;
    }

    /**
     * Determina si el jugador revelo todas las casillas y se determina si gano
     * @return Revelo todas las casillas
     */
    public boolean ganador() {
        boolean ganador = false;
        int cont=0;
        for (Casillas casilla : casillas) {
            if (casilla instanceof Numero) {
                if (casilla.isEstado()) {
                    cont++;
                }
            }
        }
        if (cont==numeros.size()) {
            ganador=true;
        }
        return ganador;
    }

    /**
     * Metodo que dice si una casilla ya fue utilizada por una mina
     * @param bomb
     * @return 
     */
    private boolean revisarLista(Casillas bomb) {
        boolean check = true;
        //Recorre la lista para saber si esa mina fue escrita antes
        for (Casillas mina1 : casillas) {
            if (mina1.equals(bomb)) {
                check = false;
            }
        }
        return check;
    }

    //Getters and Setters
    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getMina() {
        return mina;
    }

    public void setMina(int mina) {
        this.mina = mina;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public ArrayList<Casillas> getMinas() {
        return casillas;
    }

    public void setMinas(ArrayList<Casillas> minas) {
        this.casillas = minas;
    }

    /**
     * ToString para saber el contenido del tablero en caso de necesitarlo, no deberia ser mostrado al usuario
     * @return 
     */
    @Override
    public String toString() {
        return "Tablero{" + "sizeX=" + sizeX + ", mina=" + mina + ", sizeY=" + sizeY + ", minas=" + casillas + '}';
    }

}
