package ups.edu.modelo;

import java.io.Serializable;

public class Casilla implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean esMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasVecinas;

    public Casilla() {
        this.esMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasVecinas = 0;
    }

    public boolean esMina() {
        return esMina;
    }

    public void ponerMina() {
        this.esMina = true;
    }

    public boolean estaDescubierta() {
        return descubierta;
    }

    public void descubrir() {
        this.descubierta = true;
    }

    public boolean estaMarcada() {
        return marcada;
    }

    public void alternarMarcada() {
        this.marcada = !this.marcada;
    }

    public int getMinasVecinas() {
        return minasVecinas;
    }

    public void incrementarMinasVecinas() {
        this.minasVecinas++;
    }
}