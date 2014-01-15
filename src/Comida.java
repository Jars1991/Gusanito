/*
 * Comida.java permite crear circulos de colores los cuales podra comer el gusanito
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.util.*;
import java.awt.*;

public class Comida {
	int corx, cory;
	Random r;
	int re, gr, bl;
	Color nuevo;

	public Comida(int ancho, int alto) {
		r = new Random();
		corx = (2 + r.nextInt(33)) * 20;// tenemos q repintar la bolita en
										// cordenadas multiplos de 20
		cory = (2 + r.nextInt(20)) * 20;// o sea igual a la velocidad de la
										// serpiente
		re = r.nextInt(255);
		gr = r.nextInt(255);
		bl = r.nextInt(255);
	}

	public void dibujar(Graphics2D g) {
		nuevo = new Color(re, gr, bl);
		g.setColor(nuevo);
		g.fillOval(corx, cory, 20, 20);
	}

	public void reposicionar(int ancho, int alto) {
		corx = (2 + r.nextInt(33)) * 20;
		cory = (2 + r.nextInt(20)) * 20;
		re = r.nextInt(255);
		gr = r.nextInt(255);
		bl = r.nextInt(255);
	}

	public Color getcolor() {
		return nuevo;
	}
}
