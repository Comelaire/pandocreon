/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import fr.utt.pandocreon.java.ui.layout.LineLayout;

/**
 * The Class Panel.
 */
public class Panel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant BACKGROUND. */
	public static final Color BACKGROUND = new Color(255, 255, 255, 175);
	
	/** The background. */
	private final ShiningBackground background;

	/**
	 * Instantiates a new panel.
	 *
	 * @param layout
	 *            the layout
	 */
	public Panel(LayoutManager layout) {
		super(layout);
		setBackground(BACKGROUND);
		setBorder(BorderFactory.createEtchedBorder(
				new Color(0, 0, 0, 100), new Color(255, 255, 255, 100)));
		background = new ShiningBackground();
	}

	/**
	 * Instantiates a new panel.
	 */
	public Panel() {
		this(new LineLayout().setGap(10, 10));
	}

	/**
	 * Gets the background border.
	 *
	 * @return the background border
	 */
	public ShiningBackground getBackgroundBorder() {
		return background;
	}

	/**
	 * Paint background border.
	 *
	 * @param g
	 *            the g
	 */
	public void paintBackgroundBorder(Graphics g) {
		background.paintBorder(this, g, getX(), getY(), getWidth(), getHeight());
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintBackgroundBorder(g);
	}

}
