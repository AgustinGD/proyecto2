package logica;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * Clase EntidadGrafica
 * Entidad grafica de las celdas del juego sudoku.
 * @author Agustin Emanuel Gonzalez Diaz
 */
public class EntidadGrafica
{
	// Atributos de instancia
	protected ImageIcon grafico;
	protected String[][] imagenes;
	
	// Constructor
	/**
	 * Inicia la matriz con la locacion de las imagenes.
	 */
	public EntidadGrafica()
	{
		grafico = new ImageIcon();
		imagenes = new String[][] {	new String[] {"/img/n0.png","/img/i1.png","/img/i2.png","/img/i3.png","/img/i4.png","/img/i5.png","/img/i6.png","/img/i7.png","/img/i8.png","/img/i9.png"},
									new String[] {"/img/n0.png","/img/n1.png","/img/n2.png","/img/n3.png","/img/n4.png","/img/n5.png","/img/n6.png","/img/n7.png","/img/n8.png","/img/n9.png"},
									new String[] {"/img/n0.png","/img/c1.png","/img/c2.png","/img/c3.png","/img/c4.png","/img/c5.png","/img/c6.png","/img/c7.png","/img/c8.png","/img/c9.png"},
									new String[] {"/img/n0.png","/img/ic1.png","/img/ic2.png","/img/ic3.png","/img/ic4.png","/img/ic5.png","/img/ic6.png","/img/ic7.png","/img/ic8.png","/img/ic9.png"},
									new String[] {"/img/n0.png","/img/g1.png","/img/g2.png","/img/g3.png","/img/g4.png","/img/g5.png","/img/g6.png","/img/g7.png","/img/g8.png","/img/g9.png"}};
	}
	
	// Metodos
	/**
	 * Actualiza la imagen segun un estado e indice pasados por parametro.
	 * @param estado estado de la celda
	 * @param indice imagen que se quiere mostrar
	 */
	public void actualizar(int estado, int indice)
	{	
		int height, width;
		
		if (indice < imagenes[estado].length)
		{
			//System.out.println("estado"+estado);
			//System.out.println("indice"+indice);
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imagenes[estado][indice]));
			
			height = grafico.getIconHeight();
			width = grafico.getIconWidth();			
			
			grafico.setImage(imageIcon.getImage());
			redimensionar(width,height);
		}
	}
	/**
	 * Retorna el grafico de la entidad.
	 * @return grafico de la entidad.
	 */
	public ImageIcon getGrafico()
	{
		return grafico;
	}
	/**
	 * Setea el grafico de la entidad por uno pasado por parametro.
	 * @param graf grafico de la entidad.
	 */
	public void setGrafico(ImageIcon graf)
	{
		grafico = graf;
	}
	
	/**
	 * retorna una matriz con la locacion de las imagenes.
	 * @return matriz con la locacion de las imagenes.
	 */
	public String[][] getImagenes()
	{
		return imagenes;
	}
	
	/**
	 * setea una matriz con la locacion de las imagenes.
	 * @param imgs Matriz con la locacion de las imagenes.
	 */
	public void setImagenes(String[][] imgs)
	{
		imagenes = imgs;
	}
	/**
	 * Redimensiona la imagen actual segun una anchura y altura pasadas por parametro
	 * @param w anchura
	 * @param h	altura
	 */
	public void redimensionar(int w, int h)
	{
		Image image = grafico.getImage();
		Image newimg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
		grafico.setImage(newimg);
	}
}
