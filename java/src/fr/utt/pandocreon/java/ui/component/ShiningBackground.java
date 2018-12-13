/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.border.Border;

/**
 * The Class ShiningBackground.
 */
public class ShiningBackground implements Border {
	
	/** The Constant R. */
	private static final Random R = new Random();
	
	/** The progress. */
	private int progress;
	
	/** The insets. */
	private Insets insets;

	/**
	 * Instantiates a new shining background.
	 */
	public ShiningBackground() {
		progress = R.nextInt(100);
		insets = new Insets(15, 20, 15, 20);
	}

	/**
	 * Smaller.
	 *
	 * @return the shining background
	 */
	public ShiningBackground smaller() {
		insets = new Insets(5, 10, 5, 10);
		return this;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.Border#getBorderInsets(java.awt.Component)
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return insets;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.Border#isBorderOpaque()
	 */
	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.Border#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		if (c.isEnabled()) {
			progress++;
			if (progress > 50 && progress < 75) {
				int nx = ((progress - 50) * c.getWidth())/15;
				int w = width/10;
				g.setColor(new Color(255, 180 + progress, 255 - progress/2, progress * 2 - 50));
				g.fillRect(nx - w, 0, w, height);
				g.fillRect(nx - w/3, 0, w, height);
			}
			if (progress > 150) {
				progress = 0;
			}
		}
	}

}
