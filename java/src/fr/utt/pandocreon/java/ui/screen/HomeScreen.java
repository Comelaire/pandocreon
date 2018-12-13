/*
 * 
 */
package fr.utt.pandocreon.java.ui.screen;

import fr.utt.pandocreon.java.ui.component.Button;
import fr.utt.pandocreon.java.ui.component.Panel;
import fr.utt.pandocreon.java.ui.component.UIScreen;
import fr.utt.pandocreon.java.ui.layout.CenterLayout;

/**
 * The Class HomeScreen.
 */
public class HomeScreen extends UIScreen {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The create. */
	private final Button create;

	/**
	 * Instantiates a new home screen.
	 */
	public HomeScreen() {
		create = new Button("Jouer", screenSetter(CreateGameScreen::new));

		setLayout(new CenterLayout(300, 0));

		Panel options = new Panel();

		options.add(create);

		add(options);
	}


}
