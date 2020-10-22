package logica;
/**
 * Clase Celda
 * Implementacion de una celda grafica del Sudoku.
 * @author Agustin Emanuel Gonzalez Diaz
 */
public class Celda
{
	// Atributos de instancia
	protected Integer valor;
	protected EntidadGrafica entidadGrafica;
	protected boolean activado;
	protected int estado; // (0,1,2,3,4) - (Inicial,Activa,Conflictiva, Inactiva-Conflictiva, Ganaste)
	
	// Constructor
	/**
	 * Inicializa la celda con su imagen, y segun un estado y si esta activado o no pasados por parametro.
	 * @param act
	 * @param est
	 */
	public Celda(boolean act, int est)
	{
		valor = null;
		activado = act;
		estado = est;
		entidadGrafica = new EntidadGrafica();
	}
	
	
	// Metodos
	/**
	 * Incrementa en 1 el valor de la celda y actualiza su imagen.
	 * Rota siempre entre los valores del 0 al 9
	 */
	public void actualizar() 
	{	
		if (activado)
		{
			if (valor != null && valor + 1 < entidadGrafica.getImagenes()[estado].length)
			{
				valor++;
			}
			else
			{
				valor = 0;
			}
			entidadGrafica.actualizar(estado,valor);
		}		
	}
	
	/**
	 * Retorna la cantidad de elementos que tiene la matriz segun el estado.
	 * @return cantidad de elementos que tiene la matriz.
	 */
	public int getCantElementos()
	{
		return entidadGrafica.getImagenes()[estado].length;
	}
	
	/**
	 * retorna el valor actual de la celda.
	 * @return valor actual de la celda.
	 */
	public Integer getValor()
	{
		return valor;
	}
	/**
	 * setea el valor actual de la celda por una pasada por parametro y actualiza su imagen.
	 * @param val valor de celda nuevo.
	 */
	public void setValor(Integer val)
	{	
		if (val!=null && val < entidadGrafica.getImagenes()[estado].length)
		{
			valor = val;
			
			entidadGrafica.actualizar(estado,valor);
		}
		else 
		{
			valor = null;
		}
	}
	/**
	 * retorna la entidad grafica de la celda
	 * @return retorna la entidad grafica de la celda
	 */
	public EntidadGrafica getEntidadGrafica()
	{
		return entidadGrafica;
	}
	/**
	 * setea la entidad grafica de la celda por una pasada por parametro
	 * @param g entidad grafica de la celda
	 */
	public void setGrafica(EntidadGrafica g)
	{
		this.entidadGrafica = g;
	}
	
	/**
	 * setea si esta activada o no la celda
	 * @param act true si la celda es activa, false caso contrario
	 */
	public void setActivado(boolean act)
	{
		activado = act;
	}
	
	/**
	 * Indica si la celda esta activada o no
	 * @return true si esta activada, false caso contrario
	 */
	public boolean getActivado()
	{
		return activado;
	}
	/**
	 * retorna el estado actual de la celda
	 * @return estado actual de la celda
	 */
	public int getEstado()
	{
		return estado;
	}
	/**
	 * setea el estado actual de la celda por una pasado por parametro y actualiza la imagen al estado correspondiente
	 * @param est estado nuevo de la celda
	 */
	public void setEstado(int est)
	{
		estado = est;
		entidadGrafica.actualizar(estado,valor);
	}
	
	/**
	 * Actualiza la imagen de la celda
	 */
	public void refrescarImagen()
	{
		entidadGrafica.actualizar(estado,valor);
	}
}
