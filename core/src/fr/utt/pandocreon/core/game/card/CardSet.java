/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.util.Listenable;

/**
 * The Class CardSet.
 */
public class CardSet extends Listenable implements CardContainer {
	
	/** The cards. */
	private final List<ActionCard> cards;
	
	/** The random. */
	private final Random random;
	
	/** The owner. */
	private final Player owner;

	/**
	 * Instantiates a new card set.
	 *
	 * @param owner
	 *            the owner
	 */
	public CardSet(Player owner) {
		this.owner = owner;
		cards = new ArrayList<>();
		random = new Random();
	}

	/**
	 * As list.
	 *
	 * @return the list
	 */
	public List<ActionCard> asList() {
		return cards;
	}

	/**
	 * Draw.
	 *
	 * @param container
	 *            the container
	 * @param visible
	 *            the visible
	 * @return the card
	 */
	public Card draw(CardContainer container, boolean visible) {
		return draw(cards.get(random.nextInt(cards.size())), container, visible);
	}

	/**
	 * Draw.
	 *
	 * @param card
	 *            the card
	 * @param container
	 *            the container
	 * @param visible
	 *            the visible
	 * @return the card
	 */
	public Card draw(Card card, CardContainer container, boolean visible) {
		if (!cards.remove(card))
			throw new IllegalArgumentException(card + " (" + card.getOwner() +
					") isn't contained in " + this);
		notify(CardListener.class, listener -> listener.onCardMovement(card, this, container, visible));
		container.accept(card);
		return card;
	}

	/**
	 * Adds the card listener.
	 *
	 * @param l
	 *            the l
	 */
	public void addCardListener(CardListener l) {
		addListener(CardListener.class, l);
	}

	/**
	 * Removes the card listener.
	 *
	 * @param l
	 *            the l
	 */
	public void removeCardListener(CardListener l) {
		removeListener(CardListener.class, l);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardContainer#accept(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void accept(Card card) {
		if (!(card instanceof ActionCard))
			throw new IllegalArgumentException(card + " isn't of ActionCard type and can't be accepted");
		cards.add((ActionCard) card);
		if (owner != null)
			card.setOwner(owner);
		notify(CardListener.class, listener -> listener.onCardAdded(card, this));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return owner == null ? "Défausse" : "Cartes de " + owner;
	}

}
