/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardContainer;

/**
 * The Class DiscardCardAction.
 */
public class DiscardCardAction extends CardAction {

	/**
	 * Instantiates a new discard card action.
	 *
	 * @param card
	 *            the card
	 */
	public DiscardCardAction(ActionCard card) {
		super(card);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#getTarget()
	 */
	@Override
	public CardContainer getTarget() {
		return getGame().getCards().getDiscard();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.DISCARD_CARD;
	}

}
