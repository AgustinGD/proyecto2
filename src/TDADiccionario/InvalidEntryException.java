package TDADiccionario;

/**
 * Clase InvalidEntryException.
 * Implementacion de una excepcion de tipo InvalidEntryException.
 * @author Agustin Emanuel Gonzalez Diaz.
 */
public class InvalidEntryException extends Exception
{
	/**
	 * Setea el mensaje de excepcion.
	 * @param msg Mensaje de excepcion.
	 */
	public InvalidEntryException (String msg)
	{
		super(msg);
	}
}
