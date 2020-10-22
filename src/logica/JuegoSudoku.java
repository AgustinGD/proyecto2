package logica;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.Scanner;

import TDADiccionario.Diccionario_hash_abierto;
import TDADiccionario.Dictionary;
import TDADiccionario.Entry;
import TDADiccionario.InvalidKeyException;
import TDALista.Lista_doble_enlazada;
import TDALista.PositionList;
/**
 * Clase JuegoSudoku
 * Implementacion del juego sudoku
 * @author Agustin Emanuel Gonzalez Diaz
 */
public class JuegoSudoku
{
	// Atributos de instancia
	private Celda[][] tablero;
	private int estado;
	private String[] estado_del_juego;
	private LocalTime start;
	
	// Constructor
	/**
	 * Inicializa el estado del juego y las componentes del tablero a partir de un archivo de texto,
	 * Se blanquean al azar entre 9 y 40 celdas.
	 * @param ruta ruta del archivo que se utiliza para iniciar las celdas.
	 */
	public JuegoSudoku(String ruta)
	{
		tablero = new Celda[9][9];
		estado_del_juego = new String[]{"Error (archivo)", "Ganaste", "En progreso", "En progreso (hay conflictos)", "En progreso (tablero incompleto)"};
		
		// Numero al azar entre 9-40 para remover celdas.
		Random rand = new Random();
		int agregar_random = rand.nextInt(32) + 9;
		
		if(iniciarLectura(ruta) && validarSudoku())
		{			
			estado = 2;
			agregarCeros(agregar_random);			
			refrescarConflictos();			
		}
		else
		{
			System.out.println("***IniciarLectura()*** El formato del archivo no es correcto.");
			estado = 0;
			rellenarError();
		}
		
		start = LocalTime.now(ZoneId.of("America/Buenos_Aires" ));
	}
	
	// Metodos
	/**
	 * retorna el tiempo que transcurrio desde el inicio del juego.
	 * @return tiempo que transcurrio desde el inicio del juego.
	 */
	public Duration tiempoActual()
	{		
		LocalTime stop = LocalTime.now(ZoneId.of("America/Buenos_Aires"));
		Duration d = Duration.between(start, stop);
		return d;
	}
	/**
	 * Inicializa las componentes del tablero a partir de un archivo de texto.
	 * Tambien chequea que el formato del archivo sea correcto.
	 * @param ruta ruta del archivo que se utiliza para iniciar las celdas.
	 * @return true si inicio correctamente, false caso contrario.
	 */
	private boolean iniciarLectura(String ruta)
	{
		int valor;
		boolean formato_correcto = true;
		String valor_a_chequear = " ";
		Scanner escaner = null;
		
		InputStream in = JuegoSudoku.class.getClassLoader().getResourceAsStream(ruta);
		
		if (in == null)
		{
			System.out.println("***IniciarLectura()*** La locacion del archivo no existe.");
			formato_correcto = false;
		}
		else
		{			
			escaner = new Scanner(in);	
		
			for(int i=0; i<9 && formato_correcto; i++)
			{
				for(int j=0; j<9 && formato_correcto; j++)
				{
					formato_correcto = escaner.hasNext();
					
					if (formato_correcto)
					{
						valor_a_chequear = escaner.next();
					}				
					
					formato_correcto = formato_correcto && esDigito(valor_a_chequear);				
					
					if (formato_correcto)
					{
						valor = Integer.parseInt(valor_a_chequear);
						
						if(valor != 0)
						{
							tablero[i][j] = new Celda(false,0);
						}
						else
						{
							tablero[i][j] = new Celda(true,1);
						}				
						
						tablero[i][j].setValor(valor);
					}
				}
			}
			// Si el formato era correcto para los primeros 81 digitos me fijo que no haya mas de 81 digitos.
			formato_correcto = formato_correcto && !escaner.hasNext();
			
			escaner.close();					
		}
		
		return formato_correcto;		
	}
	/**
	 * Indica si la cadena pasada por parametro es un digito del 0 al 9
	 * @param str cadena con el digito
	 * @return true si es un digito, false caso contraro
	 */
	private boolean esDigito(String str)
	{
		boolean esDigito = false;
		
		if (str.length() == 1)
		{
			esDigito = Character.isDigit(str.charAt(0));
		}
		
		return esDigito;
	}
	/**
	 * Transforma una cantidad pasada por parametro de celdas iniciales por unas blancas al azar.
	 * @param cant cantidad de celdas blancas que se quieren.
	 */
	private void agregarCeros(int cant)
	{
		int ceros_agregados = 0;
		Random rand = new Random();
		int random = 1;
		
		while (ceros_agregados < cant)
		{
			
			for(int i=0; i<9 && (ceros_agregados < cant); i++)
			{
				for(int j=0; j<9 && (ceros_agregados < cant); j++)
				{
					
					if (tablero[i][j].getEstado() != 1)					
					{
						random = rand.nextInt(18);
						
						if (random == 0)
						{
							tablero[i][j].setEstado(1);
							tablero[i][j].setValor(0);
							tablero[i][j].setActivado(true);
							
							ceros_agregados++;							
						}						
					}
				}
			}
		}
	}
	/**
	 * Indica si el juego esta ganado
	 * @return true si se gano el juego, false caso contrario
	 */
	public boolean gano()
	{
		boolean gano = validarSudoku();		
		
		if (gano)
		{
			estado = 4;
		}
		else
		{
			estado = 3;
		}

		gano = gano && tableroCompleto();
		
		if(gano)
		{
			estado = 1;
			celdasGanaste();
		}
		
		return gano;
	}
	
	/**
	 * retorna una celda en las coordenadas pasadas por parametro.
	 * @param i fila
	 * @param j columna
	 * @return celda en las coordenas.
	 */
	public Celda getCelda(int i, int j)
	{
		return tablero[i][j];
	}
	
	/**
	 * Acciona una celda pasada por parametro
	 * @param c Celda que se quiere accionar
	 */
	public void accionar(Celda c) 
	{
		c.actualizar();
	}
	
	/**
	 * Retorna un string segun en el estado del juego actual.
	 * @return string con el estado del juego actual.
	 */
	public String getEstadoDelJuego()
	{
		return estado_del_juego[estado];
	}
	
	/**
	 * Setea el estado del juego por uno pasado por parametro.
	 * @param est estado del juego.
	 */
	public void setEstado(int est)
	{
		estado = est;
	}
	/**
	 * Retorna la cantidad de sub-paneles del tablero.
	 * @return cantidad de sub-paneles del tablero.
	 */
	public int getCantFilaSubPanel()
	{
		return (int) Math.sqrt(tablero.length);
	}
	/**
	 * Retorna la cantidad de filas del tablero.
	 * @return cantidad de filas del tablero.
	 */
	public int getCantFilas()
	{
		return tablero.length;
	}
	/**
	 * Indica si el tablero contiene una solucion al Sudoku valida.
	 * Si hay conflictos cambia el estado de las celdas a una conflictiva.
	 * Desactiva todas las celdas que estaban activas hasta el momento.
	 * @return true si es una solucion sudoku valida, false caso contrario.
	 */
	public boolean validarSudoku()
	{
		boolean valido = true;
		int valor;
		Dictionary<String, Celda> diccionario = new Diccionario_hash_abierto<String, Celda>();
		
		try
		{
			for(int i=0; i<9; i++)
			{				
				for(int j=0; j<9; j++)
				{
					tablero[i][j].setActivado(false);
					
					valor = tablero[i][j].getValor();

					diccionario.insert(valor+" en fila "+i, tablero[i][j]);
					diccionario.insert(valor+" en columna "+j, tablero[i][j]);
					diccionario.insert(valor+" en cajita "+i/3+"-"+j/3, tablero[i][j]);
				}
			}
			
			for (int n=1; n<10; n++)
			{
				for(int i=0; i<9; i++)
				{
					if(!chequearFlexible(n+" en fila "+i, diccionario))
					{
						valido = false;
					}
					if(!chequearFlexible(n+" en columna "+i, diccionario))
					{
						valido = false;
					}
					
					for(int j=0; j<9; j++)
					{						
						if(!chequearFlexible(n+" en cajita "+i/3+"-"+j/3, diccionario))
						{
							valido = false;
						}
					}
				}				
			}			
		}
		catch (InvalidKeyException  e) {}
		
		return valido;
	}
	
	/**
	 * Se utiliza para saber cuales son las celdas conflictivas y marcarlas como tal.
	 * @param key string que se usa para determinar si incumple una regla.
	 * @param diccionario Diccionario en el cual se buscan las celdas conflictivas.
	 * @return true si no hubieron conflictos, false caso contrario.
	 */
	private boolean chequearFlexible(String key, Dictionary<String, Celda> diccionario)
	{
		boolean valido = true;	
		Celda c;
		PositionList<Entry<String, Celda>> listaCeldas = new Lista_doble_enlazada<Entry<String,Celda>>();
		
		try
		{
			listaCeldas = (PositionList<Entry<String, Celda>>) diccionario.findAll(key);
			
			if (listaCeldas.size()>1)
			{
				for(Entry<String, Celda> e : listaCeldas)
				{
					c = e.getValue();
					
					if(c.getEstado() == 1)
					{
						c.setEstado(2);
					}
					else if (c.getEstado() == 0)
					{
						c.setEstado(3);
					}
				}
				
				valido = false;
			}
		}
		catch (InvalidKeyException e) {}
		
		return valido;
	}
	/**
	 * Indica si el tablero esta completo, es decir no tiene celdas blancas.
	 * @return true si el tablero esta completo, false caso contrario.
	 */
	public boolean tableroCompleto()
	{
		boolean completo = true;
		
		for(int i=0; i<9 && completo; i++)
		{
			for(int j=0; j<9 && completo; j++)
			{
				if (tablero[i][j].getValor() == 0)
				{
					completo = false;
				}
			}
		}
		
		return completo;
	}
	/**
	 * Se utiliza para devolver el tablero a su estado previo, antes de ser chequeado.
	 * Se vuelven a activar las celdas correspondientes.
	 */
	public void refrescarConflictos()
	{	
		estado = 2;
		
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if (tablero[i][j].getEstado() == 1)					
				{
					tablero[i][j].setActivado(true);
				}
				
				if (tablero[i][j].getEstado() == 2)
				{
					tablero[i][j].setEstado(1);
					tablero[i][j].setActivado(true);
				}
				
				if (tablero[i][j].getEstado() == 3)
				{
					tablero[i][j].setEstado(0);
				}
			}
		}
	}
	
	/**
	 * Muestra los estados de todas las celdas en el tablero por consola.
	 */
	public void printEstados()
	{
		for(int i=0; i<9; i++)
		{
			System.out.println(" ");
			for(int j=0; j<9; j++)
			{
				System.out.print(tablero[i][j].getEstado()+" ");
			}
		}
		System.out.println(" ");		
	}
	
	/**
	 * Muestra los valores de todas las celdas en el tablero por consola.
	 */
	public void printValores()
	{
		for(int i=0; i<9; i++)
		{
			System.out.println(" ");
			for(int j=0; j<9; j++)
			{
				System.out.print(tablero[i][j].getValor()+" ");
			}
		}
		System.out.println(" ");		
	}
	
	/**
	 * Indica si se inicio correctamente el juego.
	 * @return true si se inicio correctamente, false caso contrario.
	 */
	public boolean iniciadoCorrecto()
	{
		return estado != 0;
	}
	
	/**
	 * Resetea el timer, para que empieze de 0 denuevo.
	 */
	public void reiniciarTiempo()
	{
		start = LocalTime.now(ZoneId.of("America/Buenos_Aires" ));
	}
	
	/**
	 * Cambia el estado de las celdas por unas que indiquen que se gano.
	 */
	private void celdasGanaste()
	{	
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{
				if (tablero[i][j].getEstado() == 1)					
				{
					tablero[i][j].setEstado(4);
				}
			}
		}		
	}
	/**
	 * En caso de error cambia el estado de las celdas por una que llame la atencion visualmente.
	 */
	private void rellenarError()
	{
		for(int i=0; i<9; i++)
		{
			for(int j=0; j<9; j++)
			{				
				tablero[i][j] = new Celda(false,2);
				tablero[i][j].setValor(j+1);
			}
		}	
	}
}
