/*
 * 
 */
package fr.utt.pandocreon.core.game.action.impl;

import fr.utt.pandocreon.core.game.action.ActionType;
import fr.utt.pandocreon.core.game.action.GameAction;
import fr.utt.pandocreon.core.game.card.impl.DiviniteCard;

/**
 * The Class UseEffectAction.
 */
public class UseEffectAction extends GameAction {
	
	/** The card. */
	private DiviniteCard card;

	/**
	 * Instantiates a new use effect action.
	 *
	 * @param card
	 *            the card
	 */
	public UseEffectAction(DiviniteCard card) {
		this.card = card;
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#performAction()
	 */
	@Override
	public void performAction() {
		card.doEffect();
	}

	/* (non-Javadoc)
	 * @see fr.utt.pandocreon.core.game.action.GameAction#getType()
	 */
	@Override
	public ActionType getType() {
		return ActionType.USE_EFFECT;
	}

}
