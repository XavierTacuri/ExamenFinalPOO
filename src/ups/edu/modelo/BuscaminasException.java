package ups.edu.modelo;

public abstract class BuscaminasException extends Exception
{
        public BuscaminasException(String mensaje)
        {
            super(mensaje);
        }

        public BuscaminasException(String mensaje, Throwable causa)
        {
            super(mensaje, causa);
        }

}
