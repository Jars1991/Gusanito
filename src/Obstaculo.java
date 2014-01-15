/*
 * Obstaculo.java permite crear cuadritos cuya funcion es la de matar al gusanito 
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Obstaculo {
	int corx, cory, vel;
	int sen, radio = 20;
	int maxx, maxy;
	Random r;

	public Obstaculo(int cx, int cy, int v, int mx, int my) {
		corx = cx;
		cory = cy;
		vel = v;
		maxx = mx;
		maxy = my;
		r = new Random();
		sen = r.nextInt(8);
	}

	public void dibujar(Graphics2D g) {
		/*
		 * g.setColor(Color.WHITE); g.fillRect(corx, cory, 20, 20);
		 */
		Graphics2D g2d = (Graphics2D) g;
		GradientPaint g1 = new GradientPaint(10.0f, 50.0f, Color.blue, 50.0f,
				30.0f, Color.white, true);
		g2d.setPaint(g1);
		Rectangle2D r1 = new Rectangle2D.Float(corx, cory, 20, 20);
		g2d.fill(r1);
	}

	public void repos(int ancho, int alto) {
		corx = (2 + r.nextInt(33)) * 20;
		cory = (2 + r.nextInt(20)) * 20;
	}

	public void reposicionar() {
		Random r = new Random();

		switch (sen) {
		case 0: // arriba
			if (cory - vel > 0)
				cory -= vel;
			else {

				sen = 3 + r.nextInt(3);
			}
			break;
		case 1:// arriab der
			if ((corx + vel < maxx - radio) && (cory - vel > 0)) {
				cory -= vel;
				corx += vel;
			} else {
				sen = 4 + r.nextInt(3);
			}
			break;
		case 2:// der
			if (corx + vel < maxx - radio)
				corx += vel;
			else {
				sen = 5 + r.nextInt(3);
			}
			break;
		case 3:// abaajo der
			if (corx + vel < (maxx - radio) && cory + vel < (maxy - radio * 2)) {
				corx += vel;
				cory += vel;
			} else {
				sen = 6 + r.nextInt(3);
				if (sen == 8)
					sen = 0;
			}
			break;
		case 4:// abajo
			if (cory + vel < (maxy - radio * 2))
				cory += vel;
			else {
				sen = r.nextInt(3);// sen 7,0,1
				if (sen == 2)
					sen = 7;
			}

			break;

		case 5:// aabajo izq
			if (corx - vel > 0 && (cory + vel < (maxy - radio * 2))) {
				corx -= vel;
				cory += vel;
			} else {
				sen = r.nextInt(3);// 0,1,2
			}
			break;

		case 6:// izq
			if (corx - vel > 0)
				corx -= vel;
			else
				sen = 1 + r.nextInt(3);
			break;

		case 7: // izq arriba
			if (corx - vel > 0 && cory - vel > 0) {
				corx -= vel;
				cory -= vel;

			} else {
				sen = 2 + r.nextInt(3);
			}
			break;
		}
	}
}
