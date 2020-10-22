package TDALista;

/**
 * Clase EmptyListException.
 * Implementacion de una excepcion de tipo EmptyListException.
 * @author Agustin Emanuel Gonzalez Diaz.
 */
public class EmptyListException extends Exception
{
	/**
	 * Setea el mensaje de excepcion.
	 * @param msg Mensaje de excepcion.
	 */
	public EmptyListException(String msg)
	{
		super(msg);
	}	
}
