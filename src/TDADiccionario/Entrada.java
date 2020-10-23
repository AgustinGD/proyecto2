package TDADiccionario;

/**
 * Class Entrada.
 * Implementacion de una entrada con clave y valor.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <K> Tipo de dato de la clave.
 * @param <V> Tipo de dato del valor.
 */
public class Entrada<K,V> implements Entry<K,V>
{
	//Atributos de instancia
	private K key;
	private V value;
	
	//Constructor
	/**
	 * Inicializa la entrada con una key y una value.
	 * @param K Clave con la que se inicializa.
	 * @param V Clave con la que se inicializa.
	 */
	public Entrada(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	//Metodos
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
	
	/**
	 * Setea clave de la entrada.
	 * @param Clave nueva para la entrada.
	 */
	public void setKey (K clave)
	{
		key = clave;
	}
	
	/**
	 * Setea valor de la entrada.
	 * @param Valor nuevo para la entrada.
	 */
	public void setValue(V valor)
	{
		value = valor;
	}
	
	/**
	 * Entrada en texto.
	 * @return Clave y valor en formato String.
	 */
	public String toString()
	{
		return "( " + key + " , " + value + " )";
	}
}
