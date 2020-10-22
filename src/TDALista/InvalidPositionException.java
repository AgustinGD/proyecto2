package TDALista;
/**
 * Clase InvalidPositionException.
 * Implementacion de una excepcion de tipo InvalidPositionException.
 * @author Agustin Emanuel Gonzalez Diaz.
 */
public class InvalidPositionException extends Exception
{
	/**
	 * Setea el mensaje de excepcion.
	 * @param msg Mensaje de excepcion.
	 */
	public InvalidPositionException(String msg)
	{
		super(msg);
	}	
}
