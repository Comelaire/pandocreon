/*
 * 
 */
package fr.utt.pandocreon.core.game.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.utt.pandocreon.core.util.Listenable;

/**
 * The Class TypedCardSet.
 */
public class TypedCardSet extends Listenable implements CardContainer {
	
	/** The cards. */
	private final Map<CardType, List<Card>> cards;
	
	/** The random. */
	private final Random random;
	
	/** The discard. */
	private final CardSet discard;

	/**
	 * Instantiates a new typed card set.
	 */
	public TypedCardSet() {
		cards = new HashMap<>();
		random = new Random();
		discard = new CardSet(null);
		for (final CardType type : CardType.values())
			cards.put(type, new ArrayList<>());
	}

	/**
	 * Instantiates a new typed card set.
	 *
	 * @param res
	 *            the res
	 */
	public TypedCardSet(ResourceResolver res) {
		this();
		for (final CardType type : CardType.values())
			cards.get(type).addAll(res.getCards(type));
	}

	/**
	 * Gets the discard.
	 *
	 * @return the discard
	 */
	public CardSet getDiscard() {
		return discard;
	}

	/**
	 * Gets the cards.
	 *
	 * @param type
	 *            the type
	 * @return the cards
	 */
	public List<Card> getCards(CardType type) {
		return cards.get(type);
	}

	/**
	 * Refill.
	 */
	public void refill() {
		for (final ActionCard c : discard.asList())
			cards.get(c.getType()).add(c);
		discard.asList().clear();
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
		if (!getCards(card.getType()).remove(card))
			throw new IllegalArgumentException(card + " isn't contained in " + this);
		container.accept(card);
		notify(CardListener.class, listener -> listener.onCardMovement(card, this, container, visible));
		return card;
	}

	/**
	 * Draw.
	 *
	 * @param type
	 *            the type
	 * @param container
	 *            the container
	 * @param visible
	 *            the visible
	 * @return the card
	 */
	public Card draw(CardType type, CardContainer container, boolean visible) {
		List<Card> cards = getCards(type);
		if (cards.isEmpty())
			refill();
		return draw(cards.get(random.nextInt(cards.size())), container, visible);
	}

	/**
	 * Draw.
	 *
	 * @param container
	 *            the container
	 * @param visible
	 *            the visible
	 * @param types
	 *            the types
	 * @return the card
	 */
	public Card draw(CardContainer container, boolean visible, CardType... types) {
		if (types.length == 0)
			throw new IllegalArgumentException("At least one card type must be specified");
		if (count(types) == 0)
			refill();
		List<Card> cards;
		do {
			cards = getCards(types[random.nextInt(types.length)]);
		} while(cards.isEmpty());
		Card card = cards.remove(random.nextInt(cards.size()));
		container.accept(card);
		notify(CardListener.class, listener -> listener.onCardMovement(card, this, container, visible));
		return card;
	}

	/**
	 * Draw action card.
	 *
	 * @param container
	 *            the container
	 * @param visible
	 *            the visible
	 * @return the card
	 */
	public Card drawActionCard(CardContainer container, boolean visible) {
		return draw(container, visible, CardType.ACTIONS);
	}

	/**
	 * Count.
	 *
	 * @param types
	 *            the types
	 * @return the int
	 */
	public int count(CardType... types) {
		int count = 0;
		for (final CardType type : types)
			count += getCards(type).size();
		return count;
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
		getCards(card.getType()).add(card);
		notify(CardListener.class, listener -> listener.onCardAdded(card, this));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Jeu de cartes";
	}

}
