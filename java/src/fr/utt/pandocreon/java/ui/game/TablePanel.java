/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.function.Consumer;

import javax.swing.JPanel;

import fr.utt.pandocreon.core.game.Table;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardListener;
import fr.utt.pandocreon.java.ui.Sound;
import fr.utt.pandocreon.java.ui.layout.CardsLayout;

/**
 * The Class TablePanel.
 */
public class TablePanel extends JPanel implements CardListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant BOTTOM. */
	private static final Color TOP = new Color(90, 20, 10), BOTTOM = new Color(120, 70, 50);
	
	/** The table. */
	private final Table table;

	/**
	 * Instantiates a new table panel.
	 *
	 * @param table
	 *            the table
	 */
	public TablePanel(Table table) {
		super(new CardsLayout());
		this.table = table;
		setOpaque(false);
	}

	/**
	 * Gets the card panel.
	 *
	 * @param card
	 *            the card
	 * @return the card panel
	 */
	public CardPanel getCardPanel(Card card) {
		for (final Component c : getComponents())
			if (c instanceof CardPanel && ((CardPanel) c).getCard() == card)
				return (CardPanel) c;
		throw new IllegalArgumentException(card + " cannot be found in table panel");
	}

	/**
	 * Each card panel.
	 *
	 * @param consumer
	 *            the consumer
	 */
	public void eachCardPanel(Consumer<CardPanel> consumer) {
		for (final Component c : getComponents())
			if (c instanceof CardPanel)
				consumer.accept((CardPanel) c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g1d) {
		super.paintComponent(g1d);
		Graphics2D g = (Graphics2D) g1d;
		g.setClip(null);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setPaint(new GradientPaint(0, 0, TOP, 0, getHeight(), BOTTOM));
		int incl = getHeight()/8;
		int ep = getHeight()/13;
		Polygon p = new Polygon(
				new int[] {0, getWidth(), getWidth() + incl, -incl},
				new int[] {-ep, -ep, getHeight() + ep, getHeight() + ep},
				4);
		g.fill(p);
		g.setColor(Color.GRAY);
		g.draw(p);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardMovement(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer, fr.utt.pandocreon.core.game.card.CardContainer, boolean)
	 */
	@Override
	public void onCardMovement(Card card, CardContainer source, CardContainer target, boolean visible) {
		if (source == table) {
			remove(getCardPanel(card));
			Sound.getInstance().play("card.wav");
			revalidate();
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardAdded(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer)
	 */
	@Override
	public void onCardAdded(Card card, CardContainer target) {
		add(new CardPanel(card).setCardVisible(true));
		validate();
		Sound.getInstance().play("card.wav");
	}

}
