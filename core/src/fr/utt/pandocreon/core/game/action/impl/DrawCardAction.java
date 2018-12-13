/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.card.Card;
import fr.utt.pandocreon.core.game.card.CardContainer;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class DrawCardAction.
 */
public class DrawCardAction extends GameAction {
	
	/** The card type. */
	private CardType cardType;
	
	/** The card. */
	private Card card;

	/**
	 * Instantiates a new draw card action.
	 *
	 * @param cardType
	 *            the card type
	 */
	public DrawCardAction(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#performAction()
	 */
	@Override
	public void performAction() {
		CardContainer container;
		if (cardType == CardType.DIVINITE)
			container = getPlayer();
		else throw new IllegalAccessError("Not implemented");
		card = getGame().getCards().draw(cardType, container, true);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.DRAW_CARD;
	}

}
