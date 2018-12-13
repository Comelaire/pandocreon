/*
 * 
 */
package fr.utt.pandocreon.core.game.card.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import fr.utt.pandocreon.core.game.Player;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.action.impl.CardAction;
import fr.utt.pandocreon.core.game.action.impl.SacrificeCardAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class GuideSpirituelCard.
 */
public class GuideSpirituelCard extends ActionCard implements CardContainer {
	
	/** The croyants. */
	private List<CroyantsCard> croyants;

	/**
	 * Instantiates a new guide spirituel card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public GuideSpirituelCard(Map<String, String> attributes) {
		super(attributes);
		croyants = new ArrayList<>(getInitialCroyantsCapacity());
	}

	/**
	 * Adds the.
	 *
	 * @param croyant
	 *            the croyant
	 * @return true, if successful
	 */
	public boolean add(CroyantsCard croyant) {
		if (accept(croyant) && croyants.add(croyant)) {
			croyant.setGuide(this);
			return true;
		}
		return false;
	}

	/**
	 * Gets the initial croyants capacity.
	 *
	 * @return the initial croyants capacity
	 */
	public int getInitialCroyantsCapacity() {
		return Integer.valueOf(get("capacity", "1"));
	}

	/**
	 * Gets the croyants capacity.
	 *
	 * @return the croyants capacity
	 */
	public int getCroyantsCapacity() {
		return getInitialCroyantsCapacity() - croyants.size();
	}

	/**
	 * Accept.
	 *
	 * @param card
	 *            the card
	 * @return true, if successful
	 */
	public boolean accept(CroyantsCard card) {
		return getCroyantsCapacity() > 0 && hasCommonDogme(card);
	}

	/**
	 * Removes the.
	 *
	 * @param croyant
	 *            the croyant
	 */
	public void remove(CroyantsCard croyant) {
		if (!croyants.remove(croyant))
			throw new IllegalArgumentException(croyant + " isn't contained in " + this);
		if (croyants.isEmpty())
			getOwner().getCards().draw(this, getOwner().getGame().getCards().getDiscard(), true);
	}

	/**
	 * Gets the prayer count.
	 *
	 * @return the prayer count
	 */
	public int getPrayerCount() {
		int count = 0;
		for (final CroyantsCard c : croyants)
			count += c.getCroyantsCount();
		return count;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#onCardUsed(fr.utt.pandocreon.core.game.action.impl.CardAction)
	 */
	@Override
	public void onCardUsed(CardAction action) {
		super.onCardUsed(action);
		for (final CroyantsCard croyant : croyants) {
			croyant.setGuide(null);
			croyant.getOwner().getCards().draw(croyant, getOwner().getGame().getTable(), true);
		}
		croyants.clear();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#addPossibleActions(fr.utt.pandocreon.core.game.Player, boolean, java.util.function.Consumer)
	 */
	@Override
	public void addPossibleActions(Player player, boolean playerTurn, Consumer<GameAction> consumer) {
		if (playerTurn)
			consumer.accept(new SacrificeCardAction(this));
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#getType()
	 */
	@Override
	public CardType getType() {
		return CardType.GUIDE_SPIRITUEL;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.CardContainer#accept(fr.utt.pandocreon.core.game.card.Card)
	 */
	@Override
	public void accept(Card card) {
		if (!(card instanceof CroyantsCard))
			throw new IllegalArgumentException(this + " cannot accept " + card);
		if (!add((CroyantsCard) card))
			throw new IllegalStateException(this + " already contains " + croyants.size() + " croyants");
	}

}
