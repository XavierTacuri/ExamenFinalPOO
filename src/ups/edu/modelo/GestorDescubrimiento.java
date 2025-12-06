package ups.edu.modelo;

public class GestorDescubrimiento
{
    //OCP aplicado metodo recursivo aislado para descubrir casillas en cascada
    public boolean descubrirCasilla(Casilla[][] casillas, int fila, int col, int TAMANO)
            throws CasillaDescubierta, JuegoException {

        Casilla c = casillas[fila][col];

        if (c.estaDescubierta())
        {
            throw new CasillaDescubierta("La casilla ya está descubierta.");
        }

        if (c.estaMarcada())
        {
            throw new JuegoException("No puedes descubrir una casilla marcada.");
        }

        c.descubrir();

        if (c.esMina())
        {
            return true;
        }

        // Si no hay minas alrededor, descubre en cascada
        if (c.getMinasVecinas() == 0)
        {
            descubrirVecinos(casillas, fila, col, TAMANO);
        }

        return false;
    }

    private void descubrirVecinos(Casilla[][] casillas, int fila, int col, int TAMANO)
            throws JuegoException, CasillaDescubierta {

        for (int i = fila - 1; i <= fila + 1; i++)
        {
            for (int j = col - 1; j <= col + 1; j++)
            {
                if (i >= 0 && i < TAMANO && j >= 0 && j < TAMANO)
                {
                    if (i == fila && j == col) continue;

                    Casilla vecino = casillas[i][j];
                    if (!vecino.estaDescubierta() && !vecino.esMina() && !vecino.estaMarcada())
                    {
                        vecino.descubrir();
                        if (vecino.getMinasVecinas() == 0)
                        {
                            descubrirVecinos(casillas, i, j, TAMANO);
                        }
                    }
                }
            }
        }
    }
}
