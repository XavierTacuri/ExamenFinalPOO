package ups.edu.modelo;

public class CalculadorMinasVecinas
{
    /**
     * Descubre una casilla.
     * @return true si se pisa una mina (pierde), false en caso contrario.
     */
    public void calcularMinasVecinas(Casilla[][] casillas, int TAMANO)
    {
        for (int i = 0; i < TAMANO; i++)
        {
            for (int j = 0; j < TAMANO; j++)
            {
                if (! casillas[i][j].esMina())
                {
                    int minas = contarMinasAlrededor(casillas, i, j, TAMANO);

                    for (int k = 0; k < minas; k++)
                    {
                        casillas[i][j].incrementarMinasVecinas();
                    }
                }
            }
        }
    }

    private int contarMinasAlrededor(Casilla[][] casillas, int fila, int col, int TAMANO)
    {
        int contador = 0;
        for (int i = fila - 1; i <= fila + 1; i++)
        {
            for (int j = col - 1; j <= col + 1; j++)
            {
                if (i >= 0 && i < TAMANO && j >= 0 && j < TAMANO)
                {
                    if (casillas[i][j].esMina())
                    {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }
}
