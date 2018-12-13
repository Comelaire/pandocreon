/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardContainer;

/**
 * The Class SacrificeCardAction.
 */
public class SacrificeCardAction extends CardAction {

	/**
	 * Instantiates a new sacrifice card action.
	 *
	 * @param card
	 *            the card
	 */
	public SacrificeCardAction(ActionCard card) {
		super(card);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.impl.CardAction#onCardUsed()
	 */
	@Override
	public void onCardUsed() {
		super.onCardUsed();
		card.onSacrifice();
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
		return ActionType.SACRIFICE_CARD;
	}

}
