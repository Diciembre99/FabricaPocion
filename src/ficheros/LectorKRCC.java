package ficheros;

import aplicacion.Usuario;
import aplicacion.Producto;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

/**
 *
 * @author KRene
 */
public class LectorKRCC {

    File file;

    public LectorKRCC() {
    }

    public LectorKRCC(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Muestra los usuarios que hay dentro de un fichero
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public int LeerUKRCC() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
        Object o;
        int v = 0;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            o = (Usuario) ois.readObject();
            while (o != null) {
                v++;
                System.out.println("Datos del usuario " + (v));
                System.out.println(o.toString());
                o = (Usuario) ois.readObject();
            }
        } catch (EOFException eofe) {
            System.out.println("Hay un total de " + v + " Usuarios registrados");
        } catch (IOException ioe) {
            System.out.println("Error en la lectura" + ioe.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("La clase no ha sido encontrada" + cnfe.getMessage());
        } catch (Exception e) {
            System.out.println("Error en la lectura " + e.getMessage());
        }
        return v;
    }

    /**
     * Recupera los usuarios guardados en un fichero y los almacena todos dentro
     * de un a lista dinamica
     *
     * @return lista de usuarios
     */
    public LinkedList<Usuario> LeerKRCC() {
        LinkedList<Usuario> listaUsuario = new LinkedList<>();
        Usuario u;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //Se recorre el fichero hasta que se lean todos, cuando ya no haya salta la excepcion eofe y se muestra la cantidad de usuarios que se leyeron
            u = (Usuario) ois.readObject();
            while (u != null) {
                listaUsuario.add(u);
                u = (Usuario) ois.readObject();
            }//Fin del while 
        } catch (EOFException eofe) {
        } catch (ClassNotFoundException cnfe) {
            System.out.println("La clase no se ha encontrada: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("ERROR de E/S: " + ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Se ha producido una excepción al recorrer el fichero: " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        return listaUsuario;
    }

    /**
     * Metodo para leer el fichero y recuperarlo en una lista dinamica del
     * objeto Producto
     *
     * @return lista de objetos recuperados
     */
    public LinkedList<Producto> leeP() {
        LinkedList<Producto> listaProducto = new LinkedList<>();
        Producto p;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            //Mientras haya Películas en el fichero se ejecuta el while
            //Cuando salte la excepcion de EOF sale del bucle
            p = (Producto) ois.readObject();
            while (p != null) {
                listaProducto.add(p);
                p = (Producto) ois.readObject();
            }//Fin del while 
        } catch (EOFException eofe) {
        } catch (ClassNotFoundException cnfe) {
            System.out.println("La clase no se ha encontrada: " + cnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("ERROR de E/S: " + ioe.getMessage());
        } catch (Exception e) {
            System.out.println("Se ha producido una excepción al recorrer el fichero: " + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        return listaProducto;
    }

    /**
     * Buscador de usuarios, busca un usuario por su nombre de usuario y devuelve el objeto encontrado
     * @param nick
     * @return usuario buscado o null si no se encuentra
     */
    public Usuario busca(String nick) {
        Usuario u = null;
        boolean encontrado = false;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            u = (Usuario) ois.readObject();
            while (u != null && !encontrado) {
                if (u.getUsuario().equals(nick)) {
                    encontrado = true;
                } else {
                    u = (Usuario) ois.readObject();
                }
            }
            if (!encontrado) {
                u = null;
            }
        } catch (EOFException eofe) {
            System.out.println("El usuario que has solicitado no parece estar registrado:  " + nick);
            u = null;
        } catch (IOException ioe) {
            System.out.println("ERROR de E/S: " + ioe.getMessage());
            u = null;
        } catch (Exception ex) {
            System.out.println("ERROR al leer el fichero: " + ex.getMessage());
            u = null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error al cerrar el stream de lectura durante la búsqueda " + ioe.getMessage());
            }
        }
        return u;
    }
}
