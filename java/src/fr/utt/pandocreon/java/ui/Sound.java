/*
 * 
 */
package fr.utt.pandocreon.java.ui;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Singleton permettant de jouer des sons via le repertoire "sound", devant etre
 * place au meme endroit que le jar executable.
 */
public class Sound {
	
	/** The Constant PATH. */
	private static final String PATH = "/sounds/";
	
	/** The instance. */
	private static Sound instance;
	
	/** The sounds. */
	private final Map<String, Clip> sounds;
	
	/** The disabled. */
	private boolean disabled;


	/**
	 * Design-pattern singleton. Cree l'unique instance de cet objet s'il n'existe pas encore
	 * @return l'unique instance de cet classe
	 */
	public static Sound getInstance() {
		synchronized(Images.class) {
			if(instance == null)
				instance = new Sound();
			return instance;
		}
	}

	/**
	 * Constructeur prive, selon le design-pattern singleton.
	 */
	private Sound() {
		sounds = new HashMap<String, Clip>();
	}

	/**
	 * Charge le son selon son nom, depuis le repertoire "sons", devant etre
	 * place au meme endroit que le jar executable.
	 *
	 * @param name
	 *            le nom du son a jouer
	 * @throws UnsupportedAudioFileException
	 *             si le format de fichiers n'est pas supporte
	 * @throws IOException
	 *             si une erreur d'entree/sortie survient
	 * @throws LineUnavailableException
	 *             si une erreur survient a la lecture de ce fichier
	 */
	public void load(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Clip clip = AudioSystem.getClip();
		clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(PATH + name)));
		sounds.put(name, clip);
	}

	/**
	 * Si le son n'a pas ete charge, memorise le son via la methode
	 * {@link #load(String)}.
	 *
	 * @param name
	 *            le nom du son a jouer
	 * @return le son
	 * @throws UnsupportedAudioFileException
	 *             si le format de fichiers n'est pas supporte
	 * @throws IOException
	 *             si une erreur d'entree/sortie survient
	 * @throws LineUnavailableException
	 *             si une erreur survient a la lecture de ce fichier
	 */
	public Clip getSound(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(!sounds.containsKey(name))
			load(name);
		return sounds.get(name);
	}

	/**
	 * Joue le son en boucle. Si le son n'a pas ete charge, memorise le son via la methode {@link #load(String)}
	 * @param sound le nom du son a jouer
	 */
	public void loop(String sound) {
		if(!disabled) try {
			Clip c = getSound(sound);
			c.setFramePosition(0);
			c.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e) {
			System.err.println("Cannot play " + sound + ". Sounds are now disabled");
			disabled = true;
		}
	}
	
	/**
	 * Stoppe le son.
	 *
	 * @param sound
	 *            le nom du son a stopper
	 */
	public void stop(String sound) {
		try {
			getSound(sound).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Joue le son. Si le son n'a pas ete charge, memorise le son via la methode {@link #load(String)}
	 * @param sound le nom du son a jouer
	 */
	public void play(String sound) {
		if(!disabled) try {
			Clip c = getSound(sound);
			c.setFramePosition(0);
			c.start();
		} catch(Exception e) {
			System.err.println("Cannot play " + sound + ". Sounds are now disabled");
			disabled = true;
		}
	}

}
