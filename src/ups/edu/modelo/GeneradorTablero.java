package ups.edu.modelo;

public class GeneradorTablero
{
    public Casilla[][] inicializarTablero(int TAMANO)
    {
        Casilla[][] casillas = new Casilla[TAMANO][TAMANO];
        for (int i = 0; i < TAMANO; i++)
        {
            for (int j = 0; j < TAMANO; j++)
            {
                casillas[i][j] = new Casilla();
            }
        }
        return casillas;
    }
}
