package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase ElemenIterator.
 * Implementacion de un iterador que utiliza posiciones de una lista de posiciones.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <E> Tipo de dato de los elementos que utiliza el objeto iterable.
 */
public class ElementIterator<E> implements Iterator<E>
{
	//Atributos de instancia
	protected PositionList<E> list;
	protected Position<E> cursor;
	
	//Constructor
	/**
	 * Inicializa el iterador con la primer posicion de la lista pasada por parametro (si es que existe la primer posicion).
	 * @param l Lista de Posiciones.
	 */
	public ElementIterator(PositionList<E> l)
	{
		list = l;
		try
		{
			cursor = list.isEmpty() ? null : list.first();
		} catch (EmptyListException e) {}				
	}
	//Metodos
	/**
	 * Indica si el puntero actual tiene elemento.
	 * @return True si tiene elemento, false en caso contrario.
	 */
	public boolean hasNext()
	{
		return cursor != null;
	}
	
	/**
	 * Devuelve el elemento en el puntero.
	 * @return E Elemento en el puntero.
	 * @throws NoSuchElementException Si no hay elemento en el puntero.
	 */
	public E next() throws NoSuchElementException
	{
		E elemRet = null;
		try
		{
			if (cursor == null)
				throw new NoSuchElementException("**next()**No hay siguiente elemento");
			
			elemRet = cursor.element();
			
			cursor = (cursor == list.last()) ? null : list.next(cursor);
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {}		
		
		return elemRet;
	}
}
