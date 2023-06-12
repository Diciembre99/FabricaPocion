package ficheros;
import java.io.File;
import aplicacion.Usuario;
import aplicacion.Producto;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author KRene
 */
public class EscritorKRCC {

    File fiche;

    public EscritorKRCC(File fiche) {
        this.fiche = fiche;
    }

    public EscritorKRCC() {
    }

    public File getFiche() {
        return fiche;
    }

    public void setFiche(File fiche) {
        this.fiche = fiche;
    }

    /**
     * Con este metodo se agrega un objeto a un fichero
     * @param o
     * @param agregar
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public int escribirKRCC(Object o, boolean agregar) throws FileNotFoundException, IOException {
        ObjectOutputStream oos = null;
        int v;
        try {
            if (!agregar) {
                //Evita colocar la cabecera
                oos = new ObjectOutputStreamKRCC(new FileOutputStream(fiche, true));
            } else {
                //Para agregar la cabecera si el fichero es primera vez que entra al archivo, se debe desactivar el concatenar
                oos = new ObjectOutputStream(new FileOutputStream(fiche, false));
            }
            oos.writeObject(o);
            v = 1;
            oos.flush();
        } catch (IOException ioe) {
            System.out.println("Error en la escritura"+ioe);
            v = -1;
        } catch (Exception e) {
            System.out.println("Se ha producido una excepcion" + e.getMessage());
            v = -1;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ioe) {
                    System.out.println("No se pudo cerrar el escritor" + ioe.getMessage());
                }
            }
        }
        return v;
    }

    /**
     *
     * @param lista
     * @return
     * @throws IOException
     */
    public int escribirKRCC(LinkedList<Usuario> lista) throws IOException {
        boolean primera;
        ListIterator<Usuario> iteradorUser = lista.listIterator();
        Usuario u;
        int valorDevuelto = 0;
        // se recorre la lista de usuarios pasada por parametro
        while (iteradorUser.hasNext()) {
            //Determinamos si es la primera vez que se entra al bucle para sobreescribir la lista anterior y agregar cabecera
            if (!iteradorUser.hasPrevious()) {
                primera = true;
            } else {
                primera = false;
            }
            u = iteradorUser.next();
            valorDevuelto = EscritorKRCC.this.escribirKRCC(u, primera);
        }
        if (valorDevuelto == 1) {
            return lista.size();
        } else {
            return -1;
        }
    }
    /**
     * Metodo para escribirKRCC una lista de productos
     * @param lista
     * @return
     * @throws IOException 
     */
    public int guardoProducto(LinkedList<Producto> lista) throws IOException {
        // uso de LinkedList
        boolean primera;
        ListIterator<Producto> iteradorProdct = lista.listIterator();
        Producto p;
        int valorDevuelto = 0;

        // se recorre la lista
        while (iteradorProdct.hasNext()) {
            if (!iteradorProdct.hasPrevious()) {
                primera = true;
            } else {
                primera = false;
            }
            p = iteradorProdct.next();
            valorDevuelto = EscritorKRCC.this.escribirKRCC(p, primera);
        }
        if (valorDevuelto == 1) {
            return lista.size();
        } else {
            return -1;
        }
    }

    
}
