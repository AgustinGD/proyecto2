package TDADiccionario;

/**
 * Interface Entry.
 * Define los datos y operaciones aplicables sobre una entrada.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <K> Tipo de dato de la clave a almacenar en la entrada.
 * @param <V> Tipo de dato del valor a almacenar en la entrada.
 */
public interface Entry<K,V>
{
	/**
	 * Retorna la clave de la entrada.
	 * @return Clave de la entrada.
	 */
	public K getKey();
	/**
	 * Retorna el valor de la entrada.
	 * @return Valor de la entrada.
	 */
	public V getValue();
}
