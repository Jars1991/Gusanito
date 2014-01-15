/*
 * VentanaInicio.java muestra una ventana de inicio que contiene opciones
 * para salir o iniciar el juego 
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentanaInicio extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton start, exit;
	ImageIcon st, ex, fon;
	Container cont;
	int tamx = 479;
	int tamy = 350, x;
	JLabel l;
	String name = "Menu";
	JDialog d;

	public VentanaInicio(Pgusanito owner, boolean mod) {
		d = new JDialog(owner, mod);
		d.setTitle(name);
		setSize(tamx, tamy);
		d.setSize(tamx, tamy);
		d.setResizable(false);
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		x = 0;
		setLayout(null);
		cont = d.getContentPane();
		cont.setSize(tamx, tamy);
		st = new ImageIcon(getClass().getClassLoader().getResource(
				"imagenes/play.png"));
		ex = new ImageIcon(getClass().getClassLoader().getResource(
				"imagenes/exit.png"));
		fon = new ImageIcon(getClass().getClassLoader().getResource(
				"imagenes/snake2.png"));
		l = new JLabel();
		l.setBounds(0, 0, tamx, tamy - 25);
		l.setIcon(fon);
		start = new JButton();
		exit = new JButton();
		start.setIcon(st);
		exit.setIcon(ex);
		exit.addActionListener(this);
		start.addActionListener(this);
		start.setBounds(180, 200, 120, 53);
		exit.setBounds(180, 260, 120, 53);
		l.add(start);
		l.add(exit);
		add(l);
		cont.add(this);
	}

	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == exit) {
			System.exit(0);
		}
		if (a.getSource() == start) {
			x = -1;
			d.setVisible(false);
		}
	}

	public int entrar() {
		return x;
	}
}