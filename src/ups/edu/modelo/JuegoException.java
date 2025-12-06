package ups.edu.modelo;

public class JuegoException extends BuscaminasException
{
    /*
    Principio LSP aplicado se tiene una clase base BuscaminasException y clases
    derivadas como JuegoException que heredan de la clase base.
    Esto permite que cualquier instancia de JuegoException pueda ser tratada
    como una instancia de BuscaminasException sin violar el principio de sustitución de
    Liskov. Además de una jerarquía clara de excepciones que facilita la gestion
    de errores en el codigo relacionado con el juego.
     */
    public JuegoException(String mensaje)
    {
        super(mensaje);
    }
}