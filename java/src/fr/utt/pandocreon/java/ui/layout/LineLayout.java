/*
 * 
 */
package fr.utt.pandocreon.java.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * The Class LineLayout.
 */
public class LineLayout implements LayoutManager {
	
	/** The from bottom. */
	private boolean maxWidth, fromBottom;
	
	/** The h gap. */
	private int vGap, hGap;


	/**
	 * Instantiates a new line layout.
	 */
	public LineLayout() {
		this(true);
	}

	/**
	 * Instantiates a new line layout.
	 *
	 * @param largeurMax
	 *            the largeur max
	 */
	public LineLayout(boolean largeurMax) {
		this.maxWidth = largeurMax;
	}

	/**
	 * Sets the gap.
	 *
	 * @param vGap
	 *            the v gap
	 * @param hGap
	 *            the h gap
	 * @return the line layout
	 */
	public LineLayout setGap(int vGap, int hGap) {
		this.vGap = vGap;
		this.hGap = hGap;
		return this;
	}

	/**
	 * From bottom.
	 *
	 * @return the line layout
	 */
	public LineLayout fromBottom() {
		fromBottom = true;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {

	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		int y = vGap;
		for(final Component c : parent.getComponents()) {
			int w = maxWidth ? parent.getWidth() - hGap * 2 : Math.max(c.getMinimumSize().width, c.getPreferredSize().width);
			int h = Math.max(c.getMinimumSize().height, c.getPreferredSize().height);
			c.setBounds((parent.getWidth() - w)/2, fromBottom ? parent.getHeight() - h - y : y, w, h);
			y += h + vGap;
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension d = null;
		for(final Component c : parent.getComponents()) {
			if(d == null)
				d = c.getMinimumSize();
			else {
				if(c.getMinimumSize().width > d.width)
					d.width = c.getMinimumSize().width;
				d.height += c.getMinimumSize().height + vGap;
			}
		}
		if (d != null) {
			d.height += vGap * 2;
			d.width += hGap * 2;
		}
		return d == null ? new Dimension() : d;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = null;
		for(final Component c : parent.getComponents()) {
			if(d == null)
				d = c.getPreferredSize();
			else {
				if(c.getPreferredSize().width > d.width)
					d.width = c.getPreferredSize().width;
				d.height += c.getPreferredSize().height + vGap;
			}
		}
		if (d != null) {
			d.height += vGap * 2;
			d.width += hGap * 2;
		}
		return d == null ? new Dimension(150, 25 * parent.getComponentCount() + 30) : d;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {

	}



}
