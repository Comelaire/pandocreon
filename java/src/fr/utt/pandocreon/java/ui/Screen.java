/*
 * 
 */
package fr.utt.pandocreon.java.ui;

import java.util.function.Supplier;

import fr.utt.pandocreon.java.ui.component.ImagePanel;

/**
 * The Class Screen.
 */
public class Screen extends ImagePanel implements Runnable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The frame. */
	private MainFrame frame;
	
	/** The previous. */
	private Screen previous;
	
	/** The run. */
	private boolean run;

	/**
	 * On show.
	 *
	 * @param frame
	 *            the frame
	 */
	public void onShow(MainFrame frame) {
		this.frame = frame;
		if (run)
			throw new IllegalStateException("This screen is already running");
		run = true;
		new Thread(this).start();
	}

	/**
	 * On hide.
	 *
	 * @param newScreen
	 *            the new screen
	 */
	public void onHide(Screen newScreen) {
		run = false;
		newScreen.previous = this;
	}

	/**
	 * Back.
	 */
	public void back() {
		frame.setScreen(previous);
	}

	/**
	 * Sets the screen.
	 *
	 * @param screen
	 *            the new screen
	 */
	public void setScreen(Screen screen) {
		frame.setScreen(screen);
	}

	/**
	 * Screen setter.
	 *
	 * @param screen
	 *            the screen
	 * @return the runnable
	 */
	public Runnable screenSetter(Screen screen) {
		return () -> setScreen(screen);
	}

	/**
	 * Screen setter.
	 *
	 * @param screen
	 *            the screen
	 * @return the runnable
	 */
	public Runnable screenSetter(Supplier<Screen> screen) {
		return () -> setScreen(screen.get());
	}

	/**
	 * Wait.
	 *
	 * @param time
	 *            the time
	 */
	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (run) {
			repaint();
			wait(30);
		}
	}

}
