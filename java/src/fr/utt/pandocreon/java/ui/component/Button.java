/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import fr.utt.pandocreon.java.ui.Sound;

/**
 * The Class Button.
 */
public class Button extends JButton implements ActionListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FONT. */
	private static final Font FONT = new Font(Font.SERIF, Font.BOLD, 15);
	
	/** The listener. */
	private Runnable listener;

	/**
	 * Instantiates a new button.
	 *
	 * @param text
	 *            the text
	 * @param listener
	 *            the listener
	 */
	public Button(String text, Runnable listener) {
		this(text);
		this.listener = listener;
	}

	/**
	 * Instantiates a new button.
	 *
	 * @param text
	 *            the text
	 */
	public Button(String text) {
		super(text.toUpperCase());
		setToolTipText(text);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setForeground(Color.DARK_GRAY.darker());
		setFont(FONT);
		setBorder(new ShiningBackground());
		addActionListener(this);
	}

	/**
	 * Smaller.
	 *
	 * @return the button
	 */
	public Button smaller() {
		setFont(getFont().deriveFont(Font.PLAIN, 12f));
		((ShiningBackground) getBorder()).smaller();
		return this;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Sound.getInstance().play("button.wav");
		if (listener != null)
			listener.run();
	}

}
