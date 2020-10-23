package TDALista;


/**
 * Clase DNodo, utilizada por Lista_doble_enlazada.
 * Implementacion de un nodo doblemente enlazado.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <E> Tipo de dato del elemento a almacenar en el nodo.
 */
public class DNodo<E> implements Position<E>
{
	//Atributos de instancia
	private E elem;
	private DNodo<E> sig;
	private DNodo<E> ant;
	
	//Constructores
	/**
	 * inicializa el nodo con un elemento y sus referencias hacia los nodos siguiente y anterior nulas.
	 * @param elemento Elemento con el cual  se inicializa el nodo.
	 */		
	public DNodo(E element)
	{
		sig = null;
		ant = null;
		elem = element;
	}
	//Metodos
	/**
	 * Setea el elemento del nodo actual.
	 * @param elemento Nuevo elemento actual del nodo.
	 */	
	public void setElem(E elemento)
	{
		elem = elemento;
	}
	/**
	 * Setea la referencia al siguiente nodo.
	 * @param siguiente Nodo que se quiere a referenciar.
	 */	
	public void setSig(DNodo<E> siguiente)
	{
		sig = siguiente;
	}
	
	/**
	 * Setea la referencia del anterior nodo.
	 * @param anterior Nodo que se quiere a referenciar.
	 */	
	public void setAnt(DNodo<E> anterior)
	{
		ant = anterior;
	}
	
	/**
	 * Retorna el elemento del nodo actual.
	 * @return elem Elemento del nodo actual
	 */	
	public E element()
	{
		return elem;
	}
	
	/**
	 * Retorna la referencia al siguiente nodo.
	 * @return sig Referencia al siguiente nodo
	 */	
	public DNodo<E> getSig()
	{
		return sig;
	}
	
	/**
	 * Retorna la referencia del anterior nodo.
	 * @return ant Referencia del anterior nodo
	 */	
	public DNodo<E> getAnt()
	{
		return ant;
	}
}
