/*
 * 
 */
package fr.utt.pandocreon.java.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The Class MainFrame.
 */
public class MainFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The instance. */
	private static MainFrame instance;
	
	/** The screen. */
	private Screen screen;


	/**
	 * Instantiates a new main frame.
	 */
	private MainFrame() {
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(getRootPane());
		setExtendedState(MAXIMIZED_BOTH);
		setIconImage(Images.getInstance().getImage("icon_16.png"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Sets the screen.
	 *
	 * @param screen
	 *            the screen
	 * @return the main frame
	 */
	public MainFrame setScreen(Screen screen) {
		if (this.screen != null)
			this.screen.onHide(screen);
		this.screen = screen;
		screen.onShow(this);
		setContentPane(screen);
		setTitle("Pandocréon Divinæ" + (screen.getName() == null ? "" : " - " + screen.getName()));
		validate();
		repaint();
		this.screen = screen;
		return this;
	}

	/**
	 * Gets the.
	 *
	 * @return the main frame
	 */
	public static MainFrame get() {
		if (instance == null) {
			lnf("Nimbus");
			instance = new MainFrame();
		}
		return instance;
	}

	/**
	 * Set look and feel.
	 *
	 * @param name
	 *            the look and feel name
	 * @return true if Look and Feel was successfully assigned
	 */
	public static boolean lnf(String name) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if (name.equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					return true;
				}
			}
		} catch (Exception e) {}
		return false;
	}

}
