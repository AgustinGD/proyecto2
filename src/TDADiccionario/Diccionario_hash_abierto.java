package TDADiccionario;

import TDALista.BoundaryViolationException;
import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.Lista_doble_enlazada;
import TDALista.Position;
import TDALista.PositionList;

/**
 * Clase Diccionario_hash_abierto<K,V>. 
 * Implementa los datos y operaciones aplicables sobre un diccionario con tabla de hash abierta.
 * @author Agustin Emanuel Gonzalez Diaz.
 * @param <K> Tipo de dato de las claves a almacenar en los bucket.
 * @param <V> Tipo de dato de los valores a almacenar en los bucket.
 */
public class Diccionario_hash_abierto<K,V> implements Dictionary<K,V>
{
	//Atributos de instancia
	protected PositionList<Entry<K,V>> [] listaHash;
	protected final float fc = 0.9f;
	protected int tamInicial = 13;
	protected int size;
	
	//Constructor
	/**
	 * Inicializa el diccionario con una tabla de hash de 13 buckets
	 */
	public Diccionario_hash_abierto()
	{
		size = 0;
		
		listaHash = (PositionList<Entry<K,V>> []) new PositionList[tamInicial];
		for (int i=0; i<tamInicial; i++)
			listaHash[i] = new Lista_doble_enlazada<Entry<K,V>>();
	}
	//Metodos protegido
	/**
	 * Devuelve el codigo hash de una clave.
	 * @param clave Clave que se quiera saber su codigo hash.
	 * @return Codigo hash de la clave.
	 */
	protected int hash(K clave)
	{
		return Math.abs(clave.hashCode() % listaHash.length);
	}
	//Metodo
	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public Entry<K, V> find(K key) throws InvalidKeyException
	{
		Entry<K,V> entradaEncontrada = null;
		Position<Entry<K,V>> posicion, ultimaPosicion;
		boolean encontre = false;
		
		checkKey(key);
		
		int bucket = hash(key);
		
		PositionList<Entry<K, V>> D = listaHash[bucket]; //Lista del bucket correspondiente
		
		try
		{
			posicion = D.isEmpty() ? null : D.first();
			ultimaPosicion = D.isEmpty() ? null : D.last();
			
			while (posicion != null && !encontre)
			{
				if (posicion.element().getKey().equals(key))
				{
					entradaEncontrada = posicion.element();
					
					encontre = true;
				}
				
				posicion = (posicion == ultimaPosicion) ? null : D.next(posicion);
			}
			
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {}
			
		return entradaEncontrada;
	}

	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException
	{
		PositionList<Entry<K,V>> iterableEncontradas = new Lista_doble_enlazada<Entry<K,V>>();
		
		checkKey(key);
		int bucket = hash(key);
		
		PositionList<Entry<K, V>> D = listaHash[bucket]; //Lista del bucket correspondiente
		
		for (Entry<K,V> e : D)
		{
			if (e.getKey().equals(key))
				iterableEncontradas.addLast(e);
		}
		
		return iterableEncontradas;
	}

	public Entry<K, V> insert(K key, V value) throws InvalidKeyException
	{
		Entry<K,V> insertado;
		
		if (( (float) size / (float) listaHash.length) >= fc) //Si el factor de carga actual es superior al especificado
			resize();
		
		insertado =  new Entrada<K,V>(key,value);
		
		checkKey(key);
		int bucket = hash(key);
		
		PositionList<Entry<K, V>> D = listaHash[bucket]; //Lista del bucket correspondiente
		
		D.addLast(insertado);
		
		size++;
		
		return insertado;
	}

	public Entry<K, V> remove(Entry<K, V> entrada) throws InvalidEntryException
	{
		Entry<K,V> entradaRemovida = null;
		Position<Entry<K,V>> posicion, ultimaPosicion;
		boolean removi = false;
		
		
		if (entrada == null || entrada.getKey() == null)
			throw new InvalidEntryException("**Remove()** La entrada es nula");
		
		int bucket = hash(entrada.getKey());
		
		PositionList<Entry<K, V>> D = listaHash[bucket]; //Lista del bucket correspondiente
		
		try
		{
			posicion = D.isEmpty() ? null : D.first();
			ultimaPosicion = D.isEmpty() ? null : D.last();
			
			while (posicion != null && !removi)
			{
				if (posicion.element() == entrada)
				{
					entradaRemovida = entrada;
					D.remove(posicion);
					size--;
					
					removi = true;
				}
				posicion = (posicion == ultimaPosicion) ? null : D.next(posicion);
			}
			if (!removi)
				throw new InvalidEntryException("**Remove()** La entrada no pertenece al diccionario)");
			
		} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {}
		
		return entradaRemovida;
	}

	public Iterable<Entry<K, V>> entries()
	{
		PositionList<Entry<K, V>> iterableValues = new Lista_doble_enlazada<Entry<K, V>>();
		
		for (int i = 0; i< listaHash.length ; i++)
		{
			for (Entry<K,V> e : listaHash[i])
			{
				iterableValues.addLast(e);
			}
		}
		
		return iterableValues;
	}
	
	/**
	 * Lanza una exepcion si la clave es nula.
	 * @param key Clave que se quiere chequear.
	 */
	private void checkKey(K key) throws InvalidKeyException
	{
		if (key == null)
		{
			throw new InvalidKeyException("**CheckKey()** Clave nula");
		}
	}
	
	/**
	 * Duplica la cantidad de buckets.
	 * Solo ocurre si el factor de carga es superado.
	 */
	private void resize()
	{
		PositionList<Entry<K, V>> S = null; //Lista de algun bucket.
		Entry<K, V> e = null; // Entrada de alguna lista.
		PositionList<Entry<K,V>>[] viejolistaHash = listaHash;

		listaHash = (PositionList<Entry<K,V>> []) new PositionList[nextPrimo(listaHash.length * 2)];
		
		for (int i = 0; i < listaHash.length; i++)
			listaHash[i] = new Lista_doble_enlazada<Entry<K,V>>();
		
		try
		{
			for (int i = 0; i < viejolistaHash.length; i++)
			{
				S = viejolistaHash[i]; //Lista del bucket correspondiente.
				
				while (!S.isEmpty()) // Por cada entrada de cada bucket del arreglo viejo, voy agregando al arreglo nuevo.
				{
					e = S.remove(S.first()); 
					
					listaHash[hash(e.getKey())].addLast(e);// Agrego la entrada removida en el bucket correspondiente del nuevo arreglo.
				}
			}
		} catch (EmptyListException | InvalidPositionException ex){}
	}
	
	/**
	 * Devuelve el siguiente numero primo.
	 * Es utilizado por resize().
	 */
	private int nextPrimo (int primito)
	{
		int primote, max, cont;
		boolean encontre = false;
		
		primote = 0;
		
		while(!encontre)
		{
			primito++;
			max = (int) Math.sqrt(primito);
			cont = 0;
			
			for(int i = 2; i <= max && (cont < 1); i ++)
			{
			  if(primito % i == 0)
				  cont++;
			}
			
			if(cont == 0)
			{
				primote = primito;
				encontre = true;
			}
		}
		
		return primote;
	}
}
