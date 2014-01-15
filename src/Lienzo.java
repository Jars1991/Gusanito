/*
 * Lienzo.java es el area de dibujo del juego
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Lienzo extends JPanel {
	private static final long serialVersionUID = 1L;
	Comida com[];
	Gusanito fig[];
	int puntaje, cx, cy, vel;
	String msg;
	int tiem, ancho, alto;
	Obstaculo obs[];
	boolean choque;
	ImageIcon img;
	Pgusanito vent;
	int nivel;
	private BufferedImage background;
	int increment = 1, vidas;
	private Timer timer;
	private TimerTask timerTask;
	private long delay = 100;
	boolean run;

	public Lienzo(Comida com[], Gusanito fig[], int puntaje, int cx, int cy,
			int vel, String msg, int tiem, Obstaculo obs[], boolean choque,
			ImageIcon img, Pgusanito vent, int vidas) {
		ancho = 800;
		alto = 600;
		setSize(ancho, alto);
		this.com = com;
		this.fig = fig;
		this.puntaje = puntaje;
		this.cx = cx;
		this.cy = cy;
		this.vel = vel;
		this.msg = msg;
		this.tiem = tiem;
		this.obs = obs;
		this.choque = choque;
		this.img = img;
		background = getImage("papel.gif");
		setTimer();
		this.vent = vent;
		this.vidas = vidas;
	}

	private BufferedImage loadImage(String nombre) {
		URL url = null;
		try {
			url = getClass().getClassLoader().getResource(nombre);
			return ImageIO.read(url);
		} catch (Exception e) {
			System.out.println("No se pudo cargar la imagen " + nombre + " de "
					+ url);
			System.out.println("El error fue : " + e.getClass().getName() + " "
					+ e.getMessage());
			System.exit(0);
			return null;
		}
	}

	public BufferedImage getImage(String nombre) {
		BufferedImage img = null;
		img = loadImage("imagenes/" + nombre);
		return img;
	}

	public void reasignar(Comida com[], Gusanito fig[], int puntaje, int cx,
			int cy, int vel, String msg, int tiem, Obstaculo obs[],
			boolean choque, ImageIcon img, Pgusanito vent, int vidas) {
		ancho = 800;
		alto = 600;
		setSize(ancho, alto);
		this.com = com;
		this.fig = fig;
		this.puntaje = puntaje;
		this.cx = cx;
		this.cy = cy;
		this.vel = vel;
		this.msg = msg;
		this.tiem = tiem;
		this.obs = obs;
		this.choque = choque;
		this.img = img;
		nivel = 0;
		this.vent = vent;
		this.vidas = vidas;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(new TexturePaint(background, new Rectangle(increment,
				-increment, background.getWidth(), background.getHeight())));
		g2.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < com.length; i++)
			com[i].dibujar(g2);
		for (int i = fig.length - 1; i >= 0; i--) {
			fig[i].dibujar(g2);
		}
		// dibujar score
		Font fuente = new Font("Calibri", Font.BOLD, 20);
		g2.setFont(fuente);
		g2.setColor(Color.black);
		g2.drawString("SCORE : " + puntaje, 10, 20);
		nivel();
		g2.setColor(Color.red);
		g2.drawString("SALUD: ", 300, 20);
		g2.fillRect(375, 3, vent.vidas * 10, 20);
		g2.setColor(Color.black);
		g2.drawString("NIVEL : " + nivel, 680, 20);
		for (int i = 0; i < obs.length; i++)
			obs[i].dibujar(g2);
		if (choque)
			g2.drawImage(img.getImage(), cx - vel, cy - vel, 70, 70, vent);
		fuente = new Font("digifacewide", Font.BOLD, 30);
		g2.setFont(fuente);
		g2.setColor(Color.black);
		g2.drawString(msg, vent.getWidth() / 2 - 100, vent.getHeight() / 2);
	}

	private void setTimer() {
		timer = new Timer();
		timerTask = new TimerTask() {
			public void run() {
				if (run) {
					increment++;
				}
			}
		};
		timer.scheduleAtFixedRate(timerTask, 0, delay);
	}

	public void nivel() {
		switch (tiem) {
		case 100:
			nivel = 1;
			break;
		case 90:
			nivel = 2;
			break;
		case 80:
			nivel = 3;
			break;
		case 70:
			nivel = 4;
		case 60:
			nivel = 5;
		}
	}
}
