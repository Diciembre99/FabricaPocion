package aplicacion;

import aplicacion.Rareza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author KRene
 */
public class Usuario implements Serializable {

    private String usuario, clave;
    private int puntaje;
    ArrayList<ArrayList<String>> materiales = new ArrayList<>();
    //Lista de todos los objetos disponibles
    public final String[][] MATERIAL
            = {{"COMUN", "albahaca", "menta", "lavanda", "manzanilla", "romero"},
            {"RARO", "amatista", "cuarzo", "opalo", "turquesa"},
            {"UNICO", "polvo de hadas", "lagrimas de unicornio", "esencia de luna"}};

    /**
     * Constructor obligatorio para crear un usuario
     *
     * @param usuario
     * @param clave
     */
    public Usuario(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    /**
     * Constructor vacio para alcanzar los metodos del objeto
     */
    public Usuario() {
    }

    public String getUsuario() {
        return usuario;
    }
    //GETTERS AND SETTERS
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public ArrayList<ArrayList<String>> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<ArrayList<String>> materiales) {
        this.materiales = materiales;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public void registrar() {

    }

    /**
     * Metodo que busca la existencia de un usuario dentro de un linkedlist
     *
     * @param usuarios
     * @param usuario
     * @return El usuario buscado
     */
    public Usuario buscar(LinkedList<Usuario> usuarios, String usuario) {
        for (Usuario user : usuarios) {
            if (usuario.equals(user.getUsuario())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Metodo que elimina un usuario de la lista
     *
     * @param usuarios
     * @param usuario
     */
    public void eliminarUser(LinkedList<Usuario> usuarios, String usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsuario() == usuario) {
                usuarios.remove(i);
            }
        }
    }

    /**
     * Metodo donde se almacenan los ingredientes que le pertenecen al jugador
     *
     * @param rarez
     */
    public void ingrediente(Rareza rarez) {
        int random;
        String ingrediente;
        if (materiales.isEmpty()) {
            materiales.add(new ArrayList<>());
            materiales.get(0).add("COMUN");
            materiales.add(new ArrayList<>());
            materiales.get(1).add("RARO");
            materiales.add(new ArrayList<>());
            materiales.get(2).add("UNICO");
        }
        switch (rarez) {
            case COMUN:
                random = (int) (Math.random() * 5 + 1);
                ingrediente = MATERIAL[0][random];
                materiales.get(0).add(ingrediente);
                break;
            case RARO:
                random = (int) (Math.random() * 4 + 1);
                ingrediente = MATERIAL[1][random];
                materiales.get(1).add(ingrediente);
                break;
            case UNICO:
                random = (int) (Math.random() * 3 + 1);
                ingrediente = MATERIAL[2][random];
                materiales.get(2).add(ingrediente);
                break;
        }
    }

    /**
     * Metodo para mostrar los ingredientes que tiene el usuario
     */
    public void listaIngredientes() {
        if (materiales.isEmpty()) {
            System.out.println("No tienes ningun material aun...");
        } else {
            for (ArrayList<String> materiale : materiales) {
                System.out.println(materiale);
            }
        }

    }

    /**
     * Muestra los datos del usuario
     *
     * @return
     */
    @Override
    public String toString() {
        return "Usuario:" + "Nombre de Usuario: " + usuario + ", clave: " + clave;
    }
}
