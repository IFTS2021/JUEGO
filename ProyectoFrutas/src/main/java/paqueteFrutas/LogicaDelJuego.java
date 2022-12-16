package paqueteFrutas;

	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.Graphics;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.util.ArrayList;
	import javax.swing.JPanel;

	public class LogicaDelJuego extends JPanel implements KeyListener, Runnable {

		private final static int PANTALLA_INICIO = 1;
	    private final static int PANTALLA_JUEGO = 2;
	    private final static int PANTALLA_PERDEDOR = 3;
	    private final static int PANTALLA_GANADOR = 4;
	    private static final long serialVersionUID = 1L;
	    private int anchoJuego;
	    private int largoJuego;
	    private int tiempoDeEsperaEntreActualizaciones;
	    private ElementoBasico paleta;
	    ArrayList<FrutasGraficos> listaDeObjetos;    
	    private FrutasGraficos fruta1;
	    private FrutasGraficos fruta2;
	    private FrutasGraficos fruta3;
	    private FrutasGraficos fruta4;
	    private FrutasGraficos fruta5;
	    private BichosGraficos bicho;
	    private Puntaje puntaje;
	    private Vidas vidas;
	    int rango1 = 0;
	    int rango2 = 0;
	    int rango3 = 0;
	    int rango4 = 0;
	    int rango5 = 0;
	    int rango111 = 0;
	    private Sonidos sonidos;
	    private int pantallaActual;
	    private int cantidadVidas;
	    private PantallaImagen portada;
	    private PantallaImagen fondo;
	    private PantallaImagen pantallaGanador;
	    private PantallaImagen pantallaEsperar;
	    private PantallaPerdedor pantallaPerdedor;

	    public LogicaDelJuego(int anchoJuego, int largoJuego, int tiempoDeEsperaEntreActualizaciones, int vidas, ArrayList<FrutasGraficos> listaDeObjetos) {
	        this.pantallaActual = PANTALLA_INICIO;
	        this.anchoJuego = anchoJuego;
	        this.largoJuego = largoJuego;
	        this.paleta = new Canasta(150, 750, 0, 0, 130, 140, Color.black);
	        this.listaDeObjetos = listaDeObjetos;
	        this.rango1 = Aleatorio(650,10);
	        this.fruta1 = new FrutasGraficos(rango1, -15, 0, 1, 60, 60, Color.blue);
	        this.rango2 = Aleatorio(650,10);
			this.fruta2 = new FrutasGraficos(rango3, -5, 0, 1, 60, 60, Color.magenta);   
			this.rango3 = Aleatorio(650,10);
			this.fruta3 = new FrutasGraficos(rango3, -210, 0, 1, 60, 60, Color.blue);   
			this.rango4 = Aleatorio(650,10);
	        this.fruta4 = new FrutasGraficos(rango4, -120, 0, 1, 60, 60, Color.red);
	        this.rango5 = Aleatorio(650,10);
	        this.fruta5 = new FrutasGraficos(rango5, -230, 0, 1, 60, 60, Color.CYAN);       
	        this.rango111 = Aleatorio(650,10);
	        this.bicho = new BichosGraficos(rango111, -140, 0, 1, 60, 60, Color.black);      
	        this.vidas = new Vidas(10, 45, new Font("Arial", 8, 20), Color.blue, vidas);
	        this.tiempoDeEsperaEntreActualizaciones = tiempoDeEsperaEntreActualizaciones;
	        this.cantidadVidas = vidas;
	        this.portada = new PantallaImagen(anchoJuego, largoJuego, "imagenes/portada.png");
	        this.fondo = new PantallaImagen(anchoJuego, largoJuego, "imagenes/fondoJuego.PNG");
	        this.pantallaGanador = new PantallaImagen(anchoJuego, largoJuego, "imagenes/ganaste.png");
	        this.pantallaEsperar = new PantallaImagen(anchoJuego, largoJuego, "imagenes/esperar.png");
	        cargarSonidos();
	        this.sonidos.repetirSonido("juego");
	        inicializarJuego();
	    }

		public static int Aleatorio(int Max, int Min) {
	    	return (int) (Math.random() * (Max-Min));
	    }
	    
	    private void inicializarJuego() {
	        this.pantallaPerdedor = null;
	        this.vidas = new Vidas(10, 110, new Font("Arial", 80, 40), Color.BLACK, cantidadVidas);
	        this.puntaje = new Puntaje(10, 50, new Font("Arial", 80, 40), Color.BLACK);       
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return new Dimension(anchoJuego, largoJuego);
	    }

	    @Override
	    public void run() {
	        while (true) {
	        	
	            if (pantallaActual == PANTALLA_JUEGO) {
	                actualizarJuego();
	            }
	            dibujarJuego();
	            esperar(tiempoDeEsperaEntreActualizaciones);
	        }
	    }

	    @Override
	    public void keyPressed(KeyEvent arg0) {

	        if (pantallaActual == PANTALLA_INICIO) {
	            inicializarJuego();
	            pantallaActual = PANTALLA_JUEGO;
	        }

	        if (pantallaActual == PANTALLA_PERDEDOR || pantallaActual == PANTALLA_GANADOR) {
	            pantallaActual = PANTALLA_INICIO;
	        }

	        if (pantallaActual == PANTALLA_JUEGO) {
	            if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
	            	paleta.setVelocidadX(5);
	            }
	            if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
	            	paleta.setVelocidadX(-5);
	            }
	        }
	    }

	    @Override
	    public void keyReleased(KeyEvent arg0) {
	        if (arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_LEFT) {
	        	paleta.setVelocidadX(0);
	        }
	    }

	    @Override
	    public void keyTyped(KeyEvent arg0) {
	        
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        if (pantallaActual == PANTALLA_INICIO) {
	            dibujarInicioJuego(g);
	        }
	        if (pantallaActual == PANTALLA_PERDEDOR) {
	            if (this.pantallaPerdedor == null) {
	                this.pantallaPerdedor = new PantallaPerdedor(anchoJuego, largoJuego, "imagenes/perdiste.png", this.puntaje.getPuntaje());
	            }
	            pantallaPerdedor.dibujarse(g);
	        }
	        if (pantallaActual == PANTALLA_GANADOR) {
	            pantallaGanador.dibujarse(g);
	        }
	        if (pantallaActual == PANTALLA_JUEGO) {
	        	this.fondo = new PantallaImagen(anchoJuego, largoJuego, "imagenes/fondoJuego.PNG");
	        	fondo.dibujarse(g);
	        	paleta.dibujarse(g);
	            puntaje.dibujarse(g);
	            vidas.dibujarse(g);
	            fruta1.dibujarse(g);
	            fruta2.dibujarse(g);
	            fruta3.dibujarse(g);
	            fruta4.dibujarse(g);
	            fruta5.dibujarse(g);
	            bicho.dibujarse(g);
	            
	        }
	    }

	    private void actualizarJuego() {
	        verificarEstadoAmbiente();
	        paleta.moverse();
	        fruta1.moverse();
	        fruta2.moverse();
	        fruta3.moverse();
	        fruta4.moverse();
	        fruta5.moverse();
	        bicho.moverse();     
	    }

	    private void dibujarJuego() {
	        this.repaint();
	    }

	    private void dibujarInicioJuego(Graphics g) {
	        portada.dibujarse(g);
	    }

	    private void verificarEstadoAmbiente() {
	    	verificarColisiones();
	    	verificarFinDeJuego();
	    }
	    
	    private void verificarColisiones() {
	    	ArrayList<FrutasGraficos> listaDeObjetos = new ArrayList<FrutasGraficos>();
	    	listaDeObjetos.add(fruta1);
	    	listaDeObjetos.add(fruta2);
	    	listaDeObjetos.add(fruta3);
	    	listaDeObjetos.add(fruta4);
	    	listaDeObjetos.add(fruta5);
	        
	        int i;
	        for ( i=0; i<listaDeObjetos.size();i++) {
	        	FrutasGraficos frutas = (FrutasGraficos) listaDeObjetos.get(i);
	        	frutas.Ciclo();
	        	
	        	if (frutas.getPosicionY()>880) {
	        		int rango = Aleatorio(700,50);
	            	frutas.setPosicionY(0);
	            	frutas.setPosicionX(rango);
	          
	        	} else if (frutas.hayColision(paleta)) {
	        		int rango = Aleatorio(700,50);
	        		frutas.setPosicionY(0);
	        		frutas.setPosicionX(rango);
	                    puntaje.sumarPunto();
	                    sonidos.tocarSonido("tin");
	            	}
	        			frutas.setVelocidadY(frutas.getVelocidadY()+0.005);      		
	        }

	        bicho.Ciclo();
	       
	        if (bicho.getPosicionY() > 880) {
	        	int rango = Aleatorio(700,50);
	        	bicho.setPosicionY(0);
	        	bicho.setPosicionX(rango);
	        	
	        } else if (bicho.hayColision(paleta)) {
	        	bicho.setPosicionY(0);
	        	vidas.perderVida();
	        	sonidos.tocarSonido("toc");

	        	this.pantallaEsperar = new PantallaImagen(anchoJuego, largoJuego, "imagenes/esperar.png");
	        	pantallaEsperar.dibujarse(getGraphics());
	            esperar(5000);     	
	    	}
	        bicho.setVelocidadY(bicho.getVelocidadY()+0.005);
	    }

	    private void verificarFinDeJuego() {

	        if (vidas.getVidas() == 0) {
	            pantallaActual = PANTALLA_PERDEDOR;
	            
	        }

	        if (puntaje.getPuntaje() == 15) {
	            pantallaActual = PANTALLA_GANADOR;
	             
	        }
	    }

	    private void esperar(int milisegundos) {
	        try {
	            Thread.sleep(milisegundos);
	        } catch (Exception e1) {
	            throw new RuntimeException(e1);
	        }
	    }
	    
	    private void cargarSonidos() {
	        try {
	            sonidos = new Sonidos();
	            sonidos.agregarSonido("toc", "sonidos/toc.wav");
	            sonidos.agregarSonido("tin", "sonidos/tin.wav");
	            sonidos.agregarSonido("perdedor", "sonidos/perdedor.wav");
	            sonidos.agregarSonido("ganador", "sonidos/ganador.wav");
	            sonidos.agregarSonido("juego", "sonidos/juego.wav");
	        } catch (Exception e1) {
	            throw new RuntimeException(e1);
	        }
	    }
	}