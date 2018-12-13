/*
 * 
 */
package fr.utt.pandocreon.java.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * {@link LayoutManager} dont les composants se placents les uns en dessous des
 * autres en conservant leur taille preferee en hauteur, et en prennant la
 * taille maximale en largeur, moins un espace lateral.
 */
public class VerticalLayout implements LayoutManager {
	
	/** The lateral space. */
	private final int lateralSpace;


	/**
	 * Cree un layout conservant la hauteur preferee du composant, et
	 * harmonisant toutes leurs largeurs a celle du containeur moins l'espace
	 * lateral.
	 *
	 * @param lateralSpace
	 *            l'espacement (en pixels) de chaque cote du composant
	 */
	public VerticalLayout(int lateralSpace) {
		this.lateralSpace = lateralSpace;
	}


	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		if(parent.getComponentCount()>0) {
			int taille = 0, y;
			for(Component c : parent.getComponents()) taille+=c.getHeight()/(parent.getComponentCount()*2);
			int espace = (parent.getHeight()-taille)/(parent.getComponentCount());
			if(parent.getComponentCount()==1) y=espace/2;
			else y = espace/(parent.getComponentCount()-1);
			for(Component c : parent.getComponents()) {
				c.setBounds(lateralSpace, y, parent.getWidth()-lateralSpace*2, c.getPreferredSize().height);
				y+=espace;
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getMinimumSize();
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

}
