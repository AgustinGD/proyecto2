package TDADiccionario;

/**
 * Clase InvalidKeyException.
 * Implementacion de una excepcion de tipo InvalidKeyException.
 * @author Agustin Emanuel Gonzalez Diaz.
 */
public class InvalidKeyException extends Exception
{
	/**
	 * Setea el mensaje de excepcion.
	 * @param msg Mensaje de excepcion.
	 */
	public InvalidKeyException (String msg)
	{
		super(msg);
	}
}
