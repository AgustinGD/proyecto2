package TDALista;

/**
 * Clase BoundaryViolationException.
 * Implementacion de una excepcion de tipo BoundaryViolationException.
 * @author Agustin Emanuel Gonzalez Diaz.
 */
public class BoundaryViolationException extends Exception
{
	/**
	 * Setea el mensaje de excepcion.
	 * @param msg Mensaje de excepcion.
	 */
	public BoundaryViolationException(String msg)
	{
		super(msg);
	}	
}
