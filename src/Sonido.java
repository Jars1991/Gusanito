/*
 * Sonido.java permite controlar los efectos de sonido del juego 
 * Author: Jassael Ruiz
 * Version: 1.0
 */

import java.net.URL;
import java.util.HashMap;
import java.applet.*;

public class Sonido {
	AudioClip sonido;
	URL url;
	HashMap<String, AudioClip> sonidos;
	String songs[] = { "comer", "fondo", "impacto", "inicio", "muerte" };

	public Sonido() {
		sonidos = new HashMap<String, AudioClip>();
		for (String name : songs)
			sonidos.put(name, getURL("musica/" + name + ".wav"));
	}

	private AudioClip getURL(String name) {
		url = getClass().getClassLoader().getResource(name);
		sonido = Applet.newAudioClip(url);
		return sonido;
	}

	public void play(String name) {
		sonido = sonidos.get(name);
		sonido.play();
	}

	public void loop(String name) {
		sonido = sonidos.get(name);
		sonido.loop();
	}

	public void stop(String name) {
		sonido = sonidos.get(name);
		sonido.stop();
	}
}