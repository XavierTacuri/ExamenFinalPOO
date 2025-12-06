package ups.edu.modelo;

public class ValidadorVictoria
{
    public boolean esVictoria(Casilla[][] casillas, int TAMANO)
    {
        for (int i = 0; i < TAMANO; i++)
        {
            for (int j = 0; j < TAMANO; j++)
            {
                Casilla c = casillas[i][j];
                if (!c.esMina() && !c.estaDescubierta())
                {
                    return false;
                }
            }
        }
        return true;
    }
}
