/*
 * 
 */
package fr.utt.pandocreon.java.ui.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import fr.utt.pandocreon.core.game.Game;
import fr.utt.pandocreon.core.game.Player.PlayerType;
import fr.utt.pandocreon.java.ui.game.PlayerGamePanel;
import fr.utt.pandocreon.java.ui.game.TablePanel;

/**
 * The Class PlayersLayout.
 */
public class PlayersLayout implements LayoutManager {
	
	/** The max. */
	private final int max;

	/**
	 * Instantiates a new players layout.
	 *
	 * @param game
	 *            the game
	 */
	public PlayersLayout(Game game) {
		this((int) game.getAllPlayers().stream().filter(
				p -> p.getType() != PlayerType.HUMAN).count());
	}

	/**
	 * Instantiates a new players layout.
	 *
	 * @param max
	 *            the max
	 */
	public PlayersLayout(int max) {
		this.max = max;
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
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
		return new Dimension(600, 500);
	}

	/* (non-Javadoc)
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		int player = -1;
		for (final Component c : parent.getComponents())
			if (c instanceof PlayerGamePanel)
				layout(++player, (PlayerGamePanel) c, parent);
			else if (c instanceof TablePanel)
				layout(c, parent);
	}

	/**
	 * Layout.
	 *
	 * @param c
	 *            the c
	 * @param parent
	 *            the parent
	 */
	private void layout(Component c, Container parent) {
		int w = parent.getWidth()/(max + 1);
		int h = (int) (parent.getHeight()/2.5f);
		c.setBounds((parent.getWidth() - w)/2, parent.getHeight() - h, w, h);
	}

	/**
	 * Layout.
	 *
	 * @param i
	 *            the i
	 * @param c
	 *            the c
	 * @param parent
	 *            the parent
	 */
	private void layout(int i, PlayerGamePanel c, Container parent) {
		int w = parent.getWidth()/max;
		c.setBounds(i * w + 3,
				(int) (Math.tan(Math.abs(i + .5f - max/2f)/max) * parent.getHeight()/2),
				w - 6, Math.max(10, parent.getHeight()/2));
	}

}
