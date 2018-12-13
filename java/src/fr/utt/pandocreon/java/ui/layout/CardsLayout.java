/*
 * 
 */
package fr.utt.pandocreon.java.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * The Class CardsLayout.
 */
public class CardsLayout implements LayoutManager {
	
	/** The Constant FROM_END. */
	public static final boolean FROM_END = true;
	
	/** The Constant COEF. */
	private static final double COEF = 1.5;


	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		int h = parent.getHeight();
		int w = (int) (h/COEF);
		if (parent.getComponentCount() == 1) {
			parent.getComponent(0).setBounds((parent.getWidth() - w)/2, 0, w, h);
		} else {
			int x = 0;
			int dx = (parent.getWidth() - (parent.getComponentCount() * w))/(parent.getComponentCount() - 1);
			if (dx > 5) {
				dx = 5;
				x = (parent.getWidth() - parent.getComponentCount() * w)/2;
			}
			if(FROM_END)
				for(int i = parent.getComponentCount() - 1 ; i>=0 ; i--) {
					parent.getComponent(i).setBounds(x, 0, w, h);
					x += dx + w;
				}
			else for(final Component c : parent.getComponents()) {
				c.setBounds(x, 0, w, h);
				x += dx + w;
			}
		}
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
		Dimension d = new Dimension((int) (parent.getSize().height/COEF), parent.getSize().height);
		if (parent.getComponentCount() == 1)
			return d;
		return new Dimension(d.width * parent.getComponentCount(), d.height);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {}
	
	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {}
}
