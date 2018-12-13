/*
 * 
 */
package fr.utt.pandocreon.java.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * The Class CenterLayout.
 */
public class CenterLayout implements LayoutManager {
	
	/** The height. */
	private final int width, height;


	/**
	 * Instantiates a new center layout.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public CenterLayout(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Instantiates a new center layout.
	 */
	public CenterLayout() {
		this(0, 0);
	}

	/**
	 * Layout.
	 *
	 * @param parent
	 *            the parent
	 * @param c
	 *            the c
	 */
	public void layout(Component parent, Component c) {
		setSize(parent, c, width == 0 ? c.getPreferredSize().width : width,
				height == 0 ? c.getPreferredSize().height : height);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		for(Component c : parent.getComponents())
			layout(parent, c);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension();
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return new Dimension(parent.getWidth(), parent.getHeight());
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {}


	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {}

	/**
	 * Sets the size.
	 *
	 * @param parent
	 *            the parent
	 * @param c
	 *            the c
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	public static void setSize(Component parent, Component c, int w, int h) {
		if(parent.getWidth() < w)
			w = parent.getWidth();
		if(parent.getHeight() < h)
			h = parent.getHeight();
		c.setBounds((parent.getWidth() - w)/2, (parent.getHeight() - h)/2, w, h);
	}

}
