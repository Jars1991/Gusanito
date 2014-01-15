/*
 * Gusanito.java permite crear el gusanito en base a circulos de colores
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.util.*;
import java.awt.*;

public class Gusanito {
	int corx, cory, vel, sen;
	boolean game;
	Random obr;
	int re, gr, bl;
	Color nuevo;
	boolean ban = true;

	public Gusanito(int cx, int cy, int v) {
		corx = cx;
		cory = cy;
		vel = v;
		game = true;
		obr = new Random();
		re = obr.nextInt(255);
		gr = obr.nextInt(255);
		bl = obr.nextInt(255);
	}

	public void dibujar(Graphics2D g) {
		if (ban)
			nuevo = new Color(re, gr, bl);
		g.setColor(nuevo);
		g.fillRect(corx, cory, 20, 20);

	}

	public void asignarColor(Color col) {
		nuevo = col;
		ban = false;
	}

	public void reposicionar(int sen, int ancho, int alto) {

		switch (sen) {
		case 0:// arriba
			if (cory - vel > 0)
				cory -= vel;
			else
				game = false;

			break;

		case 1:// derecha
			if (corx + vel < ancho - 20)
				corx += vel;
			else
				game = false;

			break;

		case 2:// abajo

			if (cory + vel < alto - 20 * 2)
				cory += vel;
			else
				game = false;

			break;

		case 3:// izquierda

			if (corx - vel > 0)
				corx -= vel;
			else
				game = false;

			break;
		}
	}

	public boolean gameOver() {
		return game;
	}
}
