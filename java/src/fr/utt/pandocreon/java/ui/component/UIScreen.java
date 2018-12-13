/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import fr.utt.pandocreon.java.ui.Images;
import fr.utt.pandocreon.java.ui.Screen;

/**
 * The Class UIScreen.
 */
public class UIScreen extends Screen {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant TITLE_FONT. */
	private static final Font TITLE_FONT = new Font(Font.SERIF, Font.BOLD, 60);
	
	/** The scale. */
	private float scale;
	
	/** The up scale. */
	private boolean upScale;

	/**
	 * Instantiates a new UI screen.
	 */
	public UIScreen() {
		setImage(Images.getInstance().getImage("background.jpg"));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.Screen#onHide(fr.utt.pandocreon.java.ui.Screen)
	 */
	@Override
	public void onHide(Screen newScreen) {
		if (newScreen instanceof UIScreen) {
			UIScreen s = (UIScreen) newScreen;
			s.scale = scale;
			s.upScale = upScale;
		}
		super.onHide(newScreen);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.component.ImagePanel#onScale(double)
	 */
	@Override
	public double onScale(double s) {
		if (upScale) {
			scale += .001;
			upScale = scale < .2;
		} else {
			scale -= .001;
			upScale = scale <= 0;
		}
		return s * (1 + scale);
	}

	/**
	 * Draw title.
	 *
	 * @param g
	 *            the g
	 * @param color
	 *            the color
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	public void drawTitle(Graphics2D g, Color color, int dx, int dy) {
		g.setColor(color);
		g.drawString("PANDOCRÉON", getWidth() - 450 + dx, 70 + dy);
		g.drawString("DIVINÆ", getWidth() - 258 + dx, 130 + dy);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.component.ImagePanel#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g1d) {
		super.paintComponent(g1d);
		Graphics2D g = (Graphics2D) g1d;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.drawString("Développé par Athina Schmitt", getWidth() - 165, getHeight() - 5);
		g.setFont(TITLE_FONT);
		for (int i = 1; i < 10; i++)
			drawTitle(g, new Color(50, 0, 25, 100), (int) (i/1.5), i);
		drawTitle(g, Color.WHITE, 0, 0);
		g.setColor(new Color(255, 255, 220, 20));
		int s = (int) (scale * Math.max(getWidth(), getHeight()));
		for (int i = 1; i < s/2; i+=5) {
			g.fillOval((int) (getWidth()/1.1f) - i/2, (int) (getHeight()/3.5) - i/2, i, i);
			g.fillOval((int) (getWidth()/1.1f) - i/3, (int) (getHeight()/3.5) - i/2, i/4, i/4);
			g.fillOval((int) (getWidth()/1.1f), (int) (getHeight()/3.5), i/3, i/3);
			if (i % 2 == 0)
				g.fillRect((int) (getWidth()/1.1f) - i * 2, (int) (getHeight()/3.5), i * 3, 2);
			else g.fillRect((int) (getWidth()/1.098f), i * 2, 2, i * 2);

		}
	}

}
