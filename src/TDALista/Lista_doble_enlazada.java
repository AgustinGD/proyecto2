package TDALista;

import java.util.Iterator;

/**
 * Clase Lista_doble_enlazada.
 * Implementacion de una lista que utiliza nodos doblemente enlazados.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <E> Tipo de dato de los elementos a almacenar en la lista.
 * @version 2.0
 * Se solucionaron los problemas que indica el tester.
 * La clase DNodo ahora es una clase publica que pertenece al paquete TDALista.
 */
public class Lista_doble_enlazada<E> implements PositionList<E>
{
	//Atributos de instancia
	protected DNodo<E> head;
	protected DNodo<E> tail;
	protected int s;
	
	//Constructor
	/**
	 * Constructor de la clase Lista_doble_enlazada.
	 */
	public Lista_doble_enlazada ()
	{
		head = new DNodo<E>(null);
		tail = new DNodo<E>(null);
		head.setSig(tail);
		tail.setAnt(head);
		s = 0;
	}
	//Metodos
	
	public int size()
	{
		return s;
	}
	
	public boolean isEmpty()
	{
		return (s == 0);
	}
	
	public Position<E> first() throws EmptyListException
	{
		if (head.getSig()==tail)
			throw new EmptyListException("first()**Lista vacia");
		else
			return head.getSig();
	}
	
	public Position<E> last() throws EmptyListException
	{
		if (tail.getAnt()== head)
			throw new EmptyListException("**last()**Lista vacia");
		else
			return tail.getAnt();
	}	
	
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException
	{
		DNodo<E> nodoPosicion = this.checkPosition(p);
		
		if (nodoPosicion.getSig() == tail)
			throw new BoundaryViolationException("**next()**Ultima posicion");
		
		return nodoPosicion.getSig(); //Siguiente de la posicion
		
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException
	{
		DNodo<E> nodoPosicion = this.checkPosition(p);
		
		if (nodoPosicion.getAnt() == head)
			throw new BoundaryViolationException("**prev()**Primera posicion");
		
		return nodoPosicion.getAnt(); //Anterior de la posicion
		
	}
	
	public void addFirst(E element)
	{
		DNodo<E> nodoNuevo, sigNuevo;
		
		nodoNuevo = new DNodo<E>(element);
		sigNuevo = head.getSig();
		
		nodoNuevo.setSig(sigNuevo);
		nodoNuevo.setAnt(head);
		
		sigNuevo.setAnt(nodoNuevo);		
		head.setSig(nodoNuevo);	
		
		s++;
	}
	
	public void addLast(E element)
	{
		DNodo<E> nodoNuevo, antNuevo;
		
		nodoNuevo = new DNodo<E>(element);
		antNuevo = tail.getAnt();
		
		nodoNuevo.setSig(tail);
		nodoNuevo.setAnt(antNuevo);
		
		antNuevo.setSig(nodoNuevo);		
		tail.setAnt(nodoNuevo);
		
		s++;
	}
	
	public void addAfter(Position<E> p, E element) throws InvalidPositionException
	{
		DNodo<E> nodoPosicion, nodoSigP, nodoNuevo;
		
		nodoPosicion = this.checkPosition(p);
		nodoNuevo = new DNodo<E>(element);
		nodoSigP = nodoPosicion.getSig();
		
		nodoNuevo.setSig(nodoSigP);
		nodoNuevo.setAnt(nodoPosicion);
		
		nodoPosicion.setSig(nodoNuevo);		
		nodoSigP.setAnt(nodoNuevo);		
		
		s++;
	}
	
	public void addBefore(Position<E> p, E element) throws InvalidPositionException
	{
		DNodo<E> nodoPosicion, nodoAntP, nodoNuevo;
		
		nodoPosicion = this.checkPosition(p);
		nodoNuevo = new DNodo<E>(element);
		nodoAntP = nodoPosicion.getAnt();
		
		nodoNuevo.setSig(nodoPosicion);
		nodoNuevo.setAnt(nodoAntP);
		
		nodoPosicion.setAnt(nodoNuevo);
		
		nodoAntP.setSig(nodoNuevo);		
		
		s++;
	}
	
	public E remove(Position<E> p) throws InvalidPositionException
	{		
		DNodo<E> nodoPosicion, sigNodoPosicion, antNodoPosicion;
		
		nodoPosicion = this.checkPosition(p);
		
		E removido = p.element(); 
		
		
		sigNodoPosicion = nodoPosicion.getSig();
		antNodoPosicion = nodoPosicion.getAnt();
		
		sigNodoPosicion.setAnt(antNodoPosicion);
		antNodoPosicion.setSig(sigNodoPosicion);
		
		nodoPosicion.setAnt(null);
		nodoPosicion.setSig(null);
		nodoPosicion.setElem(null);		
		s--;
		
		return removido;
	}
	public E set(Position<E> p, E element) throws InvalidPositionException
	{		
		DNodo<E> nodoPosicion;
		
		nodoPosicion = this.checkPosition(p);	
		
		E reemplazado = p.element();		
			
		nodoPosicion.setElem(element);
		
		return reemplazado;
	}
	
	public Iterator<E> iterator()
	{
		return (new ElementIterator<E>(this));
	}
	
	public Iterable<Position<E>> positions()
	{
		PositionList<Position<E>> listaPosiciones = new Lista_doble_enlazada<Position<E>>();
		
		if (s > 0)
			positionsAux(listaPosiciones, head.getSig());
		
		return listaPosiciones;
	}
	
	/**
	 * Metodo privado , devuelve la referencia al DNodo al que apunta la posicion.
	 * @param p Posicion que se quiere chequear.
	 * @return Retorna el DNodo asociado a la posicion.
	 * @throws InvalidPositionException si la posicion es invalida o la lista esta vacia.
	 */
	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException
	{
		DNodo<E> nodo = null;
		
		try
		{
			if (p == null)
				throw new InvalidPositionException("**checkPosition()**Posicion nula");
			
			if (p.element() == null)
				throw new InvalidPositionException("**checkPosition()**Posicion eliminada previamente");
			
			if (head.getSig()==tail)
				throw new InvalidPositionException("**checkPosition()**Posicion no esta en la lista (lista vacia)");
			
			nodo = (DNodo<E>) p; //Posicion valida, la guardo.
		}
		catch(ClassCastException e)
		{
			throw new InvalidPositionException("**checkPosition()**Posicion no es un nodo de la lista");
		}
		
		return nodo;
	}
	
	/**
	 * Metodo privado que utiliza positions().
	 * Construye la lista de posiciones de manera recursiva.
	 * @param listaPosiciones Lista a la cual se le quiere agregar posiciones.
	 * @param p Posicion que se agrega en la lista.
	 */	
	private void positionsAux(PositionList<Position<E>> listaPosiciones, DNodo<E> p)
	{
		listaPosiciones.addLast(p);
		
		if (p.getSig() != tail)
			positionsAux(listaPosiciones, p.getSig());
	}
}
