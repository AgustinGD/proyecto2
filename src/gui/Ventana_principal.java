package gui;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import logica.Celda;
import logica.EntidadReloj;
import logica.JuegoSudoku;

import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ventana_principal extends JFrame
{

	private JPanel Principial;
	private JuegoSudoku juego;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Ventana_principal frame = new Ventana_principal();
					frame.setVisible(true);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana_principal()
	{
		
		setResizable(false);
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		Principial = new JPanel();
		Principial.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Principial);
		Principial.setLayout(null);
		
		// Inicializacion del juego
		juego = new JuegoSudoku("archivo/inicial.txt");
		
		// Inicializacion de componentes
		JPanel PanelTiempo = new JPanel();
		PanelTiempo.setBounds(5, 11, 779, 80);
		Principial.add(PanelTiempo);
		PanelTiempo.setLayout(null);
		
		JLabel labelPreTexto = new JLabel("Estado:");
		labelPreTexto.setHorizontalAlignment(SwingConstants.CENTER);
		labelPreTexto.setBounds(537, 16, 212, 23);
		PanelTiempo.add(labelPreTexto);
		
		JLabel labelEstado = new JLabel("");
		labelEstado.setBounds(537, 42, 212, 23);
		labelEstado.setHorizontalAlignment(SwingConstants.CENTER);
		labelEstado.setVerticalAlignment(SwingConstants.CENTER);
		PanelTiempo.add(labelEstado);
		
		JPanel PanelCasillas = new JPanel();
		PanelCasillas.setBounds(5, 98, 779, 462);
		Principial.add(PanelCasillas);
		PanelCasillas.setLayout(new GridLayout(3, 3, 0, 0));
		//PanelCasillas.setLayout(new GridLayout(juego.getCantFilaSubPanel(), juego.getCantFilaSubPanel(), 0, 0));

		
		
		// Variables de los botones
		JButton btnChequearSolucion = new JButton("Chequear solucion");
		JButton btnListo = new JButton("Listo");
		JButton btnIniciar = new JButton("Iniciar");
		JButton btnReiniciar = new JButton("Reiniciar");		
		
		// Variables del subpanel
		int cantPaneles = 3;
		JPanel panelReloj = new JPanel();
		panelReloj.setBounds(267, 11, 225, 43);
		PanelTiempo.add(panelReloj);		
		panelReloj.setLayout(new GridLayout(0, cantPaneles, 0, 0));				
		
		// Inicializacion de los subpaneles
		int m,n;
		int cantFilaSubPanel = juego.getCantFilaSubPanel();
		JPanel paneles[][] = new JPanel[cantFilaSubPanel][cantFilaSubPanel];		
		
		for (int i =0; i< cantFilaSubPanel; i++)
		{
			for (int j =0; j< cantFilaSubPanel; j++)
			{
				paneles[i][j] = new JPanel();
				paneles[i][j].setLayout(new GridLayout(juego.getCantFilaSubPanel(), juego.getCantFilaSubPanel(), 0, 0));				
				
				// Agregando bordes que no colisionen
				if (i == 0)
				{
					if (j == 0)
					{
						paneles[i][j].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
					}
					else
					{
						paneles[i][j].setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, Color.BLACK));
					}
					
				}
				else if (j == 0)
				{
					paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, Color.BLACK));
				}
				else
				{
					paneles[i][j].setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.BLACK));
				}
				
				
				PanelCasillas.add(paneles[i][j]);
			}
		}
		
		// Inicializacion de las celdas
		for (int i=0; i<juego.getCantFilas(); i++)
		{
			m = (int) i / cantFilaSubPanel;
			
			for (int j=0; j<juego.getCantFilas(); j++)
			{
				n = (int) j / cantFilaSubPanel;
				
				Celda c = juego.getCelda(i,j);
				ImageIcon grafico = c.getEntidadGrafica().getGrafico();
				
				JButton button = new JButton();
				
				paneles[m][n].add(button);
				
				button.addComponentListener(new ComponentAdapter()
				{
					@Override
					public void componentResized(ComponentEvent e)
					{
						c.getEntidadGrafica().redimensionar(button.getWidth(),button.getHeight());
						button.setIcon(grafico);
					}
				});
				
				button.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (c.getActivado())
						{
							juego.accionar(c);
						}
					}
				});
			}
		}
		
		// Inicializacion de los digitos del timer
		JPanel panelesDigitos[]= new JPanel[3];
		EntidadReloj digitos[] = new EntidadReloj[6];		
		
		int cantDigitos = 6;
		int cantDigitosEnSubPanel = 2;
		
		for (int i = 0; i< cantPaneles; i++)
		{
			panelesDigitos[i] = new JPanel();
			panelesDigitos[i].setLayout(new GridLayout(0, 2, 0, 0));
			panelesDigitos[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			
			panelReloj.add(panelesDigitos[i]);
		}
		
		int k;
		for (int i = 0; i < cantDigitos; i++)
		{
			k = i/cantDigitosEnSubPanel;
			JLabel label = new JLabel();	
			
			panelesDigitos[k].add(label);	
			
			EntidadReloj er = new EntidadReloj();
			digitos[i] = er;
			er.actualizar(0);
			ImageIcon grafico = er.getGrafico();
			
			label.addComponentListener(new ComponentAdapter()
			{
				@Override
				public void componentResized(ComponentEvent e)
				{
					er.redimensionar(label.getWidth(),label.getHeight());
					label.setIcon(grafico);
				}
			});
		}
		
		
		// Inicializacion del timer
		int refrescoTimer = 1000;
		
		timer = new Timer(refrescoTimer, new ActionListener()
		{
        	public void actionPerformed(ActionEvent evt)
        	{
        		refrescarDigitos(digitos);
        		panelReloj.repaint();
        	}
		});		
		
		// Inicializacion del boton chequear
		btnChequearSolucion.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(juego.gano())
				{
					timer.stop();					
					labelEstado.setForeground(Color.GREEN);
					btnListo.setEnabled(false);
				}
				else
				{
					btnListo.setEnabled(true);
				}
				
				labelEstado.setText(juego.getEstadoDelJuego());
				btnChequearSolucion.setEnabled(false);
				
				PanelCasillas.repaint();				
			}
		});
		btnChequearSolucion.setBounds(37, 16, 174, 23);
		PanelTiempo.add(btnChequearSolucion);
		
		// Inicializacion del boton listo
		btnListo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				juego.refrescarConflictos();
				btnListo.setEnabled(false);
				btnChequearSolucion.setEnabled(true);				
				
				labelEstado.setText(juego.getEstadoDelJuego());
				PanelCasillas.repaint();				
			}
		});
		btnListo.setBounds(37, 42, 174, 23);
		PanelTiempo.add(btnListo);
		
		// Inicializacion del boton iniciar
		btnIniciar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnIniciar.setEnabled(false);
				btnReiniciar.setEnabled(true);
				btnChequearSolucion.setEnabled(true);
				PanelCasillas.setVisible(true);
				juego.iniciarJuego();
				labelEstado.setForeground(Color.BLUE);
				labelEstado.setText(juego.getEstadoDelJuego());
				
				
				juego.reiniciarTiempo();
				timer.start();
			}
		});
		btnIniciar.setBounds(284, 57, 89, 23);
		PanelTiempo.add(btnIniciar);
		
		// Inicializacion del boton reiniciar
		btnReiniciar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				timer.stop();
								
				btnReiniciar.setEnabled(false);
				btnIniciar.setEnabled(true);
				btnChequearSolucion.setEnabled(false);
				btnListo.setEnabled(false);
				labelEstado.setForeground(Color.ORANGE);
				PanelCasillas.setVisible(false);
				
				juego.reiniciarJuego();
				labelEstado.setText(juego.getEstadoDelJuego());
				
				juego.reiniciarTiempo();
				refrescarDigitos(digitos);
        		panelReloj.repaint();				
			}
		});
		btnReiniciar.setBounds(384, 57, 89, 23);
		PanelTiempo.add(btnReiniciar);
		
		// Inicio generico de los botones y label
		btnListo.setEnabled(false);
		btnChequearSolucion.setEnabled(false);
		btnReiniciar.setEnabled(false);
		labelEstado.setForeground(Color.ORANGE);
		labelEstado.setText(juego.getEstadoDelJuego());
		PanelCasillas.setVisible(false);
		
		// Muestreo de botones y label segun estado del juego
		if (!juego.iniciadoCorrecto())
		{
			btnIniciar.setEnabled(false);			
			labelEstado.setForeground(Color.RED);
			
			JTextArea taError = new JTextArea(3, 30);
			taError.setText("Ocurrio un error al iniciar el juego.\nPara mas informacion dirigirse al siguiente link:\nhttps://www.youtube.com/watch?v=dQw4w9WgXcQ");
			taError.setWrapStyleWord(true);
			taError.setLineWrap(true);
			taError.setCaretPosition(0);
			taError.setEditable(false);
            
			JOptionPane.showMessageDialog(null, new JScrollPane(taError) , "Error: ", JOptionPane.ERROR_MESSAGE);
		}				
	}
	
	private void refrescarDigitos(EntidadReloj digitos[])
	{
		Duration d = juego.tiempoActual();
		
		String hms = String.format("%02d%02d%02d", 
                d.toHours(), 
                d.toMinutesPart(), 
                d.toSecondsPart());
		
		for (int i = 0; i < digitos.length; i++)
		{	
			digitos[i].actualizar((int) hms.charAt(i) - '0');		            			
		}		
	}
}
