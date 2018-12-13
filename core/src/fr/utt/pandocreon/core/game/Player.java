/*
 * 
 */
package fr.utt.pandocreon.core.game;

import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardSet;
import fr.utt.pandocreon.core.game.card.CardType;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;
import fr.utt.pandocreon.core.game.card.impl.GuideSpirituelCard;
import fr.utt.pandocreon.core.util.Listenable;

/**
 * The Class Player.
 */
public class Player extends Listenable implements CardContainer {
	
	/** The type. */
	private final PlayerType type;
	
	/** The cards. */
	private final CardSet cards;
	
	/** The points. */
	private final int[] points;
	
	/** The divinite. */
	private DiviniteCard divinite;
	
	/** The is dead. */
	private boolean isDead;
	
	/** The name. */
	private String name;
	
	/** The game. */
	private Game game;

	/**
	 * Instantiates a new player.
	 *
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 */
	public Player(String name, PlayerType type) {
		this.name = name;
		this.type = type;
		cards = new CardSet(this);
		points = new int[Origine.ACTIONS.length];
	}

	/**
	 * Gets the prayer count.
	 *
	 * @return the prayer count
	 */
	public int getPrayerCount() {
		int count = 0;
		for (final Card c : cards.asList())
			if (c instanceof GuideSpirituelCard)
				count += ((GuideSpirituelCard) c).getPrayerCount();
		return count;
	}

	/**
	 * Checks if is playing.
	 *
	 * @return true, if is playing
	 */
	public boolean isPlaying() {
		return getGame().getStep().getFinalPlayer() == this;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Sets the game.
	 *
	 * @param game
	 *            the new game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Checks if is dead.
	 *
	 * @return true, if is dead
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * Sets the dead.
	 */
	public void setDead() {
		isDead = true;
		while (!cards.asList().isEmpty()) {
			ActionCard c = cards.asList().get(0);
			cards.draw(c, c.getType() == CardType.CROYANTS ? game.getTable() :
				game.getCards().getDiscard(), true);
		}
	}

	/**
	 * Adds the player listener.
	 *
	 * @param l
	 *            the l
	 */
	public void addPlayerListener(PlayerListener l) {
		addListener(PlayerListener.class, l);
	}

	/**
	 * Removes the player listener.
	 *
	 * @param l
	 *            the l
	 */
	public void removePlayerListener(PlayerListener l) {
		removeListener(PlayerListener.class, l);
	}

	/**
	 * On dice launch.
	 *
	 * @param origine
	 *            the origine
	 */
	public void onDiceLaunch(Origine origine) {
		int points = origine.getPointsFor(divinite.getOrigine());
		if (points != 0)
			incrPoints(origine, points);
	}

	/**
	 * Gets the points.
	 *
	 * @param origine
	 *            the origine
	 * @return the points
	 */
	public int getPoints(int origine) {
		return points[origine];
	}

	/**
	 * Gets the points.
	 *
	 * @param origine
	 *            the origine
	 * @return the points
	 */
	public int getPoints(Origine origine) {
		return getPoints(origine.getIndex());
	}
	
	/**
	 * Gets the all points.
	 *
	 * @return the all points
	 */
	public int getAllPoints(){
		int points=0;
		for (Origine o : Origine.ACTIONS) {
			points+=getPoints(o);
		}
		return points;
	}

	/**
	 * Sets the points.
	 *
	 * @param origine
	 *            the origine
	 * @param points
	 *            the points
	 */
	public void setPoints(Origine origine, int points) {
		int pts = getPoints(origine);
		this.points[origine.getIndex()] = points;
		notify(PlayerListener.class, l -> l.onPointsChange(this, origine, pts, getPoints(origine)));
	}

	/**
	 * Incr points.
	 *
	 * @param origine
	 *            the origine
	 * @param incr
	 *            the incr
	 */
	public void incrPoints(Origine origine, int incr) {
		setPoints(origine, getPoints(origine) + incr);
	}

	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public CardSet getCards() {
		return cards;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public PlayerType getType() {
		return type;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the divinite.
	 *
	 * @return the divinite
	 */
	public DiviniteCard getDivinite() {
		return divinite;
	}

	/**
	 * Sets the divinite.
	 *
	 * @param divinite
	 *            the new divinite
	 */
	public void setDivinite(DiviniteCard divinite) {
		if (this.divinite != null)
			throw new IllegalStateException(this + " already owns a divinity card: " + this.divinite);
		this.divinite = divinite;
		divinite.setOwner(this);
		notify(PlayerListener.class, l -> l.onDiviniteSet(this, divinite));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardContainer#accept(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void accept(Card card) {
		if (card instanceof DiviniteCard)
			setDivinite((DiviniteCard) card);
		else throw new IllegalArgumentException("Player can only accept divinite card");
	}


	/**
	 * The Enum PlayerType.
	 */
	public enum PlayerType {
		
		/** The human. */
		HUMAN, 
 /** The bot. */
 BOT, 
 /** The network. */
 NETWORK;
	}

}
