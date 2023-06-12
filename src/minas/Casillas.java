package minas;
/**
 * @author KRene
 */
public abstract class Casillas implements Dibujable, Comparable<Casillas> {
    private boolean estado;
    private int posicionX;
    private int posicionY;
    public Casillas(boolean estado, int posicionX, int posicionY) {
        this.estado = estado;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
    }

    public Casillas(int posicionX, int posicionY) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.estado = true;
    }
    public Casillas() {
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }
    public int getPosicionY() {
        return posicionY;
    }
    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    /**
     * Metodo obligatorio para ordenar las casillas con el sort
     * @param o
     * @return 
     */
    @Override
    public int compareTo(Casillas o) {
        if (this.posicionX == o.posicionX) {
            return Integer.compare(this.posicionY, o.posicionY);
        } else {
            return Integer.compare(posicionX, o.getPosicionX());
        }
    }
}
