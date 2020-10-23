package logica;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * Clase EntidadReloj
 * Entidad grafica de los digitos de un timer.
 * @author Agustin Emanuel Gonzalez Diaz
 */
public class EntidadReloj
{
	// Atributos de instancia
	protected ImageIcon grafico;
	protected String[]imagenes;
	
	// Constructor
	/**
	 * Inicia el arreglo con la locacion de las imagenes.
	 */
	public EntidadReloj()
	{
		grafico = new ImageIcon();
		imagenes = new String[]{"/img/t0.png","/img/t1.png","/img/t2.png","/img/t3.png","/img/t4.png","/img/t5.png","/img/t6.png","/img/t7.png","/img/t8.png","/img/t9.png"};
	}
	
	// Metodos
	/**
	 * Actualiza la imagen segun un indice pasado por parametro.
	 * @param indice imagen que se quiere mostrar
	 */
	public void actualizar(int indice)
	{	
		int height, width;
		
		if (indice < imagenes.length)
		{
			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imagenes[indice]));
			
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
	 * retorna un arreglo con la locacion de las imagenes.
	 * @return Arreglo con la locacion de las imagenes.
	 */
	public String[] getImagenes()
	{
		return imagenes;
	}
	/**
	 * setea un arreglo con la locacion de las imagenes.
	 * @param imgs Arreglo con la locacion de las imagenes.
	 */
	public void setImagenes(String[] imgs)
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
