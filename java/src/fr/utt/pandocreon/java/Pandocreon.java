/*
 * 
 */
package fr.utt.pandocreon.java;

import fr.utt.pandocreon.core.game.Game.GameBuilder;
import fr.utt.pandocreon.java.ui.MainFrame;
import fr.utt.pandocreon.java.ui.Sound;
import fr.utt.pandocreon.java.ui.screen.HomeScreen;

/**
 * The Class Pandocreon.
 */
public class Pandocreon {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Sound.getInstance().loop("loop.wav");
		//Enlever le commentaire si vous voulez découvrir le mode graphique !
		MainFrame.get().setScreen(new HomeScreen()).setVisible(true);
		
		//Mode console
		new Thread(new GameInputManager(new GameBuilder(new XMLResourceResolver()))).start();
	}

}
