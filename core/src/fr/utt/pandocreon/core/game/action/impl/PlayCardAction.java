/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import java.util.Map;
import java.util.Map.Entry;

import fr.utt.pandocreon.core.game.Origine;
import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardContainer;

/**
 * The Class PlayCardAction.
 */
public class PlayCardAction extends CardAction {
	
	/** The cost. */
	private Map<Origine, Integer> cost;

	/**
	 * Instantiates a new play card action.
	 *
	 * @param card
	 *            the card
	 * @param cost
	 *            the cost
	 */
	public PlayCardAction(ActionCard card, Map<Origine, Integer> cost) {
		super(card);
		this.cost = cost;
	}

	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public Map<Origine, Integer> getCost() {
		return cost;
	}

	/**
	 * Checks if is free.
	 *
	 * @return true, if is free
	 */
	public boolean isFree() {
		return cost.isEmpty();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#onCardUsed()
	 */
	@Override
	public void onCardUsed() {
		super.onCardUsed();
		for (final Entry<Origine, Integer> c : cost.entrySet())
			getPlayer().incrPoints(c.getKey(), -c.getValue());
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#getTarget()
	 */
	@Override
	public CardContainer getTarget() {
		return getGame().getTable();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.PLAY_CARD;
	}

}
