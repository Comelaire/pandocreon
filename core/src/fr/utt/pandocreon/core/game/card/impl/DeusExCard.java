/*
 * 
 */
package fr.utt.pandocreon.core.game.card.impl;

import java.util.Map;

import fr.utt.pandocreon.core.game.action.impl.CardAction;
import fr.utt.pandocreon.core.game.card.ActionCard;
import fr.utt.pandocreon.core.game.card.CardType;

/**
 * The Class DeusExCard.
 */
public class DeusExCard extends ActionCard {

	/**
	 * Instantiates a new deus ex card.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public DeusExCard(Map<String, String> attributes) {
		super(attributes);
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.ActionCard#onCardUsed(fr.utt.pandocreon.core.game.action.impl.CardAction)
	 */
	@Override
	public void onCardUsed(CardAction action) {
		super.onCardUsed(action);
		doEffect();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.card.Card#getType()
	 */
	@Override
	public CardType getType() {
		return CardType.DEUS_EX;
	}

}
