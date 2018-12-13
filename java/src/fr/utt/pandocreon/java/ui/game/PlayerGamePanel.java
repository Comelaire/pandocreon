/*
 * 
 */
package fr.utt.pandocreon.java.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.PlayerListener;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardListener;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;
import fr.utt.pandocreon.java.ui.Images;
import fr.utt.pandocreon.java.ui.Sound;
import fr.utt.pandocreon.java.ui.component.Panel;
import fr.utt.pandocreon.java.ui.component.PlayerPanel;
import fr.utt.pandocreon.java.ui.layout.CardsLayout;

/**
 * The Class PlayerGamePanel.
 */
public class PlayerGamePanel extends Panel implements PlayerListener, CardListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant FONT. */
	private static final Font FONT = new Font(Font.SERIF, Font.BOLD, 15);
	
	/** The points labels. */
	private final JLabel[] pointsLabels;
	
	/** The divinite. */
	protected final CardPanel divinite;
	
	/** The points. */
	protected final JPanel points;
	
	/** The player. */
	protected final Player player;
	
	/** The cards. */
	protected final JPanel cards;
	
	/** The name. */
	protected final JLabel name;
	
	/** The dead. */
	private boolean dead;

	/**
	 * Instantiates a new player game panel.
	 *
	 * @param player
	 *            the player
	 */
	public PlayerGamePanel(Player player) {
		this.player = player;

		name = new JLabel(player.getName(),
				new ImageIcon(PlayerPanel.ICONS[player.getType().ordinal()]), SwingConstants.CENTER);
		name.setFont(FONT);

		divinite = createCardPanel(player.getDivinite()).setCardVisible(true);

		points = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
		cards = new JPanel(new CardsLayout());
		points.setOpaque(false);
		cards.setOpaque(false);

		pointsLabels = new JLabel[Origine.ACTIONS.length];
		for (int i = 0; i < pointsLabels.length; i++) {
			JLabel label = new JLabel("", new ImageIcon(Images.getInstance().getImage(
					Origine.ACTIONS[i].toString().toLowerCase() + ".png")), JLabel.LEFT);
			label.setFont(FONT);
			points.add(pointsLabels[i] = label);
		}

		player.addPlayerListener(this);
		player.getCards().addCardListener(this);
		addParentComponents();
		updatePoints();
	}

	/**
	 * Adds the parent components.
	 */
	protected void addParentComponents() {
		JPanel north = new JPanel(new BorderLayout());
		north.setOpaque(false);

		north.add(name, BorderLayout.WEST);
		north.add(points, BorderLayout.CENTER);

		setLayout(new BorderLayout(10, 10));
		add(north, BorderLayout.NORTH);
		add(divinite, BorderLayout.WEST);
		add(cards, BorderLayout.CENTER);
	}

	/**
	 * Update points.
	 */
	public void updatePoints() {
		for (int i = 0; i < pointsLabels.length; i++) {
			int v = player.getPoints(i);
			pointsLabels[i].setText(String.valueOf(v));
			pointsLabels[i].setToolTipText(String.format("%d point%s %s",
					v, v > 1 ? "s" : "", Origine.ACTIONS[i].readable()));
		}
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Creates the card panel.
	 *
	 * @param card
	 *            the card
	 * @return the card panel
	 */
	public CardPanel createCardPanel(Card card) {
		return new CardPanel(card);
	}

	/**
	 * Gets the card panel.
	 *
	 * @param card
	 *            the card
	 * @return the card panel
	 */
	public CardPanel getCardPanel(Card card) {
		for (final Component c : cards.getComponents())
			if (c instanceof CardPanel && ((CardPanel) c).getCard() == card)
				return (CardPanel) c;
		throw new IllegalArgumentException(card + " cannot be found in " + player + " card panel");
	}

	/**
	 * Each card panel.
	 *
	 * @param consumer
	 *            the consumer
	 */
	public void eachCardPanel(Consumer<CardPanel> consumer) {
		for (final Component c : cards.getComponents())
			if (c instanceof CardPanel)
				consumer.accept((CardPanel) c);
	}

	/**
	 * Unselect.
	 */
	public void unselect() {
		setBackground(BACKGROUND);
	}

	/**
	 * Select.
	 */
	public void select() {
		setBackground(new Color(255, 255, 200, 200));
	}

	/**
	 * Sets the dead.
	 */
	public void setDead() {
		setVisible(false);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.java.ui.component.Panel#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (player.isDead() && !dead)
			setDead();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.PlayerListener#onDiviniteSet(fr.utt.pandocreon.core.game.Player, fr.utt.pandocreon.core.game.card.impl.DiviniteCard)
	 */
	@Override
	public void onDiviniteSet(Player player, DiviniteCard divinite) {
		this.divinite.setCard(divinite);
		revalidate();
		Sound.getInstance().play("card.wav");
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.PlayerListener#onPointsChange(fr.utt.pandocreon.core.game.Player, fr.utt.pandocreon.core.game.Origine, int, int)
	 */
	@Override
	public void onPointsChange(Player player, Origine origine, int oldPoints, int newPoints) {
		updatePoints();
		revalidate();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardMovement(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer, fr.utt.pandocreon.core.game.card.CardContainer, boolean)
	 */
	@Override
	public void onCardMovement(Card card, CardContainer source, CardContainer target, boolean visible) {
		if (source == player.getCards()) {
			cards.remove(getCardPanel(card));
			Sound.getInstance().play("card.wav");
			revalidate();
		}
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardListener#onCardAdded(fr.utt.pandocreon.core.game.card.Card, fr.utt.pandocreon.core.game.card.CardContainer)
	 */
	@Override
	public void onCardAdded(Card card, CardContainer target) {
		cards.add(createCardPanel(card));
		revalidate();
		Sound.getInstance().play("card.wav");
	}

}
