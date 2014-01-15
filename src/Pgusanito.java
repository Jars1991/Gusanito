/*
 * Pgusanito.java es la clase principal que ejecuta el juego 
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.*;

public class Pgusanito extends JFrame implements KeyListener, Runnable {
	private static final long serialVersionUID = 1L;
	Container cont;
	int corx, cory, vel;
	Graphics gr;
	int sen;
	Random r = new Random();
	int tiem;
	int tamfig = 5, tamcom = 30, tamobs = 0;
	Gusanito fig[];
	Gusanito aux[];
	boolean game, pausa;
	Comida com[];
	int ancho, alto, puntaje, senaux, delay, vidas, bolitasComidas;
	Obstaculo obs[];
	Color col;
	static Timer obt;
	String msg;
	String rimg = "imagenes/explosion.gif";
	Sonido sonido = new Sonido();
	ImageIcon img;
	int cx, cy;
	boolean choque;
	static Lienzo lien;
	static VentanaInicio d;
	static Pgusanito obg;
	Thread anima;

	public Pgusanito(String t) {
		vel = 20;
		restablecer();
		setTitle(t);
		cont = getContentPane();
		cont.setSize(ancho, alto);
		setSize(ancho, alto);
		setBackground(Color.black);
		addKeyListener(this);
		requestFocus();
		setResizable(false);
		setLocationRelativeTo(null);
		com = new Comida[tamcom];
		for (int i = 0; i < com.length; i++)
			com[i] = new Comida(ancho, alto);
		img = new ImageIcon(getClass().getClassLoader().getResource(rimg));
		cx = 0;
		choque = false;
		cy = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lien = new Lienzo(com, fig, puntaje, cx, cy, vel, msg, tiem, obs,
				choque, img, this, vidas);
		cont.add(lien);
		d = new VentanaInicio(obg, true);
	}

	private void init() {
		anima = new Thread(this);
		anima.start();
	}

	public static void main(String[] x) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				obg = new Pgusanito(">>>>>> Gusanito <<<<<<<");
				JFrame.setDefaultLookAndFeelDecorated(true);
				obg.iniciar();
			}
		});
	}

	public void iniciar() {
		obt.stop();
		sonido.play("inicio");
		sonido.loop("inicio");

		d.d.setVisible(true);

		if (d.entrar() == -1) {
			obt.start();
			sonido.stop("inicio");
			sonido.stop("fondo");
			sonido.stop("muerte");
			obg.init();
			obg.setVisible(true);// ventana del juego visible
			lien.run = true;
		}
	}

	public void aumentaobs() {
		Obstaculo aux[];
		Random r = new Random();

		aux = obs;
		obs = new Obstaculo[tamobs];// construir mas grande
		int i = obs.length - 1;
		for (; i > 0; i--)
			obs[i] = aux[i - 1];// conserva los mismos colores anteriores
		// agregarle la ultima posicion

		corx = (1 + r.nextInt(25)) * 20;// tenemos q repintar el obs en
										// cordenadas multiplos de vel
		cory = (1 + r.nextInt(15)) * 20;

		obs[i] = new Obstaculo(corx, cory, vel, ancho, alto);
	}

	public void restablecer() {
		vidas = 10;
		cx = 0;
		choque = false;
		cy = 0;
		ancho = 800;
		alto = 600;
		bolitasComidas = 0;
		tiem = 100;
		corx = 400;
		cory = 300;
		tamobs = 1;
		msg = "";
		pausa = false;
		delay = 20000;
		puntaje = 0;
		tamfig = 5;
		fig = new Gusanito[tamfig];
		obs = new Obstaculo[tamobs];
		for (int i = 0, x = 200, y = 60; i < obs.length; i++, x += 100)
			obs[i] = new Obstaculo(x, y, vel, ancho, alto);
		game = true;
		sen = r.nextInt(4);
		senaux = sen;

		if (sen == 3) {
			corx -= 100;
			for (int i = fig.length - 1, dec = 0; i >= 0; i--, dec += 20)
				fig[i] = new Gusanito(corx + dec, cory, vel);
		} else
			for (int i = fig.length - 1, dec = 0; i >= 0; i--, dec += 20)
				fig[i] = new Gusanito(corx - dec, cory, vel);

		obt = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// accion a realizar
				if (tiem > 60) {
					tamobs++;
					aumentaobs();
					tiem -= 10;
					puntaje += 500;
				}
			}

		});
		obt.start();
	}

	public void aumentar(int indc) {
		int decx = 0, decy = 0, difx, dify;
		int cx, cy, cxa, cya;// del ultimo cuadro
		aux = fig;
		fig = new Gusanito[tamfig];// construir mas grande
		int i = fig.length - 1;
		for (; i > 0; i--)
			fig[i] = aux[i - 1];// conserva los mismos colores anteriores
		// agregarle la ultima posicion
		cx = fig[i + 1].corx;
		cy = fig[i + 1].cory;
		cxa = fig[i + 2].corx;
		cya = fig[i + 2].cory;
		// saber sentido del Pgusanito
		difx = (cx - cxa);

		if (difx > 0) {
			// izq
			if (difx != 0) {
				decx = 20;
				decy = 0;
			}

		} else {
			// der
			if (difx != 0) {
				decx = -20;
				decy = 0;
			}

		}
		dify = (cy - cya);

		if (dify > 0) {
			// arri
			if (dify != 0) {
				decx = 0;
				decy = 20;
			}

		} else {
			if (dify != 0) {
				// abajo
				decx = 0;
				decy = -20;
			}
		}

		fig[i] = new Gusanito(cx + decx, cy + decy, vel);
		col = com[indc].getcolor();
		fig[i].asignarColor(col);
	}

	public void retardo(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {

		}
	}

	public void pausado() {
		while (pausa) {
			obt.stop();
			sonido.stop("fondo");
		}
		obt.start();
	}

	public void reiniciar(boolean b) {
		int opt;
		if (!b) {
			choque = true;
			lien.reasignar(com, fig, puntaje, cx, cy, vel, msg, tiem, obs,
					choque, img, this, vidas);
			lien.repaint();
			sonido.stop("fondo");
			sonido.play("muerte");
			sonido.loop("muerte");
			opt = JOptionPane
					.showConfirmDialog(
							this,
							"Has Muerto \nPresiona aceptar para reiniciar \n Presiona cancelar para terminar",
							"Juego Terminado", JOptionPane.OK_CANCEL_OPTION);
			if (opt == JOptionPane.OK_OPTION) {
				obt.stop();
				sonido.stop("muerte");
				sonido.play("fondo");
				sonido.loop("fondo");
				restablecer();
			} else {
				obt.stop();
				sonido.stop("muerte");
				sonido.stop("fondo");
				obg.setVisible(false);
				restablecer();
				iniciar();
			}
		}
	}

	public void comer() {

		int cabx = fig[fig.length - 1].corx, caby = fig[fig.length - 1].cory, comx, comy;

		for (int i = 0; i < com.length; i++) {
			comx = com[i].corx;
			comy = com[i].cory;
			if ((cabx == comx) && (caby == comy)) {
				// significa q se la comio , incrementaremos el puntaje
				puntaje();
				tamfig++;
				aumentar(i);
				aumentarSalud();
				com[i].reposicionar(ancho, alto);
				sonido.stop("comer");
				sonido.play("comer");
			}
		}
	}

	private void aumentarSalud() {
		bolitasComidas++;
		if (bolitasComidas == 5) {
			bolitasComidas = 0;
			vidas++;
		}
	}

	// choque consigo misma
	public void choque() {

		int cabx = fig[fig.length - 1].corx, caby = fig[fig.length - 1].cory;

		for (int i = 0; i < fig.length - 1; i++) {
			if ((cabx == fig[i].corx) && (caby == fig[i].cory)) {
				game = false;
				cx = cabx;
				cy = caby;
				break;
			}
		}
	}

	private void quitarVida() {
		if (vidas > 0) {
			vidas--;
		} else {
			game = false;
		}

	}

	// choque con obstaculo
	public void choque_obs() {
		int cabx, caby, obsx, obsy;

		for (int i = 0; i < fig.length; i++) {
			for (int j = 0; j < obs.length; j++) {
				obsx = obs[j].corx;
				obsy = obs[j].cory;
				cabx = fig[i].corx;
				caby = fig[i].cory;
				if ((cabx == obsx) && (caby == obsy)) {
					// significa q se la comio , incrementaremos el puntaje
					quitarVida();
					cx = cabx;
					cy = caby;
					obs[j].repos(ancho, alto);
					sonido.stop("impacto");
					sonido.play("impacto");
					break;
				}
			}
		}
	}

	public void puntaje() {
		puntaje += 100;

	}

	private void actualizarcord() {
		for (int i = 0; i < fig.length - 1; i++) {
			fig[i].corx = fig[i + 1].corx;
			fig[i].cory = fig[i + 1].cory;
		}
	}

	public void keyPressed(KeyEvent e) {
		int t = e.getKeyCode();

		switch (t) {
		case KeyEvent.VK_UP:// 38
			if (senaux != 2)
				sen = 0;

			break;

		case KeyEvent.VK_DOWN:// 40
			if (senaux != 0)
				sen = 2;

			break;

		case KeyEvent.VK_LEFT:// 37
			if (senaux != 1)
				sen = 3;

			break;

		case KeyEvent.VK_RIGHT:// 39
			if (senaux != 3)
				sen = 1;
			break;
		case KeyEvent.VK_ENTER:
			pausa = !pausa;
			if (pausa)
				msg = "Juego pausado";
			else {
				msg = "";
				sonido.play("fondo");
				sonido.loop("fondo");
			}
		}
		senaux = sen;
		lien.reasignar(com, fig, puntaje, cx, cy, vel, msg, tiem, obs, choque,
				img, this, vidas);
		lien.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
	}

	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void run() {
		sonido.play("fondo");
		sonido.loop("fondo");
		while (obg.isVisible() && game == true) {
			pausado();
			// rectangulos
			actualizarcord();
			fig[fig.length - 1].reposicionar(sen, getWidth(), getHeight());// cabeza
			// del
			// gusano
			comer();
			for (int i = 0; i < obs.length; i++)
				obs[i].reposicionar();
			//
			lien.reasignar(com, fig, puntaje, cx, cy, vel, msg, tiem, obs,
					choque, img, this, vidas);
			//
			lien.repaint();// parece q choca cuando esta encima, asi es en
			// verdad
			game = fig[fig.length - 1].gameOver();
			choque();
			choque_obs();
			reiniciar(game);
			// repaint();//asi parece que choca, sin tener q estar encima xd aun
			// q no es asi
			retardo(tiem);// cada ves son 10
		}
	}
}
