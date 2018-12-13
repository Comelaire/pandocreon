/*
 * 
 */
package fr.utt.pandocreon.java.ui.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * The Class ImagePanel.
 */
public class ImagePanel extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The image. */
	private BufferedImage image;

	/**
	 * Instantiates a new image panel.
	 */
	public ImagePanel() {
		super(new BorderLayout());
		setOpaque(false);
	}

	/**
	 * Instantiates a new image panel.
	 *
	 * @param image
	 *            the image
	 */
	public ImagePanel(BufferedImage image) {
		this();
		setImage(image);
	}

	/**
	 * Instantiates a new image panel.
	 *
	 * @param layout
	 *            the layout
	 */
	public ImagePanel(LayoutManager layout) {
		this();
		setLayout(layout);
	}

	/**
	 * On scale.
	 *
	 * @param scale
	 *            the scale
	 * @return the double
	 */
	public double onScale(double scale) {
		return scale;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		if(image != null) {
			int w = image.getWidth();
			int h = image.getHeight();
			double scale = getWidth()/(double) w;
			if (h * scale < getHeight())
				scale = getHeight()/(double) h;
			scale = onScale(scale);
			w *= scale;
			h *= scale;
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(image, (getWidth() - w)/2, (getHeight() - h)/2, w, h, null);
		}
		super.paintComponent(g);
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the new image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
		repaint();
	}

	/**
	 * Fit to image.
	 *
	 * @return the image panel
	 */
	public ImagePanel fitToImage() {
		if(image != null)
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		return this;
	}

}
