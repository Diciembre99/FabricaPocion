package aplicacion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author KRene
 */
public abstract class Producto implements Comparable<Producto>, Serializable {

    //Atributos
    private int stock;
    public final int STOCKINCIAL = 1;
    private String nombre;
    private boolean disponible;
    private String usuario;
    public ArrayList<ArrayList<String>> materiales = new ArrayList<>();

    /**
     * Constructor de un productos con todos los datos
     * @param nombre
     * @param stock
     */
    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.disponible = true;
    }

    /**
     * Constructor de productos comun
     *
     * @param nombre
     * @param disponible
     */
    public Producto(String nombre, boolean disponible) {
        this.stock = STOCKINCIAL;
        this.nombre = nombre;
        this.disponible = disponible;
    }

    //Constructor vacion para acceder a sus metodos
    public Producto() {
        this.stock = STOCKINCIAL;
    }

    //Getters and Setters
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public ArrayList<ArrayList<String>> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<ArrayList<String>> materiales) {
        this.materiales = materiales;
    }

    /**
     * Metodo toString padre
     *
     * @return
     */
    @Override
    public String toString() {
        return "\tNombre: " + this.nombre + "\n\tCantidad: " + this.stock;
    }

    /**
     * Busca si un producto pertenece a un usuario en especifico, asi este aparece en su inventario
     * @param productos
     * @param p
     * @return producto buscado
     */
    public Producto buscar(LinkedList<Producto> productos, Producto p) {
        Producto proct = new Pocion();
        for (Producto producto : productos) {
            //Cada producto tiene vinculado el nombre del usuario para que este pueda ser vinculado con el objeto
            if (producto.getUsuario() == usuario) {
                Producto proctB = producto;
            }
        }
        return proct;
    }

    /**
     * CompareTo que compara por nombre
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Producto o) {
        return this.nombre.compareTo(o.getNombre());
    }

    /**
     * Metodo que devuelve si un producto existe en la lista o no
     * @param producto 
     */
    public void existe(Producto producto) {
        if (producto != null) {
            System.out.println("El objeto no existe");
        } else {
            producto.toString();
        }
    }
}
