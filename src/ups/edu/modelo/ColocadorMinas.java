package ups.edu.modelo;

import java.util.Random;

public class ColocadorMinas
{
    //Aplicar principio OCP la logica se encapsula en esta clase
    public void colocarMinas(Casilla[][] casillas, int TAMANO, int NUM_MINAS) {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < NUM_MINAS) {
            int fila = rand.nextInt(TAMANO);
            int col = rand.nextInt(TAMANO);
            if (!casillas[fila][col].esMina()) {
                casillas[fila][col].ponerMina();
                minasColocadas++;
            }
        }
    }
}
