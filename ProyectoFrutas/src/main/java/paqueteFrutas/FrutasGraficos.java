package paqueteFrutas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class FrutasGraficos extends ElementoBasico {

	private BufferedImage img;
	
	public FrutasGraficos(int posicionX,  int posicionY, double velocidadX, double velocidadY, int ancho, int largo, Color color)  {
		super(posicionX, posicionY, velocidadX, velocidadY, ancho, largo, color);
		try {
			String path = Paths.get(FrutasGraficos.class.getClassLoader().getResource("imagenes/cherry.png").toURI()).toString();
			this.img = ImageIO.read(new File(path));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void dibujarse(Graphics graphics) {
		try {
			graphics.drawImage(img, getPosicionX(), getPosicionY(), this.getAncho(), this.getLargo(), null);
			
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}
    
    public void Ciclo() {
    	int x = this.getPosicionY();
    	this.setPosicionY( x += 1);
    }
}
