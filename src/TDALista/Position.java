package TDALista;

/**
 * Interface Position.
 * Define los datos y operaciones aplicables sobre una posicion.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <E> Tipo de dato de los elementos a almacenar en la posicion.
 */
public interface Position<E> 
{
	/**
	 * Retorna el elemento de la posicion actual.
	 * @return Elemento de la posicion actual.
	 */
	public E element();
}
